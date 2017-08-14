package com.g3.tktsvc;

import junit.framework.TestCase;

public class SeatTest extends TestCase {

	Seat seat;
	public SeatTest(String name) {
		super(name);
		seat = new Seat(10, 15);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSeat() {
		seat = new Seat(10, 15);
		assertNotNull(seat);
	}

	public void testGetSeatInfo() {
		testSeat();
		seat.getSeatInfo();
	}

	public void testGetRowNum() {
		testSeat();
		assertEquals(seat.getRowNum(), 10);
	}

	public void testGetSeatNumInRow() {
		testSeat();
		assertEquals(seat.getSeatNumInRow(), 15);
	}

}
