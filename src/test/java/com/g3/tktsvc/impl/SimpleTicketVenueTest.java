package com.g3.tktsvc.impl;

import java.util.Date;

import com.g3.tktsvc.TicketService;
import com.g3.tktsvc.TicketVenue;
import com.g3.tktsvc.util.Constants;
import com.g3.tktsvc.util.TicketServiceConfig;

import junit.framework.TestCase;

public class SimpleTicketVenueTest extends TestCase {

	TicketVenue ticketVenue;
	TicketService ticketService;
	int numRows = 10;
	int seatsPerRow = 15;
	int reserveTimeLimitSecs = 120;

	
	public SimpleTicketVenueTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		TicketServiceConfig config = TicketServiceConfig.getInstance();
		numRows = Integer.parseInt(config.getValue(Constants.LIT_ROW_LIMIT));
		seatsPerRow = Integer.parseInt(config.getValue(Constants.LIT_ROW_SEAT_LIMIT));
		reserveTimeLimitSecs = Integer.parseInt(config.getValue(Constants.LIT_RESERVE_TIME_LIMIT));
	
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetTicketService() {
		testSimpleTicketVenue();
		ticketService = ticketVenue.getTicketService();	
		assertNotNull(ticketService);
	}

	public void testSimpleTicketVenue() {
		ticketVenue = new TicketVenueBuilder("ABC Concert", new Date())
				.numRows(numRows)
				.numSeatsPerRow(seatsPerRow)
				.reserveTimeLimitSecs(reserveTimeLimitSecs)
				.createTicketVenue();
		
		assertNotNull(ticketVenue);
	}
}
