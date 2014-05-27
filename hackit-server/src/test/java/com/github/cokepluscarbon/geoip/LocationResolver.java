package com.github.cokepluscarbon.geoip;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

public class LocationResolver {

	public static LocationResp resolveLocationResp(String ip) throws IOException {
		URL url = new URL(String.format("http://ip.taobao.com/service/getIpInfo.php?ip=%s",
				URLEncoder.encode(ip, "UTF-8")));

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode() == 200) {
			conn.getInputStream();
			LocationResp locationResp = new Gson().fromJson(new InputStreamReader(conn.getInputStream()),
					LocationResp.class);

			return locationResp;
		}

		return null;
	}
}
