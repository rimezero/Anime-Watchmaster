package animeApp.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public class Configuration {
	private String serverIp = "127.0.0.1";
	private boolean enableDownloads = true;
	private String globalDownloadsPath = " ";
	private boolean enableWatchnext = false;
	private boolean incrementEpisodesWatched = true;
	private boolean getHighestQuality = true;
	//private String[] qualities = {"1080p","720p","480p","Minimum"};
	private int qualityIndex = 0;
	private boolean autocreateFolder = false;
	//MT = multithreading
	private boolean watchlistUpdateMT = true;
	private boolean downloadsUpdateMT = true;
	private boolean specificUpdaters = true;
	private boolean watchlistUpdater = false;
	private boolean downloadsUpdater = false;
	private boolean databaseUpdater = true;
	private boolean seasonsUpdater = true;
	private boolean topanimeUpdater = true;
	private boolean hotanimeUpdater = false;
	public String getServerIp() {
		return serverIp;
	}
	
	//Singleton pattern
	private Configuration() {}
	
	private static Configuration configurationInstance;
	
	public static synchronized Configuration getInstance() {
		if(configurationInstance==null) {
			configurationInstance = new Configuration();
		}
		return configurationInstance;
	}
	
	public static Configuration getDefaultConfiguration() {
		configurationInstance = new Configuration();
		return configurationInstance;
	}
	
	
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public boolean isEnableDownloads() {
		return enableDownloads;
	}
	public void setEnableDownloads(boolean enableDownloads) {
		this.enableDownloads = enableDownloads;
	}
	public String getGlobalDownloadsPath() {
		return globalDownloadsPath;
	}
	public void setGlobalDownloadsPath(String globalDownloadsPath) {
		this.globalDownloadsPath = globalDownloadsPath;
	}
	public boolean isEnableWatchnext() {
		return enableWatchnext;
	}
	public void setEnableWatchnext(boolean enableWatchnext) {
		this.enableWatchnext = enableWatchnext;
	}
	public boolean isIncrementEpisodesWatched() {
		return incrementEpisodesWatched;
	}

	public void setIncrementEpisodesWatched(boolean incrementEpisodesWatched) {
		this.incrementEpisodesWatched = incrementEpisodesWatched;
	}

	public boolean isGetHighestQuality() {
		return getHighestQuality;
	}
	public void setGetHighestQuality(boolean getHighestQuality) {
		this.getHighestQuality = getHighestQuality;
	}
	public int getQualityIndex() {
		return qualityIndex;
	}
	public void setQualityIndex(int qualityIndex) {
		this.qualityIndex = qualityIndex;
	}
	
	public boolean isAutocreateFolder() {
		return autocreateFolder;
	}

	public void setAutocreateFolder(boolean autocreateFolder) {
		this.autocreateFolder = autocreateFolder;
	}

	public boolean isWatchlistUpdateMT() {
		return watchlistUpdateMT;
	}

	public void setWatchlistUpdateMT(boolean watchlistUpdateMT) {
		this.watchlistUpdateMT = watchlistUpdateMT;
	}

	public boolean isDownloadsUpdateMT() {
		return downloadsUpdateMT;
	}

	public void setDownloadsUpdateMT(boolean downloadsUpdateMT) {
		this.downloadsUpdateMT = downloadsUpdateMT;
	}

	public boolean isSpecificUpdaters() {
		return specificUpdaters;
	}

	public void setSpecificUpdaters(boolean specificUpdaters) {
		this.specificUpdaters = specificUpdaters;
	}

	public boolean isWatchlistUpdater() {
		return watchlistUpdater;
	}

	public void setWatchlistUpdater(boolean watchlistUpdater) {
		this.watchlistUpdater = watchlistUpdater;
	}

	public boolean isDownloadsUpdater() {
		return downloadsUpdater;
	}

	public void setDownloadsUpdater(boolean downloadsUpdater) {
		this.downloadsUpdater = downloadsUpdater;
	}

	public boolean isDatabaseUpdater() {
		return databaseUpdater;
	}

	public void setDatabaseUpdater(boolean databaseUpdater) {
		this.databaseUpdater = databaseUpdater;
	}

	public boolean isSeasonsUpdater() {
		return seasonsUpdater;
	}

	public void setSeasonsUpdater(boolean seasonsUpdater) {
		this.seasonsUpdater = seasonsUpdater;
	}

	public boolean isTopanimeUpdater() {
		return topanimeUpdater;
	}

	public void setTopanimeUpdater(boolean topanimeUpdater) {
		this.topanimeUpdater = topanimeUpdater;
	}

	public boolean isHotanimeUpdater() {
		return hotanimeUpdater;
	}

	public void setHotanimeUpdater(boolean hotanimeUpdater) {
		this.hotanimeUpdater = hotanimeUpdater;
	}

	public void saveConfiguration() {
		File configFile = new File("config.ini");
		try {
			if(!configFile.exists()) {
				configFile.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(configFile));
			for(Field f : this.getClass().getDeclaredFields()) {
				if(!f.getName().equals("configurationInstance")) {
					bw.write(f.getName()+"="+f.get(this));
					bw.newLine();
				}				
			}
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initialize() {
		File configFile = new File("config.ini");
		if(!configFile.exists()) {
			saveConfiguration();
		}else {
			try {
				BufferedReader br = new BufferedReader(new FileReader(configFile));
				String line = br.readLine();
				while(line!=null) {
					String[] splitLine = line.split("=");
					if(splitLine.length==2) {
						try {
							Field f = this.getClass().getDeclaredField(splitLine[0]);
							assignValue(f, splitLine[1]);

						} catch (NoSuchFieldException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
					line = br.readLine();
				}
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void assignValue(Field f, String value) {
		try {
			if(f.getType() == String.class) {
				f.set(this,value);
			}else if(f.getType() == boolean.class) {
				boolean var = Boolean.valueOf(value);
				f.setBoolean(this,var);
			}else if(f.getType() == int.class) {
				int var = Integer.valueOf(value);
				f.setInt(this, var);
			}else if(f.getType() == double.class) {
				double var = Double.valueOf(value);
				f.setDouble(this, var);
			}else if(f.getType() == long.class) {
				long var = Long.valueOf(value);
				f.setLong(this, var);
			}else if(f.getType() == short.class) {
				short var = Short.valueOf(value);
				f.setShort(this, var);
			}else {
				System.out.println("Configuration.java: Cannot assign value for class type: "+f.getType().toString());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
}
