package com.pwootage.pokebot.networking;

import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dataservlet", urlPatterns = { "/data" })
public class DataServlet extends WebSocketServlet {
	private static final Logger	logger		= LoggerFactory.getLogger(DataServlet.class);
	/**
	 * Length of a frame buffer + some overhead for safety
	 */
	private static final int	BUFF_LEN	= 92160 + 1024;
	
	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.getPolicy().setIdleTimeout(10000);
		factory.getPolicy().setMaxBinaryMessageBufferSize(BUFF_LEN);
		factory.getPolicy().setMaxBinaryMessageSize(BUFF_LEN);
		factory.getPolicy().setMaxTextMessageBufferSize(BUFF_LEN);
		factory.getPolicy().setMaxTextMessageSize(BUFF_LEN);
		factory.getPolicy().setInputBufferSize(BUFF_LEN);
		factory.register(WebSocketDataHandler.class);
	}
}
