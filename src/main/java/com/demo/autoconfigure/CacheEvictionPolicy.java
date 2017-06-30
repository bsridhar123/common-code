package com.demo.autoconfigure;


public class CacheEvictionPolicy {

	String type;
	String maxSize;
	public String getMaxSize() {
		return maxSize;
	}
	public String getType() {
		return type;
	}
	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "CacheEvictionPolicy [type=" + type + ", maxSize=" + maxSize + "]";
	}
	
}