package com.tryatach.test.concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

/**
 * @author guangzhong.wgz
 *
 */
public class CasConcurrency {

	private final AtomicInteger count = new AtomicInteger(5);
	private volatile int total = 0;
	private CountDownLatch latch = new CountDownLatch(3);

	@Test
	public void test() throws InterruptedException {
		new CompareThread().start();
		new CompareThread().start();
		new CompareThread().start();
		latch.await();
		Thread.sleep(1000);
		System.out.println("count : " + count.get());
	}

	public class CompareThread extends Thread {
		public void run() {
			for (int i = 0; i < 10; i++) {
				total += 1;
				try {
					sleep(100);
					// int expect = count.get();
					if (count.compareAndSet(total, total)) {
						// 线程安全的
						System.out.println("========= : " + total + "----" + total);
						break;
						//
					} else {
						System.out.println("==========error===============" + total);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			latch.countDown();
		}
	}
}
