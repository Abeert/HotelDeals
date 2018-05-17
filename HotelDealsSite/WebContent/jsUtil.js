function showResult(){
	alert("jsutil:findHotel");
	var destination = document.forms["findHotelForm"]["destination"].value;
	var minTripStartDate = document.forms["findHotelForm"]["minTripStartDate"].value;
	var maxTripStartDate = document.forms["findHotelForm"]["maxTripStartDate"].value;
	var minPriceRate = document.forms["findHotelForm"]["minPriceRate"].value;
	var maxPriceRate = document.forms["findHotelForm"]["maxPriceRate"].value;
	var minRating = document.forms["findHotelForm"]["minRating"].value;
	var maxRating = document.forms["findHotelForm"]["maxRating"].value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 ) {
			if ( this.status == 200) {	
				console.log(this.responseText);
				var myObj = JSON.parse(this.responseText);
				var txt = "<table border='0' class='resultTable'>";
				var data = myObj.matchingHotels;
				for (x in data) {
					txt += "<tr><td><strong class='hotelName'>" +data[x].hotelInfo.hotelName + "</strong></td>" +
					"<td><img src='"+ data[x].hotelInfo.hotelImageUrl+"'/></td></tr> " +
			"<tr><td> Province: " +data[x].hotelInfo.hotelProvince + "</td></tr>"+
			"<tr><td>" +data[x].hotelInfo.hotelCity + ", "+ data[x].hotelInfo.hotelCountryCode + "</td></tr>" ;
				}

				txt += "</table>"  ;
				alert("html table:" +txt);
				document.getElementById("result").innerHTML = txt;

			}else if(this.status == 204){
				document.getElementById("result").innerHTML = "No match found, please try other criteria";;

			}
		}

	}
	xhttp.open("POST", "rest/HotelsFinder/doFind?destination="+destination+"&minTripStartDate="+minTripStartDate , true);
	xhttp.send();

}
	


