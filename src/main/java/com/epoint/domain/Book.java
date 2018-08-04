package com.epoint.domain;

import java.util.Date;

public class Book {

	private String bookguid;
	private String bookname;
	private int booktype;
	private int suitable;
	private Date buydate;
	private int count;
	private int first;
	public int getFirst() {
		return first;
	}
	public void setFirst(String s) {
		
		if(s.equals("中心馆"))
		{
			this.first=1;
		}
		else if(s.equals("分馆1"))
		{
			this.first=2;
		}
		else
		{
			this.first=3;
		}
	}
	private String remark;
	public String getBookguid() {
		return bookguid;
	}
	public void setBookguid(String bookguid) {
		this.bookguid = bookguid;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public int getBooktype() {
		return booktype;
	}
	public void setBooktype(int booktype) {
		this.booktype = booktype;
	}
	public int getSuitable() {
		return suitable;
	}
	public void setSuitable(int suitable) {
		this.suitable = suitable;
	}
	public Date getBuydate() {
		return buydate;
	}
	public void setBuydate(Date buydate) {
		this.buydate = buydate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
