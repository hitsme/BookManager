package com.epoint.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epoint.domain.Book;
import com.epoint.jdbc.ConnectMysql;
import com.epoint.utils.JsonUtil;

/**
 * Servlet implementation class bookedit
 */
@WebServlet("/bookedit")
public class bookedit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bookedit() {
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
		if(method.equals("queryById")){
			queryById(request,response);
		}else{
			edit(request,response);
		}
	}
	
	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data=request.getParameter("data");
		Book b =JsonUtil.jsonToObject(data, Book.class);
		
		if(new ConnectMysql().UpdateBook(b).equals("2"))
		{
			response.getWriter().print("修改成功！");
		}
		else
		{
			response.getWriter().print("修改失败！");
		}
		
	}

	private void  queryById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("data")!=null?request.getParameter("data").trim():"";
		Book b=new Book();
		b=new ConnectMysql().queryById(id);
		String json = JsonUtil.objectToJson(b);
		response.getWriter().print(json);
		System.out.print(json);
	

}
}
