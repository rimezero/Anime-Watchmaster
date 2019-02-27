package animeApp.model;

import animeApp.controllers.AnimeinfoController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Peitch on 08/03/15.
 */
public class Anime implements Comparable<Anime>{

    private String name;
    private int id;
    private Button button;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(Anime other){
        return name.compareTo(other.name);
    }

    public Anime(int id, String name) {
        this.id = id;
        this.name = name;

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

                    Scene scene = new Scene(root,750,550);
                    window.initStyle(StageStyle.DECORATED);
                    window.setScene(scene);
                    window.setTitle("Anime Information");
                    window.getIcons().add(new javafx.scene.image.Image(Anime.class.getResourceAsStream("../assets/icons/animeWmIcon.png")));
                    window.setResizable(false);
                    window.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        button.setFont(Font.font("FontAwesome", FontWeight.BOLD, 14));
        button.getStylesheets().add("animeApp/assets/css/buttonview.css");

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Button getButton() {
        return button;
    }




    @Override
    public String toString() {
        return name;
    }





}
