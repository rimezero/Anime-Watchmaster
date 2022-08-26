package animeApp;

import animeApp.databaseUtils.StartUpLocation;
import animeApp.model.Configuration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    static Scene mainscene, allanimescene;
    static Stage window;
    

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        Parent allanime = FXMLLoader.load(getClass().getResource("view/allanime.fxml"));

        mainscene = new Scene(root, 1198, 695);
        allanimescene = new Scene(allanime,1198,695);
        window=primaryStage;
        primaryStage.setTitle("Anime Watchmaster");


        primaryStage.setScene(mainscene);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("assets/icons/animeWmIcon.png")));
        
      //set on active screen
        StartUpLocation startupLoc = new StartUpLocation(1198, 695);
        double xPos = startupLoc.getXPos();
        double yPos = startupLoc.getYPos();
        // Set Only if X and Y are not zero and were computed correctly
        if (xPos != 0 && yPos != 0) {
        	primaryStage.setX(xPos);
        	primaryStage.setY(yPos);
        } else {
        	primaryStage.centerOnScreen();
        }
        
        primaryStage.show();
    }

    public void setAllanimescene() throws Exception{


        window.setScene(allanimescene);
    }

    public static  void exit(){
        window.close();
    }

    public static void setMainscene() {
        window.setScene(mainscene);
    }

    public static void main(String[] args) {
    	Configuration.getInstance().initialize();
        launch(args);
    }
}
