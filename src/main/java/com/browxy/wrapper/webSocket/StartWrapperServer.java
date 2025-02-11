package com.browxy.wrapper.webSocket;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.browxy.wrapper.webSocket.config.Config;

public class StartWrapperServer {
	private static final Logger logger = LoggerFactory.getLogger(StartWrapperServer.class);
		
	public static void main(String[] args) {
		Config config = Config.getInstance();
		if (config == null) {
			throw new RuntimeException("Socket config not loaded...");
		}
		int PORT = config.getSocketPort();
		WebSocketServerWrapper server = new WebSocketServerWrapper(new InetSocketAddress(PORT));
		server.start();
		logger.info("WebSocket server started at ws://localhost:" + PORT);
	}

}



