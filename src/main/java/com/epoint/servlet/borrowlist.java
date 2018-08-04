package com.epoint.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epoint.domain.Borrow;
import com.epoint.jdbc.ConnectMysql;
import com.epoint.utils.JsonUtil;

/**
 * Servlet implementation class borrowlist
 */
@WebServlet("/borrowlist")
public class borrowlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public borrowlist() {
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
		String bookname = request.getParameter("bookname");
		if(bookname != null){
			if(bookname.trim().isEmpty()){
				bookname = null;
			}
		}
		
		int index = Integer.parseInt(request.getParameter("pageIndex"));
		int size= Integer.parseInt(request.getParameter("pageSize"));
		
			List<Borrow> dataAll = new ArrayList<Borrow>();
			
			if(bookname == null){
				dataAll=new ConnectMysql().getAllBorrow();
				
			}
			else
			{
				dataAll=new ConnectMysql().getBorrowByBookname(bookname);
			}
			
		    List<Borrow> dataNew=new ArrayList<Borrow>();
			for (int i = size*index;i<size*index+size&&i<dataAll.size(); i++)
	        {
	           dataNew.add(dataAll.get(i));
	        }			
			
			HashMap result = new HashMap();
			result.put("data", dataNew);
			result.put("total", dataAll.size());
			String json = JsonUtil.objectToJson(result);
			response.getWriter().print(json);
			
	
	


	}
}