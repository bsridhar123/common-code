package com.demo.autoconfigure;

import java.util.Arrays;
import java.util.List;

import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
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
@AutoConfigureBefore(CacheAutoConfiguration.class)
public class CustomCacheConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomCacheConfig.class);
	
	/*Flag to Determine if Cache should be Enabled or not for the service*/
	@Value("${cacheEnabled}")
	private boolean cacheEnabled;

	/*Name of the cache to be created*/
	@Value("${caches}")
	private String caches;

	/*Flag to Determine if Apache Ignite Should be started in clientMode or not*/
	@Value("${clientMode}")
	private boolean clientMode;

	
	@Bean
	@Primary
	@RefreshScope
	CacheManager cacheManager() {
		if (cacheEnabled) {
			LOGGER.info("Cache is ENABLED...");
			LOGGER.info("Injecting SpringCacheManager for Apache Ignite...");

			//Stop Ignite Instance if already running
			Ignition.stop(true);
			
			SpringCacheManager cacheManager = new SpringCacheManager();
			
			
			LOGGER.info("Creating IgniteConfiguration instance...");
			IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
			
			LOGGER.info("Ignite Client Mode...:" + clientMode);
			igniteConfiguration.setClientMode(clientMode);
			
			
			List<String> cacheNameList = Arrays.asList(caches.split(","));
			CacheConfiguration[] cacheConfigList=new CacheConfiguration[25];
			
			
			for(int i=0;i<cacheNameList.size();i++){
				cacheConfigList[i]=new CacheConfiguration();//
				cacheConfigList[i]=getConfigurationForCache(cacheNameList.get(i));
			}
			
			/*
			LOGGER.info("Creating cacheConfiguration instance for quotes...");
			CacheConfiguration quoteCacheConfiguration = new CacheConfiguration();
			quoteCacheConfiguration.setName("AllProducts");
			quoteCacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
			quoteCacheConfiguration.setBackups(1);
			
			LOGGER.info("Creating cacheConfiguration instance for books...");
			CacheConfiguration bookCacheConfiguration = new CacheConfiguration();
			bookCacheConfiguration.setName("ProductsByCriteria");
			bookCacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
			bookCacheConfiguration.setBackups(1);*/
			
			LOGGER.info("Setting cacheConfiguration instance to igniteConfiguration...");
			igniteConfiguration.setCacheConfiguration(cacheConfigList);//quoteCacheConfiguration,bookCacheConfiguration);
			LOGGER.info("Leaving");
			
			cacheManager.setConfiguration(igniteConfiguration);
			return cacheManager;
		} else {
			
			//Stop Ignite Instance if already running
			Ignition.stop(true);
			
			LOGGER.info("Cache is DISABLED...");
			LOGGER.info("Injecting NoOpCacheManager...");
			CacheManager cacheManager = new NoOpCacheManager();
			LOGGER.info("Leaving");
			return cacheManager;
		}
	}


	public CacheConfiguration getConfigurationForCache(String cacheName){
		CacheConfiguration cacheConfigurationInstance = new CacheConfiguration();
		cacheConfigurationInstance.setName(cacheName);
		cacheConfigurationInstance.setCacheMode(CacheMode.PARTITIONED);
		cacheConfigurationInstance.setBackups(1);
		return cacheConfigurationInstance;
	}
	
		
}
