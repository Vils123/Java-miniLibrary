package lv.venta.demo.utils;

public class Verification {

	public static String verifyName(String name)      //izfiltree lai paliek tikai burti
	{
	    String returnName = "";
	    for (int i = 0; i < name.length(); ++i)
	    {
	        if (Character.isLetter(name.charAt(i)))
	            returnName += name.charAt(i);
	    }
	    return returnName;
	 }
}
