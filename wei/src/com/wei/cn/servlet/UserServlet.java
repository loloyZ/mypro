package com.wei.cn.servlet;

import com.wei.cn.util.servlet.DispatcherServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.io.File;


@SuppressWarnings("serial")
@WebServlet(urlPatterns="/users/*")
public class UserServlet extends DispatcherServlet {
	// private final String insertVlidate = "users.uname|users.upass|users.uphone" ;
	 //private final String findUsersVlidate = "users.uname|users.upass" ;
	 //private final String updateVlidate = "dept.deptno|dept.dname|dept.loc" ;
	 //private Users users = new Users() ;
     public String insert() {
		 return "";
	 }

     public String update() {   	
    	 System.out.println("========================") ;  	
    	 return "" ;
     }
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "≤ø√≈";
	}

	//public Users getUsers() {
	//	return users;
	//}
	@Override
	public String getUploadDirectory() {		
		return "/upload" + File.separator + "dept" + File.separator ;
	}	
}
