package com.demo.autoconfigure;


import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("IGNITE_CACHE_JAVA_CONFIG")
@Configuration
/*@EnableConfigurationProperties({ CacheConfig.class })*/
@EnableCaching
public class IgniteCacheConfiguration {

	/*@Value(value = "${clientMode:false}")*/
	@Value(value = "#{systemProperties['clientMode'] ?:false}")
	boolean clientMode;

	/*@Bean
	CacheConfiguration<String, Object> booksCacheConfiguration() {
		CacheConfiguration<String, Object> cacheConfiguration = new CacheConfiguration<>("quotes");
		cacheConfiguration.setCacheMode(CacheMode.REPLICATED);

		/// Default OffHeapMemory is disabled , value is "-1"
		// Specify a value in bytes to allocate for offheapmemory, value of "0"
		/// will set unlimited offheapmemory
		//cacheConfiguration.seto

		cacheConfiguration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, 2)));
		cacheConfiguration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.FIVE_MINUTES));
		cacheConfiguration.setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(Duration.ONE_DAY));

		cacheConfiguration.setEvictionPolicy(new LruEvictionPolicy<String, Object>(1000000));

		return cacheConfiguration;
	}*/

	@Bean
	SpringCacheManager cacheManager() {
		SpringCacheManager cacheManager = new SpringCacheManager();
		cacheManager.setConfiguration(igniteConfiguration());
		return cacheManager;
	}

	@Bean
	IgniteConfiguration igniteConfiguration() {
		IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
		igniteConfiguration.setClientMode(isClientMode());
		igniteConfiguration.setCacheConfiguration(quoteCacheConfiguration());
		return igniteConfiguration;
	}

	public boolean isClientMode() {
		return clientMode;
	}

	@Bean
	CacheConfiguration<String, Object> quoteCacheConfiguration() {
		CacheConfiguration<String, Object> cacheConfiguration = new CacheConfiguration<>("quotes");
		cacheConfiguration.setCacheMode(CacheMode.PARTITIONED);
		return cacheConfiguration;
	}

	public void setClientMode(boolean clientMode) {
		this.clientMode = clientMode;
	}

}