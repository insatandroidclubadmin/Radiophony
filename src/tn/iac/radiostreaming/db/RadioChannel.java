package tn.iac.radiostreaming.db;

public class RadioChannel{
	
	public static final int NATIONAL = 0;
	public static final int INTERNATIONAL = 1;
	public static final int FAVORITE = 2;
	
	private int id;
	private String name;
	private String url;
	private String website;
	private String logo;
	private int type;
	private int flag;
	
	public RadioChannel(){}
	public RadioChannel(String name, String url, String website, String logo ,int type, int flag){
		this.name = name;
		this.url=url;
		this.website= website;
		this.logo= logo;
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
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}


