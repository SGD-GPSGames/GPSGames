package com.thinkijustwon.nosockrocks.user;

public class User {

	private String name;
	
	public User(String name)
	{
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public boolean equals(Object a){
		if (a instanceof User){
			User u = (User) a;
			return this.name.equals(u.getName());
		}
		return false;
	}
}
