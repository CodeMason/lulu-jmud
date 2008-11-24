package jmud.core;

/**
 * Represents the state of a login process
 *
 * Created on May 2, 2002, 8:23 PM
 *
 * ToDo: consider tracking the login attempts per username; I always thankful when I've tried to login
 * several times with the wrong user name and it doesn't kick me out when I start trying with the right
 * username
 */
public class Login {

    // how many times the user can screw up their login
    public static int MAX_FAILS = 3;

    public enum LoginState{
        LOGIN,
        PASSWORD
    }

    private LoginState state;
    private int failedLoginAttempts;
    private StringBuffer login;
    private StringBuffer password;

    public Login() {
        login = new StringBuffer();
        password = new StringBuffer();
    }

    /**
     * Report that a login attempt has failed
     */
    public void setLoginFailed() {

        // increment the number of failed login attempts
        failedLoginAttempts++;

        // clear the login and password they tried
        login.delete(0, login.length());
        password.delete(0, password.length());

        state = LoginState.LOGIN;
    }

    /**
     * Check if the user has failed their login for the "maximumth" time
     *
     * @return true if the user has failed too many times, false if not
     */
    public boolean checkMaxFailedLogins() {

        // I use >= just in case they've managed to fail more than the max number
        // of times
        return failedLoginAttempts >= MAX_FAILS;
    }

    /**
     * Store the current user name that the user is trying
     */
    public void saveLogin(String strLogin) {
        this.login = new StringBuffer(strLogin);
    }

    /**
     * Return the current user name that the user has entered
     *
     * @return StringBuffer containing the user name entered by the user
     */
    public StringBuffer getLogin() {
        return login;
    }

    /**
     * Store the current password that the user has entered
     */
    public void savePassword(String password) {
        this.password = new StringBuffer(password);
    }

    /**
     * Return the current password that the user has entered
     *
     * @return StringBuffer containing the password entered by the user
     */
    public StringBuffer getPassword() {
        return password;
    }

    /**
     * Set the state of the login process (i.e. Login or Password). That is,
     * store what step the user is at: are they entering their username or their
     * password?
     *
     * @param state Should be Login.LOGIN for "Login" and Login.PASSWORD for "Password"
     */
    public void setState(LoginState state) {
        this.state = state;
    }

    /**
     * Return the login state number
     *
     * @return Number representing the login state
     */
    public LoginState getState() {
        return state;
    }

    // return the current unfinished part of whatever string we're currently
    // working on:
    // e.g. if we are currently working on login, then pass back whatever we
    //      have stored in login so far
    public StringBuffer getCurrentStateString() {
        if(LoginState.LOGIN.equals(state)) {
            return login;
        } else {
            return password;
        }
    }

}