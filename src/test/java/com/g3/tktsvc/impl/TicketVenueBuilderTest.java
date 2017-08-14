package com.g3.tktsvc.impl;

import java.util.Date;

import com.g3.tktsvc.TicketVenue;

import junit.framework.TestCase;

public class TicketVenueBuilderTest extends TestCase {
	TicketVenue ticketVenue;
	TicketVenueBuilder builder;
	
	public TicketVenueBuilderTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testTicketVenueBuilder() {
		builder = new TicketVenueBuilder("ABC Concert", new Date())
				.numRows(10)
				.numSeatsPerRow(15)
				.reserveTimeLimitSecs(120);
		
		assertNotNull(builder);
	}

	public void testCreateTicketVenue() {
		testTicketVenueBuilder();
		ticketVenue = builder.createTicketVenue();
		assertNotNull(ticketVenue);
	}

}
