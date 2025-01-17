package com.browxy.wrapper.webSocket;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartWrapperServer {
	private static final Logger logger = LoggerFactory.getLogger(StartWrapperServer.class);
		
	public static void main(String[] args) {
		ValidationSystemProps validationSystemProps = validateSystemProperties();
		if (!validationSystemProps.isValid()) {
			throw new RuntimeException(validationSystemProps.getMessage());
		}
		int PORT = Integer.valueOf(System.getProperty("PORT"));
		WebSocketServerWrapper server = new WebSocketServerWrapper(new InetSocketAddress(PORT));
		server.start();
		logger.info("WebSocket server started at ws://localhost:" + PORT);
	}

	private static ValidationSystemProps validateSystemProperties() {
		ValidationSystemProps validationSystemProps = new ValidationSystemProps();
		
		if (System.getProperty("PORT") == null) {
			validationSystemProps.setMessage("PORT not found.");
		}
		
		if (System.getProperty("containerBasePath") == null) {
			validationSystemProps.setMessage("Container base path not found.");
		}
		
		if (System.getProperty("language") == null) {
			validationSystemProps.setMessage("Language vm arg not found.");
		}
	
		if(validationSystemProps.getMessage().trim().isEmpty()) {
			validationSystemProps.setValid(true);
		}
		return validationSystemProps;
	}
	
	private static class ValidationSystemProps {
		private boolean valid;
		private String message;

		public ValidationSystemProps() {
			this.valid = false;
			this.message = "";
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isValid() {
			return valid;
		}

		public String getMessage() {
			return message;
		}
	}	

}



