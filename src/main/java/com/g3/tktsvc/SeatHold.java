package com.g3.tktsvc;

import java.util.Date;
import java.util.List;

import com.g3.tktsvc.util.TicketServiceIdGenerator;

public class SeatHold {
	int seatHoldId;
	Date requestDateTime;
	
	int numSeats;
	String customerEmail;
	
	List<Seat> seats;
	boolean reserved;
	
	public SeatHold(int numSeats, String customerEmail) {
		this.customerEmail = customerEmail;
		this.numSeats = numSeats;
		//Need to find a better way to give int to hold seatHoldId
		seatHoldId = (int)TicketServiceIdGenerator.generateId();
		requestDateTime = new Date();
	}
	
	public boolean isReserved() {
		return reserved;
	}
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	
	public int getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}
	public Date getRequestDateTime() {
		return requestDateTime;
	}
	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	public int getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
}
