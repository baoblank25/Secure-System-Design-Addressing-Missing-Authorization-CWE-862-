package guiFirstAdmin;

import java.sql.SQLException;
import database.Database;
import entityClasses.User;
import guiUserLogin.ViewUserLogin;
import javafx.stage.Stage;

public class ControllerFirstAdmin {
	/*-********************************************************************************************

	The controller attributes for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/
	
	private static String adminUsername = "";
	private static String adminPassword1 = "";
	private static String adminPassword2 = "";		
	protected static Database theDatabase = applicationMain.FoundationsMain.database;		

	/*-********************************************************************************************

	The User Interface Actions for this page
	
	*/
	
	
	/**********
	 * <p> Method: setAdminUsername() </p>
	 * 
	 * <p> Description: This method is called when the user adds text to the username field in the
	 * View.  A private local copy of what was last entered is kept here.</p>
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
	protected static void setAdminUsername() {
		adminUsername = ViewFirstAdmin.text_AdminUsername.getText();
		String userErr = checkForValidUserName(adminUsername);
    	if (!userErr.equals("Username is valid")) {
    	    ViewUserLogin.alertUsernamePasswordError.setContentText(userErr);
    	    ViewUserLogin.alertUsernamePasswordError.showAndWait();
    	    return;
    	}
	}
	
	
	/**********
	 * <p> Method: setAdminPassword1() </p>
	 * 
	 * <p> Description: This method is called when the user adds text to the password 1 field in
	 * the View.  A private local copy of what was last entered is kept here.</p>
	 * 
	 */
	protected static void setAdminPassword1() {
		adminPassword1 = ViewFirstAdmin.text_AdminPassword1.getText();
		ViewFirstAdmin.label_PasswordsDoNotMatch.setText("");
		String passErr = evaluatePassword(adminPassword1);
    	if (!passErr.equals("Password is valid")) {
    	    ViewUserLogin.alertUsernamePasswordError.setContentText(passErr);
    	    ViewUserLogin.alertUsernamePasswordError.showAndWait();
    	    return;
    	}
	}
	
	
	/**********
	 * <p> Method: setAdminPassword2() </p>
	 * 
	 * <p> Description: This method is called when the user adds text to the password 2 field in
	 * the View.  A private local copy of what was last entered is kept here.</p>
	 * 
	 */
	protected static void setAdminPassword2() {
		adminPassword2 = ViewFirstAdmin.text_AdminPassword2.getText();		
		ViewFirstAdmin.label_PasswordsDoNotMatch.setText("");
	}
	
	
	/**********
	 * <p> Method: doSetupAdmin() </p>
	 * 
	 * <p> Description: This method is called when the user presses the button to set up the Admin
	 * account.  It start by trying to establish a new user and placing that user into the
	 * database.  If that is successful, we proceed to the UserUpdate page.</p>
	 * 
	 */
	protected static void doSetupAdmin(Stage ps, int r) {
		
		// Make sure the two passwords are the same
		if (adminPassword1.compareTo(adminPassword2) == 0) {
        	// Create the passwords and proceed to the user home page
        	User user = new User(adminUsername, adminPassword1, "", "", "", "", "", true, false, 
        			false);
            try {
            	// Create a new User object with admin role and register in the database
            	theDatabase.register(user);
            	}
            catch (SQLException e) {
                System.err.println("*** ERROR *** Database error trying to register a user: " + 
                		e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }
            
            // User was established in the database, so navigate to the User Update Page
        	guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewFirstAdmin.theStage, user);
		}
		else {
			// The two passwords are NOT the same, so clear the passwords, explain the passwords
			// must be the same, and clear the message as soon as the first character is typed.
			ViewFirstAdmin.text_AdminPassword1.setText("");
			ViewFirstAdmin.text_AdminPassword2.setText("");
			ViewFirstAdmin.label_PasswordsDoNotMatch.setText(
					"The two passwords must match. Please try again!");
		}
	}
	
	
	/**********
	 * <p> Method: performQuit() </p>
	 * 
	 * <p> Description: This method terminates the execution of the program.  It leaves the
	 * database in a state where the normal login page will be displayed when the application is
	 * restarted.</p>
	 * 
	 */
	protected static void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
	}	
}

