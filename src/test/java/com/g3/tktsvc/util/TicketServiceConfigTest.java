package com.g3.tktsvc.util;

import junit.framework.TestCase;

public class TicketServiceConfigTest extends TestCase {

	TicketServiceConfig config;
	
	public TicketServiceConfigTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetInstance() {
		config = TicketServiceConfig.getInstance();
		assertNotNull(config);
	}

	public void testGetValue() {
		testGetInstance();
		int numRows = Integer.parseInt(config.getValue(Constants.LIT_ROW_LIMIT));
		assertTrue(numRows > 0);
		int seatsPerRow = Integer.parseInt(config.getValue(Constants.LIT_ROW_SEAT_LIMIT));
		assertTrue(seatsPerRow > 0);
		int reserveTimeLimitSecs = Integer.parseInt(config.getValue(Constants.LIT_RESERVE_TIME_LIMIT));
		assertTrue(reserveTimeLimitSecs > 0);
		
	}

}
