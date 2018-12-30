package com.revature.project_0.models;

public class User {
	private Integer userId;
	private String userName;
	private String password;
	
	//TODO turn into Bean
	public User () {
		
	}
	
	public User (Integer userId, String userName, String password) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}
}
