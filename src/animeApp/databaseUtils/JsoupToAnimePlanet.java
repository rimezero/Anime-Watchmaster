package animeApp.databaseUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import animeApp.model.AnimeAP;

public class JsoupToAnimePlanet {
	private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36";
	private static final int timeoutTime = 120 * 1000;
	private static final String referrer = "http://www.google.com";
	private static final boolean debug = false;
	
	public static JSONArray getWatchlistInfo(ArrayList<String> watchlistSeasons, ArrayList<Integer> wlistAnimePlanetIds){
		ArrayList<AnimeAP> watchListAnime = new ArrayList<>();
        System.out.println("~Jsoup starting...");
        
        for(int i=0;i<watchlistSeasons.size();i++) {
        	//Create season link from season name. Might break if animeplanet link format changes.
        	final String seasonLink = "https://www.anime-planet.com/anime/seasons/"+watchlistSeasons.get(i).toLowerCase().replace(" ", "-");
        	final ArrayList<AnimeAP> seasonAnime = getSeasonData(seasonLink, watchlistSeasons.get(i));
        	for(AnimeAP anime : seasonAnime) {
        		if(wlistAnimePlanetIds.contains(anime.getId())) {
        			watchListAnime.add(anime);
        		}
        	}
        }
        
        JSONArray jarr = processAnimeData(watchListAnime);
        
        System.out.println("~Jsoup finished.");
        return jarr;
	}
	
	public static JSONArray getWatchlistInfoMultithreaded(ArrayList<String> watchlistSeasons, ArrayList<Integer> wlistAnimePlanetIds){
		ArrayList<AnimeAP> watchListAnime = new ArrayList<>();
        ArrayList<Thread> threadsList = new ArrayList<>();
        System.out.println("~Jsoup starting...");
        	
    	for(int i=0;i<watchlistSeasons.size();i++) {
    		final int index = i;
    		Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                	final String seasonLink = "https://www.anime-planet.com/anime/seasons/"+watchlistSeasons.get(index).toLowerCase().replace(" ", "-");
                	final ArrayList<AnimeAP> seasonAnime = getSeasonData(seasonLink, watchlistSeasons.get(index));
                	for(AnimeAP anime : seasonAnime) {
                		if(wlistAnimePlanetIds.contains(anime.getId())) {
                			watchListAnime.add(anime);
                		}
                	}
                }
            });
			threadsList.add(t);
			t.start();
    	}
    	
    	for(Thread thread : threadsList){
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    	JSONArray jarr = processAnimeData(watchListAnime);
    
        System.out.println("~Jsoup finished.");
        return jarr;
	}
	
	private static JSONArray processAnimeData(ArrayList<AnimeAP> watchlistAnime) {
		JSONArray jarr = new JSONArray();
		
		final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		final Date date = new Date(); //get current date
		final String cDate = dateFormat.format(date);
		
		try {
			for(AnimeAP anime : watchlistAnime) {
				JSONObject job = new JSONObject();
				job.put("id",anime.getId());
				job.put("currentepisode",StringUtils.getEpisodesFromAptype(anime.getType()));	        
				job.put("lastupdated",cDate);
				jarr.put(job);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jarr;
	}
	
	public static ArrayList<AnimeAP> getSeasonData(String seasonLink, String seasonName){
		ArrayList<AnimeAP> animeList = new ArrayList<>();
		
		final Document doc = getDocument(seasonLink);
		if(!doc.hasText()) {
			System.out.println("Get Season Data : ERROR unable to get jsoup document from link: "+seasonLink);
			return animeList;
		}
		
		print("~~~~ starto ~~~~");
		
		final Elements animeLis = doc.select("li[data-type=anime]");
		
		//get anime data from season format
		for(final Element animeLi : animeLis) {
			print();
			print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			print();
			
			//print(animeLi.html());
			AnimeAP anime = new AnimeAP();
			
			//id
			anime.setId(animeLi.attr("data-id"));
			print("Planet id: "+anime.getId());
			
			//season
			anime.setSeason(seasonName);
			print("season: "+anime.getSeason());
			
			//imgurl
			try {
				final Element img = animeLi.getElementsByTag("img").first();
				anime.setImgurl(img.attr("data-src"));
			} catch (Exception e) {
				anime.setImgurl("n/a");
			}			
			print("imgurl: "+anime.getImgurl());
			
			//title
			final Element h3 = animeLi.getElementsByTag("h3").first();
			anime.setTitle(h3.text());
			print("title: "+anime.getTitle());
			
			//get the container <a>
			String aLink = "n/a";
			String aTitleAttr = "n/a";
			try {
				final Element a = animeLi.select("a[class^=tooltip anime]").first();
				aLink = a.attr("abs:href");
				aTitleAttr = a.attr("title");
			} catch (Exception e) {
				System.out.println("Siteimport - ERROR Tooltip a tag for anime: "+anime.getTitle()+" not found");
				e.printStackTrace();
			}
			
			//link
			anime.setLink(aLink);
			print("link: "+anime.getLink());
			
			//print("attr title: "+a.attr("title"));
			final Document tmpdoc = Jsoup.parse(aTitleAttr);
			//print(tmpdoc.html());
			
			//altTitles
			anime.setAltTitles("n/a");
			Element altTitle = null;
			try {
				 altTitle = tmpdoc.select("h6[class=theme-font tooltip-alt]").first();
			} catch (Exception e) {
				//do nothing
			}
			
			String altTitles = "";
			if(altTitle!=null) {
				altTitles = altTitle.text();
			}
			
			if(!altTitles.isEmpty()) {
				if(altTitles.contains("Alt title:")) {
					altTitles = altTitles.replace("Alt title: ", "");
				}else {
					altTitles = altTitles.replace("Alt titles: ", "");
					altTitles = altTitles.replace(", ", "????");
				}
				anime.setAltTitles(altTitles);
			}
			print("Alt title/s: "+anime.getAltTitles());
			
			//type
			try {
				anime.setType(tmpdoc.select("ul[class=entryBar]").select("li[class=type]").text());
			} catch (Exception e) {
				anime.setType("n/a");
			}			
			print("type: "+anime.getType());
			
			//rating
			try {
				anime.setRating(tmpdoc.select("div[class=ttRating]").text());
			} catch (Exception e) {
				anime.setRating(-1);
			}	
			print("rating: "+anime.getRating());
			
			//description
			try {
				anime.setDescription(tmpdoc.getElementsByTag("p").first().text());
			} catch (Exception e) {
				anime.setDescription("n/a");
			}		
			print("description: "+anime.getDescription());
			
			//tags (genre)
			try {
				StringBuilder tagsBuilder = new StringBuilder();
				final Elements tagLis = tmpdoc.select("div[class=tags]").select("ul").first().children();
				for(Element tagLi : tagLis) {
					tagsBuilder.append(tagLi.text());
					tagsBuilder.append(", ");
				}
				tagsBuilder.delete(tagsBuilder.length()-2, tagsBuilder.length());
				anime.setGenre(tagsBuilder.toString());
			}catch (Exception e) {
				//e.printStackTrace();
				anime.setGenre("n/a");
			}
			
			print("genres: "+anime.getGenre());
			
			animeList.add(anime);
		}
		
		return animeList;
	}
	
	private static Document getDocument(String link) {
		Document doc = new Document("");
		
		
		try {
			final Response response = Jsoup.connect(link).userAgent(userAgent).timeout(timeoutTime).referrer(referrer)
					.execute();
			final int statusCode = response.statusCode();
			// print("Status code: "+statusCode);
			if (statusCode != 200) {
				System.out.println("JsoupToAnimePlanet - ERROR connecting to url: " + link + " response code: " + statusCode);
				return doc;
			}
			doc = response.parse();
		} catch (Exception e) {
			e.printStackTrace();
			return doc;
		}
		
		
		return doc;
	}
	
	private static void print(String string) {
    	if(debug) {
    		System.out.println(string);
    	}		
	}
    private static void print(Object string) {
    	if(debug) {
    		System.out.println(String.valueOf(string));
    	} 	
    }
    private static void print() {
    	if(debug) {
    		System.out.println();
    	}	
    }
}
