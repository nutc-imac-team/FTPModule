package com.AndroidExample.FTP;

import org.apache.commons.net.ftp.FTPFile;

import android.os.AsyncTask;

public class AsyncListFile extends AsyncTask<String, Void, FTPFile[]>{
	public interface ListFileState {
		public void listFileSuccess(FTPFile data[]);
		public void listFileError(Exception e);
	}
	
	private FTPCenter ftpCenter = new FTPCenter();
	
	private String extension = "";
	
	private ListFileState listFileState;
	
	public AsyncListFile(String extension, ListFileState listFileState) {
		this(listFileState);
		this.extension = extension;
	}
	
	public AsyncListFile(ListFileState listFileState) {
		this.listFileState = listFileState;
	}

	protected FTPFile[] doInBackground(String... params) {
		String path = params[0];
		
		FTPFile data[] = null;
		
		try {
			ftpCenter.connect(FTPTag.SERVER, FTPTag.PORT, FTPTag.NAME, FTPTag.PASSWORD);
			if(extension.equals("")){
				data = ftpCenter.listFTPFile(path);
			}
			else{
				data = ftpCenter.listFTPFile(path, new OnlyExtension(extension));
			}
		} catch (Exception e) {
			if (listFileState != null)
				listFileState.listFileError(e);
			this.cancel(true);
		} finally {
			try {
				ftpCenter.close();
			} catch (Exception e) {
				// do nothing
			}
		}
		
		return data;
	}

	protected void onPostExecute(FTPFile[] result) {
		super.onPostExecute(result);
		if (listFileState != null)
			listFileState.listFileSuccess(result);
	}
}
