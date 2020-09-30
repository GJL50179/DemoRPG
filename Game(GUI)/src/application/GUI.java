package application;
	
import java.io.File;
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GUI extends Application implements EventHandler{
	@Override
	public void start(Stage primaryStage) {
		try {
			Session t1 = new Session();
			Text text = new Text(t1.stringLog);
			text.setFont(Font.loadFont("file:fonts/EnchantedLand.otf", 25));
			
			primaryStage.setTitle("Opening Screen");
			BorderPane root = new BorderPane();
			Label mainScreenLabel = new Label("Welcome to the Game! Click the buton below to begin.");
			mainScreenLabel.setFont(Font.loadFont("file:fonts/EnchantedLand.otf", 40));
			mainScreenLabel.setStyle("-fx-text-fill: white;");
			Button mainScreenButton = new Button("Begin Game");
			mainScreenButton.setMaxSize(700, 100);
			mainScreenButton.setPrefSize(700, 100);
			root.setBottom(mainScreenButton);
			root.setTop(mainScreenLabel);
			Scene scene = new Scene(root,600,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			text.setId("gText");
			ScrollPane scp = new ScrollPane();
			scp.setContent(text);
			scp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			scp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
			scp.setPrefViewportHeight(450);
			BorderPane groot = new BorderPane();
			groot.setTop(scp);
			TextField textfield = new TextField();
			
			groot.setBottom(textfield);
			t1.startSession(text, scp, textfield);
			Scene gameScene = new Scene(groot, 540, 500);
			gameScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			mainScreenButton.setOnAction(e -> primaryStage.setScene(gameScene));
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handle(ActionEvent event)
	{
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(Event arg0) {
		// TODO Auto-generated method stub
		
	}
}
