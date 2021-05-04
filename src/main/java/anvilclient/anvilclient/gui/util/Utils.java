package anvilclient.anvilclient.gui.util;

public class Utils {
	
	public static double trimDouble(double value, int decimalCount) {
		double factor = Math.pow(10, decimalCount);
		return ((double)((int)(value * factor)))/factor;
	}
	
	public static float trimFloat(float value, int decimalCount) {
		return (float) trimDouble(value, decimalCount);
	}

}
