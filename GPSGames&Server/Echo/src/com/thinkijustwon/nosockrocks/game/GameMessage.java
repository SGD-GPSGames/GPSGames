package com.thinkijustwon.nosockrocks.game;

import com.thinkijustwon.nosockrocks.user.User;

public class GameMessage {

	private User user;
	private String message;
	public GameMessage(User user, String message){
		this.user = user;
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public User getUser() {
		return user;
	}
}
