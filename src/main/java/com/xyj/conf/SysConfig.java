package com.xyj.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SysConfig {
	private static Logger logger = LoggerFactory.getLogger(SysConfig.class);
	
	private static Map<String, String> config = new ConcurrentHashMap<String, String>();

	public static void setConfig(String key, String value) {
		if (config == null) {
			config = new ConcurrentHashMap<String, String>();
		}
		config.put(key, value);
	}

	public static String getConfig(String key) {
		logger.debug("key ===> " + ((config != null && config.containsKey(key)) ? config.get(key) : "is null"));
		return (config != null && config.containsKey(key)) ? config.get(key) : "";
	}

}
