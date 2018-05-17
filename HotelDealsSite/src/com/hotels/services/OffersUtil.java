package com.hotels.services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.hotels.entities.Hotel;
import com.hotels.entities.MyHotelDeals;

public class OffersUtil {

	private static String offersUrlString = "https://offersvc.expedia.com/offers/v2/getOffers?scenario=deal-finder&page=foo&uid=foo&productType=Hotel"; 
	public static String destinationFormat = "country, city";

	public static MyHotelDeals getOffers() throws JsonParseException, JsonMappingException, IOException {
		URL offersUrl = new URL(offersUrlString);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(offersUrl, MyHotelDeals.class);
	}

	public static List<Hotel> getHotelsOffers() throws JsonParseException, JsonMappingException, IOException {
		List<Hotel> hotels = new ArrayList<Hotel>();
		MyHotelDeals myHotelDeals = getOffers();
		hotels = myHotelDeals.getOffers().getHotel();
		return hotels;
	}
	public static JSONObject getHotelData(String regionId, Hotel hotel) throws JSONException {
		JSONObject hotelData = new JSONObject();
		hotelData.put("hotelId", hotel.getHotelInfo().getHotelId());
		hotelData.put("hotelName", hotel.getHotelInfo().getHotelName());
		hotelData.put("city", hotel.getHotelInfo().getHotelCity());
		hotelData.put("country", hotel.getHotelInfo().getHotelCountryCode());
		hotelData.put("imageUrl", hotel.getHotelInfo().getHotelImageUrl());
		hotelData.put("province", hotel.getHotelInfo().getHotelProvince());
		hotelData.put("guestsRating", hotel.getHotelInfo().getHotelGuestReviewRating());
//		hotelData.put("lengthOfStay", hotel.getOfferDateRange().getLengthOfStay());
		
		hotelData.put("offerDateRange", hotel.getOfferDateRange()); //has start date range + lengthOfStay
		
		hotelData.put("hotelPricingInfo", hotel.getHotelPricingInfo());

		return hotelData;
	}


}
