package com.AndroidExample.Activity;


import java.io.File;

import org.apache.commons.net.ftp.FTPFile;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.AndroidExample.FTP.AsyncDelete;
import com.AndroidExample.FTP.AsyncDelete.DeleteState;
import com.AndroidExample.FTP.AsyncDownload;
import com.AndroidExample.FTP.AsyncDownload.DownloadState;
import com.AndroidExample.FTP.AsyncListDirectory;
import com.AndroidExample.FTP.AsyncListDirectory.ListDirectoryState;
import com.AndroidExample.FTP.AsyncMakeDirectory;
import com.AndroidExample.FTP.AsyncMakeDirectory.MakeDirectoryState;
import com.AndroidExample.FTP.AsyncUpload;
import com.AndroidExample.FTP.AsyncUpload.UploadState;
import com.AndroidExample.FTP.FTPTag;
import com.AndroidExample.FTP.FTPTransportStructure;
import com.AndroidExample.View.MainView;

public class MainActivity extends Activity {
	private MainView mainView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(mainView = new MainView(this));
		
		setList();
		setCreate();
		setDelete();
		setUpload();
		setDownload();
	}
	
	private void setList(){
		mainView.getList().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AsyncListDirectory asyncListDirectory = new AsyncListDirectory(new ListDirectoryState() {
					public void listDirectorySuccess(FTPFile[] data) {
						for(int i=0; i<data.length; i++){
							Log.e("data" + i, data[i].getName());
						}
					}
					public void listDirectoryError(Exception e) {
						Log.e("Exception" , e.toString());
					}
				});
				asyncListDirectory.execute(File.separator);
				
//				AsyncListFile asyncListFile = new AsyncListFile(new ListFileState() {
//					public void listFileSuccess(FTPFile[] data) {
//						for(int i=0; i<data.length; i++){
//							Log.e("data" + i, data[i].getName());
//						}
//					}
//					
//					public void listFileError(Exception e) {
//						Log.e("Exception" , e.toString());
//					}
//				});
//				asyncListFile.execute(File.separator);
				
//				AsyncListFile asyncListFile = new AsyncListFile("jpg", new ListFileState() {
//					public void listFileSuccess(FTPFile[] data) {
//						for(int i=0; i<data.length; i++){
//							Log.e("data" + i, data[i].getName());
//						}
//					}
//					
//					public void listFileError(Exception e) {
//						Log.e("Exception" , e.toString());
//					}
//				});
//				asyncListFile.execute(File.separator);
				
//				AsyncListFileName asyncListFileName = new AsyncListFileName(new ListFileNameState() {
//					public void listFileNameSuccess(String[] data) {
//						for(int i=0; i<data.length; i++){
//							Log.e("data" + i, data[i]);
//						}
//					}
//					
//					public void listFileNameError(Exception e) {
//						Log.e("Exception" , e.toString());
//					}
//				});
//				asyncListFileName.execute(File.separator);
				
//				AsyncListFilePath asyncListFilePath = new AsyncListFilePath(new ListFilePathState() {
//					public void listFilePathSuccess(String[] data) {
//						for(int i=0; i<data.length; i++){
//							Log.e("data" + i, data[i]);
//						}
//					}
//					
//					public void listFilePathError(Exception e) {
//						Log.e("Exception" , e.toString());
//					}
//				});
//				asyncListFilePath.execute(File.separator + "lua");
				
//				AsyncFindFTPFile asyncFindFTPDirectory = new AsyncFindFTPFile(FTPCenter.FIND_PART_MODE, new FindFTPFileState() {
//					public void findFTPFileSuccess(ArrayList<FTPFile> data) {
//						Log.e("size", "" + data.size());
//						for(int i=0; i<data.size(); i++){
//							Log.e("data", data.get(i).getLink());
//						}
//					}
//					
//					public void findFTPFileError(Exception e) {
//						Log.e("Exception" , e.toString());
//					}
//				});
//				asyncFindFTPDirectory.execute(File.separator, "a");
			}
		});
	}
	
	private void setCreate(){
		/*
		 * 已有此資料夾data回傳false
		 * FTP沒有的路徑會自己創建
		 */
		mainView.getCreate().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AsyncMakeDirectory asyncMakeDirectory = new AsyncMakeDirectory(new MakeDirectoryState() {
					public void makeDirectorySuccess(Boolean data) {
						Log.e("data", "" + data);
					}
					
					public void makeDirectoryError(Exception e) {
						Log.e("Exception" , e.toString());
					}
				});
				asyncMakeDirectory.execute(File.separator + "lua" + File.separator + "test01" + File.separator + "test02" + File.separator + "test03", 
						File.separator + "lua" + File.separator + "test02" + File.separator + "test02" + File.separator + "test03", 
						File.separator + "lua" + File.separator + "test03" + File.separator + "test02" + File.separator + "test03", 
						File.separator + "lua" + File.separator + "test04" + File.separator + "test02" + File.separator + "test03", 
						File.separator + "lua" + File.separator + "test05" + File.separator + "test02" + File.separator + "test03");
			}
		});
	}
	
	private void setDelete(){
		mainView.getDelete().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AsyncDelete asyncDelete = new AsyncDelete(new DeleteState() {
					public void deleteSuccess(Boolean data) {
						Log.e("Delete", "" + data);
					}
					
					public void deleteError(Exception e) {
						Log.e("Exception" , e.toString());
					}
				});
				asyncDelete.execute(File.separator + "lua" + File.separator + "ASUS Android USB drivers for Windows_20110321", 
						File.separator + "lua" + File.separator + "ASUS Android USB drivers for Windows_20110929");
			}
		});
	}
	
	private void setUpload(){
		/*
		 * 沒有的目錄會自己建
		 * 已有的檔案會覆蓋
		 */
		mainView.getUpload().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AsyncUpload asyncUpload = new AsyncUpload(new UploadState() {
					public void uploadSuccess(Boolean data) {
						Log.e("Upload", "" + data);
					}
					
					public void uploadError(Exception e) {
						Log.e("Exception" , e.toString());
					}
				});
				asyncUpload.execute(
					new FTPTransportStructure(FTPTag.FTP_DIRECTORY + File.separator + "DRL.lua", File.separator + "lua" + File.separator + "0000_test01" + File.separator + "testtest" + File.separator + "DRL.lua"), 
					new FTPTransportStructure(FTPTag.FTP_DIRECTORY + File.separator + "DBPS.dbps", File.separator + "lua" + File.separator + "0000_test01" + File.separator + "DBPS.dbps"));
			}
		});
	}
	
	private void setDownload(){
		mainView.getDownload().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.e("click", "click");
				AsyncDownload asyncDownload = new AsyncDownload(new DownloadState() {
					public void downloadSuccess(Boolean data) {
						Log.e("Download", "" + data);
					}
					
					public void downloadError(Exception e) {
						Log.e("Exception" , e.toString());
					}
				});
				asyncDownload.execute(
					new FTPTransportStructure(FTPTag.FTP_DIRECTORY + File.separator + "test.txt", File.separator + "test.txt"), 
					new FTPTransportStructure(FTPTag.FTP_DIRECTORY + File.separator + "test" + File.separator + "index.html", File.separator + "WAMP" + File.separator + "index.html"));
			}
		});
	}
	
	protected void onDestroy() {
		super.onDestroy();
	}
}