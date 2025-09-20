package guiNewAccount;

import java.sql.SQLException;

import database.Database;
import entityClasses.User;

public class ControllerNewAccount {
	
	/*-********************************************************************************************

	The User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/**********
	 * <p> Method: public doCreateUser() </p>
	 * 
	 * <p> Description: This method is called when the user has clicked on the User Setup
	 * button.  This method checks the input fields to see that they are valid.  If so, it then
	 * creates the account by adding information to the database.
	 * 
	 * The method reaches back to the view page to fetch the information needed rather than
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
	private static boolean running4;					// The flag that specifies if the FSM is running
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
	private static boolean running;						// The flag that specifies if the FSM is running
	private static int userNameSize = 0;
	
	public static String evaluatePassword(String input) {
		// The following are the local variables used to perform the Directed Graph simulation
		passwordErrorMessage = "";
		passwordIndexofError = 0;			// Initialize the IndexofError
		inputLine1 = input;					// Save the reference to the input line as a global
		currentCharNdx3 = 0;				// The index of the current character
		
		if (input == null || input.isEmpty()) {
			return "*** Error *** The password is empty!";
		}
		
		// The input is not empty, so we can access the first character
		currentChar2 = input.charAt(0);		// The current character from the above indexed position

		// The Directed Graph simulation continues until the end of the input is reached or at some 
		// state the current character does not match any valid transition to a next state.  This
		// local variable is a working copy of the input.
		passwordInput = input;				// Save a copy of the input
		
		// The following are the attributes associated with each of the requirements
		foundUpperCase = false;				// Reset the Boolean flags
		foundLowerCase = false;
		foundNumericDigit = false;
		foundSpecialChar = false;
		foundLongEnough = false;
		foundTooLong = false;
		
		// This flag determines whether the directed graph (FSM) loop is operating or not
		running4 = true;					// Start the loop

		// The Directed Graph simulation continues until the end of the input is reached or at some
		// state the current character does not match any valid transition
		while (running4) {
			// The cascading if statement sequentially tries the current character against all of
			// the valid transitions, each associated with one of the requirements
			if (currentChar2 >= 'A' && currentChar2 <= 'Z') {
				foundUpperCase = true;
			} else if (currentChar2 >= 'a' && currentChar2 <= 'z') {
				foundLowerCase = true;
			} else if (currentChar2 >= '0' && currentChar2 <= '9') {
				foundNumericDigit = true;
			} else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/".indexOf(currentChar2) >= 0) {
				foundSpecialChar = true;
			} else {
				passwordIndexofError = currentCharNdx3;
				return "*** Error *** An invalid character has been found!";
			}

			if (currentCharNdx3 >= 7) {
				foundLongEnough = true;		// 8+ chars
			}

			// Go to the next character if there is one
			currentCharNdx3++;
			if (currentCharNdx3 >= inputLine1.length())
				running4 = false;
			else
				currentChar2 = input.charAt(currentCharNdx3);
		}
		
		// Final max-length check to avoid off-by-one issues
		if (inputLine1.length() > 32) {
			foundTooLong = true;
		}
		
		// Construct a String with a list of the requirement elements that were not found.
		String errMessage = "";
		if (!foundUpperCase)    errMessage += "Upper case; ";
		if (!foundLowerCase)    errMessage += "Lower case; ";
		if (!foundNumericDigit) errMessage += "Numeric digits; ";
		if (!foundSpecialChar)  errMessage += "Special character; ";
		if (!foundLongEnough)   errMessage += "Long Enough; ";
		if (foundTooLong)       errMessage += "Too long";
		
		if (errMessage.isEmpty())
			return "Password is valid";
		
		// If it gets here, something was not satisfied, so return an appropriate message
		passwordIndexofError = Math.min(currentCharNdx3, Math.max(0, inputLine1.length() - 1));
		return errMessage + " conditions were not satisfied";
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

	protected static void doCreateUser() {
		// Fetch the username and passwords
		String username = ViewNewAccount.text_Username.getText();
		username = (username == null) ? "" : username.trim();

		String password1 = ViewNewAccount.text_Password1.getText();
		String password2 = ViewNewAccount.text_Password2.getText();

		// Validate username format
		String userErr = checkForValidUserName(username);
    	if (!userErr.equals("Username is valid")) {
    	    ViewNewAccount.alertUsernamePasswordError.setHeaderText("Invalid Username");
    	    ViewNewAccount.alertUsernamePasswordError.setContentText(userErr);
    	    ViewNewAccount.alertUsernamePasswordError.showAndWait();
    	    return;
    	}

    	// If passwords don't match, fail early
    	if (password1 == null) password1 = "";
    	if (password2 == null) password2 = "";
    	if (!password1.equals(password2)) {
			ViewNewAccount.text_Password1.setText("");
			ViewNewAccount.text_Password2.setText("");
			ViewNewAccount.alertUsernamePasswordError.setHeaderText("Passwords do not match");
			ViewNewAccount.alertUsernamePasswordError.setContentText(
				"The two passwords must match. Please try again!"
			);
			ViewNewAccount.alertUsernamePasswordError.showAndWait();
			return;
    	}

    	// Validate password policy (on the confirmed value)
    	String passErr = evaluatePassword(password1);
    	if (!passErr.equals("Password is valid")) {
    	    ViewNewAccount.alertUsernamePasswordError.setHeaderText("Invalid Password");
    	    ViewNewAccount.alertUsernamePasswordError.setContentText(passErr);
    	    ViewNewAccount.alertUsernamePasswordError.showAndWait();
    	    return;
    	}
		
		// Display key information to the log
		System.out.println("** Account for Username: " + username + "; theInvitationCode: "+
				ViewNewAccount.theInvitationCode + "; email address: " + 
				ViewNewAccount.emailAddress + "; Role: " + ViewNewAccount.theRole);
		
		// Initialize local variables that will be created during this process
		int roleCode = 0;
		User user = null;

		// Set up the role and the User object based on the invitation info
		if ("Admin".equals(ViewNewAccount.theRole)) {
			roleCode = 1;
			user = new User(username, password1, "", "", "", "", "", true, false, false);
		} else if ("Role1".equals(ViewNewAccount.theRole)) {
			roleCode = 2;
			user = new User(username, password1, "", "", "", "", "", false, true, false);
		} else if ("Role2".equals(ViewNewAccount.theRole)) {
			roleCode = 3;
			user = new User(username, password1, "", "", "", "", "", false, false, true);
		} else {
			System.out.println("**** Trying to create a New Account for a role that does not exist!");
			System.exit(0);
		}
			
		// Unlike the FirstAdmin, we know the email address, so set that into the user as well.
    	user.setEmailAddress(ViewNewAccount.emailAddress);

    	// Inform the system about which role will be played
		applicationMain.FoundationsMain.activeHomePage = roleCode;
			
    	// Create the account based on user and proceed to the user account update page
        try {
        	// Create a new User object with the pre-set role and register in the database
        	theDatabase.register(user);
        } catch (SQLException e) {
            System.err.println("*** ERROR *** Database error: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        
        // The account has been set, so remove the invitation from the system
        theDatabase.removeInvitationAfterUse(ViewNewAccount.text_Invitation.getText());
        
        // Set the database so it has this user and the current user
        theDatabase.getUserAccountDetails(username);

        // Navigate to the Welcome Login Page
        guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewNewAccount.theStage, user);
	}

	/**********
	 * <p> Method: public performQuit() </p>
	 * 
	 * <p> Description: This method is called when the user has clicked on the Quit button.  Doing
	 * this terminates the execution of the application.  All important data must be stored in the
	 * database, so there is no cleanup required.  (This is important so we can minimize the impact
	 * of crashes.)
	 * 
	 */	
	protected static void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
	}	
}
