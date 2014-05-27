package com.github.cokepluscarbon.geoip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

public class GeoTest {
	@Test
	public void t1() {
		LinkedBlockingQueue<Location> locationQueue = new LinkedBlockingQueue<Location>(50);

		new Thread(new Runnable() {
			@Override
			public void run() {
				ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

				while (true) {
					for (int i = 0; i < 256; i++) {
						IPResolver task = new IPResolver(locationQueue);
						task.setIp("221.4.213." + i);
						task.setEntityId("132" + i);

						fixedThreadPool.execute(task);
					}
				}
			}
		}).start();

		int count = 0;
		while (true) {
			try {
				Location location = locationQueue.take();

				System.out.println(String.format("%15d%15s%15s%15s%15s%15s%15d", count++, location.getId(),
						location.getIp(), location.getCountry(), location.getCity(), location.getIsp(),
						locationQueue.size()));
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
