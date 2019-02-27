package animeApp.databaseUtils;

import animeApp.encryption.AES;
import animeApp.encryption.RSA;
import animeApp.model.Configuration;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by admin on 11/8/2016.
 */
public class HttpRequests {
    public static String serverurl = "http://"+Configuration.getInstance().getServerIp();

    public static JSONArray getAnimeinfoandlinksbyversion(int version){
        return getData(serverurl+"/animedraw/drawclasses/drawanimeinfoandlinksbyversion.php",version);
    }

    public static JSONArray getAPanimeinfoData(int version){
        return getData(serverurl+"/animedraw/drawclasses/drawAPanimeinfobyversion.php",version);
    }

    public static JSONArray getTopanimeData(){
        return getData(serverurl+"/animedraw/drawclasses/drawMALtopanime.php",0);
    }

    public static JSONArray getHotanimeData(){
        return getData(serverurl+"/animedraw/drawclasses/drawhotanime.php",0);
    }

    public static JSONArray getWatchlistData(){
        return getData(serverurl+"/animedraw/drawclasses/drawwatchlistanime.php",0);
    }

    public static JSONObject getVersion(){
        return getVData(serverurl+"/animedraw/drawclasses/drawversion.php");
    }

    public static JSONObject getTOPVersion(){
        return getVData(serverurl+"/animedraw/drawclasses/drawMALtopanimeversion.php");
    }

    private static JSONArray getData(String url, int version){
        String responsetext = "";
        JSONArray jarr = new JSONArray();

        try {
            JSONObject obj = new JSONObject();
            obj.put("usr","gd4#DpxKli");
            obj.put("pss","pw2hT#S%g#");
            obj.put("vrs",version);
            obj.put("key", AES.getInstance().getKey());

            byte[] ciphered_text = RSA.getInstance().encrypt(obj.toString().getBytes(), true);
            String base64 =  Base64.encodeBase64String(ciphered_text); //Base64.encode(ciphered_text);

            String urlParameters =
                    "sinfo=" + URLEncoder.encode(base64, "UTF-8");

            responsetext = executePost(url,urlParameters);

            byte[] responsedata = Base64.decodeBase64(responsetext);
            byte[] decrypteddata = AES.getInstance().decrypt(responsedata);
            /*
            System.out.println("responsetext:");
            System.out.println(responsetext);*/

            jarr = new JSONArray(new String(decrypteddata));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return jarr;
    }

    private static JSONObject getVData(String url){
        String responsetext = "";
        JSONObject job = new JSONObject();

        try {
            JSONObject obj = new JSONObject();
            obj.put("usr","gd4#DpxKli");
            obj.put("pss","pw2hT#S%g#");
            obj.put("key", AES.getInstance().getKey());

            byte[] ciphered_text = RSA.getInstance().encrypt(obj.toString().getBytes(), true);
            String base64 = Base64.encodeBase64String(ciphered_text);


            String urlParameters =
                    "sinfo=" + URLEncoder.encode(base64, "UTF-8");

            responsetext = executePost(url,urlParameters);

            byte[] responsedata = Base64.decodeBase64(responsetext);
            byte[] decrypteddata = AES.getInstance().decrypt(responsedata);

            /*
            System.out.println("responsetext:");
            System.out.println(responsetext);*/

            job = new JSONObject(new String(decrypteddata));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return job;
    }

    private static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    public static File downloadDataToFile(String targetURL, String filename) {
    	HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            /*
            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();*/
            int responseCode = connection.getResponseCode();

            //Get Response
            InputStream is = connection.getInputStream();
            
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
              buffer.write(data, 0, nRead);
            }

            buffer.flush();
            
            byte[] ress = buffer.toByteArray();
            
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(ress);
            fos.close();
            //System.out.println("Size: "+ress.length);
            
            File file = new File(filename);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
