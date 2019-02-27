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
import javafx.stage.Stage;

public class ConfigurationController implements Initializable{
	
	@FXML
	private TextField tbServerIp;	
	@FXML
	private TextField tbGlobalDownloadsPath;
	@FXML
	private CheckBox cbDownloads,cbWatchNextButtons,cbIncrementEpisodesWatched,cbChooseBestQuality,cbAutocreate,cbSpecificUpdaters,cbWatchlistUpdater,cbDownloadsUpdater,cbDatabaseUpdater,cbSeasonsUpdater,cbTopanimeUpdater,cbHotanimeUpdater,cbDownloadsMT,cbWatchlistUpdateMT;
	@FXML
	private ChoiceBox<String> cmbQuality;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> qualities = FXCollections.observableArrayList("Minimum","480p","720p","1080p");
		cmbQuality.setItems(qualities);
		cmbQuality.getSelectionModel().select(3);
		
		tbServerIp.setText(Configuration.getInstance().getServerIp());
		tbGlobalDownloadsPath.setText(Configuration.getInstance().getGlobalDownloadsPath());
		
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
		cbTopanimeUpdater.setSelected(Configuration.getInstance().isTopanimeUpdater());
		cbHotanimeUpdater.setSelected(Configuration.getInstance().isHotanimeUpdater());
		cbWatchlistUpdateMT.setSelected(Configuration.getInstance().isWatchlistUpdateMT());
		cbDownloadsMT.setSelected(Configuration.getInstance().isDownloadsUpdateMT());
		
		if(!cbSpecificUpdaters.isSelected()) {
			cbWatchlistUpdater.setDisable(true);
			cbDownloadsUpdater.setDisable(true);
			cbDatabaseUpdater.setDisable(true);
			cbSeasonsUpdater.setDisable(true);
			cbTopanimeUpdater.setDisable(true);
			cbHotanimeUpdater.setDisable(true);
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
    }
	
	@FXML
	private void enableSpecificUpdaters() {
		if(!cbSpecificUpdaters.isSelected()) {
			cbWatchlistUpdater.setDisable(true);
			cbDownloadsUpdater.setDisable(true);
			cbDatabaseUpdater.setDisable(true);
			cbSeasonsUpdater.setDisable(true);
			cbTopanimeUpdater.setDisable(true);
			cbHotanimeUpdater.setDisable(true);
		}else {
			cbWatchlistUpdater.setDisable(false);
			cbDownloadsUpdater.setDisable(false);
			cbDatabaseUpdater.setDisable(false);
			cbSeasonsUpdater.setDisable(false);
			cbTopanimeUpdater.setDisable(false);
			cbHotanimeUpdater.setDisable(false);
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
		Configuration.getInstance().setTopanimeUpdater(cbTopanimeUpdater.isSelected());
		Configuration.getInstance().setHotanimeUpdater(cbHotanimeUpdater.isSelected());
		Configuration.getInstance().saveConfiguration();
		((Stage) tbServerIp.getScene().getWindow()).close();
	}	
	
	@FXML
	private void cancel() {
		((Stage) tbServerIp.getScene().getWindow()).close();
	}
}
