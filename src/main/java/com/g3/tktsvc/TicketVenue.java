package com.g3.tktsvc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.g3.tktsvc.impl.TicketVenueBuilder;
import com.g3.tktsvc.util.TicketServiceIdGenerator;

public abstract class TicketVenue {
	static final Logger logger = LogManager.getLogger(TicketVenue.class.getName());

	protected long venueId;
	protected String venueName;
	protected Date venueDate;
	
	protected int numRows;
	protected int numSeatsPerRow;
	//reserve seat time limit in seconds 
	protected int reserveTimeLimitSecs;
	
	protected List<BitSet> seatList;
	protected int seatsAvailable;
	
	//map to hold seat reservations pending or confirmed
	Map<Integer, SeatHold> seatHoldMap = new HashMap<Integer, SeatHold>();


	/**
	 * Defers to subclasses to provide specific TicketService implementation
	 * @return TicketService subclass provided specific TicketService
	 */
	public abstract TicketService getTicketService();
	

	protected TicketVenue(TicketVenueBuilder venueBuilder) {
		
		venueId = TicketServiceIdGenerator.generateId();
		venueName = venueBuilder.getVenueName();
		venueDate = venueBuilder.getVenueDate();
		numRows = venueBuilder.getNumRows();
		numSeatsPerRow = venueBuilder.getNumSeatsPerRow();
		reserveTimeLimitSecs = venueBuilder.getReserveTimeLimitSecs();
		
		seatList = new ArrayList<BitSet>(numRows);
		
		//add configured number of BitSet rows to seatList. 
		//Each BitSet holds flags for the seats in that row.
		for (int row=0; row< numRows; row++) {
			seatList.add(new BitSet(numSeatsPerRow));
		}
		//initialize with total seats
		seatsAvailable = numRows * numSeatsPerRow;
	}	

	/**
	* Display all seats for a TicketVenue
	*
	*/
	public void displayVenueSeats() {
		logger.debug("Display of venue seats...");
		StringBuffer buf = new StringBuffer();
		for (int i=0; i<seatList.size(); i++) {
			BitSet row = seatList.get(i);
			buf.append("Row ").append(i+1).append(": ");
			for (int j=0; j < numSeatsPerRow; j++) {
				if (row.get(j)) {
					buf.append(" * ");  
				} else {
					buf.append(" ").append(j+1).append(" ");
				}
			}
			logger.debug(buf.toString());
			buf.delete(0, buf.length());
		}
	}
	
	/**
	* Display seats held or reserved for a customer
	* @param seatHoldId the seat hold identifier
	*/
	public void displayAssignedSeats(int seatHoldId) {
		SeatHold seatHold = seatHoldMap.get(seatHoldId);
		if (seatHold == null) {
			logger.debug("seatHoldId: " + seatHoldId + " not found.  Please checck.");
			return;
		}
		StringBuffer buf = new StringBuffer();
		for (Seat seat : seatHold.getSeats()) {
			buf.append(seat.getSeatInfo());
		}
		logger.debug("Assigned seats with seatHoldId: " + seatHoldId);
		logger.debug("--------------------");
		logger.debug(buf.toString());
		logger.debug("--------------------");
	}

	public long getVenueId() {
		return venueId;
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

	public List<BitSet> getSeatList() {
		return seatList;
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public Map<Integer, SeatHold> getSeatHoldMap() {
		return seatHoldMap;
	}

	public void setSeatList(List<BitSet> seatList) {
		this.seatList = seatList;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	public void setSeatHoldMap(Map<Integer, SeatHold> seatHoldMap) {
		this.seatHoldMap = seatHoldMap;
	}

}


 