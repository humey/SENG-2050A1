/* Javascript with a regex for no numbers in the userID
 * regex for a valid email address
 * and checks the current security code if its correct
 */
function validate(){
	
	var userID = document.forms["seatBooking"]["user"];
	var email = document.forms["seatBooking"]["emailAddress"];
	var secInput = document.getElementById("securityCode").value;
	var secCode = document.seatBooking.elements["secCode"].value;
	
	
		if(!userID.value){
			alert("Please enter a userID");
			userID.focus();
			return false;
		}
		if(!/^[a-zA-z]*$/g.test(userID.value)){
			alert("No numbers allowed");
			userID.focus();
			return false;
		}
		if(!email.value){
			alert("Please enter an email address");
			email.focus();
			return false;
		}
		if(!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email.value)){
			alert("Please enter a valid email");
			email.focus();
			return false;
		}
		if(secInput != secCode || secInput == ""){
			alert("Invalid security code");
			return false;
		}
		
	return true;
}
