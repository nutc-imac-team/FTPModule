package com.AndroidExample.View;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;

import com.AndroidExample.R;
import com.AndroidExample.Module.MainParentLayout;

public class MainView extends MainParentLayout{
	private Button listDirectory, create, delete, upload, download;

	public MainView(Context context) {
		super(context);
	}
	
	protected void init() {
		setParents();
		setList();
		setCreate();
		setDelete();
		setUpload();
		setDownload();
	}
	
	private void setParents(){
		LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
		this.setLayoutParams(layoutParams);
		this.setBackgroundColor(Color.BLACK);
	}
	
	private void setList(){
		listDirectory = new Button(context);
		listDirectory.setId(getRandomId());
		LayoutParams layoutParams = new LayoutParams(WH.getW(30), WH.getH(10));
		listDirectory.setLayoutParams(layoutParams);
		listDirectory.setGravity(Gravity.CENTER);
		listDirectory.setTextSize(PX, WH.getTextSize(30));
		listDirectory.setText(R.string.List);
		this.addView(listDirectory);
	}
	
	private void setCreate(){
		create = new Button(context);
		create.setId(getRandomId());
		LayoutParams layoutParams = new LayoutParams(WH.getW(30), WH.getH(10));
		layoutParams.addRule(BELOW, listDirectory.getId());
		layoutParams.topMargin = WH.getH(3);
		create.setLayoutParams(layoutParams);
		create.setGravity(Gravity.CENTER);
		create.setTextSize(PX, WH.getTextSize(30));
		create.setText(R.string.Create);
		this.addView(create);
	}
	
	private void setDelete(){
		delete = new Button(context);
		delete.setId(getRandomId());
		LayoutParams layoutParams = new LayoutParams(WH.getW(30), WH.getH(10));
		layoutParams.addRule(BELOW, create.getId());
		layoutParams.topMargin = WH.getH(3);
		delete.setLayoutParams(layoutParams);
		delete.setGravity(Gravity.CENTER);
		delete.setTextSize(PX, WH.getTextSize(30));
		delete.setText(R.string.Delete);
		this.addView(delete);
	}
	
	private void setUpload(){
		upload = new Button(context);
		upload.setId(getRandomId());
		LayoutParams layoutParams = new LayoutParams(WH.getW(30), WH.getH(10));
		layoutParams.addRule(BELOW, delete.getId());
		layoutParams.topMargin = WH.getH(3);
		upload.setLayoutParams(layoutParams);
		upload.setGravity(Gravity.CENTER);
		upload.setTextSize(PX, WH.getTextSize(30));
		upload.setText(R.string.Upload);
		this.addView(upload);
	}
	
	private void setDownload(){
		download = new Button(context);
		download.setId(getRandomId());
		LayoutParams layoutParams = new LayoutParams(WH.getW(30), WH.getH(10));
		layoutParams.addRule(BELOW, upload.getId());
		layoutParams.topMargin = WH.getH(3);
		download.setLayoutParams(layoutParams);
		download.setGravity(Gravity.CENTER);
		download.setTextSize(PX, WH.getTextSize(30));
		download.setText(R.string.Download);
		this.addView(download);
	}
	
	public Button getList(){return this.listDirectory;}
	public Button getCreate(){return this.create;}
	public Button getDelete(){return this.delete;}
	public Button getUpload(){return this.upload;}
	public Button getDownload(){return this.download;}
}
