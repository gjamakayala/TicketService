package com.g3.tktsvc.impl;

import java.util.Date;

import com.g3.tktsvc.SeatHold;
import com.g3.tktsvc.TicketService;
import com.g3.tktsvc.TicketVenue;
import com.g3.tktsvc.util.Constants;
import com.g3.tktsvc.util.TicketServiceConfig;

import junit.framework.TestCase;

public class SimpleTicketServiceTest extends TestCase {

	TicketServiceConfig config;
	int numRows;
	int seatsPerRow;
	int reserveTimeLimitSecs;
	
	TicketVenue ticketVenue;
	TicketService ticketService;
	SeatHold seatHold;
	
	public SimpleTicketServiceTest(String name) {
		super(name);
	}

	public void initApp() {
		ticketVenue = new TicketVenueBuilder("ABC Concert", new Date())
									.numRows(numRows)
									.numSeatsPerRow(seatsPerRow)
									.reserveTimeLimitSecs(reserveTimeLimitSecs)
									.createTicketVenue();
		
		ticketService = ticketVenue.getTicketService();
	}
	protected void setUp() throws Exception {
		super.setUp();
		
		config = TicketServiceConfig.getInstance();
		numRows = Integer.parseInt(config.getValue(Constants.LIT_ROW_LIMIT));
		seatsPerRow = Integer.parseInt(config.getValue(Constants.LIT_ROW_SEAT_LIMIT));
		reserveTimeLimitSecs = Integer.parseInt(config.getValue(Constants.LIT_RESERVE_TIME_LIMIT));

		initApp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testNumSeatsAvailable() {
		initApp();
		int availableTickets = ticketService.numSeatsAvailable();
		assertTrue(availableTickets > 0);
	}
	public void testNumSeatsAvailable_NegativeTest1() {
		//hold all seats; then check for available seats
		initApp();
		int numSeats = ticketService.numSeatsAvailable();
		seatHold = ticketService.findAndHoldSeats(numSeats, "abc@gmail.com");
		int availableTickets = ticketService.numSeatsAvailable();
		assertEquals(0, availableTickets);
	}
	public void testNumSeatsAvailable_NegativeTest2() {
		//Request more seats than available
		initApp();
		int numSeats = ticketService.numSeatsAvailable();
		seatHold = ticketService.findAndHoldSeats(numSeats+1, "abc@gmail.com");
		assertNull(seatHold);
	}

	public void testFindAndHoldSeats() {
		initApp();
		seatHold = ticketService.findAndHoldSeats(5, "abc@gmail.com");
		assertNotNull(seatHold);
		assertTrue(seatHold.getSeatHoldId() > 0);
	}

	public void testReserveSeats() {
		initApp();
		testFindAndHoldSeats();
		int seatHoldId = seatHold.getSeatHoldId();
		ticketService.reserveSeats(seatHoldId, "abc@gmail.com");
	}

}
