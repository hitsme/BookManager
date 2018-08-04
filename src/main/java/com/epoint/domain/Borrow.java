package com.epoint.domain;

import java.util.Date;

public class Borrow {
private String bookname;
private String useguid;
private String borrowperson;
private String handler;
private String borrowcause;
private Date borrowdate;
private Date returndate;
private String remark;
public String getBookname() {
	return bookname;
}
public void setBookname(String bookname) {
	this.bookname = bookname;
}
public String getUseguid() {
	return useguid;
}
public void setUseguid(String useguid) {
	this.useguid = useguid;
}
public String getBorrowperson() {
	return borrowperson;
}
public void setBorrowperson(String borrowperson) {
	this.borrowperson = borrowperson;
}
public String getHandler() {
	return handler;
}
public void setHandler(String handler) {
	this.handler = handler;
}
public String getBorrowcause() {
	return borrowcause;
}
public void setBorrowcause(String borrowcause) {
	this.borrowcause = borrowcause;
}
public Date getBorrowdate() {
	return borrowdate;
}
public void setBorrowdate(Date borrowdate) {
	this.borrowdate = borrowdate;
}
public Date getReturndate() {
	return returndate;
}
public void setReturndate(Date returndate) {
	this.returndate = returndate;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
}
