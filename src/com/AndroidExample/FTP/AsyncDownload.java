package com.AndroidExample.FTP;

import android.os.AsyncTask;

public class AsyncDownload extends AsyncTask<FTPTransportStructure, Void, Boolean>{
	public interface DownloadState {
		public void downloadSuccess(Boolean data);
		public void downloadError(Exception e);
	}
	
	private FTPCenter ftpCenter = new FTPCenter();
	
	private DownloadState downloadState;
	
	public AsyncDownload(DownloadState downloadState) {
		this.downloadState = downloadState;
	}

	protected Boolean doInBackground(FTPTransportStructure... params) {
		FTPTransportStructure ftpTransportStructure[] = params;
		
		try {
			ftpCenter.connect(FTPTag.SERVER, FTPTag.PORT, FTPTag.NAME, FTPTag.PASSWORD);
			for(int i=0; i<ftpTransportStructure.length; i++){
				ftpCenter.downloadFTPFile(ftpTransportStructure[i]);
			}
		} catch (Exception e) {
			if (downloadState != null)
				downloadState.downloadError(e);
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
		if (downloadState != null)
			downloadState.downloadSuccess(result);
	}
}
