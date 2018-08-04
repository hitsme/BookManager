package com.epoint.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.epoint.domain.Book;
import com.epoint.domain.Borrow;



public class ConnectMysql {
	Connection conn=null;
	PreparedStatement stmt=null;
	ResultSet rs=null;
	
	public ConnectMysql()
	{
		
	}
	
	public  Connection connectMysql()
	{
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		     } catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		                                         }   
		
	 
	 try { 
		   String url="jdbc:mysql://localhost:3306/bookmanager??useUnicode=true&characterEncoding=utf-8";
		         conn=DriverManager.getConnection(url,"root","bingo");
       

	               } catch (SQLException e)
	           {   
		
		      e.printStackTrace();
	           }
	 return conn;
		
	}
public static void closeAll(Connection con, Statement stmt, ResultSet rs){
		
		try {
			
			if(con!=null){
				con.close();
			}
			if(stmt!=null){
				stmt.close();
			}
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

public List<Book> getAllBook() {
	// TODO Auto-generated method stub
	connectMysql();
	List<Book> list=new ArrayList<Book>();
	try {
		stmt=conn.prepareStatement("select *from bookinfo");
		rs=stmt.executeQuery();
		while(rs.next())
		{
			Book b=new Book();
			b.setBookguid(rs.getString(1));
			b.setBookname(rs.getString(2));
			b.setBooktype(rs.getInt(3));
			b.setSuitable(rs.getInt(4));
			
			b.setBuydate(rs.getDate(5));
			b.setCount(rs.getInt(6));
			b.setRemark(rs.getString(7));
			list.add(b);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	closeAll(conn, stmt, rs);
	return list;
}

public List<Book> getBookByDate(String date) {
	// TODO Auto-generated method stub
	 connectMysql();
	 SimpleDateFormat df=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	 
	 List<Book> list=new ArrayList<Book>();
	try {
		java.sql.Date date1=new java.sql.Date(df.parse(date).getTime());

		  CallableStatement call;
		  try {
			call=conn.prepareCall("{call select_bydate(?)}");
			
			call.setDate(1, date1);
			rs=call.executeQuery();
			while(rs.next())
			{
				Book b=new Book();
				b.setBookguid(rs.getString(1));
				b.setBookname(rs.getString(2));
				b.setBooktype(rs.getInt(3));
				b.setSuitable(rs.getInt(4));
				
				b.setBuydate(rs.getDate(5));
				b.setCount(rs.getInt(6));
				b.setRemark(rs.getString(7));
				list.add(b);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 closeAll(conn, stmt, rs);
	return list;
}

public List<Book> getBookByBooktype(String booktype) {
	// TODO Auto-generated method stub
	connectMysql();
	List<Book> list=new ArrayList<Book>();
	int bookt=Integer.parseInt(booktype);
	try {
		stmt=conn.prepareStatement("select *from bookinfo where booktype=?");
		stmt.setInt(1,bookt );
		rs=stmt.executeQuery();
		while(rs.next())
		{
			Book b=new Book();
			b.setBookguid(rs.getString(1));
			b.setBookname(rs.getString(2));
			b.setBooktype(rs.getInt(3));
			b.setSuitable(rs.getInt(4));
			
			b.setBuydate(rs.getDate(5));
			b.setCount(rs.getInt(6));
			b.setRemark(rs.getString(7));
			list.add(b);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	closeAll(conn, stmt, rs);
	return list;
}

public List<Book> getBookByBooktypeAndDate(String booktype, String date) {
	// TODO Auto-generated method stub
	connectMysql();
	int bookt=Integer.parseInt(booktype);
	 SimpleDateFormat df=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	 List<Book> list=new ArrayList<Book>();
	 try {
		java.sql.Date date1=new java.sql.Date(df.parse(date).getTime());

		  CallableStatement call;
		  try {
			call=conn.prepareCall("{call select_bybooktypeanddate(?,?)}");
			call.setInt(1,bookt );
			call.setDate(2, date1);
			
			rs=call.executeQuery();
			while(rs.next())
			{
				Book b=new Book();
				b.setBookguid(rs.getString(1));
				b.setBookname(rs.getString(2));
				b.setBooktype(rs.getInt(3));
				b.setSuitable(rs.getInt(4));
				
				b.setBuydate(rs.getDate(5));
				b.setCount(rs.getInt(6));
				b.setRemark(rs.getString(7));
				list.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 closeAll(conn, stmt, rs);
	return list;
}

public String insertBook(Book b) {
	// TODO Auto-generated method stub
	String a="0";
	connectMysql();
	CallableStatement call;
	try {
		call=conn.prepareCall("call insert_book(?,?,?,?,?,?,?,?)");
		call.setString(1, b.getBookguid());
		call.setString(2, b.getBookname());
		call.setInt(3, b.getBooktype());
		call.setInt(4, b.getSuitable());
		java.sql.Date date=new java.sql.Date(b.getBuydate().getTime());
		
		call.setDate(5, date);
		call.setInt(6, b.getCount());
		call.setString(7, b.getRemark());
		call.registerOutParameter(8, Types.VARCHAR);
		call.executeUpdate();
		a=call.getString(8);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return a;
}

public boolean DeleteBook(List<String> ids) {
	// TODO Auto-generated method stub
	boolean b=false;
	connectMysql();
	for(String i:ids)
	{
	try {
		stmt=conn.prepareStatement("delete from bookinfo where bookguid=?");
		stmt.setString(1, i);
		stmt.executeUpdate();
		b=true;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	try {
		conn.close();
		stmt.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return b;
}

public String UpdateBook(Book b) {
	String a="0";
	connectMysql();
	CallableStatement call;
	try {
		call=conn.prepareCall("call update_book(?,?,?,?,?,?,?,?)");
		call.setString(1, b.getBookguid());
		
		call.setString(2, b.getBookname());
		call.setInt(3, b.getBooktype());
		call.setInt(4, b.getSuitable());
		java.sql.Date date=new java.sql.Date(b.getBuydate().getTime());
		
		call.setDate(5, date);
		call.setInt(6, b.getCount());
		call.setString(7, b.getRemark());
		call.registerOutParameter(8, Types.VARCHAR);
		call.executeUpdate();
		a=call.getString(8);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return a;
}

public Book queryById(String id) {
	// TODO Auto-generated method stub
	connectMysql();
	 Book b=new Book();
	
	try {
		stmt=conn.prepareStatement("select *from bookinfo where bookguid=?");
		stmt.setString(1,id );
		rs=stmt.executeQuery();
		while(rs.next())
		{
			
			b.setBookguid(rs.getString(1));
			
			b.setBookname(rs.getString(2));
			b.setBooktype(rs.getInt(3));
			b.setSuitable(rs.getInt(4));
			
			b.setBuydate(rs.getDate(5));
			b.setCount(rs.getInt(6));
			b.setRemark(rs.getString(7));
			
			b.setFirst(rs.getString(2).substring(0, 3));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	closeAll(conn, stmt, rs);
	return b;
}

public List<Borrow> getAllBorrow() {
	// TODO Auto-generated method stub
	connectMysql();
	List<Borrow> list=new ArrayList<Borrow>();
	try {
		stmt=conn.prepareStatement("select *from borrowinfo");
		rs=stmt.executeQuery();
		while(rs.next())
		{
			Borrow b=new Borrow();
			b.setBookname(rs.getString(1));
			b.setUseguid(rs.getString(2));
			b.setBorrowperson(rs.getString(3));
			b.setHandler(rs.getString(4));
			
			b.setBorrowcause(rs.getString(5));
			b.setBorrowdate(rs.getDate(6));
			b.setReturndate(rs.getDate(7));
			b.setRemark(rs.getString(8));
			list.add(b);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	closeAll(conn, stmt, rs);
	return list;
}

public List<Borrow> getBorrowByBookname(String bookname) {
	// TODO Auto-generated method stub
	connectMysql();
	List<Borrow> list=new ArrayList<Borrow>();
	try {
		stmt=conn.prepareStatement("select *from borrowinfo where bookname like'%"+bookname+"%'");
		rs=stmt.executeQuery();
		while(rs.next())
		{
			Borrow b=new Borrow();
			b.setBookname(rs.getString(1));
			b.setUseguid(rs.getString(2));
			b.setBorrowperson(rs.getString(3));
			b.setHandler(rs.getString(4));
			
			b.setBorrowcause(rs.getString(5));
			b.setBorrowdate(rs.getDate(6));
			b.setReturndate(rs.getDate(7));
			b.setRemark(rs.getString(8));
			list.add(b);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	closeAll(conn, stmt, rs);
	return list;
}

public List<Book> beforeAdd() {
	// TODO Auto-generated method stub
	connectMysql();
	List<Book> list=new ArrayList<Book>();
	try {
		stmt=conn.prepareStatement("select *from bookinfo");
		rs=stmt.executeQuery();
		while(rs.next())
		{
			Book b=new Book();
			b.setBookguid(rs.getString(1));
			b.setBookname(rs.getString(2));
			b.setBooktype(rs.getInt(3));
			b.setSuitable(rs.getInt(4));
			
			b.setBuydate(rs.getDate(5));
			b.setCount(rs.getInt(6));
			b.setRemark(rs.getString(7));
			list.add(b);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	closeAll(conn, stmt, rs);
	return list;
}

public String borrowAdd(Borrow b) {
	// TODO Auto-generated method stub
	String a="0";
	connectMysql();
	CallableStatement call;
	try {
		call=conn.prepareCall("call insert_borrow(?,?,?,?,?,?,?,?,?)");
		call.setString(1, b.getBookname());
		call.setString(2, b.getUseguid());
		call.setString(3, b.getBorrowperson());
		call.setString(4, b.getHandler());
		call.setString(5, b.getBorrowcause());
		java.sql.Date date=new java.sql.Date(b.getBorrowdate().getTime());
		java.sql.Date date2=new java.sql.Date(b.getReturndate().getTime());
		call.setDate(6, date);
		call.setDate(7, date2);
		call.setString(8, b.getRemark());
		call.registerOutParameter(9, Types.VARCHAR);
		call.execute();
		a=call.getString(9);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return a;
}

public boolean deleteBorrrow(String[] idList) {
	// TODO Auto-generated method stub
	boolean b=false;
	connectMysql();
	CallableStatement call;
	try {
		
		for(String i:idList)
		{
			call=conn.prepareCall("call delete_borrow(?)");
			call.setString(1, i);
			call.execute();
			b=true;
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return b;
}
	

	
}


