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
 * Created by admin on 8/14/2016.
 */
public class TopanimeController implements Initializable{
    @FXML
    private ListView listview;

    private List list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTopAnimeList();
    }

    private void setTopAnimeList(){
        list = new ArrayList<>();
        NewAnime anime = new NewAnime(-1,-1,"Loading data...","animeApp/assets/icons/spinnerR.gif","");
        list.add(anime);
        ObservableList<NewAnime> data1 = FXCollections.observableArrayList(list);
        listview.setItems(data1);

        reloadListWithImages();

        new Thread(new Runnable() {
            @Override
            public void run() {
                list = dbControl.getInstance().getTopanimeData();
                //Collections.sort(list);
                ObservableList<NewAnime> data = FXCollections.observableArrayList(list);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        listview.setItems(data);
                    }
                });
            }
        }).start();

        System.gc();
    }

    @SuppressWarnings("unchecked")
	private void reloadListWithImages(){
        listview.setCellFactory(new Callback<ListView<NewAnime>, ListCell<NewAnime>>() {

            @Override
            public ListCell<NewAnime> call(ListView<NewAnime> p) {
                ListCell<NewAnime> cell = new ListCell<NewAnime>() {

                    @Override
                    protected void updateItem(NewAnime t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText("    "+t.getSpot()+"\n\n"+"    "+t.getName()+"\n\n"+"    Rating: "+t.getRating());
                            setFont(Font.font("FontAwesome", FontWeight.BOLD, 14));
                            HBox box = new HBox(20);
                            box.setStyle("-fx-alignment: center;");
                            ImageView image = new ImageView("animeApp/assets/icons/spinnerR.gif");
                            box.getChildren().addAll(t.getButton(),image);
                            setGraphic(box);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    image.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);\n" +
                                            "-fx-padding: 10;\n" +
                                            "-fx-background-color: firebrick;\n" +
                                            "-fx-background-radius: 15;");
                                    if(t.getImgurl()==null||t.getImgurl().trim().equals(""))
                                        image.setImage(new Image("animeApp/assets/icons/noimage.jpg"));
                                    else
                                        image.setImage(new Image(t.getImgurl()));
                                }
                            }).start();
                            //setGraphic(t.getBox());
                            setPadding(new Insets(15, 10, 15, 10));
                        }
                    }
                };

                return cell;
            }

        });
    }
}
