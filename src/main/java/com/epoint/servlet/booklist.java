package com.epoint.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epoint.domain.Book;
import com.epoint.jdbc.ConnectMysql;
import com.epoint.utils.JsonUtil;

/**
 * Servlet implementation class booklist
 */
@WebServlet("/booklist")
public class booklist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public booklist() {
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
		String booktype=request.getParameter("type");
	    String date=request.getParameter("time");
	  
	    List<Book> data=new ArrayList<Book>();
	    if(booktype!=null)
	    {
	    	if(booktype.trim().isEmpty())
	    		booktype=null;
	    }
	    
	    if(date!=null)
	    {
	    	if(date.trim().isEmpty())
	    		date=null;
	    }
	    if(booktype==null)
	    {
	    	if(date==null)
	    	{
	    		data=new ConnectMysql().getAllBook();
	    	}
	    	else
	    	{
	    		
	    		
	    		data=new ConnectMysql().getBookByDate(date);
	    	}
	    }
	    else
	    {
	    	if(date==null)
	    	{
	    		data=new ConnectMysql().getBookByBooktype(booktype);
	    		
	    	}
	    	else
	    	{
	    		
	    		data=new ConnectMysql().getBookByBooktypeAndDate(booktype,date);
	    	}
	    }
		
	    int index=Integer.parseInt(request.getParameter("pageIndex"));
	    int size=Integer.parseInt(request.getParameter("pageSize"));
		List<Book> dataNew=new ArrayList<Book>();
		for(int i = index*size;i<index*size+size&&i<data.size();i++)
		{
			dataNew.add(data.get(i));
		}
		HashMap result = new HashMap();
		result.put("data", dataNew);
		result.put("total", data.size());
		String json = JsonUtil.objectToJson(result);
		response.getWriter().print(json);
		
	}
}
