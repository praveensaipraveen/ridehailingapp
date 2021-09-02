package com.request.response;

import java.util.*;

import com.models.AvailableRide;

public class SearchRideResponse extends Response {
	public List<AvailableRide> ridesAvailable;
	public long bookingId;
}
