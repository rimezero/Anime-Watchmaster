package animeApp.controllers;

import animeApp.assets.Dialogs.customDialogs;
import animeApp.databaseUtils.Updaters;
import animeApp.databaseUtils.dbControl;
import animeApp.model.Animeinfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.Desktop;
import java.io.IOException;
import java.net.*;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * Created by mikros tim tim on 08/04/15.
 */
public class AnimeinfoController implements Initializable {

    private int id;
    private boolean fromAP=false;
    private Animeinfo anime;
    private Thread loadThread;

    @FXML
    private Label title;

    @FXML
    private Label leftlabel;

    @FXML
    private ImageView image;

    @FXML
    private TextFlow text;

    @FXML
    private HBox hbox;
    
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vbox;

    @FXML
    private HBox hboxcenter;

    @FXML
    private HBox hboxtop;

    @FXML
    private Button baddtowlist;

    @FXML
    private Button btnWatchOnline;

    @FXML
    private Button btnAddWLater;

    @FXML
    private Button btnAddWatched;



    public void setId(int id) {
        this.id = id;
    }
    public void setFromAP(boolean fromAP) {
        this.fromAP = fromAP;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
        image.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);\n" +
                "-fx-padding: 10;\n" +
                "-fx-background-color: firebrick;\n" +
                "-fx-background-radius: 15;");

        hbox.setStyle("-fx-background-color: #2f4f4f;\n" +
                "    -fx-padding: 15;\n" +
                "    -fx-spacing: 10;");

        vbox.setStyle("-fx-background-color: #99CCFF;\n" +
                "-fx-background-radius: 15;"+
                "    -fx-padding: 15;\n" +
                "    -fx-spacing: 10;");

        hboxcenter.setStyle("-fx-background-color: #99CCFF;\n" +
                "-fx-padding: 15;\n" +
                "-fx-background-radius: 15;"+
                "-fx-spacing: 10;");

        hboxtop.setStyle("-fx-background-color: #99CCFF;\n" +
                "-fx-padding: 15;\n" +
                "-fx-background-radius: 15;"+
                "-fx-spacing: 10;");
        
        text.setStyle("-fx-background-color: #99CCFF;");
        scrollPane.setStyle("-fx-background-color: #99CCFF;");
        //text.setMaxWidth(hbox.getWidth()-30);
        

        loadThread =  new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                if(fromAP) {
                    baddtowlist.setVisible(false);
                    btnAddWatched.setVisible(false);
                    btnAddWLater.setVisible(false);
                    btnWatchOnline.setVisible(false);
                    anime = dbControl.getInstance().getAPAnimeInfo(id);
                }else
                    anime = dbControl.getInstance().getAnimeInfo(id);

                loadImageFormUrl(anime.getImgurl());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        title.setText(anime.getTitle());

                        Text des = new Text("Description:");
                        des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 13));
                        text.getChildren().add(des);

                        des = new Text("\n"+anime.getDescription());
                        des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 13));
                        text.getChildren().add(des);

                        des = new Text("\n\n Genre: ");
                        des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 12));
                        text.getChildren().add(des);
                        des = new Text(anime.getGenre());
                        des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
                        text.getChildren().add(des);

                        des = new Text("\n\n Anime Type: ");
                        des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 12));
                        text.getChildren().add(des);
                        des = new Text(anime.getAnimetype());
                        des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
                        text.getChildren().add(des);

                        if(!fromAP) {
                            des = new Text("\n\n Episodes: ");
                            des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 12));
                            text.getChildren().add(des);
                            des = new Text(anime.getEpisodes());
                            des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
                            text.getChildren().add(des);

                            des = new Text("\n\n Age rating: ");
                            des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 12));
                            text.getChildren().add(des);
                            des = new Text(anime.getAgerating());
                            des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
                            text.getChildren().add(des);
                        }else{
                            des = new Text("\n\n Season: ");
                            des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 12));
                            text.getChildren().add(des);
                            des = new Text(anime.getSeason());
                            des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
                            text.getChildren().add(des);

                            if(anime.getRating()>=0) {
                                DecimalFormat df = new DecimalFormat("#.#");
                                des = new Text("\n\n Rating: ");
                                des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 12));
                                text.getChildren().add(des);
                                des = new Text(df.format(anime.getRating() * 2));
                                des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
                                text.getChildren().add(des);
                            }
                        }

                        leftlabel.setText(anime.getTitle());
                        leftlabel.setMaxWidth(170);
                    }
                });
            }
        });
        loadThread.start();



    }


    private void loadImageFormUrl(String url) {

        if(!url.isEmpty()) {
            if(url.equals(" ")) {

            } else if(url.equals("    ")) {

            } else {
                try {
                    URLConnection con = new URL(url).openConnection();
                    con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                    image.setImage(new Image(con.getInputStream()));
                }catch (IOException e) {
                    if(!url.equals("Loading data...")){
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    @FXML
    public void addToWatchlaterlist() {
        if(!dbControl.getInstance().checkIfExistsInWatchLaterlist(anime.getId())) {
            dbControl.getInstance().insertIntoWatchLater(anime.getId());
            customDialogs.displayInformationDialog("Watch later list add", "Successfully added to watch later list!", "");
        }else{
            customDialogs.displayErrorDialog("Already in watchlist","The anime is already in the watchlater list","");
        }
    }

    @FXML
    public void addToWatchedList() {
        if(!dbControl.getInstance().checkIfExistsInWatchedlist(anime.getId())) {
            dbControl.getInstance().insertIntoWatched(anime.getId());
            customDialogs.displayInformationDialog("Watch later list add", "Successfully added to watched list!", "");
        }else{
            customDialogs.displayErrorDialog("Already in watchlist","The anime is already in the watched list","");
        }
    }

    @FXML
    public void addToWatchlist() {
        baddtowlist.setDisable(true);

        new Thread(new Runnable() {
            @Override
            public void run() {

                boolean isNotOnoing = true;
                if(!dbControl.getInstance().checkIfExistsInWatchlist(anime.getId())) {
                    try{
                        int eps = Integer.valueOf(anime.getEpisodes());
                        dbControl.getInstance().insertIntoWatchlist(anime.getId(),0,eps,"");
                    }catch (NumberFormatException ex){
                        if(anime.getEpisodes().contains("Ongoing")){
                            isNotOnoing=false;
                            System.out.println("Does contain ongoing executing update");
                            dbControl.getInstance().insertIntoWatchlist(anime.getId(), 0, 0, "");
                            Updaters.getInstance().watchlistUpdater(false,true);
                        }else{
                            dbControl.getInstance().insertIntoWatchlist(anime.getId(),0,0,"");
                        }
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            baddtowlist.setDisable(false);
                            customDialogs.displayInformationDialog("Watchlist add", "Successfully added to watchlist!", "");
                        }
                    });

                }else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            baddtowlist.setDisable(false);
                            customDialogs.displayErrorDialog("Already in watchlist", "The anime is already in the wathclist", "You cannot add an anime twice in the watchlist");
                        }
                    });

                }
                /*
                if(databaseControl.checkIfInWatchlaterlist(animetitle))
                    databaseControl.deleteFromWatchlaterlist(animetitle);*/


            }
        }).start();
    }

    @FXML
    public void watchOnline() {
        String choice = customDialogs.displaySiteChoiceDialog();

        if(choice.equals(" ")) {
            return;
        }else {
            String result = dbControl.getInstance().getLink(choice,anime.getId());
            if (result.equals("na")) {
                customDialogs.displayErrorDialog("Error", "The url was not found", "Try another site");
                return;
            } else {
                URL url = null;
                try {
                    url = new URL(result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                openWebpage(url);
            }
        }
    }

    @FXML
    public void download() {
        String title = anime.getTitle().replace(' ', '+');
        String link = "http://www.nyaa.si/?f=0&c=0_0&q="+title+"&s=seeders&o=desc";
        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        openWebpage(url);
    }
    private static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }




}
