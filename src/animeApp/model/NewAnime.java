package animeApp.model;

import animeApp.controllers.AnimeinfoController;
import animeApp.databaseUtils.StartUpLocation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by admin on 4/15/2016.
 */
public class NewAnime implements Comparable<NewAnime>{
    private int id;
    private int apId;
    private String name;
    private String imgurl;
    private Button button;
    private String genre;
    private int spot;
    private double rating;
    //private ImageView image;
    private HBox box;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(NewAnime other){
        return name.compareTo(other.name);
    }

    public NewAnime(int id, int apId, String name, String imgurl,String genre) {
        this.id = id;
        this.apId = apId;
        this.name = name;
        this.imgurl = imgurl;
        this.genre=genre;

        button = new Button("show ",new ImageView("animeApp/assets/icons/desktopicon.png"));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage window = new Stage();
                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/animeinfo.fxml"));
                    Parent root = (Parent)fxmlLoader.load();
                    AnimeinfoController controller = (AnimeinfoController)fxmlLoader.getController();
                    controller.setId(id);

                    //Scene scene = new Scene(root,750,550);
                    Scene scene = new Scene(root,850,600);
                    window.initStyle(StageStyle.DECORATED);
                    window.setScene(scene);
                    window.setTitle("Anime Information");
                    window.getIcons().add(new javafx.scene.image.Image(Anime.class.getResourceAsStream("../assets/icons/animeWmIcon.png")));
                    window.setResizable(false);
                    
                    //set on active screen
                    StartUpLocation startupLoc = new StartUpLocation(850, 600);
                    double xPos = startupLoc.getXPos();
                    double yPos = startupLoc.getYPos();
                    // Set Only if X and Y are not zero and were computed correctly
                    if (xPos != 0 && yPos != 0) {
                    	window.setX(xPos);
                    	window.setY(yPos);
                    } else {
                    	window.centerOnScreen();
                    }
                    
                    window.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        button.setFont(Font.font("FontAwesome", FontWeight.BOLD, 14));
        button.getStylesheets().add("animeApp/assets/css/buttonview.css");

    }

    public int getSpot() {
        return spot;
    }

    public void setSpot(int spot) {
        this.spot = spot;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getapId() {
        return apId;
    }

    public void setapId(int apId) {
        this.apId = apId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Button getButton() {
        return button;
    }
    /*
    public ImageView getImage() { return image; }*/

    public HBox getBox() { return box; }


    @Override
    public String toString() {
        return name;
    }





}

