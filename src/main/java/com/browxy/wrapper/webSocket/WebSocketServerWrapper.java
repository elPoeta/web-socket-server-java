package com.browxy.wrapper.webSocket;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;

public class WebSocketServerWrapper extends WebSocketServer {
	private Gson gson;

	public WebSocketServerWrapper(InetSocketAddress address) {
		super(address);
		this.gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		JsonObject json = new JsonObject();
		json.addProperty("remoteAddress", conn.getRemoteSocketAddress().toString());
		json.addProperty("isOpen", conn.isOpen());
		conn.send(this.gson.toJson(json));
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		System.out.println("Message from client: " + message);

		conn.send(message);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace();
		String errorMessage = ex.getMessage() != null || !ex.getMessage().trim().equals("") ? ex.getMessage() : "An error has occurred in the connection";
		JsonObject json = new JsonObject();
		json.addProperty("error", errorMessage);
		conn.send(this.gson.toJson(json));
	}

	@Override
	public void onStart() {
		System.out.println("WebSocket server started successfully");
	}
}
