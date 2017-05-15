/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.github.diamond.client;

import java.io.IOException;

import junit.framework.Assert;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.diamond.client.event.ConfigurationEvent;
import com.github.diamond.client.event.ConfigurationListener;


/**
 * Create on @2013-8-26 @上午10:00:54 
 * @author bsli@ustcinfo.com
 */
public class PropertiesConfigurationFactoryBeanTest {
	
	public static void main(String[] args) throws IOException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
		
		PropertiesConfiguration configuration = (PropertiesConfiguration)applicationContext.getBean("propertiesConfiguration");
		
		PropertiesConfiguration propertiesConfiguration2 = (PropertiesConfiguration)applicationContext.getBean("propertiesConfiguration2");
		
		Assert.assertSame(configuration, propertiesConfiguration2);
		
		
		PropertiesConfiguration configuration2 = (PropertiesConfiguration)applicationContext.getBean("propertiesConfiguration");
		PropertiesConfiguration propertiesConfiguration22 = (PropertiesConfiguration)applicationContext.getBean("propertiesConfiguration2");
		
		Assert.assertNotSame(configuration2, propertiesConfiguration22);
		
		Assert.assertSame(configuration, configuration2);
		Assert.assertSame(propertiesConfiguration2, propertiesConfiguration22);
		
		configuration.addConfigurationListener(new ConfigurationListener() {
	        @Override
	        public void configurationChanged(ConfigurationEvent event) {
	                            //监听配置参数变化,监听不到?
	            System.out.println(event.getType().name() + " " + event.getPropertyName() + " " + event.getPropertyValue());
	        }
	    });
		
		System.in.read();
	}

}
