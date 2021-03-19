/*SENG2050 Assignment 1
 * Created by Keeylan Hume, C3320396
 */

//package com.sengassignment1;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Random;
import java.util.Scanner;



@WebServlet(urlPatterns ={"/MainPage"})
public class MainPage extends HttpServlet{
	// Creates empty array of Users to match table
	User[][] seats = new User[8][8];
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {	    
    	// Reads txt file and supplies the User objects with booking data
    	importBooking();	
    	PrintWriter out = response.getWriter();
    	out.println(headerHtml());
    	out.println(htmlBody());
    }
	
    /* Function to make the main table filled with buttons
     * If the session is new and the import file loaded currently booked seats into the system
     * the for loop has corresponding if statements to make the booked seat class booked for CSS purposes
     * and if the same session is running on a refresh it doesn't make the whole table booked
     * upon click sends the button value to the doPost request
     */
     public String makeTable() {
    			String table = "<table id=\"seats\"><tr>";
    			String[] letters = {"A","B","C","D","E","F","G","H"};
    			
    			for(int i = 0; i <=8; i++) {
    				if(i==0) {
    					table = table + "<th> &nbsp; </th>";
    					i++;
    				}
    				table = table + "<th> " + i + " </th>";
    			} // End top row for loop
    			table = table + "</tr><tr>";
    			
    			for(int i = 0; i < 8; i ++) { // For loop for the side letters
    				table = table + "<th>" + letters[i] + "</th>"; 
    					for(int f = 1; f <=8; f++) {
    						// Checks if a seat object is in this spot
    						if(seats[i][f-1] != null) {
    							// Checks if the seat has a user attached ** implemented due to refreshing the page it will run the table function again
    							if(seats[i][f-1].getUserId() != null) {
    								table = table + "<td><button class=\"booked\" value=\""+ letters[i] + f +"\" name=\"button\" type=\"submit\">" + letters[i] + f 												+ "</button></td>";
    								seats[i][f-1].setSeatId(letters[i] + f);
    							}	
    						}
    						// Checks if there is originally a User object and due to refresh checks if the userId is null also
    						if (seats[i][f-1] == null || seats[i][f-1].getUserId() == null) {
    								seats[i][f-1] = new User();
    								seats[i][f-1].setSeatId(letters[i]+f);
    								table = table + "<td><button class=\"free\" value =\""+ letters[i] + f +"\" name=\"button\" type=\"submit\">" + letters[i] + f 												 												+ "</button></td>";
    						}
    							if(f == 8 ) {
    							table = table + "</tr><tr>";
    						}	
    					}
    			}
    	return table;
     }
     
     public String getDate() {
    	 LocalDateTime dt = LocalDateTime.now();
    	 String pattern = "dd/MM/YYYY-ss:mm:HH";
    	 DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
    	 String dateTime = format.format(dt);
    	 return dateTime;
     }
     
     // Picks the post request from clicking a seat on the table
     public void doPost(HttpServletRequest request, HttpServletResponse response)
    		 throws ServletException, IOException {   	 
     		PrintWriter out = response.getWriter();
     		
    	 	String seatBooking = request.getParameter("button");
	 			for(int i = 0; i < 8; i++) {
	 				for(int f = 1; f <= 8; f++) {
	 					// Finds the seat the user has clicked on, checks if no ones booked
	 					if(seatBooking.equals(seats[i][f-1].getSeatId()) && seats[i][f-1].getUserId() == null)  {
	 						int col = f - 1;
	 						out.println(headerHtml());
	 						out.println(bookingForm(seatBooking,i,col));
	 					// Tried to click an already booked seat, displays the user who booked information
	 					}else if(seatBooking.equals(seats[i][f-1].getSeatId()) && seats[i][f-1].getUserId() != null) {
	 						out.println(headerHtml());
	 						out.println("<p>");
	 						out.println("This seat has been taken by: " + seats[i][f-1].getUserId());
	 						out.println("<br /> Booked at: "+ seats[i][f-1].getBookingTime());
	 						out.println("</p>");
	 						break;
	 					}
	 				}
	 			}	
     	}	
     
     /* Loads the booking form with a generated security code
      * used html required and pattern matching for validation "Ask if this is allowed"
      * upon clicking submit sends to bookingPage servlet to export the buttons to the text file
      * Has 4 hidden forms for the row and col of where the seat is located in the array and the date/time it was booked
      * and one for the security code to validate it via JS
      */
      public String bookingForm(String seatBooking, int row, int col) {
    	String securityCode = randomSecurityGenerator();
    	String bookingForm = "";
	 			bookingForm += "<body>";
	 			bookingForm += "<h1> The Art Thee Theatre</h1> <hr>";
	 			bookingForm += "<h3> Your locally generated security tag is: \"" + securityCode + "\" </h3>";
	 			bookingForm += "<div class=\"divForm\"><form name=\"seatBooking\" method=\"post\" action=\"addBooking\">";
	 			bookingForm += "<label> UserId: </label> <br /> <input type=\"text\" name=\"user\" title=\"No numbers allowed\" placeholder=\"Enter a UserId\">"
	 							+ "<br/>";
	 			bookingForm += "<label> Phone: </label> <br /> <input type=\"number\" placeholder=\"Enter your contact no\" name=\"phoneNo\"> <br />";
	 			bookingForm += "<label> Address: </label> <br /> <input type=\"text\" placeholder=\"Enter your address\" name=\"address\"><br />";
	 			bookingForm += "<label> Email: </label> <br /> <input type=\"text\" placeholder=\"Enter a valid email\" name=\"emailAddress\"> <br />";
	 			bookingForm += "<label> Security code: </label> <br /> <input type=\"text\" id=\"securityCode\" name=\"securityCode\" title=\"Must match: " 								+ securityCode + "\">";
	 			bookingForm += "<br /> <p>Seat number: \"" + seatBooking + "\"";
	 			bookingForm += "<p> Booked time: \"" + getDate() + "\"<br />";
	 			bookingForm += "<input type=\"hidden\" name=\"secCode\" value=\"" + securityCode +"\">";
	 			bookingForm += "<input type=\"hidden\" name=\"row\" value=\"" + row +"\">";
	 			bookingForm += "<input type=\"hidden\" name=\"col\" value=\"" + col +"\">";
	 			bookingForm += "<input type=\"hidden\" name=\"dateTime\" value=\"" + getDate() +"\">";
	 			bookingForm += "<br /><button type=\"submit\" value=\"Submit\" onclick=\" return validate()\"> Submit </button> <button type=\"reset\""
	 							+ "value=\"Reset\"> Reset </button> <br />";
	 			bookingForm += "<br /><small> Red boxes are required </small>";
	 	return bookingForm;
      }
     
    // Generated the random security numbers and letters, converts to string to print via HTML out
    public String randomSecurityGenerator() {
    	String randomAlpha = "ABCDEFGHIJKLMNOPQRSTUVWRXYZ0123456789012345678901234567";
    	StringBuilder securityLine = new StringBuilder();
    	Random random = new Random();
    		while(securityLine.length() < 6) {
    			int index = (int) (random.nextFloat() * randomAlpha.length());
    			securityLine.append(randomAlpha.charAt(index));
    		}
    	String secCode = securityLine.toString();
    	return secCode;
    }
    
    /* Uses a scanner to read the input of the text file
     * the text file has a specific format so if someone messes with the format
     * it will incorrectly load and crash.. aka dont mess with it please
     */
 	public void importBooking() throws FileNotFoundException {
		File seatBooking = new File(getServletContext().getRealPath("WEB-INF/seatBooking.txt"));		
		Scanner input = new Scanner(seatBooking);
		if(input.hasNextLine()) {
			while(input.hasNext()) { // Reading file to see if anything is in
				int i = input.nextInt();
				int j = input.nextInt();
				seats[i][j] = new User();
				seats[i][j].setUserId(input.next());
				seats[i][j].setPhoneNumber(input.next());
				seats[i][j].setAddress(input.next());
				seats[i][j].setEmail(input.next());
				seats[i][j].setBookingTime(input.next());
				}
			input.close();
			}
 		}
 	
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
    
    // Prints out the table/date and main body of the page	
	public String htmlBody() {
		String output = "";
		output += "<body>";
       	output += "<h1>The Art Thee Theatre</h1> <hr>";
        output += "<h3>";
        output += getDate();
        output += "</h3>";
        output += "<form method=\"post\" action=\"MainPage\">";
        output += "<div class=\"table\">";
        output += makeTable();
        output += "</div>";
        output += "</form>";
        output += "</body>";
        output += "</html>";
	return output;
	}
}

