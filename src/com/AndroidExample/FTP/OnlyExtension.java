package com.AndroidExample.FTP;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;


public class OnlyExtension implements FTPFileFilter  {
	private String extension;
	
	public OnlyExtension(String extension) {
		this.extension = "." + extension;
	}

	public boolean accept(FTPFile ftpFile) {
		return ftpFile.getName().endsWith(extension);
	}
}
