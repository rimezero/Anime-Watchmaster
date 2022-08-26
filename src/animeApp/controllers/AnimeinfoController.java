package animeApp.controllers;

import animeApp.assets.Dialogs.customDialogs;
import animeApp.databaseUtils.StringUtils;
import animeApp.databaseUtils.Updaters;
import animeApp.databaseUtils.dbControl;
import animeApp.model.Animeinfo;
import animeApp.model.Configuration;
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

                
                anime = dbControl.getInstance().getAPAnimeInfo(id);

                loadImageFormUrl(anime);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        title.setText(anime.getTitle());
                        
                        
                        
                        Text des = new Text("Description:");
                        
                        if(!anime.getAltTitles().equals("n/a")) {
                        	if(anime.getAltTitles().contains("????")) {
                        		des = new Text("Alt titles: ");
                        		des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 13));
                                text.getChildren().add(des);
                                des = new Text(anime.getAltTitles().replace("????", ", "));
                                des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
                                text.getChildren().add(des);
                        	}else {
                        		des = new Text("Alt title: ");
                        		des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 13));
                                text.getChildren().add(des);
                                des = new Text(anime.getAltTitles());
                                des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
                                text.getChildren().add(des);
                        	}
                        	                  
                            des = new Text("\n\nDescription:");
                        }
                        
                        des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 13));
                        text.getChildren().add(des);

                        des = new Text("\n"+anime.getDescription());
                        des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 13));
                        text.getChildren().add(des);

                        des = new Text("\n\nGenre: ");
                        des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 12));
                        text.getChildren().add(des);
                        des = new Text(anime.getGenre());
                        des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
                        text.getChildren().add(des);

                        des = new Text("\n\nAnime Type: ");
                        des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 12));
                        text.getChildren().add(des);
                        des = new Text(anime.getAnimetype());
                        des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
                        text.getChildren().add(des);


						des = new Text("\n\nSeason: ");
						des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 12));
						text.getChildren().add(des);
						des = new Text(anime.getSeason());
						des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
						text.getChildren().add(des);

						if (anime.getRating() >= 0) {
							DecimalFormat df = new DecimalFormat("#.#");
							des = new Text("\n\nRating: ");
							des.setFont(Font.font("FontAwesome", FontWeight.BOLD, 12));
							text.getChildren().add(des);
							des = new Text(df.format(anime.getRating() * 2));
							des.setFont(Font.font("FontAwesome", FontWeight.NORMAL, 12));
							text.getChildren().add(des);
						}
                        

                        leftlabel.setText(anime.getTitle());
                        leftlabel.setMaxWidth(170);
                    }
                });
            }
        });
        loadThread.start();



    }


    private void loadImageFormUrl(Animeinfo anime) {

    	if(Configuration.getInstance().getUseLocalImages()) {
    		if(anime.getAnimeplanetId()==-1) {
        		image.setImage(new Image("animeApp/assets/icons/spinnerR.gif"));
        	}else {
        		//get local image from images folder
        		String imagePath = "file:\\\\\\"+System.getProperty("user.dir")+"\\images\\"+anime.getAnimeplanetId();
            	image.setImage(new Image(imagePath));
        	}   
    	}else {
    		if(!anime.getImgurl().isEmpty()) {
                if(anime.getImgurl().equals(" ")) {
                	image.setImage(new Image("animeApp/assets/icons/noimage.jpg"));
                } else if(anime.getImgurl().equals("n/a")) {
                	image.setImage(new Image("animeApp/assets/icons/noimage.jpg"));
                } else {
                    try {
                        URLConnection con = new URL(anime.getImgurl()).openConnection();
                        con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
                        image.setImage(new Image(con.getInputStream()));
                    }catch (IOException e) {
                        if(!anime.getImgurl().equals("Loading data...")){
                            e.printStackTrace();
                        }
                    }
                }

            }else {
            	image.setImage(new Image("animeApp/assets/icons/noimage.jpg"));
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

                if(!dbControl.getInstance().checkIfExistsInWatchlist(anime.getId())) {
                    
					int eps = StringUtils.getEpisodesFromAptype(anime.getAnimetype());
					if(eps==-1) {
						eps=0;
					}
					dbControl.getInstance().insertIntoWatchlist(anime.getId(), 0, eps, "");
                    

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
        if(!anime.getAltTitles().equals("n/a")) {
        	if(anime.getAltTitles().contains("????")) {
        		try {
        			title = anime.getAltTitles().split("????")[0].replace(' ', '+');
				} catch (Exception e) {
					e.printStackTrace();
				}
        		
        	}else {
        		title = anime.getAltTitles().replace(' ', '+');
        	}
        }
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
