package com.demo.autoconfigure;



public class CacheBean {

	String name;
	String cacheMode;

	CacheExpiryPolicy expiryPolicy;
	CacheEvictionPolicy evictionPolicy;

	boolean offheap;

	public String getCacheMode() {
		return cacheMode;
	}

	public CacheEvictionPolicy getEvictionPolicy() {
		return evictionPolicy;
	}

	public CacheExpiryPolicy getExpiryPolicy() {
		return expiryPolicy;
	}

	public String getName() {
		return name;
	}

	public boolean isOffheap() {
		return offheap;
	}

	public void setCacheMode(String cacheMode) {
		this.cacheMode = cacheMode;
	}

	public void setEvictionPolicy(CacheEvictionPolicy evictionPolicy) {
		this.evictionPolicy = evictionPolicy;
	}

	public void setExpiryPolicy(CacheExpiryPolicy expiryPolicy) {
		this.expiryPolicy = expiryPolicy;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOffheap(boolean offheap) {
		this.offheap = offheap;
	}

	@Override
	public String toString() {
		return "CacheBean [name=" + name + ", cacheMode=" + cacheMode + ", expiryPolicy=" + expiryPolicy
				+ ", evictionPolicy=" + evictionPolicy + ", offheap=" + offheap + "]";
	}

}