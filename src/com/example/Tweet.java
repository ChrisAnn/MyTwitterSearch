package com.example;

public class Tweet {
	private String _text;
	private String _profileImageUrl;
	private String _fromUser;
	
	
	public String getText() {
		return _text;
	}
	
	public void setText(String _text) {
		this._text = _text;
	}
	
	public String getProfileImageUrl() {
		return _profileImageUrl;
	}
	
	public void setProfileImageUrl(String _profileImageUrl) {
		this._profileImageUrl = _profileImageUrl;
	}
	
	public String getFromUser() {
		return _fromUser;
	}
	
	public void setFromUser(String _fromUser) {
		this._fromUser = _fromUser;
	}
}
