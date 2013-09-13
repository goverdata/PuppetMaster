package com.github.puppet.filter;

public class UserSessionUtil {
	private static ThreadLocal<User> localUser = new ThreadLocal<User>();
	//set the user by servlet filter
	public static void setUser(User user){
		localUser.set(user);
	}

	public static User getUser(){
		return localUser.get();
	}
}
