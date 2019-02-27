package animeApp.databaseUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class IDReassign {
	private ArrayList<Integer> watchlistids;
	private ArrayList<Integer> watchlaterids;
	private ArrayList<Integer> watchedids;
	private ArrayList<Integer> downloadsids;
	private ArrayList<String> watchlisttitles;
	private ArrayList<String> watchlatertitles;
	private ArrayList<String> watchedtitles;
	
	public IDReassign() {
		watchlistids = dbControl.getInstance().getWatchlistIDS();
		watchlaterids = dbControl.getInstance().getWatchlaterIDS();
		watchedids = dbControl.getInstance().getWatchedIDS();
		downloadsids = dbControl.getInstance().getDownloadsIDS();
		
		watchlisttitles = new ArrayList<>();
		watchlatertitles = new ArrayList<>();
		watchedtitles = new ArrayList<>();
		
		for(int id : watchlistids) {
			watchlisttitles.add(dbControl.getInstance().getAnimeInfo(id).getTitle());
		}
		for(int id : watchlaterids) {
			watchlatertitles.add(dbControl.getInstance().getAnimeInfo(id).getTitle());
		}
		for(int id : watchedids) {
			watchedtitles.add(dbControl.getInstance().getAnimeInfo(id).getTitle());
		}
	}
	
	public void reassignIDS() {
		Logger logger = Logger.getLogger("Synchronize logger");
		FileHandler fh;
		try {
			fh = new FileHandler("synchronize.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Starting Watchlist and Downloads Reassigment");
		if(watchlistids.size()>0) {
			for(int i=0; i<watchlistids.size(); i++){
				int newid = dbControl.getInstance().getAnimeID(watchlisttitles.get(i));
				if(newid==-1) {
					dbControl.getInstance().deleteFromWatchlist(watchlistids.get(i));
					dbControl.getInstance().deleteFromDownloads(watchlistids.get(i));
					logger.warning("Could not find anime: "+watchlisttitles.get(i)+" deleting from watchlist and downloads");
				}else {
					dbControl.getInstance().reassignWatchlistID(watchlistids.get(i), newid);
					dbControl.getInstance().reassignDownloadsID(watchlistids.get(i), newid);
					logger.info("Anime found: "+watchlisttitles.get(i)+" reassigning ids in watchlist and downloads");
				}
			}
		}
		logger.info("Starting Watchlater Reassigment");
		if(watchlaterids.size()>0) {
			for(int i=0; i<watchlaterids.size(); i++){
				int newid = dbControl.getInstance().getAnimeID(watchlatertitles.get(i));
				if(newid==-1) {
					dbControl.getInstance().deleteFromWatchLater(watchlaterids.get(i));
					logger.warning("Could not find anime: "+watchlatertitles.get(i)+" deleting from watchlaterlist");
				}else {
					dbControl.getInstance().reassignWatchlaterID(watchlaterids.get(i), newid);
					logger.info("Anime found: "+watchlatertitles.get(i)+" reassigning ids in watchlaterlist");
				}
			}
		}
		logger.info("Starting Watched Reassigment");
		if(watchedids.size()>0) {
			for(int i=0; i<watchedids.size(); i++){
				int newid = dbControl.getInstance().getAnimeID(watchedtitles.get(i));
				if(newid==-1) {
					dbControl.getInstance().deleteFromWatchLater(watchedids.get(i));
					logger.warning("Could not find anime: "+watchedtitles.get(i)+" deleting from watchedlist");
				}else {
					dbControl.getInstance().reassignWatchedID(watchedids.get(i), newid);
					logger.info("Anime found: "+watchedtitles.get(i)+" reassigning ids in watchedlist");
				}
			}
		}
	}
}
