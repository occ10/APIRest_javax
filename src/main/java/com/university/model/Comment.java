package com.university.model;

public class Comment {
	
	private String userComment;
	private String userCommented;
	private String comment;
	private UserDTO user;
	
	public Comment() {
		super();
	}
	
	public Comment(String userComment, String userCommented, String comment, UserDTO user) {
		super();
		this.userComment = userComment;
		this.userCommented = userCommented;
		this.comment = comment;
		this.user = user;
	}
	
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}


	public String getUserComment() {
		return userComment;
	}
	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}
	public String getUserCommented() {
		return userCommented;
	}
	public void setUserCommented(String userCommented) {
		this.userCommented = userCommented;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
