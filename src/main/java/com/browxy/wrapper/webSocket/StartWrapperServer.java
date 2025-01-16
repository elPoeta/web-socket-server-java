package com.browxy.wrapper.webSocket;

import java.net.InetSocketAddress;


public class StartWrapperServer {
	
	public static void main(String[] args) {
        WebSocketServerWrapper server = new WebSocketServerWrapper(new InetSocketAddress(9191));
        server.start();
        System.out.println("WebSocket server started at ws://localhost:9191");
    }
}
