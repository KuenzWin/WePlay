package com.kuenzWin.weplay.manager;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理者
 * 
 * @author KuenzWin
 * @date 2015-9-4
 */
public class ThreadManager {

	private ThreadManager() {

	}

	private static ThreadManager instance = new ThreadManager();
	private ThreadPoolProxy longThreadPool;
	private ThreadPoolProxy shortThreadPool;

	public static ThreadManager getInstance() {
		return instance;
	}

	// 多个线程池：耗时的联网操作，读取文件
	// 开多少个线程：CPU数*2+1
	/**
	 * 获取一个耗时操作的线程池
	 * 
	 * @return
	 */
	public synchronized ThreadPoolProxy getLongThreadPool() {
		if (longThreadPool == null)
			longThreadPool = new ThreadPoolProxy(5, 5, 5 * 1000L);
		return longThreadPool;
	}

	/**
	 * 获取一个短操作的线程池
	 * 
	 * @return
	 */
	public synchronized ThreadPoolProxy getShortThreadPool() {
		if (shortThreadPool == null)
			shortThreadPool = new ThreadPoolProxy(3, 3, 5 * 1000L);
		return shortThreadPool;
	}

	public class ThreadPoolProxy {
		private ThreadPoolExecutor pool;
		/**
		 * 线程创建时有多少个线程
		 */
		private int corePoolSize;
		private int maximumPoolSize;
		private long time;

		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.time = time;
		}

		/**
		 * 执行任务
		 * 
		 * @param runnable
		 *            任务
		 */
		public void executor(Runnable runnable) {
			if (pool == null) {
				// 创建线程池
				// 1. 线程池里面管理多少个线程
				// 2. 如果排队满了, 额外的开的线程数
				// 3. 如果线程池没有要执行的任务,存活多久
				// 4. 时间的单位
				// 5. 如果线程池里管理的线程都已经用了,剩下的任务 临时存到LinkedBlockingQueue对象中 排队
				pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
						time, TimeUnit.MILLISECONDS,
						new LinkedBlockingQueue<Runnable>(10));
			}
			pool.execute(runnable);
		}

		/**
		 * 取消任务
		 * 
		 * @param runnable
		 *            任务
		 */
		public void cancel(Runnable runnable) {
			if (pool != null && pool.isShutdown() && pool.isTerminated()) {
				pool.remove(runnable);
			}
		}

	}

}
