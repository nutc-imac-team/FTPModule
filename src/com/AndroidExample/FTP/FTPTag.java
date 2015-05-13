package com.AndroidExample.FTP;

import java.io.File;

/**
 * 存放FTP的所有相關訊息，如IP、Port、路徑、Key
 * @author Jason
 *
 */
public class FTPTag {
	public static final String SERVER = "180.177.81.152";
	public static final int PORT = 21;
	public static final String NAME = "SERVER";
	public static final String PASSWORD = "3325713";
	
	public static final String MAIN_DIRECTORY = android.os.Environment
			.getExternalStorageDirectory().getPath()
			+ File.separator
			+ "Android"
			+ File.separator
			+ "data"
			+ File.separator
			+ "com.RobotTab.Activity";
	public static final String ROBOTTAB_DIRECTORY = MAIN_DIRECTORY + File.separator + "Robot";
	public static final String FTP_DIRECTORY = ROBOTTAB_DIRECTORY + File.separator + "FTP";
}
