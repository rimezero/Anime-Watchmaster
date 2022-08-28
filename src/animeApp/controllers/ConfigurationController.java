package animeApp.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import animeApp.model.Configuration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfigurationController implements Initializable{
	
	@FXML
	private VBox updatersVbox;
	@FXML
	private TextField tbServerIp;	
	@FXML
	private TextField tbGlobalDownloadsPath;
	@FXML
	private CheckBox cbUseLocalImages, cbDownloads,cbWatchNextButtons,cbIncrementEpisodesWatched,cbChooseBestQuality,cbAutocreate,cbSpecificUpdaters,cbWatchlistUpdater,cbDownloadsUpdater,cbDatabaseUpdater,cbSeasonsUpdater,cbImagesUpdater,cbTopanimeUpdater,cbDownloadsMT,cbWatchlistUpdateMT;
	@FXML
	private ChoiceBox<String> cmbQuality;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> qualities = FXCollections.observableArrayList("Minimum","480p","720p","1080p");
		cmbQuality.setItems(qualities);
		cmbQuality.getSelectionModel().select(3);
		
		tbServerIp.setText(Configuration.getInstance().getServerIp());
		tbGlobalDownloadsPath.setText(Configuration.getInstance().getGlobalDownloadsPath());
		
		cbUseLocalImages.setSelected(Configuration.getInstance().getUseLocalImages());
		
		cbDownloads.setSelected(Configuration.getInstance().isEnableDownloads());
		cbWatchNextButtons.setSelected(Configuration.getInstance().isEnableWatchnext());
		cbIncrementEpisodesWatched.setSelected(Configuration.getInstance().isIncrementEpisodesWatched());
		cbChooseBestQuality.setSelected(Configuration.getInstance().isGetHighestQuality());
		cbAutocreate.setSelected(Configuration.getInstance().isAutocreateFolder());
		cbSpecificUpdaters.setSelected(Configuration.getInstance().isSpecificUpdaters());
		cbWatchlistUpdater.setSelected(Configuration.getInstance().isWatchlistUpdater());
		cbDownloadsUpdater.setSelected(Configuration.getInstance().isDownloadsUpdater());
		cbDatabaseUpdater.setSelected(Configuration.getInstance().isDatabaseUpdater());
		cbSeasonsUpdater.setSelected(Configuration.getInstance().isSeasonsUpdater());
		cbImagesUpdater.setSelected(Configuration.getInstance().isImagesUdater());
		cbTopanimeUpdater.setSelected(Configuration.getInstance().isTopanimeUpdater());
		cbWatchlistUpdateMT.setSelected(Configuration.getInstance().isWatchlistUpdateMT());
		cbDownloadsMT.setSelected(Configuration.getInstance().isDownloadsUpdateMT());
		
		if(!cbSpecificUpdaters.isSelected()) {
			cbWatchlistUpdater.setDisable(true);
			cbDownloadsUpdater.setDisable(true);
			cbDatabaseUpdater.setDisable(true);
			cbSeasonsUpdater.setDisable(true);
			cbImagesUpdater.setDisable(true);
			cbTopanimeUpdater.setDisable(true);
		}
		
		if(!cbWatchNextButtons.isSelected()) {
			cbIncrementEpisodesWatched.setDisable(true);
		}
		
		if(!cbDownloads.isSelected()) {
			cbWatchNextButtons.setDisable(true);
			cbChooseBestQuality.setDisable(true);
			cmbQuality.setDisable(true);
		}
		
		if(cbChooseBestQuality.isSelected()) {
			cmbQuality.setDisable(true);
		}
		
		if(Configuration.getInstance().getUseIndex()!=-320) {
			
			//((HBox)updatersVbox.getParent()).getChildren().remove(updatersVbox);
			
			((VBox)cbSpecificUpdaters.getParent()).getChildren().remove(cbSpecificUpdaters);
			((VBox)cbWatchlistUpdater.getParent()).getChildren().remove(cbWatchlistUpdater);
			((VBox)cbDownloadsUpdater.getParent()).getChildren().remove(cbDownloadsUpdater);
			((VBox)cbDatabaseUpdater.getParent()).getChildren().remove(cbDatabaseUpdater);
			((VBox)cbSeasonsUpdater.getParent()).getChildren().remove(cbSeasonsUpdater);
			((VBox)cbImagesUpdater.getParent()).getChildren().remove(cbImagesUpdater);
			((VBox)cbTopanimeUpdater.getParent()).getChildren().remove(cbTopanimeUpdater);
		}
    }
	
	@FXML
	private void enableSpecificUpdaters() {
		if(!cbSpecificUpdaters.isSelected()) {
			cbWatchlistUpdater.setDisable(true);
			cbDownloadsUpdater.setDisable(true);
			cbDatabaseUpdater.setDisable(true);
			cbSeasonsUpdater.setDisable(true);
			cbImagesUpdater.setDisable(true);
			cbTopanimeUpdater.setDisable(true);
		}else {
			cbWatchlistUpdater.setDisable(false);
			cbDownloadsUpdater.setDisable(false);
			cbDatabaseUpdater.setDisable(false);
			cbSeasonsUpdater.setDisable(false);
			cbImagesUpdater.setDisable(false);
			cbTopanimeUpdater.setDisable(false);
		}
	}
	
	@FXML
	private void setDefaultValues() {
		Configuration.getDefaultConfiguration();
		initialize(null, null);
	}
	
	@FXML
	private void enableDownloads() {
		if(!cbDownloads.isSelected()) {
			cbWatchNextButtons.setDisable(true);
			cbChooseBestQuality.setDisable(true);
			cmbQuality.setDisable(true);
			cbIncrementEpisodesWatched.setDisable(true);
			cbAutocreate.setDisable(true);
			cbDownloadsMT.setDisable(true);
		}else {
			cbWatchNextButtons.setDisable(false);
			cbChooseBestQuality.setDisable(false);
			cbIncrementEpisodesWatched.setDisable(false);
			cbAutocreate.setDisable(false);
			cbDownloadsMT.setDisable(false);
			if(!cbChooseBestQuality.isSelected()) {
				cmbQuality.setDisable(false);
			}
		}
	}
	
	@FXML
	private void enableWatchnext() {
		if(!cbWatchNextButtons.isSelected()) {
			cbIncrementEpisodesWatched.setDisable(true);
		}else {
			cbIncrementEpisodesWatched.setDisable(false);
		}
	}	
	
	@FXML
	private void enableBestQuality() {
		if(cbChooseBestQuality.isSelected()) {
			cmbQuality.setDisable(true);
		}else {
			cmbQuality.setDisable(false);
		}
	}	
	
	@FXML
	private void saveConfig() {
		Configuration.getInstance().setServerIp(tbServerIp.getText());
		Configuration.getInstance().setGlobalDownloadsPath(tbGlobalDownloadsPath.getText());
		Configuration.getInstance().setUseLocalImages(cbUseLocalImages.isSelected());
		Configuration.getInstance().setEnableDownloads(cbDownloads.isSelected());
		Configuration.getInstance().setEnableWatchnext(cbWatchNextButtons.isSelected());
		Configuration.getInstance().setIncrementEpisodesWatched(cbIncrementEpisodesWatched.isSelected());
		Configuration.getInstance().setAutocreateFolder(cbAutocreate.isSelected());
		Configuration.getInstance().setWatchlistUpdateMT(cbWatchlistUpdateMT.isSelected());
		Configuration.getInstance().setDownloadsUpdateMT(cbDownloadsMT.isSelected());
		Configuration.getInstance().setGetHighestQuality(cbChooseBestQuality.isSelected());
		Configuration.getInstance().setQualityIndex(cmbQuality.getSelectionModel().getSelectedIndex());
		Configuration.getInstance().setSpecificUpdaters(cbSpecificUpdaters.isSelected());
		Configuration.getInstance().setWatchlistUpdater(cbWatchlistUpdater.isSelected());
		Configuration.getInstance().setDownloadsUpdater(cbDownloadsUpdater.isSelected());
		Configuration.getInstance().setDatabaseUpdater(cbDatabaseUpdater.isSelected());
		Configuration.getInstance().setSeasonsUpdater(cbSeasonsUpdater.isSelected());
		Configuration.getInstance().setImagesUpdater(cbImagesUpdater.isSelected());
		Configuration.getInstance().setTopanimeUpdater(cbTopanimeUpdater.isSelected());
		Configuration.getInstance().saveConfiguration();
		((Stage) tbServerIp.getScene().getWindow()).close();
	}	
	
	@FXML
	private void cancel() {
		((Stage) tbServerIp.getScene().getWindow()).close();
	}
}
