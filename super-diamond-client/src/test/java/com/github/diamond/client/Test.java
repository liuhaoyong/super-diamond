/**
 * Copyright (c) 2013 by 苏州科大国创信息技术有限公司.
 *
 * Test.java Create on 2013-7-11 下午4:48:04
 */
package com.github.diamond.client;
import com.github.diamond.client.event.ConfigurationEvent;
import com.github.diamond.client.event.ConfigurationListener;

/**
 *
 * @author <a href="mailto:bsli@ustcinfo.com">li.binsong</a>
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws javax.naming.ConfigurationException
	 */
	public static void main(String[] args) throws Exception {
		PropertiesConfiguration config = new PropertiesConfiguration("172.16.20.234", 8283, "payment-engine", "development");
		config.addConfigurationListener(new ConfigurationListener() {
			@Override
			public void configurationChanged(ConfigurationEvent event) {
				System.out.println("=====configurationChanged=======: " + event.getPropertyName());
				System.out.println(event.getPropertyValue());
				if ("coeus.baseExcpReload".equals(event.getPropertyName())) {
					System.out.println("************:" + event.getPropertyValue());
					
				}
			}
		});
		//config.addConfigurationListener(new ConfigurationListenerTest());
		System.out.println(config.getMapByModule("event.subscriber.config"));
		//System.out.println(config.getString("event.2"));
		
		//System.in.read();
//		while (System.in.read() != 'X') {
//			Thread.sleep(1000);
//		}
	}

}
