package com.thinkijustwon.nosockrocks.game;

import com.thinkijustwon.nosockrocks.user.UserThread;

public class ChatGame extends Game {

	public ChatGame(UserThread creator, int gameID) {
		super(creator, gameID);
	}

	@Override
	public void processMessage(GameMessage g) {
		String text = g.getUser().getName()+","+g.getMessage();
		sendToAllButHost(text);
	}

	@Override
	public void addPlayer(String name) {
		// TODO Auto-generated method stub
		
	}
}
