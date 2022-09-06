package animeApp.databaseUtils;

import animeApp.controllers.Controller;
import animeApp.model.*;

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by admin on 11/8/2016.
 */
public class dbControl {
    private static final String dbName = "anime.db";
    private static final int dbVersion = 5;

    //genaral
    private static final String GENERAL_COLUMN_ID = "id";

    //animelinks
    private static final String ANIMELINKS_COLUMN_ANIMEFREAKLINK = "frlink";
    private static final String ANIMELINKS_COLUMN_ANIMEULTIMALINK = "ultimalink";
    private static final String ANIMELINKS_COLUMN_MALLINK = "MALlink";

    //animeinfo
    private static final String ANIMEINFO_COLUMN_TITLE = "title";
    private static final String ANIMEINFO_COLUMN_IMGURL = "imgurl";
    private static final String ANIMEINFO_COLUMN_GENRE = "genre";
    private static final String ANIMEINFO_COLUMN_EPISODES = "episodes";
    private static final String ANIMEINFO_COLUMN_ANIMETYPE = "animetype";
    private static final String ANIMEINFO_COLUMN_AGERATING = "agerating";
    private static final String ANIMEINFO_COLUMN_DESCRIPTION = "description";
    private static final String ANIMEINFO_COLUMN_ANNIMGURL = "annimgurl";

    //APanimeinfo
    private static final String AP_ANIMEINFO_COLUMN_ANIMEPLANETID = "id_animeplanet";
    private static final String AP_ANIMEINFO_COLUMN_TITLE = "title";
    private static final String AP_ANIMEINFO_COLUMN_ALTTITLES = "alt_titles";
    private static final String AP_ANIMEINFO_COLUMN_SEASON = "season";
    private static final String AP_ANIMEINFO_COLUMN_IMGURL = "imgurl";
    private static final String AP_ANIMEINFO_COLUMN_GENRE = "genre";
    private static final String AP_ANIMEINFO_COLUMN_ANIMETYPE = "animetype";
    private static final String AP_ANIMEINFO_COLUMN_DESCRIPTION = "description";
    private static final String AP_ANIMEINFO_COLUMN_RATING = "rating";
    
    
    //Downloads
    private static final String DOWNLOADS_COLUMN_WATCHLISTID = "id_watchlist";
    private static final String DOWNLOADS_COLUMN_DOWNLOADED = "downloaded";
    private static final String DOWNLOADS_COLUMN_AVAILABLE = "available";
    private static final String DOWNLOADS_COLUMN_SAVEPATH = "savepath";
    private static final String DOWNLOADS_COLUMN_TRNAMEPREFIX = "trnameprefix";
    private static final String DOWNLOADS_COLUMN_LINKS = "links";

    //APcurrentseason
    private static final String AP_CURRENTSEASON_COLUMN_SEASON = "season";

    //watchlist
    private static final String WATCHLIST_COLUMN_EPISODESWATCHED = "episodeswatched";
    private static final String WATCHLIST_COLUMN_CURRENTEPISODE = "currentepisode";
    private static final String WATCHLIST_COLUMN_LASTUPDATED = "lastupdated";

    //MALtopanime
    private static final String MAL_TOPANIME_COLUMN_SPOT = "spot";
    private static final String MAL_TOPANIME_COLUMN_SCORE = "score";

    //version
    private static final String VERSION_COLUMN_VERSION = "version";

    //APversion
    private static final String AP_VERSION_COLUMN_VERSION = "version";
    
    //SyncVersion
    private static final String SYNC_VERSION_COLUMN_VERSION = "version";
    
    //tables
    private static final String TABLE_ANIMELINKS = "animelinks";
    private static final String TABLE_ANIMEINFO = "animeinfo";
    private static final String TABLE_WATCHLIST = "watchlist";
    private static final String TABLE_WATCHLATER = "watchlaterlist";
    private static final String TABLE_WATCHED = "watchedlist";
    private static final String TABLE_AP_ANIMEINFO = "APanimeinfo";
    private static final String TABLE_HOTANIME = "hotanime";
    private static final String TABLE_MAL_TOPANIME = "MALtopanime";
    private static final String TABLE_VERSION = "version";
    private static final String TABLE_SYNC_VERSION = "syncversion";
    private static final String TABLE_AP_VERSION = "APversion";
    private static final String TABLE_TOP_VERSION ="TOPversion";
    private static final String TABLE_DATABASE_VERSION ="DBversion";
    private static final String TABLE_AP_CURRENTSEASON = "APcurrentseason";
    private static final String TABLE_DOWNLOADS = "Downloads";

    public static dbControl dbinstance = null;

    public static dbControl getInstance(){
        if(dbinstance==null){
            synchronized (dbControl.class){
                if(dbinstance==null){
                    dbinstance = new dbControl();
                }
            }
        }
        return dbinstance;
    }

    public void initializeDatabase(){
        Connection con = null;
        Statement stt;
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            String command = "create table if not exists "+TABLE_ANIMEINFO+"("
                    +GENERAL_COLUMN_ID+ " integer primary key autoincrement, "
                    +ANIMEINFO_COLUMN_TITLE+ " text, "
                    +ANIMEINFO_COLUMN_IMGURL+ " text, "
                    +ANIMEINFO_COLUMN_GENRE+ " text, "
                    +ANIMEINFO_COLUMN_EPISODES+ " text, "
                    +ANIMEINFO_COLUMN_ANIMETYPE+ " text, "
                    +ANIMEINFO_COLUMN_AGERATING+ " text, "
                    +ANIMEINFO_COLUMN_DESCRIPTION+ " text, "
                    +ANIMEINFO_COLUMN_ANNIMGURL+ " text)";
            stt = con.createStatement();
            stt.executeUpdate(command);
            
            command = "create table if not exists "+TABLE_AP_ANIMEINFO+"("
                    +GENERAL_COLUMN_ID+ " integer primary key autoincrement, "
                    +AP_ANIMEINFO_COLUMN_ANIMEPLANETID+ " int, "
                    +AP_ANIMEINFO_COLUMN_TITLE+ " text, "
                    +AP_ANIMEINFO_COLUMN_ALTTITLES+ " text, "
                    +AP_ANIMEINFO_COLUMN_SEASON+ " text, "
                    +AP_ANIMEINFO_COLUMN_IMGURL+ " text, "
                    +AP_ANIMEINFO_COLUMN_GENRE+ " text, "
                    +AP_ANIMEINFO_COLUMN_ANIMETYPE+ " text, "
                    +AP_ANIMEINFO_COLUMN_DESCRIPTION+ " text, "
                    +AP_ANIMEINFO_COLUMN_RATING+ " real)";
            stt = con.createStatement();
            stt.executeUpdate(command);

            command = "create table if not exists "+TABLE_ANIMELINKS+"("
                    +GENERAL_COLUMN_ID+ " int primary key, "
                    +ANIMELINKS_COLUMN_ANIMEFREAKLINK+ " text, "
                    +ANIMELINKS_COLUMN_ANIMEULTIMALINK+ " text, "
                    +ANIMELINKS_COLUMN_MALLINK+ " text)";
            stt = con.createStatement();
            stt.executeUpdate(command);

            command = "create table if not exists "+TABLE_AP_CURRENTSEASON+"("
                    +AP_CURRENTSEASON_COLUMN_SEASON+ " text)";
            stt = con.createStatement();
            stt.executeUpdate(command);

            command = "create table if not exists "+TABLE_WATCHLIST+"("
                    +GENERAL_COLUMN_ID+ " int primary key, "
                    +WATCHLIST_COLUMN_EPISODESWATCHED+ " int, "
                    +WATCHLIST_COLUMN_CURRENTEPISODE+ " int, "
                    +WATCHLIST_COLUMN_LASTUPDATED+ " text)";
            stt = con.createStatement();
            stt.executeUpdate(command);

            command = "create table if not exists "+TABLE_WATCHLATER+"("
                    +GENERAL_COLUMN_ID+ " int primary key)";
            stt = con.createStatement();
            stt.executeUpdate(command);

            command = "create table if not exists "+TABLE_WATCHED+"("
                    +GENERAL_COLUMN_ID+ " int primary key)";
            stt = con.createStatement();
            stt.executeUpdate(command);

            command = "create table if not exists "+TABLE_HOTANIME+"("
                    +GENERAL_COLUMN_ID+ " int primary key)";
            stt = con.createStatement();
            stt.executeUpdate(command);

            command = "create table if not exists "+TABLE_MAL_TOPANIME+"("
                    +MAL_TOPANIME_COLUMN_SPOT+ " int primary key, "
                    +GENERAL_COLUMN_ID+ " int, "
                    +MAL_TOPANIME_COLUMN_SCORE+ " real)";
            stt = con.createStatement();
            stt.executeUpdate(command);

            command = "create table if not exists "+TABLE_VERSION+"("
                    +VERSION_COLUMN_VERSION+ " int primary key)";
            stt = con.createStatement();
            stt.executeUpdate(command);
            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+VERSION_COLUMN_VERSION+" from "+TABLE_VERSION);
            if(!rs.next()){
                this.insertIntoVersion(0,con);
            }
            
            command = "create table if not exists "+TABLE_SYNC_VERSION+"("
                    +SYNC_VERSION_COLUMN_VERSION+ " int primary key)";
            stt = con.createStatement();
            stt.executeUpdate(command);
            stt = con.createStatement();
            rs = stt.executeQuery("select "+SYNC_VERSION_COLUMN_VERSION+" from "+TABLE_SYNC_VERSION);
            if(!rs.next()){
                this.insertIntoSyncVersion(0,con);
            }

            command = "create table if not exists "+TABLE_AP_VERSION+"("
                    +AP_VERSION_COLUMN_VERSION+ " int primary key)";
            stt = con.createStatement();
            stt.executeUpdate(command);
            stt = con.createStatement();
            rs = stt.executeQuery("select "+VERSION_COLUMN_VERSION+" from "+TABLE_AP_VERSION);
            if(!rs.next()){
                this.insertIntoAPVersion(0,con);
            }

            command = "create table if not exists "+TABLE_TOP_VERSION+"("
                    +VERSION_COLUMN_VERSION+ " int primary key)";
            stt = con.createStatement();
            stt.executeUpdate(command);
            stt = con.createStatement();
            rs = stt.executeQuery("select "+VERSION_COLUMN_VERSION+" from "+TABLE_TOP_VERSION);
            if(!rs.next()){
                this.insertIntoTOPVersion(0,con);
            }

            command = "create table if not exists "+TABLE_DATABASE_VERSION+"("
                    +VERSION_COLUMN_VERSION+ " int primary key)";
            stt = con.createStatement();
            stt.executeUpdate(command);          		
            
            stt = con.createStatement();
            rs = stt.executeQuery("select "+VERSION_COLUMN_VERSION+" from "+TABLE_DATABASE_VERSION);
            if(!rs.next()){
                this.insertIntoDBVersion(1,con);
                onUpdate(1,con,stt);
            }else{
                if(rs.getInt(VERSION_COLUMN_VERSION)<dbVersion){
                    onUpdate(rs.getInt(VERSION_COLUMN_VERSION),con,stt);
                }
            }

            System.out.println("Database initialized");

        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
    }

    private void onUpdate(int oldversion, Connection con, Statement stt) throws ClassNotFoundException,SQLException {
        //switch for updates here
    	switch (oldversion) {
		case 1:
			String command = "create table if not exists "+TABLE_DOWNLOADS+"("
	        		+DOWNLOADS_COLUMN_WATCHLISTID+ " int, "
	        		+DOWNLOADS_COLUMN_DOWNLOADED+ " int, "
	        		+DOWNLOADS_COLUMN_AVAILABLE+ " int, "
	        		+DOWNLOADS_COLUMN_SAVEPATH+ " text, "
	        		+DOWNLOADS_COLUMN_TRNAMEPREFIX+ " text, "
	        		+DOWNLOADS_COLUMN_LINKS+ " text)";
	        stt = con.createStatement();
	        stt.executeUpdate(command);        
	        
	        System.out.println("Executed update commands successfully");
		case 2:
			con.close();
	        Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);
	        command = "drop table if exists GlobalDownloads";
			stt = con.createStatement();
			stt.executeUpdate(command);
		case 3:
			con.close();
	        Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);
			command = "drop table if exists "+TABLE_AP_ANIMEINFO;
			stt = con.createStatement();
			stt.executeUpdate(command);
			
			command = "create table if not exists "+TABLE_AP_ANIMEINFO+"("
                    +GENERAL_COLUMN_ID+ " integer primary key autoincrement, "
                    +AP_ANIMEINFO_COLUMN_ANIMEPLANETID+ " int, "
                    +AP_ANIMEINFO_COLUMN_TITLE+ " text, "
                    +AP_ANIMEINFO_COLUMN_ALTTITLES+ " text, "
                    +AP_ANIMEINFO_COLUMN_SEASON+ " text, "
                    +AP_ANIMEINFO_COLUMN_IMGURL+ " text, "
                    +AP_ANIMEINFO_COLUMN_GENRE+ " text, "
                    +AP_ANIMEINFO_COLUMN_ANIMETYPE+ " text, "
                    +AP_ANIMEINFO_COLUMN_DESCRIPTION+ " text, "
                    +AP_ANIMEINFO_COLUMN_RATING+ " real)";
            stt = con.createStatement();
            stt.executeUpdate(command);
            command = "delete from "+TABLE_WATCHLIST;
            stt = con.createStatement();
			stt.executeUpdate(command);
			command = "delete from "+TABLE_WATCHLATER;
            stt = con.createStatement();
			stt.executeUpdate(command);
			command = "delete from "+TABLE_WATCHED;
            stt = con.createStatement();
			stt.executeUpdate(command);
			command = "delete from "+TABLE_DOWNLOADS;
            stt = con.createStatement();
			stt.executeUpdate(command);
			this.updateAPVersion(0);
		case 4:
			command = "create table if not exists "+TABLE_SYNC_VERSION+"("
                    +SYNC_VERSION_COLUMN_VERSION+ " int primary key)";
            stt = con.createStatement();
            stt.executeUpdate(command);
            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+SYNC_VERSION_COLUMN_VERSION+" from "+TABLE_SYNC_VERSION);
            if(!rs.next()){
                this.insertIntoSyncVersion(0,con);
            }
            
		default:
			this.updateDBVersion(dbVersion, con);
			break;
		}
    	
    }

    public boolean insertIntoAnimeinfo(String title, String imgurl, String genre, String episodes, String animetype, String agerating, String description, String annimgurl){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("insert into "+TABLE_ANIMEINFO+"("+ANIMEINFO_COLUMN_TITLE+","+ANIMEINFO_COLUMN_IMGURL+","+ANIMEINFO_COLUMN_GENRE+","+ANIMEINFO_COLUMN_EPISODES+","+ANIMEINFO_COLUMN_ANIMETYPE+","+ANIMEINFO_COLUMN_AGERATING+","+ANIMEINFO_COLUMN_DESCRIPTION+","+ANIMEINFO_COLUMN_ANNIMGURL+") values(?,?,?,?,?,?,?,?)");
            state.setString(1,title);
            state.setString(2,imgurl);
            state.setString(3,genre);
            state.setString(4,episodes);
            state.setString(5,animetype);
            state.setString(6,agerating);
            state.setString(7,description);
            state.setString(8,annimgurl);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoAnimeinfo - Inserted anime: "+title);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean insertIntoAPAnimeinfo(int animeplanetid, String title, String season, String imgurl, String genre, String animetype, String descritpion, double rating, String alt_tiles){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("insert into "+TABLE_AP_ANIMEINFO+"("+AP_ANIMEINFO_COLUMN_ANIMEPLANETID+","+AP_ANIMEINFO_COLUMN_TITLE+","+AP_ANIMEINFO_COLUMN_SEASON+","+AP_ANIMEINFO_COLUMN_IMGURL+","+AP_ANIMEINFO_COLUMN_GENRE+","+AP_ANIMEINFO_COLUMN_ANIMETYPE+","+AP_ANIMEINFO_COLUMN_DESCRIPTION+","+AP_ANIMEINFO_COLUMN_RATING+","+AP_ANIMEINFO_COLUMN_ALTTITLES+") values(?,?,?,?,?,?,?,?,?)");
            state.setInt(1, animeplanetid);
            state.setString(2,title);
            state.setString(3,season);
            state.setString(4,imgurl);
            state.setString(5,genre);
            state.setString(6,animetype);
            state.setString(7,descritpion);
            state.setDouble(8, rating);
            state.setString(9,alt_tiles);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoAPAnimeinfo - Inserted anime: "+title);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean insertIntoAnimelinks(int id, String frlink, String ullink, String mallink){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("insert into "+TABLE_ANIMELINKS+" values(?,?,?,?)");
            state.setInt(1, id);
            state.setString(2,frlink);
            state.setString(3, ullink);
            state.setString(4,mallink);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoAnimelinks - Inserted links for anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean insertIntoAPCurrentSeason(String season){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("insert into "+TABLE_AP_CURRENTSEASON+" values(?)");
            state.setString(1,season);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoCurrentSeason - Inserted current season: "+season);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }
    
    public boolean insertIntoDownloads(int id_watchlist, int downloaded, int available, String savepath, String trnameprefix, String links){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("insert into "+TABLE_DOWNLOADS+"("+DOWNLOADS_COLUMN_WATCHLISTID+","+DOWNLOADS_COLUMN_DOWNLOADED+","+DOWNLOADS_COLUMN_AVAILABLE+","+DOWNLOADS_COLUMN_SAVEPATH+","+DOWNLOADS_COLUMN_TRNAMEPREFIX+","+DOWNLOADS_COLUMN_LINKS+") values(?,?,?,?,?,?)");
            state.setInt(1,id_watchlist);
            state.setInt(2,downloaded);
            state.setInt(3,available);
            state.setString(4,savepath);
            state.setString(5,trnameprefix);
            state.setString(6,links);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoDownloads - Inserted anime with wlistid: "+id_watchlist);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean insertIntoWatchlist(int id, int episodeswatched, int currentepisode, String lastupdated){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("insert into "+TABLE_WATCHLIST+" values(?,?,?,?)");
            state.setInt(1, id);
            state.setInt(2, episodeswatched);
            state.setInt(3,currentepisode);
            state.setString(4,lastupdated);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoWatchlist - Inserted into watchlist anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean insertIntoWatchLater(int id){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("insert into "+TABLE_WATCHLATER+" values(?)");
            state.setInt(1, id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoWatchlater - Inserted anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean insertIntoWatched(int id){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("insert into "+TABLE_WATCHED+" values(?)");
            state.setInt(1, id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoWatchedList - Inserted anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean insertIntoVersion(int version, Connection con){
        boolean returnflag = false;
        PreparedStatement state;
        try {

            state = con.prepareStatement("insert into "+TABLE_VERSION+" values(?)");
            state.setInt(1, version);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoVersion - Inserted version: "+version);

            state.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnflag;
    }
    
    public boolean insertIntoSyncVersion(int version, Connection con){
        boolean returnflag = false;
        PreparedStatement state;
        try {

            state = con.prepareStatement("insert into "+TABLE_SYNC_VERSION+" values(?)");
            state.setInt(1, version);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoSyncVersion - Inserted version: "+version);

            state.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnflag;
    }

    public boolean insertIntoTOPVersion(int version, Connection con){
        boolean returnflag = false;
        PreparedStatement state;
        try {
            state = con.prepareStatement("insert into "+TABLE_TOP_VERSION+" values(?)");
            state.setInt(1, version);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoTOPVersion - Inserted version: "+version);

            state.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return returnflag;
    }

    public boolean insertIntoDBVersion(int version,Connection con){
        boolean returnflag = false;
        PreparedStatement state;
        try {

            state = con.prepareStatement("insert into "+TABLE_DATABASE_VERSION+" values(?)");
            state.setInt(1, version);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoDBVersion - Inserted version: "+version);

            state.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return returnflag;
    }

    public boolean insertIntoAPVersion(int version, Connection con){
        boolean returnflag = false;
        PreparedStatement state;
        try {
            state = con.prepareStatement("insert into "+TABLE_AP_VERSION+" values(?)");
            state.setInt(1, version);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoAPVersion - Inserted version: "+version);

            state.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return returnflag;
    }

    public boolean insertIntoHotanime(int id){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("insert into "+TABLE_HOTANIME+" values(?)");
            state.setInt(1, id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoHotanime - Inserted in hotanime anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean insertIntoMALtopanime(int spot, int id, double score){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("insert into "+TABLE_MAL_TOPANIME+" values(?,?,?)");
            state.setInt(1, spot);
            state.setInt(2, id);
            state.setDouble(3, score);
            state.executeUpdate();

            returnflag = true;
            System.out.println("insertIntoMALTopanime - Inserted into topanime spot: "+spot);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean updateAnimeinfo(int id, String title, String imgurl, String genre, String episodes, String animetype, String agerating, String description, String annimgurl){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            state = con.prepareStatement("update "+TABLE_ANIMEINFO+" set "+ANIMEINFO_COLUMN_TITLE+"=?,"+ANIMEINFO_COLUMN_IMGURL+"=?,"+ANIMEINFO_COLUMN_GENRE+"=?,"+ANIMEINFO_COLUMN_EPISODES+"=?,"+ANIMEINFO_COLUMN_ANIMETYPE+"=?,"+ANIMEINFO_COLUMN_AGERATING+"=?,"+ANIMEINFO_COLUMN_DESCRIPTION+"=?,"+ANIMEINFO_COLUMN_ANNIMGURL+"=? where "+GENERAL_COLUMN_ID+"=?");
            state.setString(1,title);
            state.setString(2,imgurl);
            state.setString(3,genre);
            state.setString(4,episodes);
            state.setString(5,animetype);
            state.setString(6,agerating);
            state.setString(7, description);
            state.setString(8, annimgurl);
            state.setInt(9, id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateAnimeinfo - Updated anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean updateAPAnimeinfo(int id, int animeplanetid, String title, String season, String imgurl, String genre, String animetype, String descritpion, double rating, String alt_titles){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            state = con.prepareStatement("update " + TABLE_AP_ANIMEINFO + " set " + AP_ANIMEINFO_COLUMN_ANIMEPLANETID + "=?," + AP_ANIMEINFO_COLUMN_TITLE + "=?," + AP_ANIMEINFO_COLUMN_SEASON + "=?," + AP_ANIMEINFO_COLUMN_IMGURL + "=?," + AP_ANIMEINFO_COLUMN_GENRE + "=?," + AP_ANIMEINFO_COLUMN_ANIMETYPE + "=?," + AP_ANIMEINFO_COLUMN_DESCRIPTION + "=?," + AP_ANIMEINFO_COLUMN_RATING + "=?," + AP_ANIMEINFO_COLUMN_ALTTITLES + "=? where " + GENERAL_COLUMN_ID + "=?");
            state.setInt(1,animeplanetid);
            state.setString(2,title);
            state.setString(3,season);
            state.setString(4,imgurl);
            state.setString(5,genre);
            state.setString(6,animetype);
            state.setString(7,descritpion);
            state.setDouble(8, rating);
            state.setString(9,alt_titles);
            state.setInt(10,id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateAPanimeinfo - Updated anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean updateAnimelinks(int id, String frlink, String ullink, String mallink){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_ANIMELINKS+" set "+ANIMELINKS_COLUMN_ANIMEFREAKLINK+"=?,"+ANIMELINKS_COLUMN_ANIMEULTIMALINK+"=?,"+ANIMELINKS_COLUMN_MALLINK+"=? where "+GENERAL_COLUMN_ID+"=?");
            state.setString(1,frlink);
            state.setString(2, ullink);
            state.setString(3,mallink);
            state.setInt(4, id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateAnimelinks - Updated links for anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean updateAPCurrentSeason(String season){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_AP_CURRENTSEASON+" set "+AP_CURRENTSEASON_COLUMN_SEASON+"=?");
            state.setString(1, season);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateAPCurrentSeason - Updated current season: "+season);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }
    
    public boolean updateDownloads(int id_watchlist, int downloaded, int available, String savepath, String trnameprefix, String links){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_DOWNLOADS+" set "+DOWNLOADS_COLUMN_DOWNLOADED+"=?,"+DOWNLOADS_COLUMN_AVAILABLE+"=?,"+DOWNLOADS_COLUMN_SAVEPATH+"=?,"+DOWNLOADS_COLUMN_TRNAMEPREFIX+"=?,"+DOWNLOADS_COLUMN_LINKS+"=? where "+DOWNLOADS_COLUMN_WATCHLISTID+"=?");
            state.setInt(1,downloaded);
            state.setInt(2, available);
            state.setString(3,savepath);
            state.setString(4, trnameprefix);
            state.setString(5, links);
            state.setInt(6, id_watchlist);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateDownloads - Updated downloads for anime with id: "+id_watchlist);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean updateVersion(int version){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_VERSION+" set "+VERSION_COLUMN_VERSION+"=?");
            state.setInt(1, version);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateVersion - Updated version to: "+version);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }
    
    public boolean updateSyncVersion(int version){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_SYNC_VERSION+" set "+SYNC_VERSION_COLUMN_VERSION+"=?");
            state.setInt(1, version);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateSyncVersion - Updated version to: "+version);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean updateTOPVersion(int version){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_TOP_VERSION+" set "+VERSION_COLUMN_VERSION+"=?");
            state.setInt(1, version);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateTOPVersion - Updated version to: "+version);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean updateDBVersion(int version, Connection con) throws SQLException{
        boolean returnflag = false;
        PreparedStatement state;
        state = con.prepareStatement("update "+TABLE_DATABASE_VERSION+" set "+VERSION_COLUMN_VERSION+"=?");
        state.setInt(1, version);
        state.executeUpdate();

        returnflag = true;
        System.out.println("updateDBVersion - Updated version to: "+version);

        state.close();
        
        return returnflag;
    }

    public boolean updateAPVersion(int version){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_AP_VERSION+" set "+AP_VERSION_COLUMN_VERSION+"=?");
            state.setInt(1,version);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateAPVersion - Updated version to: "+version);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean updateWatchlist(int id, Integer currentepisode, String lastupdated){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_WATCHLIST+" set "+WATCHLIST_COLUMN_CURRENTEPISODE+"=?,"+WATCHLIST_COLUMN_LASTUPDATED+"=? where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1, currentepisode);
            state.setString(2,lastupdated);
            state.setInt(3, id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateWatchlist - Updated watchlist info for anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean updateWatchlistEpisodes(int id, Integer episodeswatched, Integer currentepisode){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            if(episodeswatched!=null&&currentepisode==null){
                state = con.prepareStatement("update "+TABLE_WATCHLIST+" set "+WATCHLIST_COLUMN_EPISODESWATCHED+"=? where "+GENERAL_COLUMN_ID+"=?");
                state.setInt(1,episodeswatched);
                state.setInt(2,id);
                state.executeUpdate();
            }else if(episodeswatched==null&&currentepisode!=null){
                state = con.prepareStatement("update "+TABLE_WATCHLIST+" set "+WATCHLIST_COLUMN_CURRENTEPISODE+"=? where "+GENERAL_COLUMN_ID+"=?");
                state.setInt(1,currentepisode);
                state.setInt(2,id);
                state.executeUpdate();
            }else if(episodeswatched!=null&&currentepisode!=null){
                state = con.prepareStatement("update "+TABLE_WATCHLIST+" set "+WATCHLIST_COLUMN_EPISODESWATCHED+"=?,"+WATCHLIST_COLUMN_CURRENTEPISODE+"=? where "+GENERAL_COLUMN_ID+"=?");
                state.setInt(1,episodeswatched);
                state.setInt(2,currentepisode);
                state.setInt(3,id);
                state.executeUpdate();
            }else{
                System.out.println("updateWatchlistEpisodes - Both episodeswatched and currentepisode are null");
                return returnflag;
            }

            returnflag = true;
            System.out.println("updateWatchlist - Updated watchlist episodes of anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }
    
    /**
     * Updates one or both trnameprefix and savepath
     * @param id_watchlist
     * @param trnameprefix - null to not update
     * @param savepath - null to not update
     * @return
     */
    public boolean updateDownloadPaths(int id_watchlist, String trnameprefix, String savepath){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            if(trnameprefix!=null&&savepath==null){
                state = con.prepareStatement("update "+TABLE_DOWNLOADS+" set "+DOWNLOADS_COLUMN_TRNAMEPREFIX+"=? where "+DOWNLOADS_COLUMN_WATCHLISTID+"=?");
                state.setString(1,trnameprefix);
                state.setInt(2,id_watchlist);
                state.executeUpdate();
            }else if(trnameprefix==null&&savepath!=null){
                state = con.prepareStatement("update "+TABLE_DOWNLOADS+" set "+DOWNLOADS_COLUMN_SAVEPATH+"=? where "+DOWNLOADS_COLUMN_WATCHLISTID+"=?");
                state.setString(1,savepath);
                state.setInt(2,id_watchlist);
                state.executeUpdate();
            }else if(trnameprefix!=null&&savepath!=null){
                state = con.prepareStatement("update "+TABLE_DOWNLOADS+" set "+DOWNLOADS_COLUMN_TRNAMEPREFIX+"=?,"+DOWNLOADS_COLUMN_SAVEPATH+"=? where "+DOWNLOADS_COLUMN_WATCHLISTID+"=?");
                state.setString(1,trnameprefix);
                state.setString(2,savepath);
                state.setInt(3,id_watchlist);
                state.executeUpdate();
            }else{
                System.out.println("updateWatchlistEpisodes - Both episodeswatched and currentepisode are null");
                return returnflag;
            }

            returnflag = true;
            System.out.println("updateDownloads - Updated download paths anime with id: "+id_watchlist);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean updateMALTopanime(int spot, int id, double score){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_MAL_TOPANIME+" set "+GENERAL_COLUMN_ID+"=?,"+MAL_TOPANIME_COLUMN_SCORE+"=? where "+MAL_TOPANIME_COLUMN_SPOT+"=?");
            state.setInt(1,id);
            state.setDouble(2,score);
            state.setInt(3,spot);
            state.executeUpdate();

            returnflag = true;
            System.out.println("updateMALtopanime - Updated topanime int spot: "+spot);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean deleteFromAnimeinfo(int id){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("delete from "+TABLE_ANIMEINFO+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1, id);
            
            if(id==-5) {
            	state = con.prepareStatement("delete from "+TABLE_ANIMEINFO);
            	state.executeUpdate();
            	state = con.prepareStatement("delete from sqlite_sequence where name=?");
            	state.setString(1, TABLE_ANIMEINFO);
            }
            
            state.executeUpdate();

            returnflag = true;
            System.out.println("deleteFromAnimeinfo - Deleted anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean deleteFromAPanimeinfo(int id){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("delete from "+TABLE_AP_ANIMEINFO+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1, id);
            if(id==-5) {
            	state = con.prepareStatement("delete from "+TABLE_AP_ANIMEINFO);
            	state.executeUpdate();
            	state = con.prepareStatement("delete from sqlite_sequence where name=?");
            	state.setString(1, TABLE_AP_ANIMEINFO);
            }
            
            state.executeUpdate();

            returnflag = true;
            System.out.println("deleteFromAPanimeinfo - Deleted anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean deleteFromAnimelinks(int id){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("delete from "+TABLE_ANIMELINKS+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1, id);
            if(id==-5) {
            	state = con.prepareStatement("delete from "+TABLE_ANIMELINKS);
            	state.executeUpdate();
            	state = con.prepareStatement("delete from sqlite_sequence where name=?");
            	state.setString(1, TABLE_ANIMELINKS);
            }
            state.executeUpdate();

            returnflag = true;
            System.out.println("deleteFromAnimelinks - Deleted anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }
    
    public boolean deleteFromDownloads(int id_watchlist){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("delete from "+TABLE_DOWNLOADS+" where "+DOWNLOADS_COLUMN_WATCHLISTID+"=?");
            state.setInt(1, id_watchlist);
            state.executeUpdate();

            returnflag = true;
            System.out.println("deleteFromDownloads - Deleted anime with id: "+id_watchlist);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean deleteFromWatchlist(int id){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("delete from "+TABLE_WATCHLIST+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1, id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("deleteFromWatchlist - Deleted anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean deleteFromWatchLater(int id){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("delete from "+TABLE_WATCHLATER+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1, id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("deleteFromWatchLater - Deleted anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean deleteFromWatched(int id){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("delete from "+TABLE_WATCHED+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1, id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("deleteFromWatched - Deleted anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean deleteFromHotanime(int id){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("delete from "+TABLE_HOTANIME+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1, id);
            state.executeUpdate();

            returnflag = true;
            System.out.println("deleteFromHotanime - Deleted anime with id: "+id);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public boolean deleteFromMALtopanimeAfterSpot(int spot){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("delete from "+TABLE_MAL_TOPANIME+" where "+MAL_TOPANIME_COLUMN_SPOT+">?");
            state.setInt(1,spot);
            state.executeUpdate();

            returnflag = true;
            System.out.println("deleteFromMALtopanimeAfterSpot - Deleted topanime after spot: "+spot);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }

    public int getVersion(){
        int version = 0;
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+VERSION_COLUMN_VERSION+ " from "+TABLE_VERSION);
            if(rs.next()){
                version = rs.getInt(VERSION_COLUMN_VERSION);
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return version;
    }
    
    public int getSyncVersion(){
        int version = 0;
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+SYNC_VERSION_COLUMN_VERSION+ " from "+TABLE_SYNC_VERSION);
            if(rs.next()){
                version = rs.getInt(SYNC_VERSION_COLUMN_VERSION);
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return version;
    }

    public int getTOPVersion(){
        int version = 0;
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+VERSION_COLUMN_VERSION+ " from "+TABLE_TOP_VERSION);
            if(rs.next()){
                version = rs.getInt(VERSION_COLUMN_VERSION);
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return version;
    }

    public int getAPVersion(){
        int version = 0;
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+AP_VERSION_COLUMN_VERSION+ " from "+TABLE_AP_VERSION);
            if(rs.next()){
                version = rs.getInt(AP_VERSION_COLUMN_VERSION);
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return version;
    }
    
    public ArrayList<Integer> getWatchlistIDS(){
        ArrayList<Integer> result = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+GENERAL_COLUMN_ID+ " from "+TABLE_WATCHLIST);
            while(rs.next()){
                result.add(rs.getInt(GENERAL_COLUMN_ID));
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return result;
    }
    
    public ArrayList<Integer> getWatchlaterIDS(){
        ArrayList<Integer> result = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+GENERAL_COLUMN_ID+ " from "+TABLE_WATCHLATER);
            while(rs.next()){
                result.add(rs.getInt(GENERAL_COLUMN_ID));
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return result;
    }
    
    public ArrayList<Integer> getWatchedIDS(){
        ArrayList<Integer> result = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+GENERAL_COLUMN_ID+ " from "+TABLE_WATCHED);
            while(rs.next()){
                result.add(rs.getInt(GENERAL_COLUMN_ID));
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return result;
    }
    
    public ArrayList<Integer> getDownloadsIDS(){
        ArrayList<Integer> result = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+DOWNLOADS_COLUMN_WATCHLISTID+ " from "+TABLE_DOWNLOADS);
            while(rs.next()){
                result.add(rs.getInt(DOWNLOADS_COLUMN_WATCHLISTID));
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return result;
    }
    
    public boolean reassignWatchlistID(int oldid, int newid){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_WATCHLIST+" set "+GENERAL_COLUMN_ID+"=? where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1,newid);
            state.setInt(2,oldid);
            state.executeUpdate();

            returnflag = true;
            System.out.println("Reassigned Watchlist ID: "+oldid+" to new id: "+newid);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }
    
    public boolean reassignWatchlaterID(int oldid, int newid){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_WATCHLATER+" set "+GENERAL_COLUMN_ID+"=? where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1,newid);
            state.setInt(2,oldid);
            state.executeUpdate();

            returnflag = true;
            System.out.println("Reassigned Watchlater ID: "+oldid+" to new id: "+newid);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }
    
    public boolean reassignWatchedID(int oldid, int newid){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_WATCHED+" set "+GENERAL_COLUMN_ID+"=? where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1,newid);
            state.setInt(2,oldid);
            state.executeUpdate();

            returnflag = true;
            System.out.println("Reassigned Watched ID: "+oldid+" to new id: "+newid);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }
    
    public boolean reassignDownloadsID(int oldid, int newid){
        boolean returnflag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("update "+TABLE_DOWNLOADS+" set "+DOWNLOADS_COLUMN_WATCHLISTID+"=? where "+DOWNLOADS_COLUMN_WATCHLISTID+"=?");
            state.setInt(1,newid);
            state.setInt(2,oldid);
            state.executeUpdate();

            returnflag = true;
            System.out.println("Reassigned Downloads ID: "+oldid+" to new id: "+newid);

            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return returnflag;
    }
    
    

    /**
     *
     * @param title The title of the anime
     * @return The id of the anime in animeinfo or -1 if the anime was not found
     */
    public int getAnimeID(String title){
        int id = -1;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+GENERAL_COLUMN_ID+ " from "+TABLE_ANIMEINFO+" where "+ANIMEINFO_COLUMN_TITLE+"=?");
            state.setString(1,title);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                id = rs.getInt(GENERAL_COLUMN_ID);
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return id;
    }

    /**
     *
     * @param title The title of the anime
     * @return The id of the anime in apanimeinfo or -1 if the anime was not found
     */
    public int getAPAnimeID(String title){
        int id = -1;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+GENERAL_COLUMN_ID+ " from "+TABLE_AP_ANIMEINFO+" where "+AP_ANIMEINFO_COLUMN_TITLE+"=?");
            state.setString(1,title);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                id = rs.getInt(GENERAL_COLUMN_ID);
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return id;
    }
    
    /**
    *
    * @param animeplanetId - The id of the anime in the server database (animeplanet id)
    * @return The id of the anime in apanimeinfo or -1 if the anime was not found
    */
    public int getAPAnimeID(int animeplanetId){
        int id = -1;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+GENERAL_COLUMN_ID+ " from "+TABLE_AP_ANIMEINFO+" where "+AP_ANIMEINFO_COLUMN_ANIMEPLANETID+"=?");
            state.setInt(1,animeplanetId);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                id = rs.getInt(GENERAL_COLUMN_ID);
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return id;
    }

    /**
     *
     * @param letter can be null
     * @param searchquery can be null
     * @param filterslist can be null
     * @return
     */
    public ArrayList<NewAnime> getAnimeData(String letter, String searchquery, ArrayList<String> filterslist){
        ArrayList<NewAnime> animelist = new ArrayList<>();
        Connection con = null;
        PreparedStatement state;
        ArrayList<String> whereArgs = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            StringBuilder command = new StringBuilder();
            command.append("select " + GENERAL_COLUMN_ID + "," + AP_ANIMEINFO_COLUMN_ANIMEPLANETID + "," + AP_ANIMEINFO_COLUMN_TITLE + "," + AP_ANIMEINFO_COLUMN_IMGURL + "," + AP_ANIMEINFO_COLUMN_GENRE + " from " + TABLE_AP_ANIMEINFO);

            if(letter!=null||searchquery!=null||filterslist!=null){
                command.append(" where ");
                if(letter!=null){

                    if(letter.equals("Other")){
                        String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
                        for(String lt : letters){
                            command.append(AP_ANIMEINFO_COLUMN_TITLE+" not like ? and ");
                            whereArgs.add(lt+"%");
                        }
                    }else{
                        command.append(AP_ANIMEINFO_COLUMN_TITLE+" like ? and ");
                        whereArgs.add(letter+"%");
                    }

                }
                if(searchquery!=null){
                    command.append(AP_ANIMEINFO_COLUMN_TITLE+" like ? and ");
                    whereArgs.add("%"+searchquery+"%");
                }
                if(filterslist!=null && !filterslist.isEmpty()){
                	if(!Configuration.getInstance().isShowUpcomingWithFilters()) {
                		command.append(AP_ANIMEINFO_COLUMN_SEASON+"<>'Upcoming' and ");
                	}
                	
                    for(String filter : filterslist){
                        command.append(AP_ANIMEINFO_COLUMN_GENRE+" like ? and ");
                        whereArgs.add("%"+filter+"%");
                    }
                }
                command.delete(command.length()-5,command.length());
            }
            command.append(" order by "+AP_ANIMEINFO_COLUMN_TITLE+" collate nocase asc");
            //System.out.println(command.toString());
            state = con.prepareStatement(command.toString());
            for(int i=1; i<=whereArgs.size(); i++){
                state.setString(i,whereArgs.get(i-1));
            }
            ResultSet rs = state.executeQuery();
            while(rs.next()){
                animelist.add(new NewAnime(rs.getInt(GENERAL_COLUMN_ID),rs.getInt(AP_ANIMEINFO_COLUMN_ANIMEPLANETID),rs.getString(AP_ANIMEINFO_COLUMN_TITLE),rs.getString(AP_ANIMEINFO_COLUMN_IMGURL),rs.getString(AP_ANIMEINFO_COLUMN_GENRE)));
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return animelist;
    }

    public ArrayList<SeasonModel> getSeasonData(String season) {
        ArrayList<SeasonModel> seasonDataList = new ArrayList<>();
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+GENERAL_COLUMN_ID+","+AP_ANIMEINFO_COLUMN_ANIMEPLANETID+","+AP_ANIMEINFO_COLUMN_TITLE+","+AP_ANIMEINFO_COLUMN_IMGURL+","+AP_ANIMEINFO_COLUMN_GENRE+","+AP_ANIMEINFO_COLUMN_RATING+ " from "+TABLE_AP_ANIMEINFO+" where "+AP_ANIMEINFO_COLUMN_SEASON+"=? order by "+AP_ANIMEINFO_COLUMN_TITLE+" collate nocase asc");
            state.setString(1,season);
            ResultSet rs = state.executeQuery();
            while (rs.next()){
                seasonDataList.add(new SeasonModel(rs.getInt(GENERAL_COLUMN_ID),rs.getInt(AP_ANIMEINFO_COLUMN_ANIMEPLANETID),rs.getString(AP_ANIMEINFO_COLUMN_TITLE),rs.getString(AP_ANIMEINFO_COLUMN_IMGURL),rs.getString(AP_ANIMEINFO_COLUMN_GENRE),rs.getDouble(AP_ANIMEINFO_COLUMN_RATING)));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex) {
                //do nothing (SQLite)
            }
        }
        return seasonDataList;
    }

    /**
     *
     * @param choice Animefreak/Animeultima/MyAnimeList
     * @param id The anime id
     * @return The link or na
     */
    public String getLink(String choice, int id){
        String link = "na";
        Connection con = null;
        PreparedStatement state;
        String column = "";
        switch (choice){
            case "Animefreak":
                column = ANIMELINKS_COLUMN_ANIMEFREAKLINK;
                break;
            case "Animeultima":
                column = ANIMELINKS_COLUMN_ANIMEULTIMALINK;
                break;
            case "MyAnimeList":
                column = ANIMELINKS_COLUMN_MALLINK;
                break;
            default:
                return link;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+column+ " from "+TABLE_ANIMELINKS+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1,id);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                link = rs.getString(column);
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return link;
    }

    public int getRowCount(int table_id){
        int rowCount = 0;
        Connection con = null;
        Statement stt;
        String tablename;
        switch (table_id){
            case 5:
                tablename=TABLE_WATCHLIST;
                break;
            case 6:
                tablename=TABLE_MAL_TOPANIME;
                break;
            default:
                tablename=TABLE_ANIMEINFO;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select count(*) as rcn from "+tablename);
            if(rs.next()){
                rowCount = rs.getInt("rcn");
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return rowCount;
    }

    public List<Integer> getHotanime(){
        List<Integer> hotanimelist = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+GENERAL_COLUMN_ID+" from "+TABLE_HOTANIME);
            while(rs.next()){
                hotanimelist.add(rs.getInt(GENERAL_COLUMN_ID));
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return hotanimelist;
    }

    public Animeinfo getAnimeInfo(int id){
        Animeinfo info = new Animeinfo();
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+GENERAL_COLUMN_ID+","+ANIMEINFO_COLUMN_TITLE+","+ANIMEINFO_COLUMN_IMGURL+","+ANIMEINFO_COLUMN_GENRE+","+ANIMEINFO_COLUMN_EPISODES+","+ANIMEINFO_COLUMN_ANIMETYPE+","+ANIMEINFO_COLUMN_AGERATING+","+ANIMEINFO_COLUMN_DESCRIPTION+" from "+TABLE_ANIMEINFO+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1,id);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                info.setId(rs.getInt(GENERAL_COLUMN_ID));
                info.setTitle(rs.getString(ANIMEINFO_COLUMN_TITLE));
                info.setImgurl(rs.getString(ANIMEINFO_COLUMN_IMGURL));
                info.setGenre(rs.getString(ANIMEINFO_COLUMN_GENRE));
                info.setEpisodes(rs.getString(ANIMEINFO_COLUMN_EPISODES));
                info.setAnimetype(rs.getString(ANIMEINFO_COLUMN_ANIMETYPE));
                info.setAgerating(rs.getString(ANIMEINFO_COLUMN_AGERATING));
                info.setDescription(rs.getString(ANIMEINFO_COLUMN_DESCRIPTION));
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return info;
    }

    public Animeinfo getAPAnimeInfo(int id){
        Animeinfo info = new Animeinfo();
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+GENERAL_COLUMN_ID+","+AP_ANIMEINFO_COLUMN_ANIMEPLANETID+","+AP_ANIMEINFO_COLUMN_TITLE+","+AP_ANIMEINFO_COLUMN_ALTTITLES+","+AP_ANIMEINFO_COLUMN_IMGURL+","+AP_ANIMEINFO_COLUMN_GENRE+","+AP_ANIMEINFO_COLUMN_ANIMETYPE+","+AP_ANIMEINFO_COLUMN_SEASON+","+AP_ANIMEINFO_COLUMN_DESCRIPTION+","+AP_ANIMEINFO_COLUMN_RATING+" from "+TABLE_AP_ANIMEINFO+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1,id);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                info.setId(rs.getInt(GENERAL_COLUMN_ID));
                info.setAnimeplanetId(rs.getInt(AP_ANIMEINFO_COLUMN_ANIMEPLANETID));
                info.setTitle(rs.getString(AP_ANIMEINFO_COLUMN_TITLE));
                info.setAltTitles(rs.getString(AP_ANIMEINFO_COLUMN_ALTTITLES));
                info.setImgurl(rs.getString(AP_ANIMEINFO_COLUMN_IMGURL));
                info.setGenre(rs.getString(AP_ANIMEINFO_COLUMN_GENRE));
                info.setSeason(rs.getString(AP_ANIMEINFO_COLUMN_SEASON));
                info.setAnimetype(rs.getString(AP_ANIMEINFO_COLUMN_ANIMETYPE));
                info.setRating(rs.getDouble(AP_ANIMEINFO_COLUMN_RATING));
                info.setDescription(rs.getString(AP_ANIMEINFO_COLUMN_DESCRIPTION));
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return info;
    }

    public ArrayList<NewAnime> getTopanimeData(){
        ArrayList<NewAnime> topanimeData = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select "+"T."+GENERAL_COLUMN_ID+","+"T."+MAL_TOPANIME_COLUMN_SPOT+","+"Info."+AP_ANIMEINFO_COLUMN_ANIMEPLANETID+","+"Info."+AP_ANIMEINFO_COLUMN_TITLE+","+"Info."+AP_ANIMEINFO_COLUMN_IMGURL+","+"Info."+AP_ANIMEINFO_COLUMN_GENRE+","+"T."+MAL_TOPANIME_COLUMN_SCORE+" from "+TABLE_MAL_TOPANIME+" T inner join "+TABLE_AP_ANIMEINFO+" Info on T."+GENERAL_COLUMN_ID+"=Info."+GENERAL_COLUMN_ID+" order by T."+MAL_TOPANIME_COLUMN_SPOT+" asc");
            while(rs.next()){
                NewAnime anime = new NewAnime(rs.getInt(GENERAL_COLUMN_ID),rs.getInt(AP_ANIMEINFO_COLUMN_ANIMEPLANETID),rs.getString(ANIMEINFO_COLUMN_TITLE),rs.getString(ANIMEINFO_COLUMN_IMGURL),rs.getString(ANIMEINFO_COLUMN_GENRE));
                anime.setSpot(rs.getInt(MAL_TOPANIME_COLUMN_SPOT));
                anime.setRating(rs.getDouble(MAL_TOPANIME_COLUMN_SCORE));
                topanimeData.add(anime);
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return topanimeData;
    }
    
    public WatchlistAnime getWatchlistAnime(int id) {
    	WatchlistAnime anime = null;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select W."+WATCHLIST_COLUMN_EPISODESWATCHED+",W."+WATCHLIST_COLUMN_CURRENTEPISODE+",W."+WATCHLIST_COLUMN_LASTUPDATED+",Info."+AP_ANIMEINFO_COLUMN_TITLE+",COALESCE(Dls."+DOWNLOADS_COLUMN_DOWNLOADED+",-1) as "+DOWNLOADS_COLUMN_DOWNLOADED+",COALESCE(Dls."+DOWNLOADS_COLUMN_AVAILABLE+",-1) as "+DOWNLOADS_COLUMN_AVAILABLE+",Dls."+DOWNLOADS_COLUMN_SAVEPATH+",Dls."+DOWNLOADS_COLUMN_TRNAMEPREFIX+",Dls."+DOWNLOADS_COLUMN_LINKS+" from "+TABLE_WATCHLIST+" W inner join "+TABLE_AP_ANIMEINFO+" Info on W."+GENERAL_COLUMN_ID+"=Info."+GENERAL_COLUMN_ID+" left join "+TABLE_DOWNLOADS+" Dls on W."+GENERAL_COLUMN_ID+"=Dls."+DOWNLOADS_COLUMN_WATCHLISTID+" where W."+GENERAL_COLUMN_ID+"=?");
            state.setInt(1, id);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
            	//System.out.println(rs.getInt(GENERAL_COLUMN_ID)+" "+rs.getString(ANIMEINFO_COLUMN_TITLE)+" "+rs.getInt(WATCHLIST_COLUMN_EPISODESWATCHED)+" "+rs.getInt(WATCHLIST_COLUMN_CURRENTEPISODE)+" "+rs.getString(WATCHLIST_COLUMN_LASTUPDATED)+" "+rs.getString(DOWNLOADS_COLUMN_AVAILABLE));
            	anime = new WatchlistAnime(id,rs.getString(AP_ANIMEINFO_COLUMN_TITLE),rs.getInt(WATCHLIST_COLUMN_EPISODESWATCHED),rs.getInt(WATCHLIST_COLUMN_CURRENTEPISODE),rs.getString(WATCHLIST_COLUMN_LASTUPDATED));
            	anime.setDownloaded(rs.getInt(DOWNLOADS_COLUMN_DOWNLOADED));
            	anime.setAvailable(rs.getInt(DOWNLOADS_COLUMN_AVAILABLE));
            	anime.setSavepath(rs.getString(DOWNLOADS_COLUMN_SAVEPATH));
            	anime.setTrnameprefix(rs.getString(DOWNLOADS_COLUMN_TRNAMEPREFIX));
            	anime.setLinks(rs.getString(DOWNLOADS_COLUMN_LINKS));
            	anime.initialize();         
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return anime;
    }

    public ArrayList<WatchlistAnime> getWatchlistData(){
        ArrayList<WatchlistAnime> wlist = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select W."+GENERAL_COLUMN_ID+",W."+WATCHLIST_COLUMN_EPISODESWATCHED+",W."+WATCHLIST_COLUMN_CURRENTEPISODE+",W."+WATCHLIST_COLUMN_LASTUPDATED+",Info."+AP_ANIMEINFO_COLUMN_TITLE+",COALESCE(Dls."+DOWNLOADS_COLUMN_DOWNLOADED+",-1) as "+DOWNLOADS_COLUMN_DOWNLOADED+",COALESCE(Dls."+DOWNLOADS_COLUMN_AVAILABLE+",-1) as "+DOWNLOADS_COLUMN_AVAILABLE+",Dls."+DOWNLOADS_COLUMN_SAVEPATH+",Dls."+DOWNLOADS_COLUMN_TRNAMEPREFIX+",Dls."+DOWNLOADS_COLUMN_LINKS+" from "+TABLE_WATCHLIST+" W inner join "+TABLE_AP_ANIMEINFO+" Info on W."+GENERAL_COLUMN_ID+"=Info."+GENERAL_COLUMN_ID+" left join "+TABLE_DOWNLOADS+" Dls on W."+GENERAL_COLUMN_ID+"=Dls."+DOWNLOADS_COLUMN_WATCHLISTID);
            while(rs.next()){
            	//System.out.println(rs.getInt(GENERAL_COLUMN_ID)+" "+rs.getString(ANIMEINFO_COLUMN_TITLE)+" "+rs.getInt(WATCHLIST_COLUMN_EPISODESWATCHED)+" "+rs.getInt(WATCHLIST_COLUMN_CURRENTEPISODE)+" "+rs.getString(WATCHLIST_COLUMN_LASTUPDATED)+" "+rs.getString(DOWNLOADS_COLUMN_AVAILABLE));
            	WatchlistAnime temp = new WatchlistAnime(rs.getInt(GENERAL_COLUMN_ID),rs.getString(AP_ANIMEINFO_COLUMN_TITLE),rs.getInt(WATCHLIST_COLUMN_EPISODESWATCHED),rs.getInt(WATCHLIST_COLUMN_CURRENTEPISODE),rs.getString(WATCHLIST_COLUMN_LASTUPDATED));
            	temp.setDownloaded(rs.getInt(DOWNLOADS_COLUMN_DOWNLOADED));
            	temp.setAvailable(rs.getInt(DOWNLOADS_COLUMN_AVAILABLE));
            	temp.setSavepath(rs.getString(DOWNLOADS_COLUMN_SAVEPATH));
            	temp.setTrnameprefix(rs.getString(DOWNLOADS_COLUMN_TRNAMEPREFIX));
            	temp.setLinks(rs.getString(DOWNLOADS_COLUMN_LINKS));
            	temp.initialize();
            	//System.out.println("temp "+temp.getDownloaded()+" "+temp.getAvailable());
                wlist.add(temp);          
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return wlist;
    }
    
    public ArrayList<WatchlistUpdaterModel> getWatchlistUpdaterData(){
    	ArrayList<WatchlistUpdaterModel> wlist = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select W."+GENERAL_COLUMN_ID+",Info."+AP_ANIMEINFO_COLUMN_ANIMEPLANETID+",Info."+AP_ANIMEINFO_COLUMN_SEASON+",W."+WATCHLIST_COLUMN_CURRENTEPISODE+",W."+WATCHLIST_COLUMN_LASTUPDATED+" from "+TABLE_WATCHLIST+" W inner join "+TABLE_AP_ANIMEINFO+" Info on W."+GENERAL_COLUMN_ID+"=Info."+GENERAL_COLUMN_ID);
            while(rs.next()){
            	WatchlistUpdaterModel temp = new WatchlistUpdaterModel();
            	temp.setId(rs.getInt(GENERAL_COLUMN_ID));
            	temp.setAnimeplanetID(rs.getInt(AP_ANIMEINFO_COLUMN_ANIMEPLANETID));
            	temp.setSeason(rs.getString(AP_ANIMEINFO_COLUMN_SEASON));
            	temp.setCurrentEpisode(rs.getInt(WATCHLIST_COLUMN_CURRENTEPISODE));
            	temp.setLastupdated(rs.getString(WATCHLIST_COLUMN_LASTUPDATED));
                wlist.add(temp);          
            }
            rs.close();
            stt.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return wlist;
    }

    public ArrayList<NewAnime> getWatchLaterData() {
        ArrayList<NewAnime> wlist = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select W." + GENERAL_COLUMN_ID + ",Info." + AP_ANIMEINFO_COLUMN_ANIMEPLANETID + ",Info." + AP_ANIMEINFO_COLUMN_TITLE + ",Info." + AP_ANIMEINFO_COLUMN_IMGURL + ",Info." + AP_ANIMEINFO_COLUMN_GENRE + " from " + TABLE_WATCHLATER + " W inner join " + TABLE_AP_ANIMEINFO + " Info on W." + GENERAL_COLUMN_ID + "=Info." + GENERAL_COLUMN_ID);
            while (rs.next()) {
                wlist.add(new NewAnime(rs.getInt(GENERAL_COLUMN_ID),rs.getInt(AP_ANIMEINFO_COLUMN_ANIMEPLANETID),rs.getString(AP_ANIMEINFO_COLUMN_TITLE),rs.getString(AP_ANIMEINFO_COLUMN_IMGURL),rs.getString(AP_ANIMEINFO_COLUMN_GENRE)));
            }
            rs.close();
            stt.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex) {
                //do nothing (SQLite)
            }
        }
        return wlist;
    }

    public ArrayList<NewAnime> getWatchedData() {
        ArrayList<NewAnime> wlist = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select W." + GENERAL_COLUMN_ID + ",Info." + AP_ANIMEINFO_COLUMN_ANIMEPLANETID + ",Info." + AP_ANIMEINFO_COLUMN_TITLE + ",Info." + AP_ANIMEINFO_COLUMN_IMGURL + ",Info." + AP_ANIMEINFO_COLUMN_GENRE + " from " + TABLE_WATCHED + " W inner join " + TABLE_AP_ANIMEINFO + " Info on W." + GENERAL_COLUMN_ID + "=Info." + GENERAL_COLUMN_ID);
            while (rs.next()) {
                wlist.add(new NewAnime(rs.getInt(GENERAL_COLUMN_ID),rs.getInt(AP_ANIMEINFO_COLUMN_ANIMEPLANETID),rs.getString(AP_ANIMEINFO_COLUMN_TITLE),rs.getString(AP_ANIMEINFO_COLUMN_IMGURL),rs.getString(AP_ANIMEINFO_COLUMN_GENRE)));
            }
            rs.close();
            stt.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex) {
                //do nothing (SQLite)
            }
        }
        return wlist;
    }

    public ArrayList<SeasonsSortModel> getSeasons() {
        final String prefix = "dbControl - getSeasons: ";
        ArrayList<SeasonsSortModel> seasonsList = new ArrayList<>();
        Connection con = null;
        Statement stt;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            stt = con.createStatement();
            ResultSet rs = stt.executeQuery("select DISTINCT "+AP_ANIMEINFO_COLUMN_SEASON+" from "+TABLE_AP_ANIMEINFO);
            while (rs.next()){
                String season = rs.getString(AP_ANIMEINFO_COLUMN_SEASON);
                if (season != null && !season.equals("Upcoming")) {
                    StringTokenizer seasontokenizer = new StringTokenizer(season, " ");
                    if (seasontokenizer.countTokens() != 2) {
                        System.out.println(prefix + "Season format is incorrect: " + season);
                        return seasonsList;
                    }
                    try {
                        seasonsList.add(new SeasonsSortModel(seasontokenizer.nextToken(), Integer.valueOf(seasontokenizer.nextToken())));
                    } catch (NumberFormatException e) {
                        System.out.println(prefix + "Cannot cast year to integer for season: " + season);
                    }
                }
            }
            rs.close();
            stt.close();
            try {
                Collections.sort(seasonsList);
            } catch (ClassCastException e) {
                e.printStackTrace();
                System.out.println(prefix + "Cannot sort list");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex) {
                //do nothing (SQLite)
            }
        }
        return seasonsList;
    }

    public void handleWatchlistRemainingUpdate(ArrayList<Integer> ids){
        System.out.println("handleWatchlistRemainingUpdate - Starting");
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            StringBuilder command = new StringBuilder();
            command.append("select W."+GENERAL_COLUMN_ID+",W."+WATCHLIST_COLUMN_CURRENTEPISODE+",W."+WATCHLIST_COLUMN_LASTUPDATED+",Info."+ANIMEINFO_COLUMN_EPISODES+" from "+TABLE_WATCHLIST+" W inner join "+TABLE_ANIMEINFO+" Info on W."+GENERAL_COLUMN_ID+"=Info."+GENERAL_COLUMN_ID);
            if(ids.size()!=0){
                command.append(" where W."+GENERAL_COLUMN_ID+" not in(");
            }
            for(int i=0; i<ids.size(); i++) {
                if (i != ids.size() - 1)
                    command.append("?,");
                else
                    command.append("?)");
            }
            //System.out.println(command.toString());
            state = con.prepareStatement(command.toString());
            for(int i=1; i<=ids.size(); i++){
                state.setInt(i,ids.get(i-1));
            }
            ResultSet rs = state.executeQuery();
            while (rs.next()){
                if(rs.getString(WATCHLIST_COLUMN_LASTUPDATED)==null||!rs.getString(WATCHLIST_COLUMN_LASTUPDATED).trim().isEmpty()){
                    state = con.prepareStatement("update "+TABLE_WATCHLIST+" set "+WATCHLIST_COLUMN_LASTUPDATED+"=? where "+GENERAL_COLUMN_ID+"=?");
                    state.setString(1,"");
                    state.setInt(2,rs.getInt(GENERAL_COLUMN_ID));
                    state.executeUpdate();
                }
                if(rs.getInt(WATCHLIST_COLUMN_CURRENTEPISODE)==0){
                    try{
                        int eps = Integer.valueOf(rs.getString(ANIMEINFO_COLUMN_EPISODES));
                        if(eps!=0){
                            state = con.prepareStatement("update "+TABLE_WATCHLIST+" set "+WATCHLIST_COLUMN_CURRENTEPISODE+"=? where "+GENERAL_COLUMN_ID+"=?");
                            state.setInt(1, eps);
                            state.setInt(2, rs.getInt(GENERAL_COLUMN_ID));
                            state.executeUpdate();
                        }
                    }catch (NumberFormatException e){

                    }
                }
                Controller.updateProgress++;
            }

        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
    }

    public boolean checkIfExistsInAnimelinks(int id){
        boolean existsFlag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+GENERAL_COLUMN_ID+ " from "+TABLE_ANIMELINKS+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1,id);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                existsFlag = true;
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return existsFlag;
    }
    
    public boolean checkIfExistsInDownloads(int id_watchlist){
        boolean existsFlag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+DOWNLOADS_COLUMN_WATCHLISTID+ " from "+TABLE_DOWNLOADS+" where "+DOWNLOADS_COLUMN_WATCHLISTID+"=?");
            state.setInt(1,id_watchlist);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                existsFlag = true;
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return existsFlag;
    }

    public boolean checkIfExistsInWatchlist(int id){
        boolean existsFlag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+GENERAL_COLUMN_ID+ " from "+TABLE_WATCHLIST+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1,id);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                existsFlag = true;
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return existsFlag;
    }

    public boolean checkIfExistsInWatchLaterlist(int id){
        boolean existsFlag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+GENERAL_COLUMN_ID+ " from "+TABLE_WATCHLATER+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1,id);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                existsFlag = true;
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return existsFlag;
    }

    public boolean checkIfExistsInWatchedlist(int id){
        boolean existsFlag = false;
        Connection con = null;
        PreparedStatement state;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbName);

            state = con.prepareStatement("select "+GENERAL_COLUMN_ID+ " from "+TABLE_WATCHED+" where "+GENERAL_COLUMN_ID+"=?");
            state.setInt(1,id);
            ResultSet rs = state.executeQuery();
            if(rs.next()){
                existsFlag = true;
            }
            rs.close();
            state.close();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException npex){
                //do nothing (SQLite)
            }
        }
        return existsFlag;
    }
}
