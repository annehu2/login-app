
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	static Label label;

	public static void display(String title, String message, String buttonText) {
		Stage window = new Stage();

		// forces user to take care of this window before
		// moving back to other window
		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle(title);
		window.setMinWidth(250);
		VBox layout = new VBox(10);

		/*
		 * If the message is unicorn, instead of printing unicorn it will show a gif of a unicorn dancing
		 */
		if (message.equals("Unicorn")) {
			ImageView image = new ImageView();
			Image unicorn = null;
			try {
				unicorn = new Image(new FileInputStream("giphy.gif"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			image.setImage(unicorn); // setting the image viewer
			if (!(layout.getChildren().contains(image))) {
				layout.getChildren().addAll(image);
			}

		} else if (!(layout.getChildren().contains(label))) { // add the label if it isn't already there
			label = new Label(message);
			layout.getChildren().add(label);
		}

		Button closeButton = new Button(buttonText);
		closeButton.setOnAction(e -> window.close()); // close window 

		layout.getChildren().add(closeButton);
		layout.setAlignment(Pos.CENTER);

		layout.setPadding(new Insets(10, 10, 10, 10));

		Scene scene = new Scene(layout);
		window.setScene(scene);

		// display this window, and before returning, wait to be closed
		window.showAndWait();

	}

}
