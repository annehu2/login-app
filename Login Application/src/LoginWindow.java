import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
/*
 * Anne Hu 2017
 * Login Application
 * Java IO, Login Information is saved to database users.txt
 * Login Successfully to find a surprise!
 */
public class LoginWindow extends Application {
	Stage window;
	Button logButton, signButton, forgotPassButton;
	Label userNameStar, passWordStar;
	ArrayList<Label> redStars = new ArrayList<Label>();

	public static void main(String[] args) {
		launch(args);
	}

	 // This method is used to run all java fx elements and it is used to check for correct login results
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Login");
		
		// Gridpane
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 20));

		// Setting horizontal and vertical gaps
		grid.setVgap(10);
		grid.setHgap(20);

		// Label to welcome the user
		Label welcomeLabel = new Label("Welcome!");
		welcomeLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		GridPane.setConstraints(welcomeLabel, 0, 0);

		// User name label
		Label nameLabel = new Label("Username: ");
		GridPane.setHalignment(nameLabel, HPos.RIGHT);
		GridPane.setConstraints(nameLabel, 0, 1);

		// Password label
		Label passLabel = new Label("Password: ");
		GridPane.setHalignment(passLabel, HPos.RIGHT);
		GridPane.setConstraints(passLabel, 0, 2);

		// User name input box
		TextField nameInput = new TextField();
		GridPane.setConstraints(nameInput, 1, 1);

		// Password input box
		PasswordField passInput = new PasswordField();
		GridPane.setConstraints(passInput, 1, 2);

		// Login button
		logButton = new Button();
		logButton.setText(" Login ");
		GridPane.setHalignment(logButton, HPos.LEFT);
		GridPane.setConstraints(logButton, 1, 4);

		// Sign up button
		signButton = new Button();
		signButton.setText("Sign Up");
		GridPane.setHalignment(logButton, HPos.RIGHT);
		GridPane.setConstraints(signButton, 1, 4);

		// Forgot password button
		forgotPassButton = new Button();
		forgotPassButton.setText("      Forgot Password      ");
		GridPane.setHalignment(forgotPassButton, HPos.CENTER);
		GridPane.setConstraints(forgotPassButton, 1, 5);
		
		// Calling method to set constraints of the stars
		userNameStar = addRedStar(GridPane.getRowIndex(nameInput), userNameStar);
		passWordStar = addRedStar(GridPane.getRowIndex(passInput), passWordStar);
		
		// Sign up button is pressed, open sign up window
		signButton.setOnAction(e -> {
			SignUpWindow.start();
		});

		// when forgot password button is pressed, open forgot password window
		forgotPassButton.setOnAction(e -> {
			ForgotPasswordWindow.start();
		});

		// when the log in button is pressed do the following...
		logButton.setOnAction(e -> {

			// getting the text of the inputs and putting them in an array
			String[] inputs = { nameInput.getText(), passInput.getText() };

			// clearing the redstars array to later add red stars in
			redStars.clear();
			
			//removeing all the red stars (reset)
			grid.getChildren().removeAll(userNameStar, passWordStar);

			//creating a string called errors to later add errors to
			String errors = "";
			
			// calls method to check if any field is missing info
			if (checkRedStars(inputs)) {
				errors = errors + "Missing Information (*) \n";
				// adds a label based on what was stars were added to the red stars array ist
				for (Label lb : redStars) {
					grid.getChildren().add(lb);
				} // if the username already exists (calls exist method) 
			} else if (!(exists(inputs[0]))) {
				errors = errors + "User does not exist \n";
				// if the password input does not equal the password of the particular username
			} else if (!(inputs[1].equals(ReadFile.getFileInfo(inputs[0], 0)[1]))) {
				errors = errors + "Incorrect Password \n";
			}

			//if there are no errors than display alert box
			if (errors.equals("")) {
				AlertBox.display("Success", "Unicorn", "Close"); // passes through keyword to display something lit
				window.close(); // close the window
			} else {
				AlertBox.display("Login Fail", errors, "Close");
			}

		});

		// Adding all the objects to the gridpane
		grid.getChildren().addAll(nameLabel, welcomeLabel, nameInput, passLabel, passInput, logButton, signButton,
				forgotPassButton);

		Scene scene = new Scene(grid, 350, 210);
		window.setScene(scene);
		window.show();

	}
	/*
	 * method to check if the fields are empty, adds the star to the array if it is
	 * If it already contains the field, it will not duplicate it
	 * If the array is empty (fields are not empty) than it will return false with means there were no missing infomations
	 */
	private boolean checkRedStars(String[] inputs) {
		if (inputs[0].equals("")) {
			if (!(redStars.contains(userNameStar))) {
				redStars.add(userNameStar);
			}
		}
		if (inputs[1].equals("")) {
			if (!(redStars.contains(passWordStar))) {
				redStars.add(passWordStar);
			}
		}
		if (redStars.isEmpty()) {
			return false;
		}
		return true;
	}

	/*
	 * Method that checks the file if there is a username that already exists with the username that get passes through in the parameter
	 */
	private static boolean exists(String userName) {
		String[] fileInfo = ReadFile.getFileInfo(userName, 0); // gets a string array from the readfile class and check the first element
		if (fileInfo[0].equals(userName)) { 
			return true;
		}
		return false;
	}

	/*
	 * Creates a label (red star)
	 */
	private static Label addRedStar(int row, Label star) {
		star = new Label("*");
		GridPane.setConstraints(star, 2, row, 1, 1, HPos.RIGHT, null);
		star.setTextFill(Color.RED);
		return star;
	}
}
