package com.itwill.winter;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/*
 * ��������(controller_mapping.properties)�� ����ؼ� Controller��ü��
 * �����ϰ� �����������ٰ�ü
 */
public class HandlerMapping {
	private HashMap<String,Controller> controllerMap;
	public HandlerMapping(String controllerFilePath) {
		/*
		 * controller_mapping.properties ������ �о
		 * controllerMap��ü�� �����ϴ��۾�
		 */
		System.out.println(">>> 3.HandlerMapping������-->controller_mapping.properties ������ �о controllerMap��ü�� �����ϴ��۾�");
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
				//�⺻������ȣ��
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
