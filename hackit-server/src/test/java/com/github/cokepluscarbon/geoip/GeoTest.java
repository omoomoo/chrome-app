package com.github.cokepluscarbon.geoip;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class GeoTest {
	@Test
	public void t1() throws IOException {
		LinkedBlockingQueue<Location> locationQueue = new LinkedBlockingQueue<Location>(50);

		new Thread(new Runnable() {
			@Override
			public void run() {
				ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

				for (int i = 0; i < 256; i++) {
					for (int j = 0; j < 256; j++) {
						IPResolver task = new IPResolver(locationQueue);
						task.setIp("221.4." + i + "." + j);
						task.setEntityId("132" + i);

						fixedThreadPool.execute(task);
						try {
							TimeUnit.MILLISECONDS.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

		File file = new File("C://geoip.csv");
		if (!file.exists()) {
			if (file.createNewFile()) {
				System.out.println("创建文件C://geoip.csv");
			} else {
				System.out.println("创建文件失败，系统退出！");
				System.exit(1);
			}

		}

		FileWriter fw = new FileWriter(file);

		int count = 0;
		long start = new Date().getTime();
		while (true) {
			try {
				Location location = locationQueue.take();

				String line = String.format("%s\t,%d\t,%s\t,%s\t,%s\t,%s\t,%s\t,%d\t\n", (new Date().getTime() - start)
						/ 60000 + "分钟", count++, location.getId(), location.getIp(), location.getCountry(),
						location.getCity(), location.getIsp(), locationQueue.size());

				fw.append(line);
				fw.flush();
				System.out.print(line);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
