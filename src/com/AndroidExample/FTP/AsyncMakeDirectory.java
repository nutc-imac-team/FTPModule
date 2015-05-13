package com.AndroidExample.FTP;

import android.os.AsyncTask;

public class AsyncMakeDirectory extends AsyncTask<String, Void, Boolean>{
	public interface MakeDirectoryState {
		public void makeDirectorySuccess(Boolean data);
		public void makeDirectoryError(Exception e);
	}
	
	private FTPCenter ftpCenter = new FTPCenter();
	
	private MakeDirectoryState makeDirectoryState;
	
	public AsyncMakeDirectory(MakeDirectoryState makeDirectoryState) {
		this.makeDirectoryState = makeDirectoryState;
	}

	protected Boolean doInBackground(String... params) {
		String path[] = params;
		
		try {
			ftpCenter.connect(FTPTag.SERVER, FTPTag.PORT, FTPTag.NAME, FTPTag.PASSWORD);
			for(int i=0; i<path.length; i++){
				ftpCenter.makeDirectory(path[i]);
			}
		} catch (Exception e) {
			if (makeDirectoryState != null)
				makeDirectoryState.makeDirectoryError(e);
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
		if (makeDirectoryState != null)
			makeDirectoryState.makeDirectorySuccess(result);
	}
}
