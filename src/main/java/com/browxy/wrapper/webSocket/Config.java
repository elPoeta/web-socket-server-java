package com.browxy.wrapper.webSocket;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Config {
	private static final Logger logger = LoggerFactory.getLogger(Config.class);
	
	private static Config instance = null;
	private static final Object lock = new Object();

	private Map<String, String> configValues;

	private Config() {
		Properties properties = getProperties();
		configValues = new HashMap<>();

		configValues.put("socket.keystorePath", properties.getProperty("socket.keystorePath"));
		configValues.put("socket.keystorePassword", properties.getProperty("socket.keystorePassword"));	
	}

	public static Config getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new Config();
				}
			}
		}
		return instance;
	}

	private static Properties getProperties() {
		String resource = "resource.socket.properties";
		Properties properties = new Properties();

		try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(resource)) {
			if (inputStream != null) {
				properties.load(inputStream);

			} else {
				logger.error("Properties file not found!");
			}
		} catch (IOException e) {
			logger.error("error reading properties file", e);
		}
		return properties;
	}

	public String get(String key) {
		return configValues.get(key);
	}

	public void set(String key, String value) {
		configValues.put(key, value);
	}

	public String getKeystorePath() {
		return configValues.get("socket.keystorePath");
	}

	public void setKeystorePath(String keystorePath) {
		configValues.put("socket.keystorePath", keystorePath);
	}

	public String getKeystorePassword() {
		return configValues.get("socket.keystorePassword");
	}

	public void setKeystorePassword(String keystorePassword) {
		configValues.put("socket.keystorePassword", keystorePassword);
	}

	
}


