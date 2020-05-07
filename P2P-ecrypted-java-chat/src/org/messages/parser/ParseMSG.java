package org.messages.parser;

public class ParseMSG {

	// Style your received messages
	public static String parseMessageRecv(String msg) {
		
		String ret;
		
		ret = "<p text-align:left;color:green;>"+msg+"</p>";
		
		return ret;
	}
	
	// Style your sent messages
	public static String parseMessageEnv(String msg) {
		String ret;
		
		ret = "<p text-align:right;color:red;>"+msg+"</p>";
		
		return ret;
	}

}
