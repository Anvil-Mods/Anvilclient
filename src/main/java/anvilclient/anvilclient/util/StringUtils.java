package anvilclient.anvilclient.util;

import java.util.ArrayList;

public class StringUtils {

	
	//Splits a given String into lines with n max Characters and returns the resulting lines as String[]
	public static String[] lineSplit(String string, int charLimit) {
		String line = "";
		String[] strings = string.split(" ");
		ArrayList<String> lines = new ArrayList<String>();
		for(String word : strings) {
			if(line.length() != 0) {
				if((line.length() + 1 + word.length()) >= charLimit) {
					lines.add(line);
					line = "";
				}
			line = line + " " + word;
			}
		}
		lines.add(line);
		return (String[]) lines.toArray();
	}
}
