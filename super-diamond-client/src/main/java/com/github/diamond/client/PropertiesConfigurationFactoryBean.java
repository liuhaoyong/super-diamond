/**        
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.    
 */    
package com.github.diamond.client;

import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

import com.github.diamond.client.event.ConfigurationListener;

/**
 * Create on @2013-8-26 @上午9:29:52 
 * @author bsli@ustcinfo.com
 */
public class PropertiesConfigurationFactoryBean implements FactoryBean<PropertiesConfiguration> {
	private PropertiesConfiguration __configuration;
	
	private volatile boolean init = false;
	
	public PropertiesConfigurationFactoryBean(final String projCode, final String profile) {
		this(projCode, profile, null);
	}
	
	public PropertiesConfigurationFactoryBean(final String projCode, final String profile, List<ConfigurationListener> listeners) {
		__configuration = new PropertiesConfiguration(projCode, profile);
		
		if(listeners != null) {
			for(ConfigurationListener listener : listeners) {
				__configuration.addConfigurationListener(listener);
			}
		}
		init = true;
	}
	
	public PropertiesConfigurationFactoryBean(String host, int port, final String projCode, final String profile) {
		this(host, port, projCode, profile, null);
	}
	
	public PropertiesConfigurationFactoryBean(String host, int port, final String projCode, final String profile, List<ConfigurationListener> listeners) {
		__configuration = new PropertiesConfiguration(host, port, projCode, profile);
		
		if(listeners != null) {
			for(ConfigurationListener listener : listeners) {
				__configuration.addConfigurationListener(listener);
			}
		}
		init = true;
	}
	
	public PropertiesConfiguration getConfiguration() {
		if(init) {
			return __configuration;
		}
		return null;
	}
	
	@Override
	public PropertiesConfiguration getObject() throws Exception {
		Assert.notNull(__configuration);
		return __configuration;
	}

	@Override
	public Class<?> getObjectType() {
		return PropertiesConfiguration.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
