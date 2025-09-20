package guiUserLogin;

import database.Database;
import entityClasses.User;
import javafx.stage.Stage;
public class ControllerUserLogin {
	
	/*-********************************************************************************************

	The User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/


	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	private static Stage theStage;	
	
	/**********
	 * <p> Method: public doLogin() </p>
	 * 
	 * <p> Description: This method is called when the user has clicked on the Login button. This
	 * method checks the username and password to see if they are valid.  If so, it then logs that
	 * user in my determining which role to use.
	 * 
	 * The method reaches batch to the view page and to fetch the information needed rather than
	 * passing that information as parameters.
	 * 
	 */	
	public static String passwordErrorMessage = "";		// The error message text
	public static String passwordInput = "";			// The input being processed
	public static int passwordIndexofError = -1;		// The index where the error was located
	public static boolean foundUpperCase = false;
	public static boolean foundLowerCase = false;
	public static boolean foundNumericDigit = false;
	public static boolean foundSpecialChar = false;
	public static boolean foundLongEnough = false;
	private static String inputLine1 = "";				// The input line
	private static char currentChar2;					// The current character in the line
	private static int currentCharNdx3;					// The index of the current character
	private static boolean running4;						// The flag that specifies if the FSM is 
	//
	public static boolean foundTooLong = false;
												
	public static String userNameRecognizerErrorMessage = "";	// The error message text
	public static String userNameRecognizerInput = "";			// The input being processed
	public static int userNameRecognizerIndexofError = -1;		// The index of error location
	private static int state = 0;						// The current state value
	private static int nextState = 0;					// The next state value
	private static boolean finalState = false;			// Is this state a final state?
	private static String inputLine = "";				// The input line
	private static char currentChar;					// The current character in the line
	private static int currentCharNdx;					// The index of the current character
	private static boolean running;						// The flag that specifies if the FSM is 
														// running
	private static int userNameSize = 0;
	
	public static String evaluatePassword(String input) {
		// The following are the local variable used to perform the Directed Graph simulation
		passwordErrorMessage = "";
		passwordIndexofError = 0;			// Initialize the IndexofError
		inputLine1 = input;					// Save the reference to the input line as a global
		currentCharNdx3 = 0;					// The index of the current character
		
		if(input.length() <= 0) {
			return "*** Error *** The password is empty!";
		}
		
		// The input is not empty, so we can access the first character
		currentChar2 = input.charAt(0);		// The current character from the above indexed position

		// The Directed Graph simulation continues until the end of the input is reached or at some 
		// state the current character does not match any valid transition to a next state.  This
		// local variable is a working copy of the input.
		passwordInput = input;				// Save a copy of the input
		
		// The following are the attributes associated with each of the requirements
		foundUpperCase = false;				// Reset the Boolean flag
		foundLowerCase = false;				// Reset the Boolean flag
		foundNumericDigit = false;			// Reset the Boolean flag
		foundSpecialChar = false;			// Reset the Boolean flag
		foundNumericDigit = false;			// Reset the Boolean flag
		foundLongEnough = false;			// Reset the Boolean flag
		//
		foundTooLong = false;
		
		// This flag determines whether the directed graph (FSM) loop is operating or not
		running4 = true;						// Start the loop

		// The Directed Graph simulation continues until the end of the input is reached or at some
		// state the current character does not match any valid transition
		while (running4) {
			// The cascading if statement sequentially tries the current character against all of
			// the valid transitions, each associated with one of the requirements
			if (currentChar2 >= 'A' && currentChar2 <= 'Z') {
				System.out.println("Upper case letter found");
				foundUpperCase = true;
			} else if (currentChar2 >= 'a' && currentChar2 <= 'z') {
				System.out.println("Lower case letter found");
				foundLowerCase = true;
			} else if (currentChar2 >= '0' && currentChar2 <= '9') {
				System.out.println("Digit found");
				foundNumericDigit = true;
			} else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/".indexOf(currentChar2) >= 0) {
				System.out.println("Special character found");
				foundSpecialChar = true;
			} else {
				passwordIndexofError = currentCharNdx3;
				return "*** Error *** An invalid character has been found!";
			}
			if (currentCharNdx3 >= 7) {
				System.out.println("At least 8 characters found");
				foundLongEnough = true;
			}
			//
			if(currentCharNdx3 >= 32) {
				System.out.println("Too Long");
				foundTooLong = true;
			}
			// Go to the next character if there is one
			currentCharNdx3++;
			if (currentCharNdx3 >= inputLine1.length())
				running4 = false;
			else
				currentChar2 = input.charAt(currentCharNdx3);
			
			System.out.println();
		}
		
		// Construct a String with a list of the requirement elements that were found.
		String errMessage = "";
		if (!foundUpperCase)
			errMessage += "Upper case; ";
		
		if (!foundLowerCase)
			errMessage += "Lower case; ";
		
		if (!foundNumericDigit)
			errMessage += "Numeric digits; ";
			
		if (!foundSpecialChar)
			errMessage += "Special character; ";
			
		if (!foundLongEnough)
			errMessage += "Long Enough; ";
		//
		if(foundTooLong) {
			errMessage += "Too long";
		}
		
		if (errMessage == "")
			return "Password is valid";
		
		// If it gets here, there something was not found, so return an appropriate message
		passwordIndexofError = currentCharNdx3;
		return errMessage + "conditions were not satisfied";
	}
	
	public static String checkForValidUserName(String input) {
	    if (input == null || input.isEmpty()) {
	        userNameRecognizerIndexofError = 0;
	        return "\n*** ERROR *** The input is empty";
	    }

	    state = 0;
	    inputLine = input;
	    currentCharNdx = 0;
	    currentChar = input.charAt(0);
	    userNameRecognizerInput = input;
	    running = true;
	    nextState = -1;
	    finalState = false;
	    userNameSize = 0;

	    while (running) {
	        switch (state) {
	            case 0:
	                if ((currentChar >= 'A' && currentChar <= 'Z') ||
	                    (currentChar >= 'a' && currentChar <= 'z')) {
	                    nextState = 1;
	                    userNameSize++;
	                } else {
	                    running = false;
	                }
	                break;

	            case 1:
	                if ((currentChar >= 'A' && currentChar <= 'Z') ||
	                    (currentChar >= 'a' && currentChar <= 'z') ||
	                    (currentChar >= '0' && currentChar <= '9')) {
	                    nextState = 1;
	                    userNameSize++;
	                } else if (currentChar == '.' || currentChar == '_' || currentChar == '-') {
	                    nextState = 2;
	                    userNameSize++;
	                } else {
	                    running = false;
	                }
	                if (userNameSize > 16) running = false;
	                break;

	            case 2:
	                if ((currentChar >= 'A' && currentChar <= 'Z') ||
	                    (currentChar >= 'a' && currentChar <= 'z') ||
	                    (currentChar >= '0' && currentChar <= '9')) {
	                    nextState = 1;
	                    userNameSize++;
	                } else {
	                    running = false;
	                }
	                if (userNameSize > 16) running = false;
	                break;
	        }

	        if (running) {
	            // advance to next char
	            currentCharNdx++;
	            if (currentCharNdx >= inputLine.length()) {
	                running = false;                 // end of input
	            } else {
	                currentChar = inputLine.charAt(currentCharNdx);
	            }
	            // next state
	            state = nextState;
	            if (state == 1) finalState = true;
	            nextState = -1;
	        }
	    }

	    // Final decision (state & size & full consumption)
	    userNameRecognizerIndexofError = currentCharNdx;
	    userNameRecognizerErrorMessage = "\n*** ERROR *** ";

	    if (finalState && currentCharNdx >= input.length()) {
	        if (userNameSize < 4) {
	            return userNameRecognizerErrorMessage + "A UserName must have at least 4 characters.\n";
	        }
	        if (userNameSize > 16) {
	            return userNameRecognizerErrorMessage + "A UserName must have no more than 16 characters.\n";
	        }
	        // success
	        userNameRecognizerIndexofError = -1;
	        userNameRecognizerErrorMessage = "";
	        return "Username is valid";
	    }

	    // Non-final or trailing junk
	    if (state == 0) {
	        return userNameRecognizerErrorMessage + "A UserName must start with A-Z, a-z\n";
	    }
	    if (state == 2) {
	        return userNameRecognizerErrorMessage + "A UserName character after a period must be A-Z, a-z, 0-9.\n";
	    }
	    return userNameRecognizerErrorMessage + "A UserName character may only contain the characters A-Z, a-z, 0-9,.-_\n";
	}
	protected static void doLogin(Stage ts) {
		theStage = ts;
		String username = ViewUserLogin.text_Username.getText();
		String password = ViewUserLogin.text_Password.getText();
    	boolean loginResult = false;
    	
		// Fetch the user and verify the username
    	String userErr = checkForValidUserName(username);
    	if (!userErr.equals("Username is valid")) {
    	    ViewUserLogin.alertUsernamePasswordError.setContentText(userErr);
    	    ViewUserLogin.alertUsernamePasswordError.showAndWait();
    	    return;
    	}
     	if (theDatabase.getUserAccountDetails(username) == false) {
     		// Don't provide too much information.  Don't say the username is invalid or the
     		// password is invalid.  Just say the pair is invalid.
    		ViewUserLogin.alertUsernamePasswordError.setContentText(
    				"Incorrect username/password. Try again!");
    		ViewUserLogin.alertUsernamePasswordError.showAndWait();
    		return;
    	}
		
		// Check to see that the login password matches the account password
    	String actualPassword = theDatabase.getCurrentPassword();
    	String passErr = evaluatePassword(password);
    	if (!passErr.equals("Password is valid")) {
    	    ViewUserLogin.alertUsernamePasswordError.setContentText(passErr);
    	    ViewUserLogin.alertUsernamePasswordError.showAndWait();
    	    return;
    	}
    	if (password.compareTo(actualPassword) != 0) {
    		ViewUserLogin.alertUsernamePasswordError.setContentText(
    				"Incorrect username/password. Try again!");
    		ViewUserLogin.alertUsernamePasswordError.showAndWait();
    		return;
    	}
		
		// Establish this user's details
    	User user = new User(username, password, theDatabase.getCurrentFirstName(), 
    			theDatabase.getCurrentMiddleName(), theDatabase.getCurrentLastName(), 
    			theDatabase.getCurrentPreferredFirstName(), theDatabase.getCurrentEmailAddress(), 
    			theDatabase.getCurrentAdminRole(), 
    			theDatabase.getCurrentNewRole1(), theDatabase.getCurrentNewRole2());
    	
    	// See which home page dispatch to use
		int numberOfRoles = theDatabase.getNumberOfRoles(user);		
		System.out.println("*** The number of roles: "+ numberOfRoles);
		if (numberOfRoles == 1) {
			// Single Account Home Page - The user has no choice here
			
			// Admin role
			if (user.getAdminRole()) {
				loginResult = theDatabase.loginAdmin(user);
				if (loginResult) {
					guiAdminHome.ViewAdminHome.displayAdminHome(theStage, user);
				}
			} else if (user.getNewRole1()) {
				loginResult = theDatabase.loginRole1(user);
				if (loginResult) {
					guiRole1.ViewRole1Home.displayRole1Home(theStage, user);
				}
			} else if (user.getNewRole2()) {
				loginResult = theDatabase.loginRole2(user);
				if (loginResult) {
					guiRole2.ViewRole2Home.displayRole2Home(theStage, user);
				}
				// Other roles
			} else {
				System.out.println("***** UserLogin goToUserHome request has an invalid role");
			}
		} else if (numberOfRoles > 1) {
			// Multiple Account Home Page - The user chooses which role to play
			System.out.println("*** Going to displayMultipleRoleDispatch");
			guiMultipleRoleDispatch.ViewMultipleRoleDispatch.
				displayMultipleRoleDispatch(theStage, user);
		}
	}
	
		
	/**********
	 * <p> Method: setup() </p>
	 * 
	 * <p> Description: This method is called to reset the page and then populate it with new
	 * content.</p>
	 * 
	 */
	protected static void doSetupAccount(Stage theStage, String invitationCode) {
		guiNewAccount.ViewNewAccount.displayNewAccount(theStage, invitationCode);
	}

	
	/**********
	 * <p> Method: public performQuit() </p>
	 * 
	 * <p> Description: This method is called when the user has clicked on the Quit button.  Doing
	 * this terminates the execution of the application.  All important data must be stored in the
	 * database, so there is no cleanup required.  (This is important so we can minimize the impact
	 * of crashed.)
	 * 
	 */	
	protected static void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
	}	

}
