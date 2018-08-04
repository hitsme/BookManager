package com.epoint.servlet;

import java.awt.print.Book;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epoint.domain.Borrow;
import com.epoint.jdbc.ConnectMysql;
import com.epoint.utils.JsonUtil;

/**
 * Servlet implementation class borrowadd
 */
@WebServlet("/borrowadd")
public class borrowadd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public borrowadd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String method= request.getParameter("method");
		if(method.equals("beforAdd")){
			beforeAdd(request,response);
		}
		else if(method.equals("add")){
			add(request,response);
		}
	}
	
	private void beforeAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		    
		
		    List data=new ArrayList<Book>();
		    data=new ConnectMysql().getAllBook();
		
			HashMap result = new HashMap();
			result.put("data", data);
			String json = JsonUtil.listToJson(data);
			response.getWriter().print(json);
		
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String data=request.getParameter("data");
		Borrow b=new Borrow();
		
		
		//判断该车在这个时间段是否被占用
		b=JsonUtil.jsonToObject(data, Borrow.class);
		UUID uuid = UUID.randomUUID();
		b.setUseguid(uuid.toString());
		String a=new ConnectMysql().borrowAdd(b);
		if(a.equals("3"))
		{
			response.getWriter().print("添加成功！");
		}
		else if(a.equals("2"))
		{
			response.getWriter().print("书籍已经被借阅，添加失败！");
		}
		else
		{
			response.getWriter().print("借阅日期早于书籍购入日期，添加失败！");
		}
	}
}
