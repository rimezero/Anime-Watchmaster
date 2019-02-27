package animeApp.databaseUtils;

import java.io.File;
import java.util.ArrayList;

public class fileSystemUtils {
	
	public static boolean checkIfFileExists(String path) {
		File file = new File(path);
		return file.exists();
	}
	
	public static boolean checkIfFileIsDirectory(String path) {
		File file = new File(path);
		return file.exists() && file.isDirectory();
	}
	
	public static boolean checkIfFileIsFile(String path) {
		File file = new File(path);
		return file.exists() && file.isFile();
	}
	
	public static String checkIfFolderExists(String globalpath, String foldername) {
		if(!checkIfFileIsDirectory(globalpath)) {
			System.out.println("Global path is invalid.");
			return "";
		}
		
		ArrayList<String> dirlisting = getDirectoryListing(globalpath);
		ArrayList<String> templist = new ArrayList<>();
		for(String filename : dirlisting) {
			templist.add(filename.toLowerCase());
		}
		
		String tempFolderName = foldername.toLowerCase();
		
		int index = templist.indexOf(tempFolderName);
		if(index!=-1) {
			return globalpath+"\\"+dirlisting.get(index);
		}
		return "";
	}
	
	public static ArrayList<String> getDirectoryListing(String path) {
		ArrayList<String> listing = new ArrayList<>();
		
		File dir = new File(path);
		
		File[] fileslist = dir.listFiles();
		for(File file : fileslist) {
			listing.add(file.getName());
		}
		
		return listing;
	}
	
	public static boolean createFolder(String path) {
		File dir = new File(path);
		return dir.mkdir();
	}
}
