package com.demo.autoconfigure;



import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dummy")
@Component
@ConfigurationProperties(prefix = "configuration")
public class CacheConfig {

	private List<CacheBean> caches = new ArrayList<CacheBean>();
	private boolean clientMode;
	
	public List<CacheBean> getCaches() {
		return caches;
	}
	public boolean isClientMode() {
		return clientMode;
	}
	public void setCaches(List<CacheBean> caches) {
		this.caches = caches;
	}
	public void setClientMode(boolean clientMode) {
		this.clientMode = clientMode;
	}
	
}