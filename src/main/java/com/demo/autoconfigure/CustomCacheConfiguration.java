package com.demo.autoconfigure;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
@RefreshScope
//@AutoConfigureBefore(CacheAutoConfiguration.class)
//@EnableAutoConfiguration(exclude={CacheAutoConfiguration.class})
public class CustomCacheConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomCacheConfiguration.class);
	
	/*Flag to Determine if Cache should be Enabled or not for the service*/
	@Value("${cacheEnabled}")
	private boolean cacheEnabled;

	/*Name of the cache to be created*/
	@Value("${caches}")
	private String caches;

	@Value("${clientMode}")
	private boolean clientMode;

	
	@Bean
	@Primary
	@RefreshScope
	CacheManager cacheManager() {
		if (cacheEnabled) {
			LOGGER.info("Cache is ENABLED...");
			LOGGER.info("Injecting SpringCacheManager for Apache Ignite...");

			//For Demo a SimpleCacheManager using ConcurrentMapCache has been plugged.
			//SimpleCacheManager cacheManager = new SimpleCacheManager();
			//cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache(caches)));
			//We can plugin any other caching Manager like SpringCacheManager when using like Apache Ignite 
			
			SpringCacheManager cacheManager = new SpringCacheManager();
			cacheManager.setConfiguration(igniteConfiguration());
			return cacheManager;

		} else {
			LOGGER.info("Cache is DISABLED...");
			LOGGER.info("Injecting NoOpCacheManager...");
			CacheManager cacheManager = new NoOpCacheManager();
			return cacheManager;
		}
	}
	
	
	@Bean
	IgniteConfiguration igniteConfiguration() {
		LOGGER.info("Entering");
		IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
		LOGGER.info("Setting Ignite ClientMode as: " + isClientMode());
		igniteConfiguration.setClientMode(isClientMode());
		igniteConfiguration.setCacheConfiguration(createCacheConfiguration(caches));
		LOGGER.info("Leaving");
		return igniteConfiguration;
	}

	public boolean isClientMode() {
		return clientMode;
	}

	@Bean
	CacheConfiguration<String,Object> createCacheConfiguration(String cacheName) {
		LOGGER.info("Entering");
		LOGGER.info("Creating Cache Configuration for cache: " + cacheName);
		CacheConfiguration<String,Object> cacheConfiguration = new CacheConfiguration<String,Object>(cacheName);
		cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
		LOGGER.info("Leaving");
		return cacheConfiguration;
	}

	public void setClientMode(boolean clientMode) {
		this.clientMode = clientMode;
	}

	
}