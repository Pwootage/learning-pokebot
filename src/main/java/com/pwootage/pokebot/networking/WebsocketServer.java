package com.pwootage.pokebot.networking;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebsocketServer {
	private static final Logger	logger	= LoggerFactory.getLogger(WebsocketServer.class);
	private int					port	= 44540;
	private Server				server;
	private ServletHandler		servletHandler;
	
	public WebsocketServer(int port) throws Exception {
		this.port = port;
		server = new Server(port);
		servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(DataServlet.class, "/data");
		server.setHandler(servletHandler);
		server.start();
	}
	
	public void stop() throws Exception {
		server.stop();
	}
	
	@Override
	protected void finalize() throws Throwable {
		stop();
	}
	
	public void join() throws InterruptedException {
		server.join();
	}
}
