package com.wei.cn.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;


public class BeanOperate {
	private Object currentObj ; //��ʾ��ǰ����ı������
	private String attribute ; //Ҫ����������
	private String value ; //Ҫ����������
	private String arrayValue [] ; //Ҫ��������������
	private Field field ; //��ʾҪ�����ĳ�Ա����
	/**
	 * ���в������ݵĽ��գ����պ���Խ������ݵ����ò���
	 * @param obj ��ʾ��ǰҪ�����˹��ܵ������
	 * @param attribute �����ˡ�����.����.����...���ַ���
	 * @param value ��ʾ���Ե�����
	 */
    public BeanOperate(Object obj, String attribute, String value) {
    	this.currentObj = obj ; //���浱ǰ�Ĳ�������
    	this.attribute = attribute ;
    	this.value = value ;
    	this.handleParameter() ;
    	this.setValue() ;
    }

	/**
	 * ��������Ĳ���
	 * @param obj
	 * @param attribute
	 * @param arrayValue
	 */
    public BeanOperate(Object obj, String attribute, String arrayValue[]) {
    	this.currentObj = obj ; //���浱ǰ�Ĳ�������
    	this.attribute = attribute ;
    	this.arrayValue = arrayValue;
    	this.handleParameter() ;
    	this.setValue() ;
    }
    public void handleParameter() {
    	try {
    	    String result[] = this.attribute.split("\\.") ;
    	    if (result.length == 2) {
	         	//�������е�getter�������ǲ����ڲ�����,���Բ�������Ϊ��
	    	    Method getMet = this.currentObj.getClass().getMethod("get" + StringUtils.initcap(result[0])) ;
	    	    this.currentObj = getMet.invoke(this.currentObj) ;
	    	    this.field = this.currentObj.getClass().getDeclaredField(result[1]) ; //ȡ�ö����Ա
	         	
    	    } else {
    	    	for (int x = 0 ; x < result.length ; x ++) {
    	    		//System.out.println("x=" + x + "=" + this.currentObj) ;
    	    		//����֪����ǰ�����ĳ�Ա������ΪֻҪ�ж�����ڲſ����ҵ��������ͣ��ſ��Ե���setter����
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
    //����һ��ר�������������ݵķ���������setter����
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
