package com.github.cokepluscabron;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class IPResolver implements Runnable {
	private LinkedBlockingQueue<Location> locationQueue = null;
	private String entityId;
	private String ip;

	@Override
	public void run() {
		LocationResp locationResp = null;
		try {
			locationResp = LocationResolver.resolveLocationResp(ip);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		if (locationResp != null && locationResp.getCode() == 0) {
			Location location = locationResp.getLocation();
			location.setId(entityId);

			try {
				locationQueue.put(location);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public IPResolver(LinkedBlockingQueue<Location> locationQueue) {
		this.locationQueue = locationQueue;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
