package anvilclient.anvilclient.util.utils;

public class TimeUtils {
	
	private TimeUtils() {
	}
	
	public static String formatTimeSecs(long secs) {
		String formattedTime;
	    if (secs / 3600 >= 1) {
			formattedTime = String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, secs % 60);
		} else {
			formattedTime = String.format("%02d:%02d", (secs % 3600) / 60, secs % 60);
		}
		return formattedTime;
	}
	
	public static String formatTimeMillis(long millis) {
		return formatTimeSecs(millis / 1000L);
	}

}
