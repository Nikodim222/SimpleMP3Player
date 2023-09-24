/**
 * Simple MP3 Player
 * *************************
 * This is a very ascetic MP3 player which was
 * written in Java 18, plus JavaFX/OpenJFX, ver.
 * 21 (http://openjfx.io). It has been tested
 * under Ubuntu 22.04 LTS; and it works fine. All
 * the application does is choosing and playing MP3
 * files.
 * 
 * @author Efremov A. V., 2023-09-24
 */

package main;

import java.io.File;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import music.MP3Playback;

public class startIt extends Application {
    static File selectedFile;
    static MP3Playback player;

    static final double sizeWidth = 250, sizeHeight = 180;

	@Override
	public void start(Stage primaryStage) {
	    Button button1 = new Button("Open...");
		button1.setOnAction((event) -> {
		    selectedFile = null;
		    FileChooser fileChooser = new FileChooser();
		    fileChooser.getExtensionFilters().addAll(
		            new FileChooser.ExtensionFilter("MP3 files", "*.mp3"),
		            new FileChooser.ExtensionFilter("MP3 files", "*.MP3")
		            );
		    selectedFile = fileChooser.showOpenDialog(primaryStage);
		    if (selectedFile != null) {
		        System.out.println("You have chosen the file of \"" + selectedFile + "\".");
		    }
		});

		Button buttonPlay = new Button("Play");
		buttonPlay.setOnAction((event) -> {
		    if (selectedFile != null) {
		        if (player != null) {
		            player.setFile(selectedFile.getAbsolutePath());
		        } else {
		            player = new MP3Playback(selectedFile.getAbsolutePath());
		        }
		        System.out.println("Playing \"" + selectedFile.getAbsolutePath() + "\"...");
		    } else {
		        System.err.println("The player has no file to be played.");
		    }
		});

		Button buttonStop = new Button("Stop");
		buttonStop.setOnAction((event) -> {
		    if (player != null) {
		        player.stop();
		        System.out.println("The player has stopped.");
		    } else {
		        System.err.println("The player is not playing anything.");
		    }
		});

		// a few buttons will be stacked one
		// on top of the other because that is
		// the purpose of a stackpane which means
		// you will only see the top one (button1)
		StackPane topStackPane = new StackPane(button1);

		// these two buttons are wrapped in a HBox
		// which fills in it's nodes horizontally,
		// side by side from left to right.
		HBox controlWrapped = new HBox(buttonPlay, buttonStop);
		controlWrapped.setAlignment(Pos.CENTER);
		StackPane bottomStackPane = new StackPane(controlWrapped);

		topStackPane.setPrefSize(sizeWidth - 50, sizeHeight);
		bottomStackPane.setPrefSize(sizeWidth - 50, sizeHeight);
		VBox vbox = new VBox(topStackPane, new Separator(Orientation.HORIZONTAL), bottomStackPane);
		BackgroundImage myBI = new BackgroundImage(new Image(Paths.get("note.png").toUri().toString(), sizeWidth, sizeHeight, false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		vbox.setBackground(new Background(myBI));

		Scene scene = new Scene(vbox);
		// scene.setFill(Color.web("#AE1253")); // background

		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false); // запрет изменения размеров окна
		primaryStage.setTitle("Simple MP3 Player"); // заголовок окна
		primaryStage.setWidth(sizeWidth);
		primaryStage.setHeight(sizeHeight);
		// primaryStage.getIcons().add(new Image("file:doge.png")); // https://stackoverflow.com/questions/26039593/icon-set-for-javafx-application-is-visible-in-windows-but-not-in-ubuntu
		primaryStage.setOnCloseRequest((event) -> { // обработчик выхода из программы
			Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?"); // Cancel, OK
			a.showAndWait().ifPresent(response -> {
				String l_yn = response.getText();
				if (l_yn.equals("OK")) {
					Platform.exit();
					System.exit(0);
				} else {
					if (l_yn.equals("Cancel")) {
						event.consume();
					} else {
						System.err.println("An unknown state of the response.");
					}
				}
			});
		});
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
