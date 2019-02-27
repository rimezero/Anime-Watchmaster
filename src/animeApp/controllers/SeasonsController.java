package animeApp.controllers;

import animeApp.databaseUtils.dbControl;
import animeApp.model.SeasonModel;
import animeApp.model.SeasonsSortModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by admin on 8/14/2016.
 */
public class SeasonsController implements Initializable {
    @FXML
    private ChoiceBox cbSeasons;

    @FXML
    private ListView seasonListView;

    private List seasonsList;
    private List seasonDataList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        seasonListView.setStyle("/animeApp/assets/css/listview.css");
        seasonsList = new ArrayList<String>();
        ArrayList<SeasonsSortModel> dbseasons = dbControl.getInstance().getSeasons();
        seasonsList.add("Upcoming");
        for(SeasonsSortModel model : dbseasons){
            seasonsList.add(model.toString());
        }
        ObservableList cbData = FXCollections.observableArrayList(seasonsList);
        //cbSeasons = new ChoiceBox<>(data);
        cbSeasons.setItems(cbData);
        cbSeasons.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setSeasonDataList();
            }
        });
        cbSeasons.getSelectionModel().select(1); //1 is the current season

    }

    private void setSeasonDataList(){
        seasonDataList = new ArrayList<>();
        SeasonModel anime = new SeasonModel(-1,-1,"Loading data...","animeApp/assets/icons/spinnerR.gif","",-1);
        seasonDataList.add(anime);
        ObservableList<SeasonModel> data = FXCollections.observableArrayList(seasonDataList);
        seasonListView.setItems(data);

        reloadListWithImages();

        new Thread(new Runnable() {
            @Override
            public void run() {
                seasonDataList = dbControl.getInstance().getSeasonData((String)cbSeasons.getSelectionModel().getSelectedItem());
                //Collections.sort(list);
                ObservableList<SeasonModel> data = FXCollections.observableArrayList(seasonDataList);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        seasonListView.setItems(data);
                    }
                });
            }
        }).start();

        System.gc();
    }

    private void reloadListWithImages(){
        seasonListView.setCellFactory(new Callback<ListView<SeasonModel>, ListCell<SeasonModel>>() {

            @Override
            public ListCell<SeasonModel> call(ListView<SeasonModel> p) {
                ListCell<SeasonModel> cell = new ListCell<SeasonModel>() {

                    @Override
                    protected void updateItem(SeasonModel t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            if(t.getRating()>=0) {
                                DecimalFormat df = new DecimalFormat("#.#");
                                setText("    " + t.getName() + "\n\n" + "    Genre: " + t.getGenre() + "\n\n" + "    Rating: " + df.format(t.getRating() * 2));
                            }else{
                                setText("    " + t.getName() + "\n\n" + "    Genre: " + t.getGenre());
                            }
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
                                    else {
                                        try {
                                            URLConnection con = new URL(t.getImgurl()).openConnection();
                                            con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                                            image.setImage(new Image(con.getInputStream()));
                                        } catch (IOException e) {
                                            if(!t.getName().equals("Loading data...")){
                                                e.printStackTrace();
                                            }
                                        }

                                    }
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
