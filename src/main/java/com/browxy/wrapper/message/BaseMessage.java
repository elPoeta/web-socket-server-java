package com.browxy.wrapper.message;

public class BaseMessage {
	private String method;
	private String arguments;
	private String userCodePath;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getArguments() {
		return arguments;
	}
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}
	public String getUserCodePath() {
		return userCodePath;
	}
	public void setUserCodePath(String userCodePath) {
		this.userCodePath = userCodePath;
	}
	
	
}
