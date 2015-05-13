package com.AndroidExample.FTP;

import android.os.AsyncTask;

public class AsyncListFilePath extends AsyncTask<String, Void, String[]>{
	public interface ListFilePathState {			
		public void listFilePathSuccess(String data[]);
		public void listFilePathError(Exception e);
	}
	
	private FTPCenter ftpCenter = new FTPCenter();
	
	private ListFilePathState listFilePathState;
	
	public AsyncListFilePath(ListFilePathState listFilePathState) {
		this.listFilePathState = listFilePathState;
	}

	protected String[] doInBackground(String... params) {
		String path = params[0];
		String data[] = null;
		
		try {
			ftpCenter.connect(FTPTag.SERVER, FTPTag.PORT, FTPTag.NAME, FTPTag.PASSWORD);
			data = ftpCenter.listFTPFilePath(path);
		} catch (Exception e) {
			if (listFilePathState != null)
				listFilePathState.listFilePathError(e);
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

	protected void onPostExecute(String[] result) {
		super.onPostExecute(result);
		if (listFilePathState != null)
			listFilePathState.listFilePathSuccess(result);
	}
}
