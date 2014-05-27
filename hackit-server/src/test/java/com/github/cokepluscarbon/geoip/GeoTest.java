package com.github.cokepluscarbon.geoip;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class GeoTest {
	@Test
	public void t1() throws InterruptedException {
		LinkedBlockingQueue<Location> locationQueue = new LinkedBlockingQueue<Location>(10);

		for (int i = 0; i < 255; i++) {
			IPResolver task = new IPResolver(locationQueue);
			task.setIp("221.4.213." + i);
			task.setEntityId("132" + i);

			Thread thread = new Thread(task);
			thread.start();
		}

		int count = 0;
		while (true) {
			Location location = locationQueue.take();

			System.out.println(String.format("%10d%10s%20s%10s%10s%10s", count++, location.getId(), location.getIp(),
					location.getCountry(), location.getCity(), location.getIsp()));
		}
	}
}
