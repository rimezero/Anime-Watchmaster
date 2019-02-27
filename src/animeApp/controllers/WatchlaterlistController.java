package animeApp.controllers;

import animeApp.databaseUtils.dbControl;
import animeApp.model.NewAnime;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Peitch on 08/09/15.
 */
public class WatchlaterlistController implements Initializable{
    @FXML
    private ListView listview1;

    private List list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeWithImages();
    }

    private void initializeWithImages(){
        list = new ArrayList<>();
        NewAnime anime = new NewAnime(1,"Loading data...","animeApp/assets/icons/spinnerR.gif","");
        list.add(anime);
        ObservableList<NewAnime> data1 = FXCollections.observableArrayList(list);
        listview1.setItems(data1);

        reloadListWithImages();

        new Thread(new Runnable() {
            @Override
            public void run() {
                list = dbControl.getInstance().getWatchLaterData();
                ObservableList<NewAnime> obs = FXCollections.observableArrayList(list);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        listview1.setItems(obs);
                    }
                });
            }
        }).start();
        System.gc();
    }

    private void reloadListWithImages(){
        listview1.setCellFactory(new Callback<ListView<NewAnime>, ListCell<NewAnime>>() {

            @Override
            public ListCell<NewAnime> call(ListView<NewAnime> p) {
                ListCell<NewAnime> cell = new ListCell<NewAnime>() {

                    @Override
                    protected void updateItem(NewAnime t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText("    " + t.getName() + "\n\n" + "    Genre: " + t.getGenre());
                            setFont(Font.font("FontAwesome", FontWeight.BOLD, 14));
                            HBox box = new HBox(20);
                            box.setStyle("-fx-alignment: center;");
                            ImageView image = new ImageView("animeApp/assets/icons/spinnerR.gif");
                            box.getChildren().addAll(t.getButton(), image);
                            setGraphic(box);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    image.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);\n" +
                                            "-fx-padding: 10;\n" +
                                            "-fx-background-color: firebrick;\n" +
                                            "-fx-background-radius: 15;");
                                    if (t.getImgurl() == null || t.getImgurl().trim().equals(""))
                                        image.setImage(new Image("animeApp/assets/icons/noimage.jpg"));
                                    else
                                        image.setImage(new Image(t.getImgurl()));
                                }
                            }).start();
                            setPadding(new Insets(15, 10, 15, 10));
                        }
                    }
                };

                return cell;
            }

        });
    }

    @FXML
    private void Remove_selected_anime(){

        ObservableList<NewAnime> animeSelected, allanime;
        allanime = listview1.getItems();
        animeSelected = listview1.getSelectionModel().getSelectedItems();

        for(NewAnime anime : animeSelected){
            dbControl.getInstance().deleteFromWatchLater(anime.getId());
            allanime.remove(anime);
        }

        reloadListWithImages();
        System.gc();
    }

}
