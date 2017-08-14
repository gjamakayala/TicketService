package com.g3.tktsvc.impl;

import java.util.Date;

import com.g3.tktsvc.TicketVenue;

public class TicketVenueBuilder {

	String venueName;
	Date venueDate;
	
	int numRows;
	int numSeatsPerRow;
	int reserveTimeLimitSecs;
	
	public TicketVenueBuilder(String venueName, Date venueDate) {
		this.venueName = venueName;
		this.venueDate = venueDate;
	}
	
	public TicketVenueBuilder numRows(int numRows) {
		this.numRows = numRows;
		return this;
	}
	public TicketVenueBuilder numSeatsPerRow(int numSeatsPerRow) {
		this.numSeatsPerRow = numSeatsPerRow;
		return this;
	}
	public TicketVenueBuilder reserveTimeLimitSecs(int reserveTimeLimitSecs) {
		this.reserveTimeLimitSecs = reserveTimeLimitSecs;
		return this;
	}

	public String getVenueName() {
		return venueName;
	}

	public Date getVenueDate() {
		return venueDate;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumSeatsPerRow() {
		return numSeatsPerRow;
	}

	public int getReserveTimeLimitSecs() {
		return reserveTimeLimitSecs;
	}
	
	TicketVenue createTicketVenue() {
		return new SimpleTicketVenue(this);
	}
	
}
