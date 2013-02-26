package tn.iac.radiostreaming.bd;

import tn.iac.radiostreaming.R;
import tn.iac.radiostreaming.R.layout;
import tn.iac.radiostreaming.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RadioChannel{
	private int id;
	private String name;
	private String url;
	private String tag;
	private String type;
	private int flag;
	
	public RadioChannel(){}
	public RadioChannel(String name,String tag,String url, String type, int flag){
		this.name = name;
		this.tag = tag;
		this.url=url;
		this.type = type;
		this.flag = flag;
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	
	
	}


