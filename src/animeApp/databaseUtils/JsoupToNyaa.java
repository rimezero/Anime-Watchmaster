package animeApp.databaseUtils;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class JsoupToNyaa {
	public static JSONArray getTorrents(String trnameprefix) {
		JSONArray jArray = new JSONArray();		
		String link = "http://www.nyaa.si/?f=0&c=0_0&q="+trnameprefix.replace(' ', '+')+"&s=seeders&o=desc&p=";
		int numberOfPages=20;
		
		System.out.println("~Jsoup starting...");
        Document doc = null;
		
        try {
        	for(int i=1; i<=numberOfPages; i++) {
    			doc = Jsoup.connect(link+String.valueOf(i)).userAgent("Mozilla/5.0").timeout(120 * 1000).get();
    			Elements tbodies = doc.getElementsByTag("tbody");
    			if(tbodies.size()==0) {
    				System.out.println("~Jsoup finished.");
    				return jArray;
    			}
    			Element tbody = tbodies.first();
    			Elements trs = tbody.getElementsByTag("tr");
    			if(trs.size()==0) {
    				System.out.println("~Jsoup finished.");
    				return jArray;
    			}
    			for(Element tr : trs) {
    				Elements alinks = tr.getElementsByTag("a");
    				Element alink = alinks.get(1);
    				if(alink.attr("class").equals("comments")) {
    					alink = alinks.get(2);
    				}
    				String downloadlink = alink.attr("href");
    				String title = alink.text();
    				downloadlink = "https://nyaa.si"+downloadlink.replace("view", "download")+".torrent";
    				
    				JSONObject jObject = new JSONObject();
    				jObject.put("link", downloadlink);
    				jObject.put("title", title);
    				jArray.put(jObject);
    				
    				//System.out.println(" Link: "+downloadlink+"     title: "+title);
    			}
    		}
        } catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
        System.out.println("~Jsoup finished.");
		return jArray;
	}
}
