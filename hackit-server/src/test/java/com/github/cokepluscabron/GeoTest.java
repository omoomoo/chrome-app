package com.github.cokepluscabron;

import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

public class GeoTest {
	@Test
	public void t1() {
		LinkedBlockingQueue<Location> locationQueue = new LinkedBlockingQueue<Location>(10);

		IPResolver task = new IPResolver(locationQueue);
		task.setIp("221.4.213.94");

		Thread thread = new Thread(new IPResolver(locationQueue));

		thread.start();
	}
}
