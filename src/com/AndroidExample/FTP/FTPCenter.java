package com.AndroidExample.FTP;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;

/**
 * FTP運作模組
 * Download JAR file ： http://commons.apache.org/proper/commons-net/download_net.cgi
 * Check documents ： http://commons.apache.org/proper/commons-net/apidocs/org/apache/commons/net/ftp/package-summary.html
 * @author Jason
 *
 */
public class FTPCenter {
	private FTPClient ftp;
	
	public static final int FIND_ENTIRE_MODE = 0x1001;
	public static final int FIND_PART_MODE = 0x1002;
	
	public FTPCenter(){
		ftp = new FTPClient();
	}

	public void connect(String server, int port, String name, String password) throws Exception{
		// 設定檔，主要用於國家、FTP Server的作業系統等設定；http://commons.apache.org/proper/commons-net/apidocs/org/apache/commons/net/ftp/FTPClientConfig.html
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		
		// 連線
		ftp.connect(server, port);
		
		// 等待FTP Server的回應碼
		if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
			ftp.disconnect();
			throw new Exception("與FTP Server連線請求失敗");
		}
		
		// 登入
		// 補
		ftp.login(name, password);
		
		// 檔案轉移的模式設定
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		
		// Client與Server間檔案傳輸的模式(分主動/被動，enterLocalPassiveMode()/enterLocalActiveMode())，一般傳輸請使用被動方式
		ftp.enterLocalPassiveMode();
	}
	
	public boolean changeDirectory(String path) throws Exception{
		return ftp.changeWorkingDirectory(path);
	}
	
	public FTPFile[] listFTPFile(String path) throws Exception {
		// 轉換至指定的目錄
		if(!ftp.changeWorkingDirectory(path)){
			throw new Exception("路徑轉換錯誤，請檢查是否路徑有誤");
		}
		
		return ftp.listFiles();
	}
	
	public FTPFile[] listFTPFile(String path, FTPFileFilter filter) throws Exception {
		// 轉換至指定的目錄
		if(!ftp.changeWorkingDirectory(path)){
			throw new Exception("路徑轉換錯誤，請檢查是否路徑有誤");
		}
		
		return ftp.listFiles(path, new OnlyExtension("jpg"));
	}
	
	public FTPFile[] listFTPDirectory(String path) throws Exception {
		// 轉換至指定的目錄
		if(!ftp.changeWorkingDirectory(path)){
			throw new Exception("路徑轉換錯誤，請檢查是否路徑有誤");
		}
		
		// ftp.mlistDir()
		return ftp.listDirectories();
	}
	
	public String[] listFTPFileName(String path) throws Exception {
		// 轉換至指定的目錄
		if(!ftp.changeWorkingDirectory(path)){
			throw new Exception("路徑轉換錯誤，請檢查是否路徑有誤");
		}
		
		return ftp.listNames();
	}
	
	public String[] listFTPFilePath(String path) throws Exception {
		// 轉換至指定的目錄
		if(!ftp.changeWorkingDirectory(path)){
			throw new Exception("路徑轉換錯誤，請檢查是否路徑有誤");
		}
		
		return ftp.listNames(path);
	}
	
	public ArrayList<FTPFile> findFTPFile(String path, String directoryName, int findMode) throws Exception {
		return findFTPFileRecursive(path, directoryName, findMode, new ArrayList<FTPFile>());
	}
	private ArrayList<FTPFile> findFTPFileRecursive(String path, String directoryName, int findMode, ArrayList<FTPFile> files) throws Exception{
		// 轉換至指定的目錄
		if(!ftp.changeWorkingDirectory(path)){
			throw new Exception("路徑轉換錯誤，請檢查是否路徑有誤");
		}
		
		FTPFile file[] = ftp.listFiles();
		
		for(int i=0; i<file.length; i++){
			if(findMode == FIND_ENTIRE_MODE){
				if(file[i].getName().equals(directoryName)){
					file[i].setLink(path + File.separator + file[i].getName());
					files.add(file[i]);
				}
			}
			else{
				if(file[i].getName().indexOf(directoryName) != -1){
					file[i].setLink(path + File.separator + file[i].getName());
					files.add(file[i]);
				}
			}
			if(file[i].isDirectory()){
				findFTPFileRecursive(path + File.separator + file[i].getName(), directoryName, findMode, files);
			}
		}
		return files;
	}
	
	public boolean makeDirectory(String pathname) throws Exception{
		return ftp.makeDirectory(pathname);
	}
	
	public boolean deleteFile(String path) throws Exception{
		FTPFile ftpFile = null;
		
		if((ftpFile = getCurrentFTPFile(path)) == null){
			throw new Exception("找無此目錄或是檔案");
		}
		else{
			if(ftpFile.isFile()){
				// 檔案
				return ftp.deleteFile(path);
			}
			else{
				// 目錄
				return deleteFileRecursive(path);
			}
		}
	}
	private boolean deleteFileRecursive(String path) throws Exception{
		FTPFile ftpFile[] = listFTPFile(path);
		
		for(int i=0; i<ftpFile.length; i++){
			if(ftpFile[i].isFile()){
				if(!ftp.deleteFile(path+ File.separator + ftpFile[i].getName())){
					throw new Exception("檔案刪除失敗，" + path+ File.separator + ftpFile[i].getName());
				}
			}
			else{
				if(!deleteFileRecursive(path + File.separator + ftpFile[i].getName())){
					throw new Exception("目錄刪除失敗，" + path+ File.separator + ftpFile[i].getName());
				}
			}
		}
		
		return ftp.removeDirectory(path);
	}
	
	public FTPFile getCurrentFTPFile(String path) throws Exception{
		String pathArray[] = path.split(File.separator);
		String pathNew = "";
		
		for(int i=0; i<pathArray.length-1; i++){
			pathNew = pathNew + pathArray[i] + File.separator;
		}
		
		// 轉換至指定的目錄
		if(!ftp.changeWorkingDirectory(pathNew)){
			throw new Exception("找無此目錄或是檔案");
		}
		
		FTPFile ftpFile[] = listFTPFile(pathNew);
		
		for(int i=0; i<ftpFile.length; i++){
			if(pathArray[pathArray.length-1].equals(ftpFile[i].getName())){
				return ftpFile[i];
			}
		}
		
		return null;
	}
	
	//------------
	/*
	 * 重複會覆蓋
	 * 裝置路徑錯誤會跑Exception
	 * FTP沒有的路徑會自動創建
	 */
	public void uploadFTPFile(FTPTransportStructure ftpTransportStructure) throws Exception {
		String pathFTPArray[] = ftpTransportStructure.pathFTP.split(File.separator);
		String pathFTPNew = "";
		
		for(int i=0; i<pathFTPArray.length-1; i++){
			pathFTPNew = pathFTPNew + pathFTPArray[i] + File.separator;
		}
		makeDirectory(pathFTPNew);
		if(!changeDirectory(pathFTPNew)){
			throw new Exception("路徑轉換錯誤，請檢查是否路徑有誤");
		}
		
		FileInputStream input = new FileInputStream(ftpTransportStructure.pathDevice);
		
		if(!ftp.storeFile(pathFTPArray[pathFTPArray.length-1], input)){
			input.close();
			throw new Exception("上傳失敗\n" +
					"Device Path：" + ftpTransportStructure.pathDevice + "\n" + 
					"FTP Path：" + ftpTransportStructure.pathFTP);
		}
		
		input.close();
	}
	
	public void downloadFTPFile(FTPTransportStructure ftpTransportStructure) throws Exception{
		/*
		 * 裝置無此目錄會自動創建
		 * 檔案重複會覆蓋
		 * FTP路徑錯誤會拋出Exception
		 */
		String pathFTPArray[] = ftpTransportStructure.pathFTP.split(File.separator);
		String pathFTPNew = "";
		
		for(int i=0; i<pathFTPArray.length-1; i++){
			pathFTPNew = pathFTPNew + pathFTPArray[i] + File.separator;
		}
		if(!changeDirectory(pathFTPNew)){
			throw new Exception("路徑轉換錯誤，請檢查是否路徑有誤");
		}
		
		createDeviceDirectory(ftpTransportStructure.pathDevice);
		File file = new File(ftpTransportStructure.pathDevice);
		
		OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
		InputStream inputStream = ftp.retrieveFileStream(pathFTPArray[pathFTPArray.length-1]);
		
		byte[] bytesArray = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(bytesArray)) != -1) {
        	outputStream.write(bytesArray, 0, bytesRead);
        }
        
        ftp.completePendingCommand();
        
        outputStream.close();
        inputStream.close();
	}
	private void createDeviceDirectory(String path){
		String pathArray[] = path.split(File.separator);
		String pathNew = pathArray[0];
		
		for(int i=1; i<pathArray.length -1; i++){
			pathNew = pathNew + File.separator + pathArray[i];
			File MaindirFile = new File(pathNew);
			if (!MaindirFile.exists()) {
				MaindirFile.mkdir();
			}
		}
	}
	
	public void close() throws Exception{
		if(ftp.isConnected()){
			ftp.logout();
			ftp.disconnect();
		}
	}
	
	
	/*
	 * public void uploadFTPFile(FTPTransportStructure ftpTransportStructure) throws Exception {
		Log.e("t", "test01");
		
		Log.e("t", "test02");
		String pathFTPArray[] = ftpTransportStructure.pathFTP.split(File.separator);
		String pathFTPNew = "";
		
		Log.e("t", "test03");
		for(int i=0; i<pathFTPArray.length-1; i++){
			pathFTPNew = pathFTPNew + pathFTPArray[i] + File.separator;
		}
		Log.e("t", "test04");
		makeDirectory(pathFTPNew);
		Log.e("t", "test05");
		if(!changeDirectory(pathFTPNew)){
			throw new Exception("路徑轉換錯誤，請檢查是否路徑有誤");
		}
		
		FileInputStream input = new FileInputStream(ftpTransportStructure.pathDevice);
		
		Log.e("t", "test06");
		OutputStream outputStream = ftp.storeFileStream(pathFTPArray[pathFTPArray.length-1]);
		byte[] buffer = new byte[1024];
		int len;
		while ((len = input.read(buffer)) != -1){
			outputStream.write(buffer, 0, len);
			outputStream.flush();
	    }
		
		Log.e("t", "test07");
//		ftp.completePendingCommand();
		
		Log.e("t", "test08");
		input.close();
//		outputStream.close();
	}
	 */
}
