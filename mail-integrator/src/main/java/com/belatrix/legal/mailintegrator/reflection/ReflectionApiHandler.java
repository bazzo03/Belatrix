package com.belatrix.legal.mailintegrator.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import com.belatrix.legal.apihandler.facade.ApiHandler;
import com.belatrix.legal.apihandler.facade.IApiHandler;

public class ReflectionApiHandler {
	
	
	private static IApiHandler facadeHandler = new ApiHandler();
	
	private  static ConcurrentHashMap<String, Method> cofigHandler = new ConcurrentHashMap<String, Method>();
	
	
	
	private static Method getMethod(String operationName) throws NoSuchMethodException, SecurityException{
		
		if(cofigHandler.get(operationName) == null ){
			Method method =facadeHandler.getClass().getMethod(operationName, String.class,String.class);		
			cofigHandler.put(operationName, method);
		}
		
		return cofigHandler.get(operationName);
	}
	
	
	
	public static String execute(String content, String transactionId, String operation) throws Exception{
		String response="";
		Method method = getMethod(operation);
		if(method != null){
		response = (String) method.invoke(facadeHandler, content,transactionId);
		}else{
			throw new Exception("Error to process operation, Method not found ");
		}
		return response;
		
	}
	

}
