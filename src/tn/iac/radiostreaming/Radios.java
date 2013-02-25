package tn.iac.radiostreaming;

import java.util.HashMap;

public class Radios {
	private HashMap<String, String> radios;

	public Radios() {
		radios = new HashMap<String, String>();
		radios.put("mosaique", "http://radio.mosaiquefm.net:8000/mosalive");
		radios.put("shems", "http://stream8.tanitweb.com/shems");
		radios.put("ifm", "http://radioifm.ice.infomaniak.ch/radioifm-128.mp3");
		radios.put("express", "http://217.114.200.125/;stream.mp3");
	} 
	
	public String getUrl(String name){
		return radios.get(name);
	}
	
	
}
