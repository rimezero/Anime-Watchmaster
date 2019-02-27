package animeApp.databaseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by admin on 9/4/2016.
 */
public class JsoupToAnimefreak {
    public static JSONArray getWatchlistInfo(){
    	ArrayList<Document> documents = new ArrayList<>();
        System.out.println("~Jsoup starting...");
        try {
        	for(int i=1;i<11;i++) {
        		documents.add(Jsoup.connect("https://www.animefreak.tv/home/latest-episodes/page/"+i).timeout(120 * 1000).get());
        	}
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jarr = processDocumentData(documents);
        System.out.println("~Jsoup finished.");
        return jarr;
    }
    
    
    public static JSONArray getWatchlistInfoMultithreaded(){
        ArrayList<Document> documents = new ArrayList<>();
        ArrayList<Thread> threadsList = new ArrayList<>();
        System.out.println("~Jsoup starting...");
        	
    	for(int i=0;i<10;i++) {
    		final int index = i+1;
    		Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                	try {
						documents.add(Jsoup.connect("https://www.animefreak.tv/home/latest-episodes/page/"+index).timeout(120 * 1000).get());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
    	
    	JSONArray jarr = processDocumentData(documents);
    
        System.out.println("~Jsoup finished.");
        return jarr;
    }
    
    private static JSONArray processDocumentData(ArrayList<Document> documents) {
    	JSONArray jarr = new JSONArray();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Integer> episodes = new ArrayList<>();
        ArrayList<String> lastupdated = new ArrayList<>();
    	for(Document doc : documents) {
    		//doc = Jsoup.connect("https://www.animefreak.tv/home/latest-episodes/page/"+i).timeout(120 * 1000).get();
        	Elements condivs = doc.getElementsByClass("date-list");
        	Element condiv = condivs.first();
        	Elements dlitemdivs = condiv.getElementsByClass("dl-item");
        	for(Element dlitemdiv : dlitemdivs) {
        		Elements datadivs = dlitemdiv.getElementsByTag("div");
        		String[] splitdata = datadivs.get(1).text().split(" - Episode ");
        		if(splitdata.length==2) {
            		try {
                		int episodeNum = Integer.valueOf(splitdata[1]);
                		
                		int index = titles.indexOf(splitdata[0]);
                		if(index==-1) {
                			titles.add(splitdata[0]);
                    		episodes.add(episodeNum);
                    		lastupdated.add(datadivs.get(2).text());
                		}else {
                			if(episodeNum > episodes.get(index)) {
                				episodes.set(index, episodeNum);
                				lastupdated.set(index, datadivs.get(2).text());
                			}
                		}
                		
                	}catch (NumberFormatException e) {
    					// TODO: handle exception
    				}
            	}          		
        	}
    	}

        /*
        for(int i=0; i<titles.size(); i++){
            System.out.println(titles.get(i)+" "+episodes.get(i)+" "+lastupdated.get(i));
        }*/

        for(int i=0;i<titles.size();i++){
            try {
                JSONObject job = new JSONObject();
                job.put("title",titles.get(i));
                job.put("currentepisode",episodes.get(i));
                job.put("lastupdated",lastupdated.get(i));
                jarr.put(job);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        
        return jarr;
    }
}
