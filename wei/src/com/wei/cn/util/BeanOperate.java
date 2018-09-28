package com.wei.cn.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;


public class BeanOperate {
	private Object currentObj ; //表示当前程序的保存对象
	private String attribute ; //要操作的属性
	private String value ; //要操作的内容
	private String arrayValue [] ; //要操作的数组内容
	private Field field ; //表示要操作的成员对象
	/**
	 * 进行操作数据的接收，接收后可以进行数据的设置操作
	 * @param obj 表示当前要操作此功能的类对象
	 * @param attribute 包含了“对象.属性.属性...”字符串
	 * @param value 表示属性的内容
	 */
    public BeanOperate(Object obj, String attribute, String value) {
    	this.currentObj = obj ; //保存当前的操作对象
    	this.attribute = attribute ;
    	this.value = value ;
    	this.handleParameter() ;
    	this.setValue() ;
    }

	/**
	 * 进行数组的操作
	 * @param obj
	 * @param attribute
	 * @param arrayValue
	 */
    public BeanOperate(Object obj, String attribute, String arrayValue[]) {
    	this.currentObj = obj ; //保存当前的操作对象
    	this.attribute = attribute ;
    	this.arrayValue = arrayValue;
    	this.handleParameter() ;
    	this.setValue() ;
    }
    public void handleParameter() {
    	try {
    	    String result[] = this.attribute.split("\\.") ;
    	    if (result.length == 2) {
	         	//对于类中的getter方法上是不存在参数的,所以参数类型为空
	    	    Method getMet = this.currentObj.getClass().getMethod("get" + StringUtils.initcap(result[0])) ;
	    	    this.currentObj = getMet.invoke(this.currentObj) ;
	    	    this.field = this.currentObj.getClass().getDeclaredField(result[1]) ; //取得对象成员
	         	
    	    } else {
    	    	for (int x = 0 ; x < result.length ; x ++) {
    	    		//System.out.println("x=" + x + "=" + this.currentObj) ;
    	    		//必须知道当前操作的成员对象，因为只要有对象存在才可以找到属性类型，才可以调用setter方法
    	    		this.field = this.currentObj.getClass().getDeclaredField(result[x]) ;
    	    		//System.out.println(this.field.getName()) ;
    	    		if (x < result.length - 1) {
	    	    		Method met = this.currentObj.getClass().getMethod("get" + StringUtils.initcap(result[x])) ;    		
	    	     	    this.currentObj = met.invoke(this.currentObj) ;
    	    		}
    	    	}
    	    }
    	} catch (Exception e) {
    		e.printStackTrace() ;
    	}
    }
    //定义一个专门设置属性内容的方法，调用setter方法
    private void setValue() {
    	try {
	    	Method setMet = this.currentObj.getClass().getMethod("set" + StringUtils.initcap(this.field.getName()),this.field.getType()) ;
	    	String type = this.field.getType().getSimpleName() ;	    	
	    	if ("int".equalsIgnoreCase(type) || "integer".equalsIgnoreCase(type)) {
	    		if (this.value.matches("\\d+")) {
	    		//	System.out.println(Integer.parseInt(this.value)) ;
	    			setMet.invoke(this.currentObj, Integer.parseInt(this.value)) ;
	    		}
	    	} else if ("double".equalsIgnoreCase(type)) {
	    		if (this.value.matches("\\d+(\\.\\d+)?")) {
	    		    setMet.invoke(this.currentObj, Double.parseDouble(this.value)) ;
	    		}
	    	} else if ("string".equalsIgnoreCase(type)) {
	    		setMet.invoke(this.currentObj, this.value) ;
	    	} else if ("date".equalsIgnoreCase(type)) {
	    		if (this.value.matches("\\d{4}-\\d{2}-\\d{2}")) {
	    		    setMet.invoke(this.currentObj, new SimpleDateFormat("yyyy-MM-dd").parse(this.value)) ;
	    		}
	    		if (this.value.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
	    		    setMet.invoke(this.currentObj, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.value)) ;
	    		}
	    	} else if ("string[]".equalsIgnoreCase(type)) {
	    		setMet.invoke(this.currentObj, new Object[]{this.arrayValue}) ;    		
	    	} else if ("int[]".equalsIgnoreCase(type)) {
	    		int temp[] = new int[this.arrayValue.length] ;
	    		for (int x = 0 ; x < temp.length ; x ++) {
	    			if (this.arrayValue[x].matches("\\d+")) {
	    				temp[x] = Integer.parseInt(this.arrayValue[x]) ;	  		
	    			}
	    		}
	    		setMet.invoke(this.currentObj, new Object[]{temp}) ;
	    	} else if ("integer[]".equalsIgnoreCase(type)) {
	    		Integer temp[] = new Integer[this.arrayValue.length] ;
	    		for (int x = 0 ; x < temp.length ; x ++) {
	    			if (this.arrayValue[x].matches("\\d+")) {
	    				temp[x] = Integer.parseInt(this.arrayValue[x]) ;	  		
	    			}
	    		}
	    		setMet.invoke(this.currentObj, new Object[]{temp}) ;
	    	} else if ("double[]".equals(type)) {
	    		double temp[] = new double[this.arrayValue.length] ;
	    		for (int x = 0 ; x < temp.length ; x ++) {
	    			if (this.arrayValue[x].matches("\\d+(\\.\\d+)?")) {
	    				temp[x] = Double.parseDouble(this.arrayValue[x]) ;	  		
	    			}
	    		}
	    		setMet.invoke(this.currentObj, new Object[]{temp}) ;
	    	} else if ("Double[]".equals(type)) {
	    		Double temp[] = new Double[this.arrayValue.length] ;
	    		for (int x = 0 ; x < temp.length ; x ++) {
	    			if (this.arrayValue[x].matches("\\d+(\\.\\d+)?")) {
	    				temp[x] = Double.parseDouble(this.arrayValue[x]) ;	  		
	    			}
	    		}
	    		setMet.invoke(this.currentObj, new Object[]{temp}) ;
	    	}
	     	//setMet.invoke(this.currentObj, this.value) ;
    	} catch(Exception e) {
    		e.printStackTrace() ;
    	}
    }
}
