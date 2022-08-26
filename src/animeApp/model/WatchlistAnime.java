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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Peitch on 08/03/15.
 */
public class WatchlistAnime  {

    private int id;
    private String name;
    private Button button;
    private int episodeswatched;
    private String episodesWatched;
    private int currentepisode;
    private String currentEpisode;
    private int newepisodes;
    private String lastupdated;
    
    //DOWNLOADS
    private int downloaded;
    private int available;
    private String downloads;
    private Button dlnext;
    private Button watchnext;
    private Button update;
    private String trnameprefix;
    private String savepath;
    private String links;
    
    public void initializeUpdateButton() {
    	update = new Button("Update");
    	update.setFont(Font.font("FontAwesome", FontWeight.BOLD, 14));
    	update.getStylesheets().add("animeApp/assets/css/buttonview.css");
    	update.setMinWidth(130);
    }
    
    public void initializeDlnextButton() {
    	dlnext = new Button("Download Next");
		dlnext.setFont(Font.font("FontAwesome", FontWeight.BOLD, 14));
		dlnext.getStylesheets().add("animeApp/assets/css/buttonview.css");
		dlnext.setMinWidth(130);
    }
    
    public void initializeWatchnextButton() {
    	watchnext = new Button("Watch Next");
    	watchnext.setFont(Font.font("FontAwesome", FontWeight.BOLD, 14));
    	watchnext.getStylesheets().add("animeApp/assets/css/buttonview.css");
    	watchnext.setMinWidth(130);
    }
    
    public void initialize() {
    	if(trnameprefix!=null && !trnameprefix.trim().equals("")) {
	    	if(this.available!=-1) {
	    		if(this.downloaded<0 || this.downloaded>this.available) {
	    			this.downloaded=0;
	    		}
	    		if(this.downloaded>this.episodeswatched && this.newepisodes>0 && !this.savepath.trim().equals("")) {
	    			initializeWatchnextButton();
	    		}
	    		setDownloads(this.downloaded+"/"+this.available);
	    		if(this.downloaded<this.available)
	    			initializeDlnextButton();
	    	}
	    	else {
	    		setDownloads("n/a");
	    	}
	    	
	    	initializeUpdateButton();
    	}else {
    		this.downloads = "";
    		//this.savepath = "";
    	}
    }

    public WatchlistAnime(int id, String name, int episodeswatched, int currentepisode, String lastupdated) {
        this.id = id;
        this.name = name;
        this.episodeswatched=episodeswatched;
        this.episodesWatched = String.valueOf(episodeswatched);
        this.currentepisode=currentepisode;
        this.currentEpisode = String.valueOf(currentepisode);
        this.lastupdated=lastupdated;
        if(currentepisode!=0&&currentepisode>=episodeswatched)
            this.newepisodes = this.currentepisode - this.episodeswatched;
        else
            this.newepisodes = 0;
        
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

                    Scene scene = new Scene(root,850,600);
                    window.initStyle(StageStyle.DECORATED);
                    window.setScene(scene);
                    window.setTitle("Anime Information");
                    window.getIcons().add(new javafx.scene.image.Image(WatchlistAnime.class.getResourceAsStream("../assets/icons/animeWmIcon.png")));
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentEpisode() {
        return currentEpisode;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public void setCurrentEpisode(String currentEpisode) {
        this.currentEpisode = currentEpisode;
    }

    public int getNewepisodes() {
        return newepisodes;
    }

    public void setNewepisodes(int newepisodes) {
        this.newepisodes = newepisodes;
    }

    public int getEpisodeswatched() {
        return episodeswatched;
    }


    public String getEpisodesWatched() {
        return episodesWatched;
    }

    public void setEpisodeswatched(int episodeswatched) {
        this.episodeswatched = episodeswatched;
    }


    public void setEpisodeswatched(String episodeswatched) {
        this.episodeswatched = Integer.valueOf(episodeswatched);
        this.episodesWatched = episodeswatched;
    }


    public int getCurrentepisode() {
        return currentepisode;
    }

    public void setCurrentepisode(int currentepisode) {
        this.currentepisode = currentepisode;
    }

    //DOWNLOADS
    public int getDownloaded() {
		return downloaded;
	}

	public void setDownloaded(int downloaded) {
		this.downloaded = downloaded;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public String getDownloads() {
		return downloads;
	}

	public void setDownloads(String downloads) {
		this.downloads = downloads;
	}

	public Button getWatchnext() {
		return watchnext;
	}

	public void setWatchnext(Button watchnext) {
		this.watchnext = watchnext;
	}

	public Button getDlnext() {
		return dlnext;
	}

	public void setDlnext(Button dlnext) {
		this.dlnext = dlnext;
	}

	public Button getUpdate() {
		return update;
	}

	public void setUpdate(Button update) {
		this.update = update;
	}

	public String getTrnameprefix() {
		return trnameprefix;
	}

	public void setTrnameprefix(String trnameprefix) {
		this.trnameprefix = trnameprefix;
	}

	public String getSavepath() {
		return savepath;
	}

	public String getLinks() {
		return links;
	}

	public void setLinks(String links) {
		this.links = links;
	}

	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
	//DOWNLOADS

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Button getButton() {
        return button;
    }




    @Override
    public String toString() {
        return name;
    }





}