/*SENG2050 Assignment 1
 * Created by Keeylan Hume, C3320396
 */
//package com.sengassignment1;

/* User class to store all user information
 * class just contains the following Get/Set methods for all data
 */

public class User {

	private String userid;
	private String email;
	private String phoneNumber;
	private String bookingTime;
	private String time;
	private String address;
	private String seatId;
	
	// Default constructor setting all values to null
	User(){
		userid = null;
		email = null;
		phoneNumber = null;
		bookingTime = null;
		address = null;
		seatId = null;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUserId() {
		return userid;
	}
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getBookingTime() {
		return bookingTime;
	}
	public String getAddress() {
		return address;
	}
	public String getSeatId() {
		return seatId;
	}
	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
	public void setUserId(String userId) {
		this.userid = userId;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
