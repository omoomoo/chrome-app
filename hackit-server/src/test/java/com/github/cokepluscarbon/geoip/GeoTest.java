package com.github.cokepluscarbon.geoip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

public class GeoTest {
	@Test
	public void t1() throws InterruptedException {
		LinkedBlockingQueue<Location> locationQueue = new LinkedBlockingQueue<Location>(10);

		new Thread(new Runnable() {
			@Override
			public void run() {
				ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

				while (true) {
					for (int i = 0; i < 200; i++) {
						IPResolver task = new IPResolver(locationQueue);
						task.setIp("221." + i + ".1.213");
						task.setEntityId("132" + i);

						fixedThreadPool.execute(task);
					}
				}
			}
		}).start();

		int count = 0;
		while (true) {
			Location location = locationQueue.take();

			System.out.println(String.format("%15d%15s%15s%15s%15s%15s", count++, location.getId(), location.getIp(),
					location.getCountry(), location.getCity(), location.getIsp()));
		}
	}
}
