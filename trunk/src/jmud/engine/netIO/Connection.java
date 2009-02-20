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

import jmud.engine.account.Account;
import jmud.engine.character.PlayerCharacter;
import jmud.engine.core.JMudStatics;
import jmud.engine.job.definitions.ConnectionStateJob;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.UUID;

/**
 * The Connection class represents a single connection to/from a user. The
 * Connection class is the single, high level point of access for sending data
 * to a user. This abstraction of functionality away from the ConnectionManager
 * simplifies use of the netIO package.
 * 
 * @author David Loman
 * @version 0.1
 */
public class Connection {
	// private int accountID = 0;
	private UUID connID = null;
	// private String uName = "";
	// private String passWd = "";
	// private int loginAttempts = 0;
	private final SocketChannel socketChannel;
	private ConnectionState connState;
	private ConnectionManager connMan;
	private CommandBuffer cmdBuf;

	private PlayerCharacter pc;
	private Account account;

	/**
	 * Explicit constructor.
	 * 
	 * @param inSc
	 *            SocketChannel used for communications
	 * @param name
	 *            Name to identify this object by.
	 */
	public Connection(ConnectionManager connMan, final SocketChannel inSc) {
		this.socketChannel = inSc;
		this.connMan = connMan;
		this.connState = ConnectionState.DISCONNECTED;
		this.connID = connMan.getNewConnectionID();
		this.cmdBuf = new CommandBuffer();
		this.pc = null;
		this.account = null;
	}

	/**
	 * Disconnect
	 * 
	 * @return true if the disconnect succeeded
	 * @throws java.io.IOException
	 */
	public boolean disconnect() {
		this.connMan.disconnect(this);
		try {
			socketChannel.close();
		} catch (IOException e) {
			System.err.println("ConnectionManager.disconnect(SocketChannel): Failed to close socket connection.");
			e.printStackTrace();
		}
		return socketChannel.isConnected();
	}

	/*
	 * Data IO
	 */
	public void handleInputFromClient() {

		// Copy the data over into the commandBuffer and parse it into commands.
		try {
			this.cmdBuf.write(this.socketChannel);
			this.cmdBuf.parseBuffer();
		} catch (ClosedChannelException e) {
			this.disconnect();
		}

		System.err.println(this.cmdBuf.toString());
		
		// If a valid command exists, then route it and generate a job.
		if (this.cmdBuf.hasNextCommand() == false) {
			return;
		}

		// Determine Action appropriate for the current ConnectionState
		ConnectionStateJob j = new ConnectionStateJob(this);
		j.selfSubmit();
	}

	/**
	 * Send multiple CRLFs to the client.
	 */
	public void sendCRLFs(int numberOfCRLFs) {
		StringBuilder crlfs = new StringBuilder();
		for (int i = 0; i < numberOfCRLFs; ++i) {
			crlfs.append(JMudStatics.CRLF);
		}
		this.sendText(crlfs.toString());
	}

	/**
	 * Send a CRLF to the client.
	 */
	public void sendCRLF() {
		this.sendText(JMudStatics.CRLF);
	}

	/**
	 * Send the text, with a CRLF, to the client.
	 * 
	 * @param textToSend
	 */
	public void sendTextLn(String textToSend) {
		this.sendText(textToSend + JMudStatics.CRLF);
	}

	/**
	 * Send the text, without a CRLF, to the client.
	 * 
	 * @param text
	 */
	public void sendText(String text) {
		// Attach the SocketChannel and send the text on its way!
		this.connMan.send(this.socketChannel, text);
	}

	public void sendPrompt() {
		this.sendCRLF();
		this.sendText("jMUD:");
	}

	/*
	 * Getters n Setters
	 */
	/**
	 * @return the Connection State associated with this Connection
	 */
	public ConnectionState getConnState() {
		return this.connState;
	}

	/**
	 * Set this Connection object's Connection State.
	 * 
	 * @param newState
	 */
	public void changeConnState(ConnectionState newState) {
		ConnectionState oldState = this.connState;

		if (oldState == newState) {
			// There is no change in state

		} else {
			// DEBUG ONLY
			// this.sendText("\n(Changed ConnectionState from " +
			// oldState.toString() + " to "
			// + newState.toString() + " : ");

			oldState.runExitJob(this, newState);
			this.connState = newState;
			newState.runEnterJob(this, oldState);
		}
	}

	/**
	 * @return this Connection's UUID.
	 */
	public UUID getConnectionID() {
		return this.connID;
	}

	/**
	 * @return this Connection's ConnectionManager reference.
	 */
	public ConnectionManager getConnectionManager() {
		return this.connMan;
	}

	/**
	 * @return the SocketChannel reference associated with this Connection.
	 */
	public SocketChannel getSocketChannel() {
		return this.socketChannel;
	}

	/**
	 * @return the CommandBuffer reference associated with this Connection.
	 */
	public CommandBuffer getCmdBuffer() {
		return cmdBuf;
	}

	/**
	 * @return the Player object reference associated with this Connection.
	 */
	public PlayerCharacter getPc() {
		return pc;
	}

	/**
	 * @param pc
	 *            the Player object reference to associate with this Connection.
	 */
	public void setPc(PlayerCharacter pc) {
		this.pc = pc;
	}

	/**
	 * @return whether or not there is a PlayerCharacter object reference
	 *         associated with this Connection.
	 */
	public boolean hasPc() {
		return (this.pc != null);
	}

	/**
	 * @return the Account object reference associated with this Connection
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the Account object reference to associate with this
	 *            Connection.
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return whether or not there is an Account object reference associated
	 *         with this Connection.
	 */
	public boolean hasAccount() {
		return (this.account != null);
	}

}
