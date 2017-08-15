package com.g3.tktsvc.impl;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.g3.tktsvc.Seat;
import com.g3.tktsvc.SeatHold;
import com.g3.tktsvc.TicketService;
import com.g3.tktsvc.TicketVenue;

public class SimpleTicketService implements TicketService {
 
	static final Logger logger = LogManager.getLogger(SimpleTicketService.class.getName());
	
	TicketVenue ticketVenue;

	SimpleTicketService(SimpleTicketVenue venue) {
		this.ticketVenue = venue;
	}
	
	/**
	* The number of seats in the venue that are neither held nor reserved
	*
	* @return the number of tickets available in the venue
	*/
	public int numSeatsAvailable() {
		return ticketVenue.getSeatsAvailable();
	}
	
	/**
	* Find and hold the best available seats for a customer
	*
	* @param numSeats the number of seats to find and hold
	* @param customerEmail unique identifier for the customer
	* @return a SeatHold object identifying the specific seats and related information
	*/
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		int seatsAvailable = numSeatsAvailable();
		
		if (seatsAvailable < 1 || seatsAvailable < numSeats) {	
			logger.debug("Sorry! Requested number of seats are not available at this time.");
			return null;
		}
		
		//reduce number of seats available by requested number of seats
		seatsAvailable -= numSeats;
		ticketVenue.setSeatsAvailable(seatsAvailable);
		
		//Initialize list to hold assigned seats
		List<Seat> assignedSeats = new ArrayList<Seat>();
		int assignedSeatsCnt = 0;
		
		List<BitSet> seatList = ticketVenue.getSeatList();
		
		for (int row = 0; row< seatList.size(); row++) {
			BitSet seatRow = seatList.get(row);
			int nextAvailableSeat = 0;
			while (assignedSeatsCnt < numSeats) {
				nextAvailableSeat = seatRow.nextClearBit(nextAvailableSeat);
				//If available seat exceeds the total number of seats in that row 
				//then move onto to check next row.
				if (nextAvailableSeat >= ticketVenue.getNumSeatsPerRow()) {
					break;
				}
				if (!seatRow.get(nextAvailableSeat)) {
					seatRow.set(nextAvailableSeat);
					//create available Seat and add it to assigned seats list.
					Seat seat = new Seat(row+1, nextAvailableSeat+1);
					assignedSeats.add(seat);
					//Increment assigned seats count
					assignedSeatsCnt++;
				}
			}
			//If requested number of seats are found then stop scanning more rows for seats.
			if (assignedSeatsCnt >= numSeats) {
				break;
			}
		}
		// create SeatHold and hold seats.
		SeatHold seatHold = new SeatHold(numSeats, customerEmail);
		seatHold.setSeats(assignedSeats);
		ticketVenue.getSeatHoldMap().put(seatHold.getSeatHoldId(), seatHold);
		
		//Schedule a timer task to run after the reserve time limit 
		//to release held seats if not reserved by then. 
		Timer timer = new Timer();
		TimerTask timerTask = new ReleaseSeatsTimerTask(seatHold.getSeatHoldId());
		timer.schedule(timerTask, ticketVenue.getReserveTimeLimitSecs() * 1000);
		
		return seatHold;
	}
	
	/**
	 * class ReleaseSeatsTimerTask provides seatHoldId to release seats
	 *
	 */
	class ReleaseSeatsTimerTask extends TimerTask {
		int seatHoldId;
		
		ReleaseSeatsTimerTask(int seatHoldId) {
			this.seatHoldId = seatHoldId;
		}
		@Override
		public void run() {
			logger.debug("Timer task attempting to release seats if held seats are not reserved with in the specified time limit.");
			releaseHeldSeats(seatHoldId);
			ticketVenue.displayVenueSeats();
			
		}
	}
	
	/**
	* Reserve seats held for a specific customer
	*
	* @param seatHoldId the seat hold identifier
	* @param customerEmail the email address of the customer to which the seat hold is assigned
	* @return a reservation confirmation code
	*/
	public String reserveSeats(int seatHoldId, String customerEmail) {
		//Retrieve SeatHold from map using seatHoldId
		SeatHold seatHold = ticketVenue.getSeatHoldMap().get(seatHoldId);
		if (seatHold == null || seatHold.isReserved()) {
			logger.debug("Invalid seatHoldId: " + seatHoldId + " or seats are already reserved.");
			return null;
		}
		
		if (isTicketSeatHoldExpired(seatHold)) {
			releaseHeldSeats(seatHoldId);
			logger.debug("Reserve time limit exceeded. Please retry again.");
			return null;
		}
		seatHold.setReserved(true);

		return String.valueOf(seatHold.getSeatHoldId());
	}
	
	/**
	 * Release held seats
	 * @param seatHoldId SeatHold Id
	 */
	private void releaseHeldSeats(int seatHoldId) {
		SeatHold seatHold = ticketVenue.getSeatHoldMap().get(seatHoldId);
		if (seatHold != null && !seatHold.isReserved()) {
			int numSeats = seatHold.getSeats().size();
			logger.debug("Releasing seats");
			for (Seat seat : seatHold.getSeats()) {
				BitSet seatRow = ticketVenue.getSeatList().get(seat.getRowNum()-1);
				seatRow.clear(seat.getSeatNumInRow()-1);
			}
			ticketVenue.getSeatHoldMap().remove(seatHold.getSeatHoldId());
			ticketVenue.setSeatsAvailable(ticketVenue.getSeatsAvailable() + numSeats);
		}
	}
	
	/**
	 * Checks if seats held exceeded reserve time limit
	 * @param seatHold
	 * @return
	 */
	private boolean isTicketSeatHoldExpired(SeatHold seatHold) {
		return ((new Date().getTime() - seatHold.getRequestDateTime().getTime()) > (ticketVenue.getReserveTimeLimitSecs() * 1000));
	}
	
}
