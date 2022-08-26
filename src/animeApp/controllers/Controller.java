package animeApp.controllers;

import animeApp.databaseUtils.HttpRequests;
import animeApp.databaseUtils.StartUpLocation;
import animeApp.databaseUtils.Updaters;
import animeApp.databaseUtils.dbControl;
import animeApp.model.Configuration;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import animeApp.Main;
import animeApp.assets.Dialogs.customDialogs;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    Main main = new Main();
    public static String textDisplay = "about";

    @FXML
    private ImageView shupic;
    @FXML
    private Pane shupicpane;
    @FXML
    private MenuItem mi_update;
    @FXML
    private ProgressBar mainProgressBar;
    @FXML
    public  Label statusLabel;

    public static Stage window;
    public static Stage windownew;

    public static int updateProgress = 1;
    public static int maxUpdateProgress = 2000;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shupic.fitWidthProperty().bind(shupicpane.widthProperty());
        shupic.fitHeightProperty().bind(shupicpane.heightProperty());

        dbControl.getInstance().initializeDatabase();

        //runUpdaters(false);
    }

    private void resetProgress(String statusMessage){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                statusLabel.setText(statusMessage);
            }
        });
        updateProgress = 1;
        maxUpdateProgress = 2000;
    }

    private void runUpdaters(boolean doSync){

        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            	boolean noUpdatersSelected = false;
            	if(Configuration.getInstance().isSpecificUpdaters() && !(Configuration.getInstance().isWatchlistUpdater() || Configuration.getInstance().isDownloadsUpdater() || Configuration.getInstance().isDatabaseUpdater() || Configuration.getInstance().isSeasonsUpdater() || Configuration.getInstance().isImagesUdater() ||Configuration.getInstance().isTopanimeUpdater())) {
            		noUpdatersSelected = true;
            	}
            	
            	if(!Configuration.getInstance().isSpecificUpdaters() || Configuration.getInstance().isWatchlistUpdater() || noUpdatersSelected) {
            		resetProgress("Updating watchlist...");
                    Updaters.watchlistUpdaterThread = null;
                    Updaters.getInstance().watchlistUpdater(false, true);

                    while (Updaters.watchlistUpdaterThread.isAlive()) {
                        Thread.sleep(100);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                updateProgress(updateProgress, maxUpdateProgress);
                            }
                        });

                    }
            	}
            	
            	if(!Configuration.getInstance().isSpecificUpdaters() || Configuration.getInstance().isDownloadsUpdater() || noUpdatersSelected) {
            		resetProgress("Updating downloads...");
                    Updaters.downloadsUpdaterThread = null;
                    Updaters.getInstance().downloadsUpdater(false, true, null);

                    while (Updaters.downloadsUpdaterThread.isAlive()) {
                        Thread.sleep(100);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                updateProgress(updateProgress, maxUpdateProgress);
                            }
                        });

                    }
            	}
                

            	if(!Configuration.getInstance().isSpecificUpdaters() || Configuration.getInstance().isDatabaseUpdater() || noUpdatersSelected) {
            		resetProgress("Updating database...");
                    Updaters.databaseUpdaterThread = null;
                    Updaters.getInstance().databaseUpdater(false, true, doSync);

                    while (Updaters.databaseUpdaterThread.isAlive()) {
                        Thread.sleep(100);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                updateProgress(updateProgress, maxUpdateProgress);
                            }
                        });

                    }
            	}
                

            	if(!Configuration.getInstance().isSpecificUpdaters() || Configuration.getInstance().isSeasonsUpdater() || noUpdatersSelected) {
            		resetProgress("Updating seasons...");
                    Updaters.apUpdaterThread = null;
                    Updaters.getInstance().apUpdater(false, true, doSync);

                    while (Updaters.apUpdaterThread.isAlive()) {
                        Thread.sleep(100);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                updateProgress(updateProgress, maxUpdateProgress);
                            }
                        });

                    }
            	}
            	
            	if(!Configuration.getInstance().isSpecificUpdaters() || Configuration.getInstance().isImagesUdater() || noUpdatersSelected) {
            		resetProgress("Updating images...");
                    Updaters.imagesUpdaterThread = null;
                    Updaters.getInstance().imagesUpdater(false, true, doSync);

                    while (Updaters.imagesUpdaterThread.isAlive()) {
                        Thread.sleep(100);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                updateProgress(updateProgress, maxUpdateProgress);
                            }
                        });

                    }
            	}
                

            	if(!Configuration.getInstance().isSpecificUpdaters() || Configuration.getInstance().isTopanimeUpdater() || noUpdatersSelected) {
            		resetProgress("Updating topanime...");
                    Updaters.topanimeUpdaterThread = null;
                    Updaters.getInstance().topanimeUpdater(false, true, doSync);

                    while (Updaters.topanimeUpdaterThread.isAlive()) {
                        Thread.sleep(100);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                updateProgress(updateProgress, maxUpdateProgress);
                            }
                        });

                    }
            	}
                
                
                



                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //enimeronete to ui component
                        statusLabel.setText("Update completed!");
                        updateProgress(10, 10);
                    }
                });
                return null;
            }
        };
        mainProgressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }
    
    /*
    @FXML
    public void setGlobalDownloadsPath() {
    	String currentPath = dbControl.getInstance().getGlobalDownloadsPath();
    	
    	String result = customDialogs.displayTextInputDialog(currentPath, "Global Downloads Folder", "Set the global downloads path", "Path:");
    	
    	if(!result.equals("Cancel")) {
    		dbControl.getInstance().updateGlobalDownloads(result);
    	}
    }*/

    public void setAllAnimeScene() throws Exception{
        main.setAllanimescene();
    }

    public void mi_updateClicked(){
        runUpdaters(false);
    }

    @FXML
    private void syncDatabases(){
        runUpdaters(true);
    }

    @FXML
    private void exit(){
        main.exit();
    }

    private void loadWindow(String fxml, String title, int width, int height){
        try {
            window = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = null;
            root = (Parent)fxmlLoader.load();
            Scene scene = new Scene(root,width,height);
            window.initStyle(StageStyle.DECORATED);
            window.setScene(scene);
            window.setTitle(title);
            window.getIcons().add(new Image(Controller.class.getResourceAsStream("../assets/icons/animeWmIcon.png")));
            
            //set on active screen
            StartUpLocation startupLoc = new StartUpLocation(width, height);
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

    @FXML
    private void watchlist() {
    	int width = 1243;
    	if(Configuration.getInstance().isEnableDownloads()) {
    		if(Configuration.getInstance().isEnableWatchnext()) {
    			width = 2043;
    		}else {
    			width = 1843;
    		}        	
        }
        loadWindow("../view/watchlist.fxml","Watchlist",width,730);
    }

    @FXML
    private void watchlaterlist(){
        loadWindow("../view/watchlaterlist.fxml","Watchlater list",1240,730);
    }

    @FXML
    private void watchedlist(){
        loadWindow("../view/watched.fxml", "Watched list",1240,730);
    }

    @FXML
    private void seasonsList(){
        loadWindow("../view/seasons.fxml","Seasons",1240,730);
    }

    @FXML
    private void topanimeList(){
        loadWindow("../view/topanime.fxml","Top anime",1240,730);
    }
    
    @FXML
    private void showConfig(){
        loadWindow("../view/Configuration.fxml","Configuration",800,500);
    }
    
    @FXML
    private void helpabout(){
        textDisplay="about";
        loadWindow("../view/textdisplay.fxml","About",1240,730);
    }

    @FXML
    private void showButtonInstructions(){
        textDisplay="button_instructions";
        loadWindow("../view/textdisplay.fxml","Button instructions",1240,730);
    }
}
