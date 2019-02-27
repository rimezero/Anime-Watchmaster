package animeApp.controllers;

import animeApp.databaseUtils.dbControl;
import animeApp.model.NewAnime;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import animeApp.Main;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AllAnimeController implements Initializable{
    Main main = new Main();

    private boolean showImages = true;

    @FXML
    private ListView listview;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton1;

    private List list;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listview.setStyle("/animeApp/assets/css/listview.css");

        searchButton1.setDefaultButton(true);

        setAllAnimeTableA();


    }

    public void setMainScene(){
        main.setMainscene();
    }

    public void setAllAnimeTableA(){
        setAllAnimeTable("A");
    }
    public void setAllAnimeTableB(){
        setAllAnimeTable("B");
    }
    public void setAllAnimeTableC(){
        setAllAnimeTable("C");
    }
    public void setAllAnimeTableD(){
        setAllAnimeTable("D");
    }
    public void setAllAnimeTableE(){
        setAllAnimeTable("E");
    }
    public void setAllAnimeTableF(){
        setAllAnimeTable("F");
    }
    public void setAllAnimeTableG(){
        setAllAnimeTable("G");
    }
    public void setAllAnimeTableH(){
        setAllAnimeTable("H");
    }
    public void setAllAnimeTableI(){
        setAllAnimeTable("I");
    }
    public void setAllAnimeTableJ(){
        setAllAnimeTable("J");
    }
    public void setAllAnimeTableK(){
        setAllAnimeTable("K");
    }
    public void setAllAnimeTableL(){
        setAllAnimeTable("L");
    }
    public void setAllAnimeTableM(){
        setAllAnimeTable("M");
    }
    public void setAllAnimeTableN(){
        setAllAnimeTable("N");
    }
    public void setAllAnimeTableO(){
        setAllAnimeTable("O");
    }
    public void setAllAnimeTableP(){
        setAllAnimeTable("P");
    }
    public void setAllAnimeTableQ(){
        setAllAnimeTable("Q");
    }
    public void setAllAnimeTableR(){
        setAllAnimeTable("R");
    }
    public void setAllAnimeTableS(){
        setAllAnimeTable("S");
    }
    public void setAllAnimeTableT(){
        setAllAnimeTable("T");
    }
    public void setAllAnimeTableU(){
        setAllAnimeTable("U");
    }
    public void setAllAnimeTableV(){
        setAllAnimeTable("V");
    }
    public void setAllAnimeTableW(){
        setAllAnimeTable("W");
    }
    public void setAllAnimeTableX(){
        setAllAnimeTable("X");
    }
    public void setAllAnimeTableY(){
        setAllAnimeTable("Y");
    }
    public void setAllAnimeTableZ(){
        setAllAnimeTable("Z");
    }
    public void setAllAnimeTableOther(){
        setAllAnimeTable("Other");
    }

    private void setAllAnimeTable(String letter){
        setAllAnimeTableWithImages(letter);
    }

    private void reloadListWithImages(){
        listview.setCellFactory(new Callback<ListView<NewAnime>, ListCell<NewAnime>>() {

            @Override
            public ListCell<NewAnime> call(ListView<NewAnime> p) {
                ListCell<NewAnime> cell = new ListCell<NewAnime>() {

                    @Override
                    protected void updateItem(NewAnime t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText("    "+t.getName()+"\n\n"+"    Genre: "+t.getGenre());
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

    private void setAllAnimeTableWithImages(String letter){
        list = new ArrayList<>();
        NewAnime anime = new NewAnime(-1,"Loading data...","animeApp/assets/icons/spinnerR.gif","");
        list.add(anime);
        ObservableList<NewAnime> data1 = FXCollections.observableArrayList(list);
        listview.setItems(data1);

        reloadListWithImages();

        new Thread(new Runnable() {
            @Override
            public void run() {
                list = dbControl.getInstance().getAnimeData(letter,null,null);
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


    public static ArrayList<String> filterslist = new ArrayList<>();
    public static Stage windownew;
    @FXML
    private void popfilterWindow(){
        try {
            windownew = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/filterswindow.fxml"));
            Parent root = null;
            root = (Parent)fxmlLoader.load();
            Scene scene = new Scene(root,600,300);
            windownew.initStyle(StageStyle.DECORATED);
            windownew.initModality(Modality.APPLICATION_MODAL);
            windownew.setScene(scene);
            windownew.setTitle("Filters by Genre");
            windownew.getIcons().add(new javafx.scene.image.Image(AllAnimeController.class.getResourceAsStream("../assets/icons/animeWmIcon.png")));
            windownew.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void searchForTitle(){
        searchForTitleWithImages();
    }

    private void searchForTitleWithImages(){
        String titlesearch = searchField.getText();

        list = new ArrayList<>();
        NewAnime anime = new NewAnime(-1,"Loading data...","animeApp/assets/icons/spinnerR.gif","");
        list.add(anime);
        ObservableList<NewAnime> data = FXCollections.observableArrayList(list);
        listview.setItems(data);
        reloadListWithImages();

        new Thread(new Runnable() {
            @Override
            public void run() {
                list = dbControl.getInstance().getAnimeData(null,titlesearch,filterslist);
                ObservableList<NewAnime> data = FXCollections.observableArrayList(list);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        listview.setItems(data);
                    }
                });
                filterslist = new ArrayList<>();
            }
        }).start();
        System.gc();
    }




}
