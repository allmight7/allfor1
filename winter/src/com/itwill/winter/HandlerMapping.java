package com.itwill.winter;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/*
 * 설정파일(controller_mapping.properties)을 사용해서 Controller객체를
 * 생성하고 유지관리해줄객체
 */
public class HandlerMapping {
	private HashMap<String,Controller> controllerMap;
	public HandlerMapping(String controllerFilePath) {
		/*
		 * controller_mapping.properties 파일을 읽어서
		 * controllerMap객체를 생성하는작업
		 */
		System.out.println(">>> 3.HandlerMapping생성자-->controller_mapping.properties 파일을 읽어서 controllerMap객체를 생성하는작업");
		controllerMap=new HashMap<String,Controller>();
		try{
			FileInputStream fis=
					new FileInputStream(controllerFilePath);
			Properties prop=new Properties();
			prop.load(fis);
			Set commandURIKeySet =prop.keySet();
			Iterator commandURIKeyIter = 
					commandURIKeySet.iterator();
			System.out.println("***********controller_mapping.properties start**********");
			while (commandURIKeyIter.hasNext()) {
				String commandURIKey = (String) commandURIKeyIter.next();
				String controllerClassStr=
						prop.getProperty(commandURIKey);
				System.out.println(commandURIKey+"-->"+controllerClassStr);
				Class controllerClass = 
						Class.forName(controllerClassStr);
				//기본생성자호출
				Controller controller=(Controller)controllerClass.newInstance();
				controllerMap.put(commandURIKey, controller);
			}
			System.out.println("***********controller_mapping.properties end***********************");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Controller getController(String command) {
		return controllerMap.get(command);
	}

}
