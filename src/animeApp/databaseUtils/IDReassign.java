package animeApp.databaseUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import animeApp.model.Animeinfo;

public class IDReassign {
	private ArrayList<Integer> watchlistids;
	private ArrayList<Integer> watchlaterids;
	private ArrayList<Integer> watchedids;
	private ArrayList<Integer> downloadsids;
	
	private ArrayList<Integer> watchlistApIds;
	private ArrayList<Integer> watchlaterApIds;
	private ArrayList<Integer> watchedApIds;
	
	private ArrayList<String> watchlistTitles;
	private ArrayList<String> watchlaterTitles;
	private ArrayList<String> watchedTitles;
	
	public IDReassign() {
		watchlistids = dbControl.getInstance().getWatchlistIDS();
		watchlaterids = dbControl.getInstance().getWatchlaterIDS();
		watchedids = dbControl.getInstance().getWatchedIDS();
		downloadsids = dbControl.getInstance().getDownloadsIDS();
		
		watchlistApIds = new ArrayList<>();
		watchlaterApIds = new ArrayList<>();
		watchedApIds = new ArrayList<>();
		
		watchlistTitles = new ArrayList<>();
		watchlaterTitles = new ArrayList<>();
		watchedTitles = new ArrayList<>();
		
		for(int id : watchlistids) {
			final Animeinfo info = dbControl.getInstance().getAPAnimeInfo(id);
			watchlistApIds.add(info.getAnimeplanetId());
			watchlistTitles.add(info.getTitle());
		}
		for(int id : watchlaterids) {
			final Animeinfo info = dbControl.getInstance().getAPAnimeInfo(id);
			watchlaterApIds.add(info.getAnimeplanetId());
			watchlaterTitles.add(info.getTitle());
		}
		for(int id : watchedids) {
			final Animeinfo info = dbControl.getInstance().getAPAnimeInfo(id);
			watchedApIds.add(info.getAnimeplanetId());
			watchedTitles.add(info.getTitle());
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
				int newid = dbControl.getInstance().getAPAnimeID(watchlistApIds.get(i));
				if(newid==-1) {
					dbControl.getInstance().deleteFromWatchlist(watchlistids.get(i));
					dbControl.getInstance().deleteFromDownloads(watchlistids.get(i));
					logger.warning("Could not find anime: "+watchlistTitles.get(i)+" deleting from watchlist and downloads");
				}else {
					dbControl.getInstance().reassignWatchlistID(watchlistids.get(i), newid);
					dbControl.getInstance().reassignDownloadsID(watchlistids.get(i), newid);
					logger.info("Anime found: "+watchlistTitles.get(i)+" reassigning ids in watchlist and downloads");
				}
			}
		}
		logger.info("Starting Watchlater Reassigment");
		if(watchlaterids.size()>0) {
			for(int i=0; i<watchlaterids.size(); i++){
				int newid = dbControl.getInstance().getAPAnimeID(watchlaterApIds.get(i));
				if(newid==-1) {
					dbControl.getInstance().deleteFromWatchLater(watchlaterids.get(i));
					logger.warning("Could not find anime: "+watchlaterTitles.get(i)+" deleting from watchlaterlist");
				}else {
					dbControl.getInstance().reassignWatchlaterID(watchlaterids.get(i), newid);
					logger.info("Anime found: "+watchlaterTitles.get(i)+" reassigning ids in watchlaterlist");
				}
			}
		}
		logger.info("Starting Watched Reassigment");
		if(watchedids.size()>0) {
			for(int i=0; i<watchedids.size(); i++){
				int newid = dbControl.getInstance().getAPAnimeID(watchedApIds.get(i));
				if(newid==-1) {
					dbControl.getInstance().deleteFromWatchLater(watchedids.get(i));
					logger.warning("Could not find anime: "+watchedTitles.get(i)+" deleting from watchedlist");
				}else {
					dbControl.getInstance().reassignWatchedID(watchedids.get(i), newid);
					logger.info("Anime found: "+watchedTitles.get(i)+" reassigning ids in watchedlist");
				}
			}
		}
	}
}
