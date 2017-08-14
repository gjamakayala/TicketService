package com.g3.tktsvc;

import junit.framework.TestCase;

public class SeatHoldTest extends TestCase {
	SeatHold seatHold;
	
	public SeatHoldTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSeatHold() {
		seatHold = new SeatHold(5, "abc@gmail.com");
		assertNotNull(seatHold);
	}

	public void testIsReserved() {
		testSeatHold();
		assertFalse(seatHold.isReserved());
	}

	public void testGetSeatHoldId() {
		testSeatHold();
		assertTrue(seatHold.getSeatHoldId() > 0);
	}

}
