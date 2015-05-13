package com.AndroidExample.FTP;

import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPFile;

import android.os.AsyncTask;

public class AsyncFindFTPFile extends AsyncTask<String, Void, ArrayList<FTPFile>>{
	public interface FindFTPFileState {
		public void findFTPFileSuccess(ArrayList<FTPFile> data);
		public void findFTPFileError(Exception e);
	}
	
	private FTPCenter ftpCenter = new FTPCenter();
	
	private FindFTPFileState findFTPFileState;
	
	private int findMode;
	
	public AsyncFindFTPFile(int findMode, FindFTPFileState findFTPFileState) {
		this.findMode = findMode;
		this.findFTPFileState = findFTPFileState;
	}

	protected ArrayList<FTPFile> doInBackground(String... params) {
		String path = params[0];
		String fileName = params[1];
		
		ArrayList<FTPFile> data = null;
		
		try {
			ftpCenter.connect(FTPTag.SERVER, FTPTag.PORT, FTPTag.NAME, FTPTag.PASSWORD);
			data = ftpCenter.findFTPFile(path, fileName, findMode);
		} catch (Exception e) {
			if (findFTPFileState != null)
				findFTPFileState.findFTPFileError(e);
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

	protected void onPostExecute(ArrayList<FTPFile> result) {
		super.onPostExecute(result);
		if (findFTPFileState != null)
			findFTPFileState.findFTPFileSuccess(result);
	}
}
