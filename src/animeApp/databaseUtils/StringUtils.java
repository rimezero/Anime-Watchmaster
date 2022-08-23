package animeApp.databaseUtils;

public class StringUtils {
	
	/**
	 * 
	 * @param animeType - Anime planet anime type string
	 * @return - Episodes number int or -1 if type does not contain episode number
	 */
	public static int getEpisodesFromAptype(String animeType) {
		final String[] splitType = getSplitAnimeType(animeType);
		if(splitType[1].equals("n/a")) {
			return -1;
		}
		
		try {
			return Integer.valueOf(splitType[1]);
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * 
	 * @param animeType - Anime planet anime type string
	 * @return - String array of size 2. index 0 containing type and index 1 containing episodes. Episodes value n/a if episodes not found.
	 */
	public static String[] getSplitAnimeType(String animeType) {
		String[] typeAr = new String[2];
		
		if(!animeType.contains("(")) {
			typeAr[0] = animeType;
			typeAr[1] = "n/a";
 		}else {
 			final String[] splitType = animeType.split("(");
 			typeAr[0] = splitType[0].replace(" ", "");
 			typeAr[1] = splitType[1].replace("+", "").replace(" ep)", "").replace(" eps)", "");
 		}
		
		
		return typeAr;
	}
}
