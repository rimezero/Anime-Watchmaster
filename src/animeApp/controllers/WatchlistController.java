package animeApp.controllers;

import animeApp.assets.Dialogs.customDialogs;
import animeApp.databaseUtils.HttpRequests;
import animeApp.databaseUtils.Updaters;
import animeApp.databaseUtils.dbControl;
import animeApp.databaseUtils.fileSystemUtils;
import animeApp.model.Configuration;
import animeApp.model.WatchlistAnime;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

import javafx.event.ActionEvent;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Created by Peitch on 08/04/15.
 */
public class WatchlistController implements Initializable {

    @FXML
    private TableView<WatchlistAnime> watchtable;
    @FXML
    private TableColumn<WatchlistAnime, Button> buttoncol;

    @FXML
    private Button button_update;
    @FXML
    private Button button_remove;
    @FXML
    private Button button_updatedl;
    @FXML
    private TableColumn<WatchlistAnime, String> titlecol;

    @FXML
    private TableColumn<WatchlistAnime, String> episodeswatchedcol;
    @FXML
    private TableColumn<WatchlistAnime, String> currentepisodecol;
    @FXML
    private TableColumn<WatchlistAnime, Integer> newepisodescol;
    @FXML
    private TableColumn<WatchlistAnime, String> lastupdatedcol;
    
    //DOWNLOADS
    @FXML
    private TableColumn<WatchlistAnime, String> downloadedcol;
    @FXML
    private TableColumn<WatchlistAnime, Button> watchnextcol;
    @FXML
    private TableColumn<WatchlistAnime, Button> dlnextcol;
    @FXML
    private TableColumn<WatchlistAnime, Button> updatecol;
    @FXML
    private TableColumn<WatchlistAnime, String> trprefixcol;
    @FXML
    private TableColumn<WatchlistAnime, String> savepathcol;
    
    private int enableCounter = 0;
    
    private synchronized void increment() {
    	enableCounter++;
    }
    
    private synchronized void decrement() {
    	enableCounter--;
    }
    
    private synchronized int getEnableCounter() {
    	return enableCounter;
    }
    
    private synchronized void threadStartRequest() {
    	enableCounter++;
    	if(enableCounter==1) {
    		button_update.setDisable(true);
            button_remove.setDisable(true);
            button_updatedl.setDisable(true);
    	}
    }
    
    private synchronized void threadFinishRequest() {
    	enableCounter--;
    	if(enableCounter==0) {
    		button_update.setDisable(false);
            button_remove.setDisable(false);
            button_updatedl.setDisable(false);
    	}
    }
    
    private void disableInnerButtons(boolean disable) {
    	for(WatchlistAnime anime : data) {
    		if(anime.getDlnext()!=null) {
    			anime.getDlnext().setDisable(disable);
    		}
    		if(anime.getUpdate()!=null) {
    			anime.getUpdate().setDisable(disable);
    		}
    	}
    }
    
    private void disableWatchNextButtons(boolean disable) {
    	for(WatchlistAnime anime : data) {
    		if(anime.getWatchnext()!=null) {
    			anime.getWatchnext().setDisable(disable);
    		}
    	}
    }
    

    private ObservableList<WatchlistAnime> data;
    private ArrayList<WatchlistAnime> list;

    EventHandler<ActionEvent> watchNext = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	int rowindex = Integer.valueOf(((Button)event.getSource()).getId());
        	int tmpval = 0;
        	WatchlistAnime anime = null;
        	while(tmpval<data.size() && anime==null) {
        		if(data.get(tmpval).getId()==rowindex) {
        			rowindex = tmpval;
        			anime = data.get(rowindex);
        		}
        		tmpval++;
        	}
        	
        	anime.getWatchnext().setDisable(true);
        	final WatchlistAnime runtimeAnime = anime;
        	final int rindex = rowindex;
        	
        	new Thread(new Runnable() {
                @Override
                public void run() {
                	
                	ArrayList<String> filenameslist = fileSystemUtils.getDirectoryListing(runtimeAnime.getSavepath());
            		ArrayList<Integer> episodenums = new ArrayList<>();
            		ArrayList<String> filenames = new ArrayList<>();
            		for(String filename : filenameslist) {
            			if(filename.contains(runtimeAnime.getTrnameprefix())) {
            				String temp = filename.replace(runtimeAnime.getTrnameprefix(), "");
            				StringTokenizer tokenizer = new StringTokenizer(temp, " ");
            				temp = "";
                			int episodesNum = 0;
                			int tempvalue = 0;
                			while (tokenizer.hasMoreElements() && temp.equals("")) {
        						String temp2 = tokenizer.nextToken();
        						try {
        							episodesNum = Integer.valueOf(temp2);
        							temp = temp2;
        							episodenums.add(episodesNum);
        							filenames.add(filename);
        							
        						}catch (NumberFormatException e) {
        							// TODO: handle exception
        						}
                			}
            			}
            		}
            		
            		int nextepisode = runtimeAnime.getEpisodeswatched()+1;
            		//periptwsi p exei diaforetika noumera sta torrents
            		if(runtimeAnime.getAvailable()>runtimeAnime.getCurrentepisode()) {
            			int temp = runtimeAnime.getNewepisodes()-1;
            			nextepisode = runtimeAnime.getDownloaded()-temp;
            		}
            		
            		int animeindex = episodenums.indexOf(nextepisode);
            		
            		if(animeindex!=-1) {
            			//System.out.println(anime.getSavepath()+"\\"+filenames.get(animeindex));			
            			try {
            				Desktop.getDesktop().open(new File(runtimeAnime.getSavepath()+"\\"+filenames.get(animeindex)));
            				if(Configuration.getInstance().isIncrementEpisodesWatched()) {
            					dbControl.getInstance().updateWatchlistEpisodes(runtimeAnime.getId(), runtimeAnime.getEpisodeswatched()+1, runtimeAnime.getCurrentepisode());
            					WatchlistAnime loadedanime = runtimeAnime;
            					loadedanime.setEpisodeswatched(runtimeAnime.getEpisodeswatched()+1);
            					loadedanime.setEpisodeswatched(String.valueOf(loadedanime.getEpisodeswatched()));
            					loadedanime.setNewepisodes(loadedanime.getCurrentepisode()-loadedanime.getEpisodeswatched());
            					if(loadedanime.getEpisodeswatched()==loadedanime.getDownloaded()) {
            						loadedanime.setWatchnext(null);
            						runtimeAnime.setWatchnext(null);
            					}
            					data.set(rindex, loadedanime);
            				}
            			} catch (Exception e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			}						
            		}
                	
                	Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                        	                 	
                        	reloadTable();
                            System.gc();
                            
                            if(runtimeAnime.getWatchnext() != null) {
                            	runtimeAnime.getWatchnext().setDisable(false);
                            }
                            
                        }
                	});    
                }
        	}).start();
        	
        	
    		
        }
    };
    
    EventHandler<ActionEvent> downloadNext = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	int rowindex = Integer.valueOf(((Button)event.getSource()).getId());
        	int tmpval = 0;
        	WatchlistAnime anime = null;
        	while(tmpval<data.size() && anime==null) {
        		if(data.get(tmpval).getId()==rowindex) {
        			rowindex = tmpval;
        			anime = data.get(rowindex);
        		}
        		tmpval++;
        	}
        	//System.out.println("Hello "+rowindex);
        	
        	anime.getUpdate().setDisable(true);
        	anime.getDlnext().setDisable(true);
        	
        	threadStartRequest();
            
            final WatchlistAnime runtimeAnime = anime;
            final int rindex = rowindex;
            
            
        	new Thread(new Runnable() {
                @Override
                public void run() {             	
                	int currentDownloadEp = runtimeAnime.getDownloaded();
                	currentDownloadEp++;
                	
                	ArrayList<Integer> episodes = new ArrayList<>();
                	ArrayList<String> links = new ArrayList<>();
                	
                	String[] dividedlinks = runtimeAnime.getLinks().split("\\?\\?");
                	for(int i=0; i<dividedlinks.length; i=i+2) {
                		try {
                			episodes.add(Integer.valueOf(dividedlinks[i]));
                			links.add(dividedlinks[i+1]);
                		}catch (Exception e) {
							e.printStackTrace();
						}
                	}
                	
                	if(!fileSystemUtils.checkIfFileIsDirectory("TorrentsFolder")) {
                		fileSystemUtils.createFolder("TorrentsFolder");
                	}
                	
                	int index = episodes.indexOf(currentDownloadEp);
                	if(index!=-1) {
                		if(runtimeAnime.getSavepath().trim().equals("") && Configuration.getInstance().isAutocreateFolder() && !Configuration.getInstance().getGlobalDownloadsPath().trim().equals("")) {
                			String fname = Configuration.getInstance().getGlobalDownloadsPath()+"/"+runtimeAnime.getName().replaceAll("[\\\\|\\/|:|\\*|\\?|\"|\\<|\\>|\\|]","");
                			if(!fileSystemUtils.checkIfFileIsDirectory(fname)) {
                        		fileSystemUtils.createFolder(fname);
                        		runtimeAnime.setSavepath(fname);
                        	}
                		}
                		
                		File file = HttpRequests.downloadDataToFile(links.get(index), "TorrentsFolder/"+links.get(index).replace("https://nyaa.si/download/", ""));
                    	try {
							Desktop.getDesktop().open(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	
                    	dbControl.getInstance().updateDownloads(runtimeAnime.getId(), currentDownloadEp, runtimeAnime.getAvailable(), runtimeAnime.getSavepath(), runtimeAnime.getTrnameprefix(), runtimeAnime.getLinks());
                	}else {
                		customDialogs.displayErrorDialog("No link available", "Cannot find the link for the next episode in the database", "Please update and try again");
                	}
                	
                	WatchlistAnime loadedanime = dbControl.getInstance().getWatchlistAnime(runtimeAnime.getId());
                	
                	//set action listeners for buttons of new anime
                	if(loadedanime.getTrnameprefix()!=null && !loadedanime.getTrnameprefix().equals("")) {
                		loadedanime.getUpdate().setOnAction(updateSpecificDownloads);
                		loadedanime.getUpdate().setId(String.valueOf(loadedanime.getId()));
                		if(loadedanime.getDownloaded()>loadedanime.getEpisodeswatched() && loadedanime.getNewepisodes()>0 && !loadedanime.getSavepath().trim().equals("")) {
                			loadedanime.getWatchnext().setOnAction(watchNext);
                			loadedanime.getWatchnext().setId(String.valueOf(loadedanime.getId()));
                		}
                		if(loadedanime.getAvailable()>0 && loadedanime.getDownloaded()<loadedanime.getAvailable()) {
                			loadedanime.getDlnext().setOnAction(downloadNext);
                			loadedanime.getDlnext().setId(String.valueOf(loadedanime.getId()));
                		}
                	}
                	
                    
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                        	data.set(rindex, loadedanime);
                        	
                            reloadTable();
                            System.gc();
                            
                            /*
                            decrement();
                            if(getEnableCounter()==0) {
                            	button_update.setDisable(false);
                                button_remove.setDisable(false);
                                button_updatedl.setDisable(false);
                            } */
                            threadFinishRequest();
                        }
                    });               	
                }
        	}).start();
        }
    };
    
    EventHandler<ActionEvent> updateSpecificDownloads = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	int rowindex = Integer.valueOf(((Button)event.getSource()).getId());
        	int tmpval = 0;
        	WatchlistAnime anime = null;
        	while(tmpval<data.size() && anime==null) {
        		if(data.get(tmpval).getId()==rowindex) {
        			rowindex = tmpval;
        			anime = data.get(rowindex);
        		}
        		tmpval++;
        	}
        	//System.out.println("Hello "+rowindex);
        	
        	anime.getUpdate().setDisable(true);
        	if(anime.getDlnext()!=null) {
        		anime.getDlnext().setDisable(true);
        	}
        	
        	threadStartRequest();
        	
            //has to be final to be read in thread
            final WatchlistAnime runtimeAnime = anime;
            final int rindex = rowindex;
            
        	//HANDLE CODE HERE
        	new Thread(new Runnable() {
                @Override
                public void run() {
                	Updaters.getInstance().downloadsUpdater(false, false, runtimeAnime);
                	WatchlistAnime loadedanime = dbControl.getInstance().getWatchlistAnime(runtimeAnime.getId());
                	
                	//set action listeners for buttons of new anime
                	if(loadedanime.getTrnameprefix()!=null && !loadedanime.getTrnameprefix().equals("")) {
                		loadedanime.getUpdate().setOnAction(updateSpecificDownloads);
                		loadedanime.getUpdate().setId(String.valueOf(loadedanime.getId()));
                		if(loadedanime.getDownloaded()>loadedanime.getEpisodeswatched() && loadedanime.getNewepisodes()>0 && !loadedanime.getSavepath().trim().equals("")) {
                			loadedanime.getWatchnext().setOnAction(watchNext);
                			loadedanime.getWatchnext().setId(String.valueOf(loadedanime.getId()));
                		}
                		if(loadedanime.getAvailable()>0 && loadedanime.getDownloaded()<loadedanime.getAvailable()) {
                			loadedanime.getDlnext().setOnAction(downloadNext);
                			loadedanime.getDlnext().setId(String.valueOf(loadedanime.getId()));
                		}
                	}
                	
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                        	data.set(rindex, loadedanime);
                        	
                            reloadTable();
                            System.gc();
                            
                            /*
                            decrement();
                            if(getEnableCounter()==0) {
                            	button_update.setDisable(false);
                                button_remove.setDisable(false);
                                button_updatedl.setDisable(false);
                            } */
                            threadFinishRequest();
                        }
                    });
                }
            }).start();
        	
        	      	
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        watchtable.setEditable(true);

        list = dbControl.getInstance().getWatchlistData();
        for(WatchlistAnime tempanime : list) {
        	if(tempanime.getTrnameprefix()!=null && !tempanime.getTrnameprefix().equals("")) {
        		tempanime.getUpdate().setOnAction(updateSpecificDownloads);
        		tempanime.getUpdate().setId(String.valueOf(tempanime.getId()));
        		if(tempanime.getDownloaded()>tempanime.getEpisodeswatched() && tempanime.getNewepisodes()>0 && !tempanime.getSavepath().trim().equals("")) {
        			tempanime.getWatchnext().setOnAction(watchNext);
        			tempanime.getWatchnext().setId(String.valueOf(tempanime.getId()));
        		}
        		if(tempanime.getAvailable()>0&&tempanime.getDownloaded()<tempanime.getAvailable()) {
        			tempanime.getDlnext().setOnAction(downloadNext);
        			tempanime.getDlnext().setId(String.valueOf(tempanime.getId()));
        		}
        	}else {
        		tempanime.setSavepath("");
        	}
        }
        //list.get(0).getButton().setOnAction(eventsda);

        watchtable.setStyle("/animeApp/assets/css/tableview.css");

        //watchtable.setEditable(true);



        buttoncol.setCellValueFactory(new PropertyValueFactory<>("button"));
        buttoncol.setEditable(true);

        titlecol.setMinWidth(300);

        titlecol.setCellValueFactory(new PropertyValueFactory<>("name"));

        episodeswatchedcol.setMinWidth(200);
        episodeswatchedcol.setEditable(true);
        episodeswatchedcol.setCellValueFactory(new PropertyValueFactory<>("episodesWatched"));
        episodeswatchedcol.setCellFactory(TextFieldTableCell.forTableColumn());
        episodeswatchedcol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WatchlistAnime, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WatchlistAnime, String> event) {
            	int newValue = 0;
            	try {
            		newValue = Integer.valueOf(event.getNewValue());
            	}catch (NumberFormatException e) {
            		customDialogs.displayErrorDialog("Wrong value", "The value you have entered is incorrect", "Please enter a new value");
                    WatchlistAnime temp = event.getRowValue();
                    int tempos = event.getTablePosition().getRow();
                    data.set(tempos,temp);
                    reloadTable();
                    return;
				}
                if(newValue>=0) {
                    //((WatchlistAnime) event.getTableView().getItems().get((event.getTablePosition().getRow()))).setEpisodeswatched(Integer.valueOf(event.getNewValue().toString()));
                    WatchlistAnime temp = event.getRowValue();
                    temp.setEpisodeswatched(event.getNewValue());
                    temp.setNewepisodes(temp.getCurrentepisode()-temp.getEpisodeswatched());
                    
                    //check for watchnext button
                    if(temp.getDownloaded()>temp.getEpisodeswatched() && temp.getNewepisodes()>0 && !temp.getSavepath().trim().equals("")) {
                    	temp.initializeWatchnextButton();
                    	temp.getWatchnext().setOnAction(watchNext);
                    	temp.getWatchnext().setId(String.valueOf(temp.getId()));
            		}else {
            			temp.setWatchnext(null);
            		}
                    
                    data.set(event.getTablePosition().getRow(), temp);
                    
                    
                    reloadTable();
                    dbControl.getInstance().updateWatchlistEpisodes(temp.getId(), Integer.valueOf(event.getNewValue()), null);
                    //databaseControl.updateWatchlistField(0,event.getRowValue().getName(), Integer.valueOf(event.getNewValue()));
                    System.gc();
                }
                else {
                    customDialogs.displayErrorDialog("Wrong value", "The value you have entered is incorrect", "Please enter a new value");
                    WatchlistAnime temp = event.getRowValue();
                    int tempos = event.getTablePosition().getRow();
                    data.set(tempos,temp);
                }
            }
        });


        currentepisodecol.setMinWidth(200);
        currentepisodecol.setCellValueFactory(new PropertyValueFactory<>("currentEpisode"));
        currentepisodecol.setEditable(true);
        currentepisodecol.setCellFactory(TextFieldTableCell.forTableColumn());
        currentepisodecol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WatchlistAnime, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WatchlistAnime, String> event) {
            	int newValue = 0;
            	try {
            		newValue = Integer.valueOf(event.getNewValue());
            	}catch (NumberFormatException e) {
            		customDialogs.displayErrorDialog("Wrong value", "The value you have entered is incorrect", "Please enter a new value");
                    WatchlistAnime temp = event.getRowValue();
                    int tempos = event.getTablePosition().getRow();
                    data.set(tempos,temp);
                    reloadTable();
                    return;
				}
                if(newValue>=0) {
                    //((WatchlistAnime) event.getTableView().getItems().get((event.getTablePosition().getRow()))).setCurrentepisode(Integer.valueOf(event.getNewValue().toString()));
                    WatchlistAnime temp = event.getRowValue();
                    temp.setCurrentepisode(Integer.valueOf(event.getNewValue()));
                    temp.setCurrentEpisode(event.getNewValue());
                    temp.setNewepisodes(temp.getCurrentepisode()-temp.getEpisodeswatched());
                    data.set(event.getTablePosition().getRow(), temp);

                    reloadTable();
                    dbControl.getInstance().updateWatchlistEpisodes(temp.getId(), null, Integer.valueOf(event.getNewValue()));
                    //databaseControl.updateWatchlistField(1,event.getRowValue().getName(), Integer.valueOf(event.getNewValue()));
                    System.gc();
                }
                else {
                    customDialogs.displayErrorDialog("Wrong value", "The value you have entered is incorrect", "Please enter a new value");
                    WatchlistAnime temp = event.getRowValue();
                    int tempos = event.getTablePosition().getRow();
                    data.set(tempos,temp);
                }
            }
        });

        newepisodescol.setMinWidth(200);
        newepisodescol.setCellValueFactory(new PropertyValueFactory<>("newepisodes"));
        reloadTable();





        titlecol.setCellFactory(new Callback<TableColumn<WatchlistAnime, String>, TableCell<WatchlistAnime, String>>() {
            @Override
            public TableCell<WatchlistAnime, String> call(TableColumn<WatchlistAnime, String> param) {
                return new TableCell<WatchlistAnime, String>() {
                    @Override
                    public void updateItem(String s, boolean b) {
                        super.updateItem(s, b);

                        if (!b) {
                            setText(s);
                            setFont(Font.font("FontAwesome", FontWeight.BOLD, 14));
                        }

                    }
                };
            }
        });

        lastupdatedcol.setCellValueFactory(new PropertyValueFactory<WatchlistAnime, String>("lastupdated"));
        lastupdatedcol.setMinWidth(200);
        
        
        //DOWNLOADS
        downloadedcol.setMinWidth(200);
        downloadedcol.setCellValueFactory(new PropertyValueFactory<WatchlistAnime, String>("downloads"));
        downloadedcol.setEditable(true);
        downloadedcol.setCellFactory(TextFieldTableCell.forTableColumn());
        downloadedcol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WatchlistAnime, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WatchlistAnime, String> event) {
            	WatchlistAnime temp = event.getRowValue();
            	
            	int newValue = 0;
            	try {
        			newValue = Integer.valueOf(event.getNewValue());
        			if(newValue>temp.getAvailable()) {
        				customDialogs.displayErrorDialog("Wrong value", "Downloaded number cannot exceed available", "Please enter a new value");
        				int tempos = event.getTablePosition().getRow();
                        data.set(tempos,temp);
                        reloadTable();
        				return;
        			}
        		} catch (NumberFormatException e) {
        			customDialogs.displayErrorDialog("Wrong value", "The value you have entered is incorrect", "Please enter a new value");
        			int tempos = event.getTablePosition().getRow();
                    data.set(tempos,temp);
                    reloadTable();
        			return;
				}
            	
            	
                temp.setDownloaded(newValue);
                temp.setDownloads(temp.getDownloaded()+"/"+temp.getAvailable());
                
                if(temp.getDownloaded()==temp.getAvailable()) {
                	temp.setDlnext(null);
                }else {
                	if(temp.getDlnext()==null) {
                		temp.initializeDlnextButton();
                		temp.getDlnext().setOnAction(downloadNext);
            			temp.getDlnext().setId(String.valueOf(temp.getId()));
                	}
                }
                
                data.set(event.getTablePosition().getRow(), temp);
                reloadTable();
                
                dbControl.getInstance().updateDownloads(temp.getId(), temp.getDownloaded(), temp.getAvailable(), temp.getSavepath(), temp.getTrnameprefix(), temp.getLinks());
                
                System.gc();
            }
        });
        
        watchnextcol.setMinWidth(200);
        watchnextcol.setCellValueFactory(new PropertyValueFactory<>("watchnext"));
        watchnextcol.setEditable(true);
        
        dlnextcol.setMinWidth(200);
        dlnextcol.setCellValueFactory(new PropertyValueFactory<>("dlnext"));
        dlnextcol.setEditable(true);
        
        updatecol.setMinWidth(200);
        updatecol.setCellValueFactory(new PropertyValueFactory<>("update"));
        updatecol.setEditable(true);
        
        
        trprefixcol.setMinWidth(200);   
        trprefixcol.setCellValueFactory(new PropertyValueFactory<WatchlistAnime, String>("trnameprefix"));
        trprefixcol.setEditable(true);
        trprefixcol.setCellFactory(TextFieldTableCell.forTableColumn());
        trprefixcol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WatchlistAnime, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WatchlistAnime, String> event) {
            	String newValue = "";
            	String tempsavepath = "";
            	WatchlistAnime temp = event.getRowValue();
            	if(!event.getNewValue().trim().equals("")) {
            		newValue = event.getNewValue();          		
            		temp = dbControl.getInstance().getWatchlistAnime(temp.getId());
            		temp.setTrnameprefix(newValue);
            		temp.initialize();
            		//temp.initializeUpdateButton();
            		temp.getUpdate().setOnAction(updateSpecificDownloads);
            		temp.getUpdate().setId(String.valueOf(temp.getId()));
            		if(temp.getDlnext()!=null) {
            			temp.getDlnext().setOnAction(downloadNext);
            			temp.getDlnext().setId(String.valueOf(temp.getId()));
            		}
            	}else {
            		temp.setDownloads("");
            		tempsavepath = temp.getSavepath();
            		temp.setSavepath("");
            		temp.setDlnext(null);
            		temp.setUpdate(null);
            	}       	
                temp.setTrnameprefix(newValue);
                
                data.set(event.getTablePosition().getRow(), temp);
                reloadTable();
                if(dbControl.getInstance().checkIfExistsInDownloads(temp.getId())) {
                	dbControl.getInstance().updateDownloadPaths(temp.getId(), newValue, null);
                }else {
                	dbControl.getInstance().insertIntoDownloads(temp.getId(), temp.getDownloaded(), temp.getAvailable(), tempsavepath, temp.getTrnameprefix(), temp.getLinks());
                }
                
                System.gc();
            }
        });
        
        savepathcol.setMinWidth(200);
        savepathcol.setCellValueFactory(new PropertyValueFactory<WatchlistAnime, String>("savepath"));
        savepathcol.setEditable(true);
        savepathcol.setCellFactory(TextFieldTableCell.forTableColumn());
        savepathcol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WatchlistAnime, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WatchlistAnime, String> event) {
            	String newValue = "";       	
            	if(!event.getNewValue().trim().equals("")) {
            		newValue = event.getNewValue();
            	}
            	WatchlistAnime temp = event.getRowValue();
                temp.setSavepath(newValue);
                data.set(event.getTablePosition().getRow(), temp);
                reloadTable();
                if(dbControl.getInstance().checkIfExistsInDownloads(temp.getId())) {
                	dbControl.getInstance().updateDownloadPaths(temp.getId(), null, newValue);
                }else {
                	dbControl.getInstance().insertIntoDownloads(temp.getId(), temp.getDownloaded(), temp.getAvailable(), temp.getSavepath(), temp.getTrnameprefix(), temp.getLinks());
                }            
                System.gc();
            }
        });
        
        if(!Configuration.getInstance().isEnableDownloads()) {
        	downloadedcol.setVisible(false);
        	dlnextcol.setVisible(false);
        	updatecol.setVisible(false);
        	trprefixcol.setVisible(false);
        	savepathcol.setVisible(false);
        	watchnextcol.setVisible(false);
        	button_updatedl.setVisible(false);
        }
        if(!Configuration.getInstance().isEnableWatchnext()) {
        	watchnextcol.setVisible(false);
        }


        data=FXCollections.observableArrayList(list);
        watchtable.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {

                //for(Integer k : newepisodescol.get)
                reloadTable();
                System.gc();


                //newepisodescol.getCellFactory().
            }
        });
        watchtable.setItems(data);      
        System.gc();
    }



    @FXML
    public void Update_watchlist(){
        button_update.setDisable(true);
        button_remove.setDisable(true);
        button_updatedl.setDisable(true);
        disableInnerButtons(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Updaters.getInstance().watchlistUpdater(false,false);
                list = dbControl.getInstance().getWatchlistData();
                for(WatchlistAnime tempanime : list) {
                	if(tempanime.getTrnameprefix()!=null && !tempanime.getTrnameprefix().equals("")) {
                		tempanime.getUpdate().setOnAction(updateSpecificDownloads);
                		tempanime.getUpdate().setId(String.valueOf(tempanime.getId()));
                		if(tempanime.getAvailable()>0 && tempanime.getDownloaded()<tempanime.getAvailable()) {
                			tempanime.getDlnext().setOnAction(downloadNext);
                			tempanime.getDlnext().setId(String.valueOf(tempanime.getId()));
                		}
                	}
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0; i<list.size(); i++){
                            data.set(i,list.get(i));
                        }
                        reloadTable();
                        System.gc();
                        button_update.setDisable(false);
                        button_remove.setDisable(false);
                        button_updatedl.setDisable(false);
                        //disableInnerButtons(false);
                    }
                });
            }
        }).start();
    }
    
    @FXML
    public void Update_downloads(){
        button_update.setDisable(true);
        button_remove.setDisable(true);
        button_updatedl.setDisable(true);
        disableInnerButtons(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
            	Updaters.getInstance().downloadsUpdater(false, false, null);
                list = dbControl.getInstance().getWatchlistData();
                for(WatchlistAnime tempanime : list) {
                	if(tempanime.getTrnameprefix()!=null && !tempanime.getTrnameprefix().equals("")) {
                		tempanime.getUpdate().setOnAction(updateSpecificDownloads);
                		tempanime.getUpdate().setId(String.valueOf(tempanime.getId()));
                		if(tempanime.getAvailable()>0 && tempanime.getDownloaded()<tempanime.getAvailable()) {
                			tempanime.getDlnext().setOnAction(downloadNext);
                			tempanime.getDlnext().setId(String.valueOf(tempanime.getId()));
                		}
                	}
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0; i<list.size(); i++){
                            data.set(i,list.get(i));
                        }
                        reloadTable();
                        System.gc();
                        button_update.setDisable(false);
                        button_remove.setDisable(false);
                        button_updatedl.setDisable(false);
                        //disableInnerButtons(false);
                    }
                });
            }
        }).start();
    }

    private void reloadTable(){
        newepisodescol.setCellFactory(new Callback<TableColumn<WatchlistAnime, Integer>, TableCell<WatchlistAnime, Integer>>() {
            @Override
            public TableCell<WatchlistAnime, Integer> call(TableColumn<WatchlistAnime, Integer> param) {
                return new TableCell<WatchlistAnime, Integer>() {
                    @Override
                    public void updateItem(Integer s, boolean b) {
                        super.updateItem(s, b);
                        if (!b) {
                            setText(String.valueOf(s));
                            if(s > 0) {
                                this.setStyle(" -fx-background-color: #FF4D4D;");
                                this.setFont(Font.font("goodfont", FontWeight.BOLD, 14));
                                this.setAlignment(Pos.CENTER);
                            }
                        }
                    }
                };

            }
        });
    }


    @FXML
    public void Remove_selected_anime(){
        ObservableList<WatchlistAnime> animeSelected, allanime;
        allanime = watchtable.getItems();
        animeSelected = watchtable.getSelectionModel().getSelectedItems();

        for(WatchlistAnime anime : animeSelected){
            dbControl.getInstance().deleteFromWatchlist(anime.getId());
            if(dbControl.getInstance().checkIfExistsInDownloads(anime.getId())) {
            	dbControl.getInstance().deleteFromDownloads(anime.getId());
            }
            allanime.remove(anime);
        }

        reloadTable();

        System.gc();

        //animeSelected.forEach(allanime::remove);
    }

    /*
    @FXML
    public void Save_changes(){
        ObservableList<WatchlistAnime> animlist = watchtable.getItems();
        ArrayList<WatchlistAnime> animelist = new ArrayList<>();
        for(WatchlistAnime anim : animlist){
            animelist.add(anim);
        }
        databaseControl.saveChangesToWatchlist(animelist);
       // customDialogs.displayInformationDialog("Watchlist save", "Changes successfully saved!", "");
    }
    */







}
