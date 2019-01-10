import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ForgotPasswordWindow {
	static Button getPassButton, cancelButton;
	static Label answerStar, userNameStar, questionLabel;

	static ArrayList<Label> redStars = new ArrayList<Label>();

	public static void start() {
		Stage window = new Stage();
		window.setTitle("Forgot Password");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 20));

		grid.setVgap(10);
		grid.setHgap(20);

		// Label to tell them they want their password
		Label forgotPassLabel = new Label("Forgot Password");
		forgotPassLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		GridPane.setConstraints(forgotPassLabel, 0, 0, 3, 1);

		// User name label
		Label nameLabel = new Label("Username: ");
		GridPane.setHalignment(nameLabel, HPos.RIGHT);
		GridPane.setConstraints(nameLabel, 0, 1);

		// Question label
		Label questionLabel = new Label("Question will appear here");
		questionLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		GridPane.setConstraints(questionLabel, 0, 2, 2, 1, HPos.LEFT, null);

		// Answer label
		Label answerLabel = new Label("Answer: ");
		GridPane.setConstraints(answerLabel, 0, 3, 1, 1, HPos.RIGHT, null);

		// User name input box
		TextField nameInput = new TextField();
		GridPane.setConstraints(nameInput, 1, 1);

		// Answer input box
		TextField answerInput = new TextField();
		GridPane.setConstraints(answerInput, 1, 3);

		// get pass button
		getPassButton = new Button();
		getPassButton.setText("Get Password");
		GridPane.setHalignment(getPassButton, HPos.LEFT);
		GridPane.setConstraints(getPassButton, 1, 5);

		// cancel button
		cancelButton = new Button();
		cancelButton.setText("Cancel");
		GridPane.setHalignment(cancelButton, HPos.RIGHT);
		GridPane.setConstraints(cancelButton, 1, 5);

		// adding the red star
		userNameStar = addRedStar(GridPane.getRowIndex(nameInput), userNameStar);
		answerStar = addRedStar(GridPane.getRowIndex(answerInput), answerStar);

		cancelButton.setOnAction(e -> {
			window.close();
		});

		// listening for a change in the text field
		nameInput.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!(nameInput.getText().equals(ReadFile.getFileInfo(newValue, 0)[0]))) {
				questionLabel.setText("Question will appear here");
			} else {
				questionLabel.setText(ReadFile.getFileInfo(newValue, 0)[2]);
			}
		});

		// when the get password button is pressed
		getPassButton.setOnAction(e -> {

			// add the inputs into an array
			String[] inputs = { nameInput.getText(), answerInput.getText() };

			redStars.clear(); // clear the red star array
			grid.getChildren().removeAll(userNameStar, answerStar); // setting the red stars

			String errors = "";
			if (checkRedStars(inputs)) {
				errors = errors + "Missing Information (*) \n";
				for (Label lb : redStars) {
					grid.getChildren().add(lb);
				}
			} else if (!(inputs[0].equals(ReadFile.getFileInfo(inputs[0], 0)[0]))) {
				errors = errors + "That user does not exist \n";
				// if the answer is not equal to the answer under the same username from the data base
			} else if (!(inputs[1].equals(ReadFile.getFileInfo(inputs[0], 0)[3]))) {
				errors = errors + "The answer is incorrect \n";
			}

			if (errors.equals("")) {
				// if there are no errors, searches for username and gives the password of the username from the data base
				AlertBox.display("Success", "Your password is " + ReadFile.getFileInfo(inputs[0], 0)[1],
						"Close");
				window.close();
			} else {
				AlertBox.display("Fail", errors, "Close");
			}

		});

		// Adding all the objects to the gridpane
		grid.getChildren().addAll(nameLabel, forgotPassLabel, nameInput, answerLabel, answerInput, getPassButton,
				cancelButton, questionLabel);

		Scene scene = new Scene(grid, 350, 210);
		window.setScene(scene);
		window.show();

	}

	private static boolean checkRedStars(String[] inputs) {
		if (inputs[0].equals("")) {
			if (!(redStars.contains(userNameStar))) {
				redStars.add(userNameStar);
			}
		}
		if (inputs[1].equals("")) {
			if (!(redStars.contains(answerStar))) {
				redStars.add(answerStar);
			}
		}
		if (redStars.isEmpty()) {
			return false;
		}
		return true;
	}

	private static Label addRedStar(int row, Label star) {
		star = new Label("*");
		GridPane.setConstraints(star, 2, row, 1, 1, HPos.RIGHT, null);
		star.setTextFill(Color.RED);
		return star;
	}
}
