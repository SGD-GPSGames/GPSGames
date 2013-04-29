package com.thinkijustwon.nosockrocks;

import com.thinkijustwon.nosockrocks.user.User;

public final class Authenticator {

	public static User Authenticate(String login){
		String[] parts = login.split(",");
		if (parts[0].equals("kat") && parts[1].equals("pass")){
			return new User(parts[0]);
		}
		if (parts[0].equals("zack") && parts[1].equals("pass")){
			return new User(parts[0]);
		}
		if (parts[0].equals("hunter") && parts[1].equals("pass")){
			return new User(parts[0]);
		}
		if (parts[0].equals("jack") && parts[1].equals("pass")){
			return new User(parts[0]);
		}
		
		//end all case
		return null;
	}
}
