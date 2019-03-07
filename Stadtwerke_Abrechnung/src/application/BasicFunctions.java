package application;

public class BasicFunctions {
	
	public static double roundDoubleNachkommastellen(double value, int anzStellen) {
		return  Math.round(value * Math.pow(10, anzStellen)) / Math.pow(10, anzStellen);
	}
	
	
}
