package com.epoint.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epoint.domain.Book;

import com.epoint.jdbc.ConnectMysql;
import com.epoint.utils.JsonUtil;

/**
 * Servlet implementation class bookadd
 */
@WebServlet("/bookadd")
public class bookadd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bookadd() {
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
		String data=request.getParameter("data");
		
		 Book b = JsonUtil.jsonToObject(data, Book.class);
		UUID uuid = UUID.randomUUID();
		b.setBookguid(uuid.toString());
		if(new ConnectMysql().insertBook(b).equals("2"))
		{
			response.getWriter().print("添加成功！");
		}
		else if(new ConnectMysql().insertBook(b).equals("1"))
		{
			response.getWriter().print("图书名重复，添加失败！");
		}
		else
		{
			response.getWriter().print("图书编号重复,添加失败！");//可以不写
		}
		
	
	}
}
