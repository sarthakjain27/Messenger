package org.jain.sarthak.messenger;

public class MyDate {

	private int date;
	private int month;
	private int year;
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public String toString()
	{
		return "Date is: "+date+" month is: "+month+" year is: "+year;
	}
}
