package com.github.cokepluscarbon.geoip;

import com.google.gson.annotations.SerializedName;

public class LocationResp {
	private int code;
	@SerializedName("data")
	private Location location;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
