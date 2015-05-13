package com.AndroidExample.FTP;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncUpload extends AsyncTask<FTPTransportStructure, Void, Boolean>{
	public interface UploadState {
		public void uploadSuccess(Boolean data);
		public void uploadError(Exception e);
	}
	
	private FTPCenter ftpCenter = new FTPCenter();
	
	private UploadState uploadState;
	
	public AsyncUpload(UploadState uploadState) {
		this.uploadState = uploadState;
	}

	protected Boolean doInBackground(FTPTransportStructure... params) {
		FTPTransportStructure ftpTransportStructure[] = params;
		
		try {
			ftpCenter.connect(FTPTag.SERVER, FTPTag.PORT, FTPTag.NAME, FTPTag.PASSWORD);
			for(int i=0; i<ftpTransportStructure.length; i++){
				Log.e("t", "t" + i);
				ftpCenter.uploadFTPFile(ftpTransportStructure[i]);
			}
		} catch (Exception e) {
			if (uploadState != null)
				uploadState.uploadError(e);
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
		if (uploadState != null)
			uploadState.uploadSuccess(result);
	}
}
