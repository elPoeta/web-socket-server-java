package com.browxy.wrapper.webSocket.config;

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

		configValues.put("socket.port", properties.getProperty("socket.port"));	
		configValues.put("container.basePath", properties.getProperty("container.basePath"));
		configValues.put("container.mavenRepoPath", properties.getProperty("container.mavenRepoPath"));
		configValues.put("container.mavenSettingsPath", properties.getProperty("container.mavenSettingsPath"));
		configValues.put("BXY_SOCKET_KEYSTORE_PATH", System.getenv("BXY_SOCKET_KEYSTORE_PATH") != null ? System.getenv("BXY_SOCKET_KEYSTORE_PATH") : "");
		configValues.put("BXY_SOCKET_KEYSTORE_PASSWORD", System.getenv("BXY_SOCKET_KEYSTORE_PASSWORD") != null ? System.getenv("BXY_SOCKET_KEYSTORE_PASSWORD") : "");	
		configValues.put("BXY_SOCKET_IS_SECURE",
				System.getenv("BXY_SOCKET_IS_SECURE") != null ? System.getenv("BXY_SOCKET_IS_SECURE") : "false");
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

	public int getSocketPort() {
		return Integer.valueOf(configValues.get("socket.port"));
	}

	public void setSocketPort(int port) {
		configValues.put("socket.port", String.valueOf(port));
	}
	
	public String getContainerBasePath() {
		return configValues.get("container.basePath");
	}

	public void setContainerBasePath(String containerBasePath) {
		configValues.put("container.basePath", containerBasePath);
	}
	
	public String getContainerMavenRepoPath() {
		return configValues.get("container.mavenRepoPath");
	}

	public void setContainerMavenRepoPath(String containerMavenRepoPath) {
		configValues.put("container.mavenRepoPath", containerMavenRepoPath);
	}
	
	public String getContainerMavenSettingsPath() {
		return configValues.get("container.mavenSettingsPath");
	}

	public void setContainerMavenSettingsPath(String containerMavenSettingsPath) {
		configValues.put("container.mavenSettingsPath", containerMavenSettingsPath);
	}
	
	public String getKeystorePath() {
		return configValues.get("BXY_SOCKET_KEYSTORE_PATH");
	}

	public void setKeystorePath(String keystorePath) {
		configValues.put("BXY_SOCKET_KEYSTORE_PATH", keystorePath);
	}

	public String getKeystorePassword() {
		return configValues.get("BXY_SOCKET_KEYSTORE_PASSWORD");
	}

	public void setKeystorePassword(String keystorePassword) {
		configValues.put("BXY_SOCKET_KEYSTORE_PASSWORD", keystorePassword);
	}

	public boolean isSecure() {
		return Boolean.valueOf(configValues.get("BXY_SOCKET_IS_SECURE"));
	}

	public void setIsSecure(boolean isSecure) {
		configValues.put("BXY_SOCKET_IS_SECURE", String.valueOf(isSecure));
	}
	
}


