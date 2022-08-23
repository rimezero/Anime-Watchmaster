package animeApp.model;

public class WatchlistUpdaterModel {
	private int id;
	private int animeplanetID;
	private String season;
	private int currentEpisode;
	private String lastupdated;
	
	public WatchlistUpdaterModel() {}
	public WatchlistUpdaterModel(int id, int animeplanetID, String season, int currentEpisode, String lastupdated) {
		super();
		this.id = id;
		this.animeplanetID = animeplanetID;
		this.currentEpisode = currentEpisode;
		this.lastupdated = lastupdated;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAnimeplanetID() {
		return animeplanetID;
	}
	public void setAnimeplanetID(int animeplanetID) {
		this.animeplanetID = animeplanetID;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public int getCurrentEpisode() {
		return currentEpisode;
	}
	public void setCurrentEpisode(int currentEpisode) {
		this.currentEpisode = currentEpisode;
	}
	public String getLastupdated() {
		return lastupdated;
	}
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}
	
	
}
