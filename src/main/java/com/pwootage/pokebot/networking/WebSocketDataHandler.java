package com.pwootage.pokebot.networking;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwootage.pokebot.App;

public class WebSocketDataHandler implements WebSocketListener {
	private static final Logger	logger	= LoggerFactory.getLogger(WebSocketDataHandler.class);
	private Session				session;
	
	@Override
	public void onWebSocketText(String message) {
		logger.debug("Recieved text frame (len: {}, data: {})", message.length(), message);
		if (session.isOpen()) {
		}
	}
	
	@Override
	public void onWebSocketBinary(byte[] data, int offset, int len) {
		// logger.debug("Recieved binary frame (offset: {}, len: {})", offset, len);
		App.processFrame(data, offset, len);
		if (session.isOpen()) {
		}
	}
	
	@Override
	public void onWebSocketClose(int statusCode, String reason) {
	}
	
	@Override
	public void onWebSocketConnect(Session session) {
		this.session = session;
	}
	
	@Override
	public void onWebSocketError(Throwable cause) {
	}
	
}
