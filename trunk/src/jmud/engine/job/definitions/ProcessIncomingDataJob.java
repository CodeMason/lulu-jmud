package jmud.engine.job.definitions;

import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;

public class ProcessIncomingDataJob extends AbstractJob {

	private Connection connection = null;

	public ProcessIncomingDataJob(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.connection) {
            ConnectionState connectionState = connection.getConnState();
            String data;

            if(!connection.isCommandComplete()){
                return false;
            }

            data = connection.getAndClearCommand();

            return createAndRegisterAppropriateCommand(connectionState, data);
		}
	}

    private boolean createAndRegisterAppropriateCommand(ConnectionState connectionState, String data){
        if (connectionState == ConnectionState.NotConnected) {
            // Shouldn't be here!
            System.err.println("An attempt was made to processIncoming() while ConnectionState == NotConnected");
            return false;

        } else if (connectionState == ConnectionState.ConnectedButNotLoggedIn) {
            LoginValidateJob hlj = new LoginValidateJob(this.connection, data);
            hlj.submitSelf();

        } else if (connectionState == ConnectionState.LoggedInToCharacterSelect) {
            CharacterSelectJob csj = new CharacterSelectJob(this.connection, data);
            csj.submitSelf();

        } else if (connectionState == ConnectionState.LoggedInToNewCharacter) {
            NewCharacterJob ncj = new NewCharacterJob(this.connection, data);
            ncj.submitSelf();

        } else if (connectionState == ConnectionState.LoggedInToGameServer) {

            BuildCmdFromStringJob job = new BuildCmdFromStringJob(this.connection, data);
            job.submitSelf();

        } else {
            // Shouldn't be here!
            System.err .println("An attempt was made to processIncoming() while ConnectionState was in an unknown state.");
            return false;
        }
        return true;
    }
}
