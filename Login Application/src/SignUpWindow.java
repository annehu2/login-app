import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SignUpWindow {
	static Button cancelButton, createButton;
	static Label userNameStar, passWordStar, confirmStar, questionStar, answerStar;

	public static void start() {
		Stage window = new Stage();
		window.setTitle("Sign Up");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 20));

		grid.setVgap(10);
		grid.setHgap(20);
		
		// Label to welcome the user
		Label createLabel = new Label("Create Your Account");
		createLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		GridPane.setConstraints(createLabel, 0, 0, 2, 1);

		// User name label
		Label nameLabel = new Label("Create Username: ");
		GridPane.setConstraints(nameLabel, 0, 1, 1, 1, HPos.RIGHT, null);

		// Password label
		Label passLabel = new Label("Create Password: ");
		GridPane.setConstraints(passLabel, 0, 2, 1, 1, HPos.RIGHT, null);

		// Confirm Password label
		Label confirmLabel = new Label("Confirm Password: ");
		GridPane.setConstraints(confirmLabel, 0, 3, 1, 1, HPos.RIGHT, null);

		// User name input box
		TextField nameInput = new TextField();
		GridPane.setConstraints(nameInput, 1, 1);

		// Password input box
		PasswordField passInput = new PasswordField();
		GridPane.setConstraints(passInput, 1, 2);

		// Password SEE input box
		TextField passInputSee = new TextField();
		GridPane.setConstraints(passInputSee, 1, 2);

		// Confirm Password input box
		PasswordField confirmInput = new PasswordField();
		GridPane.setConstraints(confirmInput, 1, 3);

		// Confirm Password SEE input box
		TextField confirmInputSee = new TextField();
		GridPane.setConstraints(confirmInputSee, 1, 3);

		// Security questions label
		Label questionLabel = new Label("Security Question");
		questionLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		GridPane.setConstraints(questionLabel, 0, 5, 2, 1);

		// create button
		createButton = new Button();
		createButton.setText(" Create ");
		GridPane.setConstraints(createButton, 1, 10, 1, 1, HPos.LEFT, null);

		// Cancel button
		cancelButton = new Button();
		cancelButton.setText(" Cancel ");
		GridPane.setConstraints(cancelButton, 1, 10, 1, 1, HPos.RIGHT, null);

		// Question label
		Label questionEntLabel = new Label("Enter Question: ");
		GridPane.setConstraints(questionEntLabel, 0, 7, 1, 1, HPos.RIGHT, null);

		// answer label
		Label answerLabel = new Label("Enter Answer: ");
		GridPane.setConstraints(answerLabel, 0, 8, 1, 1, HPos.RIGHT, null);

		// question input box
		TextField questionInput = new TextField();
		GridPane.setConstraints(questionInput, 1, 7);

		// answer Password input box
		TextField answerInput = new TextField();
		GridPane.setConstraints(answerInput, 1, 8);

		// checkbox show character
		CheckBox checkBox = new CheckBox();
		checkBox.setText("Show Characters");
		GridPane.setConstraints(checkBox, 0, 10);

		TextField[] seeFields = { passInputSee, confirmInputSee };
		TextField[] notSeeFields = { passInput, confirmInput };

		for (TextField tf : seeFields) {
			tf.managedProperty().bind(checkBox.selectedProperty());
			tf.visibleProperty().bind(checkBox.selectedProperty());
		}

		for (TextField tf : notSeeFields) {
			tf.managedProperty().bind(checkBox.selectedProperty().not());
			tf.visibleProperty().bind(checkBox.selectedProperty().not());
		}
		
		confirmInputSee.textProperty().bindBidirectional(confirmInput.textProperty());
		passInputSee.textProperty().bindBidirectional(passInput.textProperty());
		
		// red star labels
		userNameStar = new Label();
		passWordStar = new Label();
		confirmStar = new Label();
		questionStar = new Label();
		answerStar = new Label();

		Label[] redStarArray = { null, userNameStar, passWordStar, confirmStar, null, null, null, questionStar,
				answerStar };

		cancelButton.setOnAction(e -> window.close());

		createButton.setOnAction(e -> {
			boolean missingInfo = false;
			TextField[] inputs = { nameInput, passInput, confirmInput, questionInput, answerInput };

			// CHECKING FOR RED STAR
			for (TextField input : inputs) {
				Label lb = redStarArray[GridPane.getRowIndex(input)];
				if (!(lb == null)) {
					if (input.getText().equals("")) {
						missingInfo = true;
						if (!(grid.getChildren().contains(lb))) {
							grid.getChildren().add(addRedStar(GridPane.getRowIndex(input), lb));
						}
					} else if (!(input.getText().equals("")) && grid.getChildren().contains(lb)) {
						grid.getChildren().remove(lb);
					}
				}
			}

			String errors = "";
			if (missingInfo) {
				errors = errors + "Missing Information (*) \n";
			} else {
				if (exists(nameInput.getText())) {
					errors = errors + "User already Exists, Try Again \n";
				} else if (!(correctCharSize(nameInput.getText(), 3, 15))) {
					errors = errors + "Username must be in between 3-15 characters \n";
				}
				if (correctSymbol(nameInput.getText(), "~`!@#$%^&*()+= {}|[]\":;'<>?,./ ")) {
					errors = errors + "Username can only contain letters, numbers, (underscore) _, (dash) -, (period). \n";
				}
				if (!(correctSymbol(passInput.getText(), "~`!@#$%^&*()+= {}|[]\":;'<>?./ "))) {
					errors = errors + "Passwords must contain a symbol \n";
				}
				if (!(correctSymbol(passInput.getText(), "ABCDEFGHIJKLMNOPQRSTUVWXYZ"))) {
					errors = errors + "Passwords must contain an uppercase letter \n";
				}
				if (!(samePass(passInput.getText(), confirmInput.getText()))) {
					errors = errors + "Passwords do not match \n";
				}
				if (!(correctCharSize(passInput.getText(), 6, 12))) {
					errors = errors + "Passwords must be in between 6-12 characters \n";
				}
				if (correctSymbol(passInput.getText(), ",")) {
					errors = errors + "Passwords cannot have a \",\" (comma) \n";
				}
				if (correctSymbol(questionInput.getText(), ",")
						&& (correctSymbol(answerInput.getText(), ","))) {
					errors = errors + "Question and Answer cannot have a \",\" (comma) \n";
				}
				if (!(correctCharSize(questionInput.getText(), 1, 30))) {
					errors = errors + "Keep Question between 1 and 30 characters\n";
				}
				if (!(correctCharSize(answerInput.getText(), 1, 20))) {
					errors = errors + "Keep Answer between 1 and 20 characters\n";
				}
			}

			if (errors.equals("") && !missingInfo) {
				String[] loginInfo = { nameInput.getText(), passInput.getText(), questionInput.getText(),
						answerInput.getText() };
				WriteFile writer = new WriteFile();
				writer.write(loginInfo);
				AlertBox.display("Success", "Sign Up Successful", "Close");
				window.close();
			} else {
				AlertBox.display("Sign Up Failed", errors, "Close");
			}

		});

		// Adding all the objects to the gridpane
		grid.getChildren().addAll(createLabel, nameLabel, passLabel, confirmLabel, nameInput, passInput, confirmInput,
				createButton, cancelButton, questionLabel, questionEntLabel, answerLabel, answerInput, questionInput,
				checkBox, confirmInputSee, passInputSee);

		Scene scene = new Scene(grid, 400, 350);
		window.setScene(scene);
		window.show();

	}

	private static boolean exists(String userName) {
		String []fileInfo = ReadFile.getFileInfo(userName, 0);
		if (fileInfo[0].equals(userName)) {
			return true;
		}
		return false;
	}

	private static boolean correctCharSize(String input, int min, int max) {
		if (input.length() >= min && input.length() <= max) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean correctSymbol(String input, String chars) {
		String[] charsArray = chars.split("");
		for (String x : charsArray) {
			if (input.contains(x)) {
				return true;
			}
		}
		return false;
	}
	

	private static Label addRedStar(int row, Label star) {
		star.setText("*");
		GridPane.setConstraints(star, 2, row, 1, 1, HPos.RIGHT, null);
		star.setTextFill(Color.RED);
		return star;
	}

	private static boolean samePass(String pass1, String pass2) {
		if (pass1.equals(pass2)) {
			return true;
		} else {
			return false;
		}
	}

}
