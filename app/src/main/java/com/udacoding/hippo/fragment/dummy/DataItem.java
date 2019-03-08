package com.udacoding.hippo.fragment.dummy;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataItem implements Serializable {

	@SerializedName("phone")
	private String phone;

	@SerializedName("lon")
	private String lon;

	@SerializedName("location")
	private String location;

	@SerializedName("id")
	private String id;

	@SerializedName("placename")
	private String placename;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("lat")
	private String lat;

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setLon(String lon){
		this.lon = lon;
	}

	public String getLon(){
		return lon;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPlacename(String placename){
		this.placename = placename;
	}

	public String getPlacename(){
		return placename;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"phone = '" + phone + '\'' + 
			",lon = '" + lon + '\'' + 
			",location = '" + location + '\'' + 
			",id = '" + id + '\'' + 
			",placename = '" + placename + '\'' + 
			",gambar = '" + gambar + '\'' + 
			",lat = '" + lat + '\'' + 
			"}";
		}
}