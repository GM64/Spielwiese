package at.gernot.meins.config;


import java.net.URL;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.gernot.meins.DataService;

import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManagerBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;
import static org.ehcache.config.units.MemoryUnit.MB;

@Configuration
public class Config {
	public static final String EHCACHE_CONFIG = "/ehcache-config.xml";
	
	 @Value("${gernot.api.path}")
	 String contextPath;
	 @Bean
	 public DataService dataService() {
		 DataService dataService=new DataService();
		 return dataService;
	 }

}
