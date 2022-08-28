package animeApp.databaseUtils;

import animeApp.assets.Dialogs.customDialogs;
import animeApp.controllers.Controller;
import animeApp.model.AnimeAP;
import animeApp.model.Configuration;
import animeApp.model.NewAnime;
import animeApp.model.WatchlistAnime;
import animeApp.model.WatchlistUpdaterModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by admin on 11/8/2016.
 */
public class Updaters {

    public static Updaters updatersInstance = null;

    public static Updaters getInstance(){
        if(updatersInstance==null){
            synchronized (Updaters.class){
                if(updatersInstance==null){
                    updatersInstance = new Updaters();
                }
            }
        }
        return updatersInstance;
    }

    public static Thread databaseUpdaterThread;
    public static Thread apUpdaterThread;
    public static Thread imagesUpdaterThread;
    public static Thread topanimeUpdaterThread;
    public static Thread watchlistUpdaterThread;
    public static Thread downloadsUpdaterThread;
    private LinkedList<Integer> newAnimeIDS;

    public void databaseUpdater(boolean showDialogs, boolean async, boolean doSync){
        if(databaseUpdaterThread!=null&&databaseUpdaterThread.isAlive()){
            if(showDialogs){
                customDialogs.displayInformationDialog("DatabaseUpdater","Update already running","Please wait until update finishes");
                Controller.updateProgress = 10;
                Controller.maxUpdateProgress = 10;
                return;
            }
            Controller.updateProgress = 10;
            Controller.maxUpdateProgress = 10;
            return;
        }

        if(async){
            databaseUpdaterThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    databaseUpdater(showDialogs,doSync);
                }
            });
            databaseUpdaterThread.start();
        }else{
            databaseUpdater(showDialogs,doSync);
        }
    }

    private void databaseUpdater(boolean showDialogs, boolean doSync){
        final String prefix = "databaseUpdater - ";
        dbControl dbinstance = dbControl.getInstance();
        JSONObject versionjob = HttpRequests.getVersion();
        IDReassign idReassign = null;
        if(versionjob!=null){
            int localversion = 0;
            if(!doSync)
                localversion = dbinstance.getVersion();
            else {
            	idReassign = new IDReassign();
            	dbControl.getInstance().deleteFromAnimeinfo(-5);
            	dbControl.getInstance().deleteFromAnimelinks(-5);
            }
            int onlineversion = -1;

            try {
                onlineversion = versionjob.getInt("version");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(prefix+"localversion: "+localversion+" onlineversion: "+onlineversion);
            JSONArray jarr = new JSONArray();

            if (onlineversion > localversion) {
                jarr = HttpRequests.getAnimeinfoandlinksbyversion(localversion);

                if(jarr != null && jarr.length() > 0) {
                    if(jarr.length()==1){
                        Controller.maxUpdateProgress = 10;
                    }else {
                        Controller.maxUpdateProgress = jarr.length()-1;
                    }
                    JSONObject job = null;
                    try {
                        int lastVersion = ((JSONObject) jarr.get(0)).getInt("version");
                        int currentVersion = 0;

                        for (int i = 0; i < jarr.length(); i++) {
                            job = (JSONObject) jarr.get(i);
                            int id;

                            currentVersion=job.getInt("version");
                            if(currentVersion>lastVersion) {
                                dbinstance.updateVersion(lastVersion);
                                lastVersion=currentVersion;
                            }

                            id = dbinstance.getAnimeID(job.getString("title"));
                            if(id==-1){
                                boolean s = dbinstance.insertIntoAnimeinfo(job.getString("title"), job.getString("imgurl"), job.getString("genre"), job.getString("episodes"), job.getString("animetype"), job.getString("agerating"), job.getString("description"),job.getString("annimgurl"));
                                id = dbinstance.getAnimeID(job.getString("title"));
                                s = dbinstance.insertIntoAnimelinks(id,job.getString("frlink"),job.getString("ultimalink"), job.getString("MALlink"));
                                //System.out.println(s);
                            } else {
                                boolean s = dbinstance.updateAnimeinfo(id, job.getString("title"), job.getString("imgurl"), job.getString("genre"), job.getString("episodes"), job.getString("animetype"), job.getString("agerating"), job.getString("description"),job.getString("annimgurl"));
                                if(dbinstance.checkIfExistsInAnimelinks(id)) {
                                    s = dbinstance.updateAnimelinks(id, job.getString("frlink"), job.getString("ultimalink"),job.getString("MALlink"));
                                }else{
                                    s = dbinstance.insertIntoAnimelinks(id,job.getString("frlink"),job.getString("ultimalink"),job.getString("MALlink"));
                                }
                            }
                            Controller.updateProgress = i;
                            
                        }
                        dbinstance.updateVersion(onlineversion);
                        if(jarr.length()==1){
                            Controller.updateProgress=10;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                
                if(doSync) {
                	idReassign.reassignIDS();            	
                }


            } else {
                System.out.println(prefix + "Up to date update not needed");
                Controller.updateProgress = 10;
                Controller.maxUpdateProgress = 10;
                if(showDialogs){
                    javafx.application.Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            customDialogs.displayInformationDialog("Database Update", "Database is up to date", "Update not needed");
                        }
                    });
                }
            }
        }
    }
    private synchronized int getIdFromList() {
    	if(newAnimeIDS.isEmpty()) {
    		return -5;
    	}
    	int id = newAnimeIDS.get(0);
    	if(id==-10) {
    		return -10;
    	}
    	newAnimeIDS.remove(0);
    	return id;
    }

    public void apUpdater(boolean showDialogs, boolean async, boolean doSync){
        if((databaseUpdaterThread!=null&&databaseUpdaterThread.isAlive())||(apUpdaterThread!=null&&apUpdaterThread.isAlive())){
            if(showDialogs){
                customDialogs.displayInformationDialog("SeasonsUpdater","Update already running","Please wait until update finishes");
                Controller.updateProgress = 10;
                Controller.maxUpdateProgress = 10;
                return;
            }
            Controller.updateProgress = 10;
            Controller.maxUpdateProgress = 10;
            return;
        }

        if(async){
            apUpdaterThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    apUpdater(showDialogs,doSync);
                }
            });
            apUpdaterThread.start();
        }else{
            apUpdater(showDialogs,doSync);
        }
    }

    private void apUpdater(boolean showDialogs, boolean doSync){
        final String prefix = "apUpdater - ";
        final String serverUrl = "http://"+Configuration.getInstance().getServerIp()+"/animedraw/images/";
        dbControl dbinstance = dbControl.getInstance();
        JSONObject versionjob = HttpRequests.getVersion();
        JSONObject syncVersionJob = HttpRequests.getSyncVersion();
        IDReassign idReassign = null;
        int numberOfThreads = Configuration.getInstance().getNumberOfThreadsImagesUpdater();
        Controller.updateProgress=0;
        newAnimeIDS = new LinkedList<>();
        
        if(!fileSystemUtils.checkIfFileIsDirectory("Images")) {
    		fileSystemUtils.createFolder("Images");
    	}
        
        if(versionjob!=null){
        	
        	//sync version
        	int onlineSyncVersion = -1;
        	try {
        		onlineSyncVersion = syncVersionJob.getInt("syncversion");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        	int localSyncVersion = dbinstance.getSyncVersion();
        	if(localSyncVersion<onlineSyncVersion) {
        		doSync=true;
        	}
        	System.out.println(prefix+"sync version - localversion: "+localSyncVersion+" onlineversion: "+onlineSyncVersion);
        	
        	//version
            int localversion = 0;
            if(!doSync)
                localversion = dbinstance.getAPVersion();
            else {
            	System.out.println("Performing database sync");
            	idReassign = new IDReassign();
            	dbControl.getInstance().deleteFromAPanimeinfo(-5);
            }
            int onlineversion = -1;

            try {
                onlineversion = versionjob.getInt("version");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(prefix+"localversion: "+localversion+" onlineversion: "+onlineversion);
            JSONArray jarr = new JSONArray();
            
            ArrayList<Thread> threadsList = new ArrayList<>();
            
            

            if (onlineversion > localversion) {
                jarr = HttpRequests.getAPanimeinfoData(localversion);
                System.out.println("Number of threads: "+numberOfThreads);
              //Images downloader thread
                for(int i=0; i<numberOfThreads; i++) {
     	
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                        	int id = 0;
                        	while(id!=-10) {
                        		try {
    								Thread.sleep(10);
    							} catch (InterruptedException e) {
    								e.printStackTrace();
    							}
								id = getIdFromList();
								if (id != -10 && id != -5) {
									boolean downloadedSuccessfully = false;
									// System.out.println("Server url: "+serverUrl+id);
									downloadedSuccessfully = HttpRequests.downloadDataToFileV(serverUrl + id, "images/" + id);
									if(!downloadedSuccessfully) {
										System.out.println("Retrying to download image with id: "+id);
										downloadedSuccessfully = HttpRequests.downloadDataToFileV(serverUrl + id, "images/" + id);
									}
									if(!downloadedSuccessfully) {
										System.out.println("Retrying to download image with id: "+id);
										downloadedSuccessfully = HttpRequests.downloadDataToFileV(serverUrl + id, "images/" + id);
									}
									Controller.updateProgress++;
								}

                        	}
                        }
                    });
                    
                    threadsList.add(t);
                    
                    t.start();              
                    
                    
                }
              //Images downloader thread
                

                if(jarr != null && jarr.length() > 0) {
                    if(jarr.length()==1){
                        Controller.maxUpdateProgress = 10;
                    }else {
                        Controller.maxUpdateProgress = jarr.length()-1;
                    }
                    JSONObject job = null;
                    try {

                        job = (JSONObject) jarr.get(0);
                        String currentSeason = job.getString("season");
                        dbinstance.updateAPCurrentSeason(currentSeason);

                        int lastVersion = ((JSONObject) jarr.get(1)).getInt("version");
                        int currentVersion = 0;

                        for (int i = 1; i < jarr.length(); i++) {
                            job = (JSONObject) jarr.get(i);
                            int id;

                            currentVersion=job.getInt("version");
                            if(currentVersion>lastVersion) {
                                dbinstance.updateAPVersion(lastVersion);
                                lastVersion=currentVersion;
                            }

                            if(job.getInt("id")>0) {
                            	id = dbinstance.getAPAnimeID(job.getInt("id"));
                            }else {
                            	id = dbinstance.getAPAnimeID(job.getString("title"));
                            }                         
                            if(id==-1){
                                boolean s = dbinstance.insertIntoAPAnimeinfo(job.getInt("id"),job.getString("title"),job.getString("season"),job.getString("imgurl"),job.getString("genre"),job.getString("animetype"),job.getString("description"),job.getDouble("rating"),job.getString("frtitle"));
                                newAnimeIDS.add(job.getInt("id"));
                                Controller.maxUpdateProgress++;
                                //HttpRequests.downloadDataToFileV(serverUrl+job.getInt("id"), "images/"+job.getInt("id"));
                            } else {
                                boolean s = dbinstance.updateAPAnimeinfo(id,job.getInt("id"),job.getString("title"),job.getString("season"),job.getString("imgurl"),job.getString("genre"),job.getString("animetype"),job.getString("description"),job.getDouble("rating"),job.getString("frtitle"));
                            }
                            Controller.updateProgress++;
                        }
                        dbinstance.updateAPVersion(onlineversion);
                        if(doSync) {
                        	dbinstance.updateSyncVersion(onlineSyncVersion);
                        }
                        newAnimeIDS.add(-10);
                        if(jarr.length()==1){
                            Controller.updateProgress=10;                           
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                
                if(doSync) {
                	idReassign.reassignIDS();            	
                }

                //wait for image threads to finish
                try {
					for(Thread t : threadsList) {
						t.join();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                

            } else {
                System.out.println(prefix + "Up to date update not needed");
                Controller.updateProgress = 10;
                Controller.maxUpdateProgress = 10;
                if(showDialogs){
                    javafx.application.Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            customDialogs.displayInformationDialog("Database Update", "Database is up to date", "Update not needed");
                        }
                    });
                }
            }
        }
    }
    
    public void imagesUpdater(boolean showDialogs, boolean async, boolean doSync){
    	if((imagesUpdaterThread!=null&&imagesUpdaterThread.isAlive())||(apUpdaterThread!=null&&apUpdaterThread.isAlive())||(databaseUpdaterThread!=null&&databaseUpdaterThread.isAlive())){
            if(showDialogs){
                customDialogs.displayInformationDialog("ImagesUpdater","Update already running","Please wait until update finishes");
                Controller.updateProgress = 10;
                Controller.maxUpdateProgress = 10;
                return;
            }
            Controller.updateProgress = 10;
            Controller.maxUpdateProgress = 10;
            return;
        }

        if(async){
            imagesUpdaterThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    imagesUpdater(showDialogs,doSync);
                }
            });
            imagesUpdaterThread.start();
        }else{
            apUpdater(showDialogs,doSync);
        }
    }
    
    private void imagesUpdater(boolean showDialogs, boolean doSync){
    	dbControl dbinstance = dbControl.getInstance();
    	final int numberOfThreads = Configuration.getInstance().getNumberOfThreadsImagesUpdater();
    	final String serverUrl = "http://"+Configuration.getInstance().getServerIp()+"/animedraw/images/";
    	
    	if(!fileSystemUtils.checkIfFileIsDirectory("Images")) {
    		fileSystemUtils.createFolder("Images");
    	}
    	
    	final ArrayList<NewAnime> animelist = dbinstance.getAnimeData(null, null, null);
    	
    	Controller.updateProgress = 0;
    	Controller.maxUpdateProgress = animelist.size();
    	
    	System.out.println("List size: "+animelist.size());
    	
    	ArrayList<Thread> threadsList = new ArrayList<>();
    	
    	int reqPerThread = animelist.size()/numberOfThreads;
    	System.out.println("Requests per thread: "+reqPerThread);
    	int lowerLimit = 0;
    	int higherLimit = animelist.size();
    	
    	for(int i=0; i<=numberOfThreads; i++) {
    		final int innerLowerLimit = lowerLimit;
    		if(innerLowerLimit+reqPerThread<higherLimit) {
        		final int innerHigherLimit = innerLowerLimit + reqPerThread;
        		System.out.println("Thread id="+i+" Lower Limit: "+innerLowerLimit+", Higher limit: "+innerHigherLimit);
        		//new thread
        		Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	for(int j=innerLowerLimit; j<innerHigherLimit; j++) {
                    		int apId = animelist.get(j).getapId();
                    		HttpRequests.downloadDataToFileV(serverUrl+apId, "images/"+apId);
                    		Controller.updateProgress++;
                    	}
                    }
                });
    			threadsList.add(t);
    			t.start();
    		}else {
    			final int innerHigherLimit = higherLimit;
    			System.out.println("Thread id="+i+" Lower Limit: "+innerLowerLimit+", Higher limit: "+innerHigherLimit);
    			//last thread
    			Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	for(int j=innerLowerLimit; j<innerHigherLimit; j++) {
                    		int apId = animelist.get(j).getapId();
                    		HttpRequests.downloadDataToFileV(serverUrl+apId, "images/"+apId);
                    		Controller.updateProgress++;
                    	}
                    }
                });
    			threadsList.add(t);
    			t.start();
    		}
    			
    		
    		lowerLimit = innerLowerLimit + reqPerThread;
    	}
    	
    	//wait for every thread to finish
    	for(Thread thread : threadsList){
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    	System.out.println("Images update has finished sucessfully. No explosions!");
    	
    	
    }

    public void topanimeUpdater(boolean showDialogs, boolean async, boolean doSync){
        if((databaseUpdaterThread!=null&&databaseUpdaterThread.isAlive())||(topanimeUpdaterThread!=null&&topanimeUpdaterThread.isAlive())){
            if(showDialogs){
                customDialogs.displayInformationDialog("TopanimeUpdater","Update already running","Please wait until update finishes");
                Controller.updateProgress = 10;
                Controller.maxUpdateProgress = 10;
                return;
            }
            Controller.updateProgress = 10;
            Controller.maxUpdateProgress = 10;
            return;
        }

        if(async){
            topanimeUpdaterThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    topanimeUpdater(showDialogs,doSync);
                }
            });
            topanimeUpdaterThread.start();
        }else{
            topanimeUpdater(showDialogs,doSync);
        }
    }

    private void topanimeUpdater(boolean showDialogs, boolean doSync){
        dbControl dbinstance = dbControl.getInstance();

        JSONObject versionjob = HttpRequests.getTOPVersion();
        int localversion = 0;
        if(!doSync)
            localversion = dbinstance.getTOPVersion();
        int onlineversion = -1;

        try {
            onlineversion = versionjob.getInt("TOPversion");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("online version: "+onlineversion +" local version: "+localversion);

        if(localversion>=onlineversion){
            System.out.println("TopanimeUpdater - Up to date");
            Controller.updateProgress = 10;
            Controller.maxUpdateProgress = 10;
            return;
        }

        JSONArray jarr = HttpRequests.getTopanimeData();

        if(jarr.length()==0){
            System.out.println("TopanimeUpdater - Empty array");
            Controller.updateProgress = 10;
            Controller.maxUpdateProgress = 10;
            return;
        }

        int spot = 1;
        final int topAnimeNumber = dbinstance.getRowCount(6);
        Controller.updateProgress = 0;
        if(jarr.length()==1){
            Controller.maxUpdateProgress = 10;
        }else {
            Controller.maxUpdateProgress = jarr.length()-1;
        }
        for(int i=0; i<jarr.length(); i++){
            try {
                JSONObject job = jarr.getJSONObject(i);
                int id = dbinstance.getAnimeID(job.getString("title"));
                if(id!=-1) {
                    if (spot<=topAnimeNumber) {
                        dbinstance.updateMALTopanime(spot, id, job.getDouble("score"));
                    } else {
                        dbinstance.insertIntoMALtopanime(spot, id, job.getDouble("score"));
                    }
                    spot++;
                }else{
                    System.out.println("TopanimeUpdater - Cannot find id for anime with title: "+job.getString("title"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("TopanimeUpdater - Json exception in loop");
            }
            Controller.updateProgress = i;
        }

        dbinstance.deleteFromMALtopanimeAfterSpot(--spot);
        dbinstance.updateTOPVersion(onlineversion);
        if(jarr.length()==1){
            Controller.updateProgress=10;
        }
    }

    public void watchlistUpdater(boolean showDialogs, boolean async){
        if((databaseUpdaterThread!=null&&databaseUpdaterThread.isAlive())||(watchlistUpdaterThread!=null&&watchlistUpdaterThread.isAlive())){
            if(showDialogs){
                customDialogs.displayInformationDialog("WatchlistUpdater","Update already running","Please wait until update finishes");
                Controller.updateProgress = 10;
                Controller.maxUpdateProgress = 10;
                return;
            }
            Controller.updateProgress = 10;
            Controller.maxUpdateProgress = 10;
            return;
        }

        if(async){
            watchlistUpdaterThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    watchlistUpdater(showDialogs);
                }
            });
            watchlistUpdaterThread.start();
        }else{
            watchlistUpdater(showDialogs);
        }
    }

    private void watchlistUpdater(boolean showDialogs){
        dbControl dbinstance = dbControl.getInstance();
        
        //getWatchlistData
        final ArrayList<WatchlistUpdaterModel> wlistData = dbinstance.getWatchlistUpdaterData();
        final ArrayList<String> watchlistSeasons = new ArrayList<>();
        final ArrayList<Integer> wlistApIds = new ArrayList<>();
        
        for(int i=0;i<wlistData.size();i++) {
        	if(!watchlistSeasons.contains(wlistData.get(i).getSeason())) {
        		watchlistSeasons.add(wlistData.get(i).getSeason());
        	}
        	wlistApIds.add(wlistData.get(i).getAnimeplanetID());
        }
        
        //HttpRequests.getWatchlistData();
        JSONArray jarr = null;
        if(Configuration.getInstance().isWatchlistUpdateMT()) {
        	jarr = JsoupToAnimePlanet.getWatchlistInfoMultithreaded(watchlistSeasons,wlistApIds);
        }else {
        	jarr = JsoupToAnimePlanet.getWatchlistInfo(watchlistSeasons,wlistApIds);
        }
        

        if(jarr.length()==0){
            System.out.println("WatchlistUpdater - Empty array");
            return;
        }

        //int watchlistCount = dbinstance.getRowCount(5);
        Controller.maxUpdateProgress = jarr.length()-1;
        Controller.updateProgress = 0;

        for(int i=0; i<jarr.length(); i++){
            try {
                JSONObject job = jarr.getJSONObject(i);
                final int apIdIndex = wlistApIds.indexOf(job.getInt("id"));
                if(apIdIndex!=-1) {
                	if(job.getInt("currentepisode")!=-1&&job.getInt("currentepisode")!=wlistData.get(apIdIndex).getCurrentEpisode()) {
                		dbinstance.updateWatchlist(wlistData.get(apIdIndex).getId(), job.getInt("currentepisode"), job.getString("lastupdated"));
                	}
                }
                Controller.updateProgress = i;
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("WatchlistUpdater - JSON exception trying to parse array index to json object");
            }
        }

        /*
        //telos jsoup arxi SQLite
        int watchlistCount = dbinstance.getRowCount(5);
        Controller.maxUpdateProgress = watchlistCount - jarr.length()-1;
        Controller.updateProgress = 0;
        for(int i=0; i<ids.size(); i++){
            dbinstance.updateWatchlist(ids.get(i), episodes.get(i), lastupdated.get(i));
            Controller.updateProgress = i;
        }
        */

        //dbinstance.handleWatchlistRemainingUpdate(ids);
    }
    
    /**
     * 
     * @param showDialogs
     * @param async
     * @param anime - Null to update for all anime in downloads or set to update for the specific anime
     */
    public void downloadsUpdater(boolean showDialogs, boolean async, WatchlistAnime anime){
        if((databaseUpdaterThread!=null&&databaseUpdaterThread.isAlive())||(downloadsUpdaterThread!=null&&downloadsUpdaterThread.isAlive())){
            if(showDialogs){
                customDialogs.displayInformationDialog("DownloadsUpdater","Update already running","Please wait until update finishes");
                Controller.updateProgress = 10;
                Controller.maxUpdateProgress = 10;
                return;
            }
            Controller.updateProgress = 10;
            Controller.maxUpdateProgress = 10;
            return;
        }

        if(async){
            downloadsUpdaterThread = new Thread(new Runnable() {
                @Override
                public void run() {
                	downloadsUpdater(showDialogs, anime);
                }
            });
            downloadsUpdaterThread.start();
        }else{
        	downloadsUpdater(showDialogs, anime);
        }
    }
    
    private void downloadsUpdater(boolean showDialogs, WatchlistAnime anime) {
    	if(anime==null) {
    		ArrayList<WatchlistAnime> tempanimelist = dbControl.getInstance().getWatchlistData();
    		ArrayList<WatchlistAnime> animelist = new ArrayList<>();
    		ArrayList<Thread> threadsList = new ArrayList<>();
    		for(WatchlistAnime tmpanime : tempanimelist) {
    			if(tmpanime.getTrnameprefix()!=null&&!tmpanime.getTrnameprefix().trim().equals("")) {
    				animelist.add(tmpanime);
    			}
    		}
    		if(animelist.size()>0) {
    			Controller.updateProgress = 0;
    			Controller.maxUpdateProgress = animelist.size();
    			
    			
    			for(WatchlistAnime rsAnime : animelist) {
    				Thread t = new Thread(new Runnable() {
    	                @Override
    	                public void run() {
    	                	downloadsUpdater(rsAnime);
    	                }
    	            });
    				threadsList.add(t);
    				t.start();
    			}
    			
    			
    			for(Thread thread : threadsList){
    				try {
						thread.join();
						Controller.updateProgress++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}else {
    			Controller.updateProgress = 10;
    			Controller.maxUpdateProgress = 10;
    		}
    	}else {
    		Controller.updateProgress = 0;
    		Controller.maxUpdateProgress = 10;
    		downloadsUpdater(anime);
    		Controller.updateProgress = 10;
    	}
    }
    
    private void downloadsUpdater(WatchlistAnime anime) {
    	JSONArray jArray = JsoupToNyaa.getTorrents(anime.getTrnameprefix());
    	ArrayList<String> links = new ArrayList<>();
    	ArrayList<Integer> episodes = new ArrayList<>();
    	ArrayList<Integer> value = new ArrayList<>();
    	
    	int[] qualityValues = {3,2,1,0};
    	if(!Configuration.getInstance().isGetHighestQuality()) {
    		switch (Configuration.getInstance().getQualityIndex()) {
			case 0:
				qualityValues[0]=0;
				qualityValues[1]=1;
				qualityValues[2]=2;
				qualityValues[3]=3;
				break;			
			case 1:
				qualityValues[0]=1;
				qualityValues[1]=2;
				qualityValues[2]=3;
				qualityValues[3]=0;
				break;
			case 2:
				qualityValues[0]=2;
				qualityValues[1]=3;
				qualityValues[2]=1;
				qualityValues[3]=0;
				break;
			case 3:
				break;
			default:
				break;
			}
    	}
    	
    	try {
    		for(int i=0; i<jArray.length(); i++) {
        		JSONObject jObject = (JSONObject) jArray.get(i);
        		if(jObject.getString("title").contains(anime.getTrnameprefix())) {
        			String temp = jObject.getString("title").replace(anime.getTrnameprefix(),"");
        			StringTokenizer tokenizer = new StringTokenizer(temp, " ");
        			temp = "";
        			int episodesNum = 0;
        			int tempvalue = 0;
        			while (tokenizer.hasMoreElements() && temp.equals("")) {
						String temp2 = tokenizer.nextToken();
						try {
							episodesNum = Integer.valueOf(temp2);
							
							temp = temp2; //episodes found break loop
							
							//prefers maximum quality
							if(jObject.getString("title").contains("1080p")) {
								tempvalue = qualityValues[0];
							} else if (jObject.getString("title").contains("720p")){
								tempvalue = qualityValues[1];
							} else if (jObject.getString("title").contains("480p")){
								tempvalue = qualityValues[2];
							} else {
								tempvalue = qualityValues[3];
							}
							
							int index = episodes.indexOf(episodesNum);
							if(index==-1) {
								links.add(jObject.getString("link"));
								episodes.add(episodesNum);
								value.add(tempvalue);
								//System.out.println("Links: "+jObject.getString("link")+"   Episode number: "+episodesNum);
							}else {
								if(tempvalue>value.get(index)) {
									links.set(index, jObject.getString("link"));
									value.set(index, tempvalue);
									//System.out.println("Updating value for episode: "+episodesNum);
								}
							}
							
						} catch (NumberFormatException e) {
							// TODO: handle exception
						}
					}
        		}
        	}		
    		/*
    		for(int i=0; i<episodes.size(); i++) {
    			System.out.println("END  Link: "+links.get(i)+" Episode: "+episodes.get(i));
    		}*/
    	} catch (JSONException e) {
			e.printStackTrace();
		}
    	int available = anime.getAvailable();
    	if(episodes.size()>0) {
    		available = java.util.Collections.max(episodes);
    	}
    	int downloaded = anime.getDownloaded();
    	if(downloaded==-1 && available>0) {
    		downloaded = 0;
    	}
    	
    	String savepath = anime.getSavepath();
    	
    	//checking if folcer can be found
    	String folderpath = "";
    	if(savepath==null || savepath.trim().equals("")) {
    		if(!Configuration.getInstance().getGlobalDownloadsPath().trim().equals("")) {
    			folderpath = fileSystemUtils.checkIfFolderExists(Configuration.getInstance().getGlobalDownloadsPath(), anime.getName());
    			savepath = folderpath;
    		}
    	}else {
    		if(fileSystemUtils.checkIfFileIsDirectory(savepath)) {
    			folderpath = savepath;
    		}
    	}
    	
    	//searches for episodes in folder
    	if(!folderpath.equals("")) {
    		ArrayList<String> filenameslist = fileSystemUtils.getDirectoryListing(folderpath);
    		ArrayList<Integer> episodenums = new ArrayList<>();
    		for(String filename : filenameslist) {
    			if(filename.contains(anime.getTrnameprefix())) {
    				String temp = filename.replace(anime.getTrnameprefix(), "");
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
							
						}catch (NumberFormatException e) {
							// TODO: handle exception
						}
        			}
    			}
    		}
    		if(episodenums.size()>0) {
    			downloaded = java.util.Collections.max(episodenums);
    		}else {
    			downloaded = 0;
    		}
    	}
    	
    	// edw psaxnei file system na vrei posa katevasmena exei kanei kai set to path ama to vrei me to title sto global path
    	
    	StringBuilder linktodb = new StringBuilder();
    	for(int i=0; i<episodes.size(); i++) {
    		linktodb.append(episodes.get(i));
    		linktodb.append("??");
    		linktodb.append(links.get(i));
    		linktodb.append("??");
    	}
    	
    	dbControl.getInstance().updateDownloads(anime.getId(), downloaded, available, savepath, anime.getTrnameprefix(), linktodb.toString());
    	
    }

}
