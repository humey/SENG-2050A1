/*SENG2050 Assignment 1
 * Created by Keeylan Hume, C3320396
 */

//package com.sengassignment1;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Scanner; 

	@WebServlet(urlPatterns ={"/addBooking"})
	public class BookingPage extends HttpServlet {
		
	    public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	    	int count = bookingLimit(request.getParameter("user"));
	    	PrintWriter out = response.getWriter();
	    	// Checking how many seats the user has booked previously
	    	if(count <= 2) {
	    		String outPut = "";
	    			outPut += request.getParameter("row");
	    			outPut += " ";
	    			outPut += request.getParameter("col");
	    			outPut += " ";
	    			outPut += request.getParameter("user");
	    			outPut += " ";
	    			// Following if statements are checking if the user has entered a phone number or an address
	    			// If they have left blank insert them as null
	    			if ((request.getParameter("phoneNo") != null) && !request.getParameter("phoneNo").isEmpty()) {
	    				outPut += request.getParameter("phoneNo");
	    				outPut += " ";
	    			}else {
	    				outPut += "null";
	    				outPut += " ";
	    			}
	    			if ((request.getParameter("address") != null) && !request.getParameter("address").isEmpty()) {
	    				outPut += request.getParameter("address");
	    				outPut += " ";
	    			}else {
	    				outPut += "null";
	    				outPut += " ";
	    			}
	    			outPut += request.getParameter("emailAddress");
	    			outPut += " ";
	    			outPut += request.getParameter("dateTime");		
	    			out.println(headerHtml());
		    		out.println(bodyHtml());
	    			saveData(outPut);	
	    	//If trying to book over 3 will produce the following
	    	}else {
	    		out.println(headerHtml());
	    		out.println("<p> You have reached the booking limit of 3 seats </p>");
		    	out.println("<form action=\"MainPage\" method=\"GET\">");
		    	out.println("<input type=\"submit\" value=\"Back to main\">");
		    	out.println("</form>");
	    	}	    		    		 
	    }
	    // Saves the form data to the file, appends the file per new booking on a new line	   
	    public void saveData(String outPut) throws IOException {
	    	
	    		FileWriter writer = new FileWriter(getServletContext().getRealPath("WEB-INF/seatBooking.txt"),true);
	    		BufferedWriter out = new BufferedWriter(writer);
	    		out.write(outPut + "\n");	  
	    		out.close();	   
	    }
	    // Reads the booking file and counts how many times the user has booked
	    public int bookingLimit(String user) throws FileNotFoundException {
	    	int count = 0;
	    	File seatBooking = new File(getServletContext().getRealPath("WEB-INF/seatBooking.txt"));
			Scanner input = new Scanner(seatBooking);
	    	if(input.hasNextLine()) {
	    		while(input.hasNext()) {
				String word = input.next();
	    			if(word.equalsIgnoreCase(user)) {
	    			count++;
	    			}
	    		}
	    	}
	    	input.close();	    	
	    	return count;
	    }
	     // Outputs header HTML
	     public String headerHtml() {
	    	 String header = "";
	    	 header += "<!DOCTYPE html>";
	    	 header += "<html>";
	    	 header += "<head>";
	    	 header += "<link rel=\"stylesheet\" href=\"css/assignment1.css\" type=\"text/css\">";
	    	 header += "<script type=\"text/javascript\" src=\"Js/assignment1.js\"></script>";
	    	 header += "<title>The Art Thee Booking System</title>";
	    	 header += "</head>";	   
	    	 return header;
	     }
	     public String bodyHtml() {
	    	 String body = "";
	 		body += "<body>";
	       	body += "<h1>The Art Thee Theatre</h1> <hr>";
	       	body += "<p> Enjoy your time at our theatre </p>";
	    	body += "<form action=\"MainPage\" method=\"GET\">";
	    	body += "<input type=\"submit\" value=\"Back to main menu\">";
	    	body += "</form>";
	    	body += "</body>";
	    	body += "</html>";
	    	return body;
	     }	     
	}
