package com.AndroidExample.FTP;

import android.os.AsyncTask;

public class AsyncListFileName extends AsyncTask<String, Void, String[]>{
	public interface ListFileNameState {			
		public void listFileNameSuccess(String data[]);
		public void listFileNameError(Exception e);
	}
	
	private FTPCenter ftpCenter = new FTPCenter();
	
	private ListFileNameState listFileNameState;
	
	public AsyncListFileName(ListFileNameState listFileNameState) {
		this.listFileNameState = listFileNameState;
	}

	protected String[] doInBackground(String... params) {
		String path = params[0];
		String data[] = null;
		
		try {
			ftpCenter.connect(FTPTag.SERVER, FTPTag.PORT, FTPTag.NAME, FTPTag.PASSWORD);
			data = ftpCenter.listFTPFileName(path);
		} catch (Exception e) {
			if (listFileNameState != null)
				listFileNameState.listFileNameError(e);
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
		if (listFileNameState != null)
			listFileNameState.listFileNameSuccess(result);
	}
}
