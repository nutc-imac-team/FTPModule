# FTPModule
FTP模組

##說明
結合commons-net-3.3.jar實做出來的FTP模組，簡單化FTP操作的程式碼。


##使用說明

* 專案匯入commons-net-3.3.jar

* 把FTP模組的java檔放入專案，共有FTPCenter、FTPTag、FTPTransportStructure四個檔案


* 修改FTPTag中的SERVER(IP)、PORT、NAME(帳號)、PASSWORD內容


* 在FTPTag中可以定義FTP操作時所用到的所有路徑


* 使用者自行撰寫AsyncTask來呼叫模組的方法與處裡FTP，模組的方法呼叫如下



```
// 上傳或下載時，手機平板這端的路徑
private String pathDevice = android.os.Environment
    .getExternalStorageDirectory().getPath()
    + File.separator
    + "Android"
    + File.separator
    + "data"
    + File.separator
    + "com.RobotTab.Activity";
// 上傳或下載時，FTP上的路徑
private String pathFTP = "/lua";

// 上傳下載時用的資料結構
private FTPTransportStructure ftpTransportStructure = new FTPTransportStructure(pathDevice, pathFTP);
  
// 實體化FTP模組
private FTPCenter ftpCenter = new FTPCenter();

try {
  // 與FTP Server連線
  ftpCenter.connect(FTPTag.SERVER, FTPTag.PORT, FTPTag.NAME, FTPTag.PASSWORD);
  
  // 一連串的FTP操作
  ftpCenter.downloadFTPFile(ftpTransportStructure);
  ftpCenter.listFTPFile(pathFTP);
  
  // 釋放FTP模組
  ftpCenter.close();
} catch (Exception e) {
} finally {
}
```

##模組類別說明

* FTPTag

存放FTP Server的IP/Port/帳號/Password(可修改)，另外使用者可以在此模組自行定義FTP的操作路徑。


* FTPTransportStructure

呼叫FTP模組中上傳與下載時所使用的資料結構，內容存放一組「設備路徑」與「FTP路徑」。


* OnlyExtension

檔案選擇器模組，在呼叫FTPCenter中的listFTPFile()方法時，可以加入此類別來當作選擇器。

* FTPCenter

FTP操作的模組，FTP操作時的一連串複雜動作都已經實作在此類別上，使用者僅需把此類別new出來，呼叫裡面的方法即可。


* 使用者自行撰寫的AsyncTask範例

```
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

```





