package com.AndroidExample.FTP;

import org.apache.commons.net.ftp.FTPFile;

import android.os.AsyncTask;

public class AsyncListDirectory extends AsyncTask<String, Void, FTPFile[]>{
	public interface ListDirectoryState {			
		public void listDirectorySuccess(FTPFile data[]);
		public void listDirectoryError(Exception e);
	}
	
	private FTPCenter ftpCenter = new FTPCenter();
	
	private ListDirectoryState listDirectoryState;
	
	public AsyncListDirectory(ListDirectoryState listDirectoryState) {
		this.listDirectoryState = listDirectoryState;
	}

	protected FTPFile[] doInBackground(String... params) {
		String path = params[0];
		FTPFile data[] = null;
		
		try {
			ftpCenter.connect(FTPTag.SERVER, FTPTag.PORT, FTPTag.NAME, FTPTag.PASSWORD);
			data = ftpCenter.listFTPDirectory(path);
		} catch (Exception e) {
			if (listDirectoryState != null)
				listDirectoryState.listDirectoryError(e);
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
		if (listDirectoryState != null)
			listDirectoryState.listDirectorySuccess(result);
	}
}
