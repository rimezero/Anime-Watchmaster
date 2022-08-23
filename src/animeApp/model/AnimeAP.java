package animeApp.model;

import java.text.DecimalFormat;

public class AnimeAP {
	private int id;
	private String title;
	private String altTitles;
	private String season;
	private String link;
	private String imgurl;
	private String genre;
	private String type;
	private String description;
	private double rating;
	private int version=-1;
	
	public AnimeAP() {}
	public AnimeAP(String id, String title, String altTitles, String season, String link, String imgurl, String genre, String type, String description, String rating) {
		try {
			this.id = Integer.parseInt(id);
		} catch (Exception e) {
			this.id = -1;
		}
		
		try {
			final DecimalFormat df = new DecimalFormat("0.0");
			this.rating = Double.parseDouble(df.format(rating));
		} catch (Exception e) {
			this.rating = -1;
		}
		
		this.title = title;
		this.altTitles = altTitles;
		this.season = season;
		this.link = link;
		this.imgurl = imgurl;
		this.genre = genre;
		this.type = type;
		this.description = description;
	}
	public AnimeAP(int id, String title, String altTitles, String season, String link, String imgurl, String genre,
			String type, String description, double rating) {
		super();
		this.id = id;
		this.title = title;
		this.altTitles = altTitles;
		this.season = season;
		this.link = link;
		this.imgurl = imgurl;
		this.genre = genre;
		this.type = type;
		this.description = description;
		this.rating = rating;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		try {
			this.id = Integer.parseInt(id);
		} catch (Exception e) {
			this.id = -1;
		}
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAltTitles() {
		return altTitles;
	}
	public void setAltTitles(String altTitles) {
		this.altTitles = altTitles;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public void setRating(String rating) {
		try {
			final DecimalFormat df = new DecimalFormat("0.0");
			this.rating = Double.parseDouble(df.format(Double.parseDouble(rating)));
		} catch (Exception e) {
			//e.printStackTrace();
			this.rating = -1;
		}
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public String toString() {
		return "Anime - id="+id+" title="+title+" alt titles="+altTitles+" season="+season+" link="+link+" imgurl="+imgurl+" genre="+genre+ " type="+type+" description="+description+ " rating="+rating+" version="+version;
	}
	
}
