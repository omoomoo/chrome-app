package com.github.cokepluscabron;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

public class GeoIPTest {
	public static void main(String[] args) throws IOException {
		LocationResp locationResp = resolveLocationResp("221.4.213.94");

		if (locationResp != null && locationResp.getCode() == 0) {
			Location location = locationResp.getLocation();
			System.out.println(location.getIsp());
		}
	}

	public static LocationResp resolveLocationResp(String ip) throws IOException {
		URL url = new URL(String.format("http://ip.taobao.com/service/getIpInfo.php?%s=%s",
				URLEncoder.encode(ip, "UTF-8"), URLEncoder.encode(ip, "UTF-8")));

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
