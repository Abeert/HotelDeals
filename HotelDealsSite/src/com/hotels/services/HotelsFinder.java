package com.hotels.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hotels.entities.Destination;
import com.hotels.entities.Hotel;
import com.hotels.entities.MyHotelDeals;
import com.hotels.entities.Offers;
import com.hotels.entities.UserInfo;

@Path("/HotelsFinder") 
public class HotelsFinder {
	
	private static String offersUrlString = "https://offersvc.expedia.com/offers/v2/getOffers?scenario=deal-finder&page=foo&uid=foo&productType=Hotel"; 
	
	@GET
	@Path("/HDestinations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDestination(){

		JSONObject destObj = new JSONObject();
		JSONArray destArray = new JSONArray();
		
		try {		
			MyHotelDeals myHotelDeals = OffersUtil.getOffers();
			if(myHotelDeals == null)
			{
				return Response.status(200).build();
			}
			List<Hotel> hotels = myHotelDeals.getOffers().getHotel();
			Destination destination;
			for(Hotel hotel: hotels){
				destination = hotel.getDestination();
				JSONObject dest = new JSONObject();
				dest.put("id", destination.getRegionID());
				dest.put("city", destination.getCity());
				dest.put("country", destination.getCountry());
				destArray.put(dest);
			}

			destObj.put("Destinations", destArray);

		} catch (JSONException |JsonParseException |JsonMappingException e) {
			System.err.println("JSON related Problem: "+ e.getMessage());
			return Response.status(500).build();
		} catch (IOException e) {
			System.err.println("IO Error: "+ e.getMessage());		
			return Response.status(500).entity("Error getting destinations").build();
		}
		
		return Response.status(200).entity(destObj.toString()).build();
	}
	
	
	@POST
	@Path("/doFind")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOffers(@QueryParam("destination") String inDestinationRegion, 
			@QueryParam("minTripStartDate") String inMinStartDate, @QueryParam("maxTripStartDate") String inMaxStartDate, 
			@QueryParam("lengthOfStay") int inLengthOfStay, @QueryParam("minPriceRate") float inMinRate, 
			@QueryParam("maxPriceRate") float inMaxRate, @QueryParam("minRating") String inMinRating, @QueryParam("maxRating") String inMaxRating){
	
		JSONObject matchingHotelsJson = new JSONObject();
		JSONArray matchingHotelsJsonArray = new JSONArray();
		try {
			List<Hotel> hotelsOffers = OffersUtil.getHotelsOffers();
			JSONObject hotelJson = null;
			for(Hotel hotel: hotelsOffers){
				String regionId = hotel.getDestination().getRegionID();
				if(inDestinationRegion != null && inDestinationRegion.equalsIgnoreCase(regionId)){
					 hotelJson = new JSONObject( hotel );
					matchingHotelsJsonArray.put(hotelJson);
				} 
			}
			if(matchingHotelsJsonArray != null &&  matchingHotelsJsonArray.length()>0){
				matchingHotelsJson.put("matchingHotels", matchingHotelsJsonArray);
				System.out.println("matchingHotelsJson=" + matchingHotelsJson.toString());
				return Response.status(200).entity(matchingHotelsJson.toString()).build();
			}
		}catch (JSONException | IOException e) {
			return Response.status(500).build();

		}
		System.out.println("No match");
		return Response.status(204).entity("{ \"message\": \"No Match Found\"}").build();	
	}

}
