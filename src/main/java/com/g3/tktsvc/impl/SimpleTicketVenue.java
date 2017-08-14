package com.g3.tktsvc.impl;

import com.g3.tktsvc.TicketService;
import com.g3.tktsvc.TicketVenue;

public class SimpleTicketVenue extends TicketVenue {

	protected SimpleTicketVenue(TicketVenueBuilder venueBuilder) {
		super(venueBuilder);
	}
	
	@Override
	public TicketService getTicketService() {
		return new SimpleTicketService(this);
	}
}
