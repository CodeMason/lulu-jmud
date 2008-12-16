package jmud.engine.netIO;

public enum ConnectionState{
    NotConnected, ConnectedButNotLoggedIn, LoggedInToCharacterSelect, LoggedInToNewCharacter, LoggedInToGameServer
}
