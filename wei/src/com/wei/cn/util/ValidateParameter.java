package com.wei.cn.util;

import com.jspsmart.upload.SmartUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ValidateParameter {
	private HttpServletRequest request ;
	private SmartUpload smart ;
	private String rule ;
	private Object currentObject ;
	//保存所有的错误信息，其中Key保存的是参数名称，value保存的是内容消息
	private Map<String,String> map = new HashMap<String,String>() ;
	private ResourceBundle msg = null ;
	
	public ValidateParameter(Object currentObject, HttpServletRequest request, SmartUpload smart, String rule, ResourceBundle msg) {
		this.request = request ;
		this.smart = smart ;
		this.rule = rule ;
		this.currentObject = currentObject ;
		this.msg = msg ;
	}
	
	public boolean validate() {		
		boolean flag = true ;
		String result [] = this.rule.split("\\|") ;	
		for (int x = 0 ; x < result.length ; x ++) {
			AttributeType at = new AttributeType(this.currentObject,result[x]) ;
			String type = at.getFiledType().toLowerCase() ;			
			if (type.contains("[]")) {
				String value[] = null ;
				if (request.getContentType().contains("multipart/form-data")) {					
					value = smart.getRequest().getParameterValues(result[x]) ;
				} else {
				    value = request.getParameterValues(result[x]) ;
				}
				if (value == null) { //现在没有数据提交
					this.map.put(result[x], this.msg.getString("validate.string")) ;			
				} else {
					if ("int[]".equals(type) || "integer[]".equals(type)) {
						for (int y = 0 ; y < value.length ; y ++) {
							if (!ValidateUtil.validateRegex(value[y], "\\d+")) {				
							   this.map.put(result[x], this.msg.getString("validate.int")) ;
							}
						}
					} else if ("double[]".equals(type)) {									    
						for (int y = 0 ; y < value.length ; y ++) {
							if (!ValidateUtil.validateRegex(value[y], "\\d+(\\.\\d+)?")) {						
							   this.map.put(result[x], this.msg.getString("validate.double")) ;
							}
						}
					}
				}		    
			} else {
				String value = null ;
				if (request.getContentType().contains("multipart/form-data")) {
					value = this.smart.getRequest().getParameter(result[x]) ;
				} else {
				    value = request.getParameter(result[x]) ;
				}
			//	System.out.println(value) ;
				if ("string".equals(type)) {
					if (!ValidateUtil.validateEmpty(value)) {
						this.map.put(result[x], this.msg.getString("validate.string")) ;
					}				
				} else if ("int".equals(type) || "integer".equals(type)) {
					if (!ValidateUtil.validateRegex(value, "\\d+")) {
						this.map.put(result[x], this.msg.getString("validate.int")) ;
					}
				} else if ("double".equals(type)) {
					if (!ValidateUtil.validateRegex(value, "\\d+(\\.\\d+)?")) {
						this.map.put(result[x], this.msg.getString("validate.double")) ;
					}
				} else if ("date".equals(type)) {
					if (!ValidateUtil.validateRegex(value, "\\d{4}-\\d{2}-\\d{2}")) {
						if (!ValidateUtil.validateRegex(value, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
						   this.map.put(result[x], this.msg.getString("validate.date")) ;
					    }
					}				
				} 
			}			
		} 
		if (this.map.size() > 0) { //有错误
			flag = false ;
		}
		return flag ;
	}
	public Map<String,String> getErrors() {
		return this.map ;
	}
}
