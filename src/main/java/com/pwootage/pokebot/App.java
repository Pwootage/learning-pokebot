package com.pwootage.pokebot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwootage.pokebot.networking.WebsocketServer;
import com.pwootage.pokebot.video.GameFrame;

public class App {
	private static final Logger		logger	= LoggerFactory.getLogger(App.class);
	private static PokebotUI		ui;
	private static Pokebot			bot;
	private static WebsocketServer	server;
	
	public static void main(String[] args) throws Exception {
		logger.info("Starting Pokebot...");
		ui = new PokebotUI();
		bot = new Pokebot(ui);
		logger.info("Starting websocket server on port 8080...");
		server = new WebsocketServer(8080);
		logger.info("Pokebot is started!");
		ui.setVisible(true);
		ui.mainRenderLoop();
	}
	
	public static void processFrame(byte[] data, int offset, int len) {
		GameFrame gf = new GameFrame(data, offset, len);
		bot.processFrame(gf);
	}
}
