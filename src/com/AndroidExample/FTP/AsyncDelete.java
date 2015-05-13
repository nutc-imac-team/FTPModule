package com.AndroidExample.FTP;

import android.os.AsyncTask;

public class AsyncDelete extends AsyncTask<String, Void, Boolean>{
	public interface DeleteState {
		public void deleteSuccess(Boolean data);
		public void deleteError(Exception e);
	}
	
	private FTPCenter ftpCenter = new FTPCenter();
	
	private DeleteState deleteState;
	
	public AsyncDelete(DeleteState deleteState) {
		this.deleteState = deleteState;
	}

	protected Boolean doInBackground(String... params) {
		String path[] = params;
		
		try {
			ftpCenter.connect(FTPTag.SERVER, FTPTag.PORT, FTPTag.NAME, FTPTag.PASSWORD);
			for(int i=0; i<path.length; i++){
				ftpCenter.deleteFile(path[i]);
			}
		} catch (Exception e) {
			if (deleteState != null)
				deleteState.deleteError(e);
			this.cancel(true);
		} finally {
			try {
				ftpCenter.close();
			} catch (Exception e) {
				// do nothing
			}
		}
		
		return true;
	}

	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (deleteState != null)
			deleteState.deleteSuccess(result);
	}
}
