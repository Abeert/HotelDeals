/**
 * 
 */
function formValidator(form){
	
	var destination = document.forms["findHotelForm"]["destination"].value;
    if (destination == "") {
        alert("destination is required");
        return false;
    }
	var startDate = document.forms["findHotelForm"]["minTripStartDate"].value;
	var startDate = document.forms["findHotelForm"]["minTripEndDate"].value;
return false;
//	validateDate(startDate);
    
}

function validateDate(date)
{
  // regular expression to match date format
  re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
  if(date != '' && !date.match(re)) {
	    alert("Invalid date format: " + date);
	    date.focus();
	    return false;
	  }
  return false;

}