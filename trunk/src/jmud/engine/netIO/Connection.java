/**
 * This file is part of Lulu's JMud.
 *
 *  Lulu's JMud is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Lulu's JMud is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Lulu's JMud.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmud.engine.netIO;

import jmud.engine.core.JMudStatics;
import jmud.engine.job.definitions.SplashScreenJob;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * The Connection class represents a single connection to/from a user. The
 * Connection class is the single, high level point of access for sending data
 * to a user. This abstraction of functionality away from the ConnectionManager
 * simplifies use of the netIO package.
 *
 * @author David Loman
 * @version 0.1
 */
public class Connection{
    private int accountID = 0;
    private String uName = "";
    private String passWd = "";
    private int loginAttempts = 0;
    private LoginState loginstate = LoginState.Neither;
    private final String name;
    private final SocketChannel socketChannel;
    private ByteBuffer readBuffer = ByteBuffer.allocate(JMudStatics.CONNECTION_READ_BUFFER_SIZE);
    private StringBuilder receivedText;
    private ConnectionState connState = ConnectionState.DISCONNECTED;

    /**
     * Explicit constructor.
     *
     * @param inSc SocketChannel used for communications
     * @param name Name to identify this object by.
     */
    public Connection(final SocketChannel inSc, final String name){
        this.socketChannel = inSc;
        this.connState = ConnectionState.DISCONNECTED;
        this.name = name;
        this.receivedText = new StringBuilder();
    }

    /**
     * @return the AccountID associated with this Connection.
     */
    public int getAccountID(){
        return accountID;
    }

    /**
     * Set the AccountID associated with this Connection
     *
     * @param accountID
     */
    public void setAccountID(int accountID){
        this.accountID = accountID;
    }

    /**
     * @return the Connection State associated with this Connection
     */
    public ConnectionState getConnState(){
        return connState;
    }

    /**
     * Set this Connection object's Connection State.
     *
     * @param connState
     */
    public void setConnState(ConnectionState connState){
        this.connState = connState;
    }

    /**
     * @return this Connection object's name.
     */
    public String getName(){
        return name;
    }

    /**
     * @return the username associated with this Connection.
     */
    public String getUName(){
        return uName;
    }

    /**
     * Set the username associated with this Connection.
     *
     * @param uName
     */
    public void setUName(String uName){
        this.uName = uName;
    }

    /**
     * @return the password associated with this Connection.
     */
    public String getPassWd(){
        return passWd;
    }

    /**
     * Set the password associated with this Connection
     *
     * @param passWd
     */
    public void setPassWd(String passWd){
        this.passWd = passWd;
    }

    /**
     * @return the number of Login attempts for this connection.
     */
    public int getLoginAttempts(){
        return loginAttempts;
    }

    /**
     * Reset this Connection object's logAttempts;
     */
    public void resetLoginAttempts(){
        this.loginAttempts = 0;
    }

    /**
     * Increment this Connection object's logAttempts;
     */
    public void incrementLoginAttempts(){
        ++this.loginAttempts;
    }

    /**
     * @return the Login State associated with this Connection
     */
    public LoginState getLoginstate(){
        return loginstate;
    }

    /**
     * Set this Connection object's Login State
     *
     * @param loginstate
     */
    public void setLoginstate(LoginState loginstate){
        this.loginstate = loginstate;
    }

    /**
     * Send the text, with a CRLF, to the client.
     *
     * @param textToSend
     */
    public void sendTextLn(String textToSend){
        // Attach the SocketChannel and send the text on its way!
        this.sendText(textToSend + JMudStatics.CRLF);
    }

    /**
     * Send multiple CRLFs to the client.
     */
    public void sendCRLFs(int numberOfCRLFs){
        StringBuilder crlfs = new StringBuilder();
        for(int i = 0; i < numberOfCRLFs; ++i){
            crlfs.append(JMudStatics.CRLF);
        }
        this.sendText(crlfs.toString());
    }

    /**
     * Send a CRLF to the client.
     */
    public void sendCRLF(){
        this.sendText(JMudStatics.CRLF);
    }

    /**
     * Send the text, without a CRLF, to the client.
     *
     * @param text
     */
    public void sendText(String text){
        // Attach the SocketChannel and send the text on its way!
        ConnectionManager.getLazyLoadedInstance().send(this.socketChannel, text);
    }

    public void sendSplashScreen(){
        JMudStatics.getDefaultJobManager().pushJobToQueue(new SplashScreenJob(this));
    }

    public boolean isConnectionLost() throws IOException{
        return socketChannel.read(readBuffer) < 0;
    }

    public boolean isCommandComplete(){
        storeAndClearReadBuffer();
        return receivedText.toString().contains(JMudStatics.CRLF);
    }

    public String getAndClearCommand(){
        String data;
        storeAndClearReadBuffer();
        data = receivedText.toString().replace(JMudStatics.CRLF, "");
        receivedText = new StringBuilder();
        return data;
    }

    private void storeAndClearReadBuffer(){
        Charset cs = Charset.forName("ISO-8859-1");
        CharsetDecoder dec = cs.newDecoder();

        try{
            readBuffer.flip();
            CharBuffer cb = dec.decode(readBuffer);
            receivedText.append(cb.toString());
        } catch(CharacterCodingException e){
            System.err.println("Connection: CharacterCodingException in ByteBuffer of: " + socketChannel.socket().getInetAddress().toString());
            e.printStackTrace();
        } catch(Exception e){
            System.err.println("Connection: Exception in ByteBuffer of: " + socketChannel.socket().getInetAddress().toString());
            e.printStackTrace();
        }
    }


    /**
     * Disconnect
     *
     * @return true if the disconnect succeeded
     * @throws java.io.IOException
     */
    public boolean disconnect(){
        ConnectionManager.getLazyLoadedInstance().disconnectFrom(this);
        try{
            socketChannel.close();
        } catch(IOException e){
            System.err.println("ConnectionManager.disconnect(SocketChannel): Failed to close socket connection.");
            e.printStackTrace();
        }
        return socketChannel.isConnected();
    }
}
