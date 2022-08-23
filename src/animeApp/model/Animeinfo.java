package animeApp.model;

public class Animeinfo {

   private int id, animeplanetId;
   private String title, alt_titles, imgurl, genre, episodes, animetype, agerating, description, season;
   private double rating;


    public Animeinfo(){
        this.id = -1;
        this.animeplanetId = -1;
        this.title = "";
        this.alt_titles = "";
        this.imgurl = "";
        this.genre = "";
        this.episodes = "";
        this.animetype = "";
        this.season = "";

        this.agerating = "";
        this.description = "";
        this.rating = -1;
    }



    public Animeinfo(int id, int animeplanetId, String title,String alt_titles, String imgurl, String genre, String animetype, String description) {
        this.id = id;
        this.animeplanetId = animeplanetId;
        this.title = title;
        this.alt_titles = alt_titles;
        this.imgurl = imgurl;
        this.genre = genre;
        this.animetype = animetype;
        this.description = description;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getAnimeplanetId() {
        return animeplanetId;
    }

    public void setAnimeplanetId(int animeplanetId) {
        this.animeplanetId = animeplanetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAltTitles() {
    	return alt_titles;
    }
    
    public void setAltTitles(String alt_titles) {
    	this.alt_titles = alt_titles;
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

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getAgerating() {
        return agerating;
    }

    public void setAgerating(String agerating) {
        this.agerating = agerating;
    }

    public String getAnimetype() {
        return animetype;
    }

    public void setAnimetype(String animetype) {
        this.animetype = animetype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}