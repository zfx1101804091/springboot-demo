package com.example.springbootshiro.utils;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class CommonUtils {
	private static Logger logger1 = Logger.getLogger(CommonUtils.class);
	/** 锁对象 */
	private static final Object lockObj = new Object();
	private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
	public static DecimalFormat df = new DecimalFormat("0.0");

	private static ThreadPoolExecutor pool = null;
	private static ThreadPoolExecutor tcppool = null;
	private static ScheduledExecutorService executorSchedule=null;
	private static ThreadPoolExecutor tcpServerpool = null;
	//private static ThreadPoolExecutor udpServerpool = null;
	public static synchronized ThreadPoolExecutor getPool(){
		if (pool == null) {
			synchronized (CommonUtils.class) {
				if (pool == null) {
					pool = new ThreadPoolExecutor(35,65, 60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(800));
				}
			}
		}
		//logger1.debug("从getPool线程池获取线程pool_15="+pool);
		return pool;
	}

	

	public static synchronized ThreadPoolExecutor getTCPServerPool(){
		if (tcpServerpool == null) {
			synchronized (CommonUtils.class) {
				if (tcpServerpool == null) {
					tcpServerpool = new ThreadPoolExecutor(80,150, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(800));
				}
			}
		}
		logger1.debug("从getTCPServerPool线程池获取线程tcppool_80="+tcpServerpool);
		return tcpServerpool;
	}

	/*public static synchronized ThreadPoolExecutor getUDPServerPool(){
		if (udpServerpool == null) {
            synchronized (CommonUtils.class) {
                if (udpServerpool == null) {
                	udpServerpool = new ThreadPoolExecutor(130,130, 180, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
                }
            }
        }
		//logger1.error("从getUDPServerPool线程池获取线程udppool_130="+udpServerpool);
		return udpServerpool;
	}*/
	public static synchronized ScheduledExecutorService getExcutorSchedule() {
		if (executorSchedule == null) {
			synchronized (CommonUtils.class) {
				if (executorSchedule == null) {
					executorSchedule = Executors.newScheduledThreadPool(80);
				}
			}
		}
		logger1.debug("getExcutorSchedule线程池获取线程executorSchedule50="+executorSchedule);
		return executorSchedule;
	}

	private static SimpleDateFormat getSdf(final String pattern) {
		ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

		// 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
		if (tl == null) {
			synchronized (lockObj) {
				tl = sdfMap.get(pattern);
				if (tl == null) {
					// 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
					logger1.debug(Thread.currentThread().getName() +"put new sdf of pattern " + pattern + " to map");

					// 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
					tl = new ThreadLocal<SimpleDateFormat>() {

						@Override
						protected SimpleDateFormat initialValue() {
							logger1.debug("thread: " + Thread.currentThread() + " init pattern: " + pattern);
							return new SimpleDateFormat(pattern);
						}
					};
					sdfMap.put(pattern, tl);
				}
			}
		}

		return tl.get();
	}

	/**
	 * 是用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return getSdf(pattern).format(date);
	}

	public static Date parse(String dateStr, String pattern) throws ParseException {
		return getSdf(pattern).parse(dateStr);
	}


}
