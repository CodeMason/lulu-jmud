package jmud.engine.core;

public class Login {

   public enum LoginState {
      LOGIN, PASSWORD
   }

   /**
    * Maximum allowable login failures.
    */
   public static final int MAX_FAILED_LOGINS = 3;

   private int failedLogins;
   private StringBuffer login;
   private StringBuffer password;
   private LoginState loginState;

   /**
    * Default constructor.
    */
   public Login() {
      login = new StringBuffer();
      password = new StringBuffer();
   }

   /**
    * Check if the user has failed their login for the "maximumth" time.
    * @return true if the user has failed too many times, false if not
    */
   public final boolean isLoginFailureLimitReached() {

      return failedLogins >= MAX_FAILED_LOGINS;
   }

   /**
    * @return the current unfinished portion of the login attempt
    */
   public final StringBuffer getCurrentStateString() {
      if (LoginState.LOGIN.equals(loginState)) {
         return login;
      } else {
         return password;
      }
   }

   /**
    * Return the current user name that the user has entered.
    * @return StringBuffer containing the user name entered by the user
    */
   public final StringBuffer getLogin() {
      return login;
   }

   /**
    * Return the current password that the user has entered.
    * @return StringBuffer containing the password entered by the user
    */
   public final StringBuffer getPassword() {
      return password;
   }

   /**
    * Return the login state number.
    * @return Number representing the login state
    */
   public final LoginState getLoginState() {
      return loginState;
   }

   /**
    * Store the current username that the user is trying.
    * @param strLogin
    *           the supplied username
    */
   public final void setLogin(final String strLogin) {
      this.login = new StringBuffer(strLogin);
   }

   /**
    * Store the current password that the user has entered.
    * @param inPassword
    *           the supplied password
    */
   public final void savePassword(final String inPassword) {
      this.password = new StringBuffer(inPassword);
   }

   /**
    * Report that a login attempt has failed.
    */
   public final void setLoginFailed() {

      // increment the number of failed login attempts
      failedLogins++;

       clearLoginAndPassword();

      loginState = LoginState.LOGIN;
   }

    private void clearLoginAndPassword() {
        login.delete(0, login.length());
        password.delete(0, password.length());
    }

    /**
    * Set the state of the login process (i.e. Login or Password). That is,
    * store what step the user is at: are they entering their username or their
    * password?
    * @param inState
    *           Should be Login.LOGIN for "Login" and Login.PASSWORD for
    *           "Password"
    */
   public final void setLoginState(final LoginState inState) {
      this.loginState = inState;
   }

}
