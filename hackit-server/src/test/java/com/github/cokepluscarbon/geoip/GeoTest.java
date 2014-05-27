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
				ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

				for (int i = 10; i < 50; i++) {
					IPResolver task = new IPResolver(locationQueue);
					task.setIp("221.4." + i +".213");
					task.setEntityId("132" + i);

					fixedThreadPool.execute(task);
				}

				fixedThreadPool.shutdown();
			}
		}).start();

		int count = 0;
		while (true) {
			Location location = locationQueue.take();

			System.out.println(String.format("%10d%10s%20s%10s%10s%10s", count++, location.getId(), location.getIp(),
					location.getCountry(), location.getCity(), location.getIsp()));
		}
	}
}
