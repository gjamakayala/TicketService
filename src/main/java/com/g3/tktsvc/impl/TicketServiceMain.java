package com.g3.tktsvc.impl;

import java.util.Date;
import java.util.Scanner;

import javax.mail.internet.InternetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.g3.tktsvc.SeatHold;
import com.g3.tktsvc.TicketService;
import com.g3.tktsvc.TicketVenue;
import com.g3.tktsvc.util.Constants;
import com.g3.tktsvc.util.TicketServiceConfig;

public class TicketServiceMain {
	static final Logger logger = LogManager.getLogger(TicketServiceMain.class.getName());

	
	public static void main(String[] args) {

		TicketServiceConfig config = TicketServiceConfig.getInstance();
		int numRows = Integer.parseInt(config.getValue(Constants.LIT_ROW_LIMIT));
		int seatsPerRow = Integer.parseInt(config.getValue(Constants.LIT_ROW_SEAT_LIMIT));
		int reserveTimeLimitSecs = Integer.parseInt(config.getValue(Constants.LIT_RESERVE_TIME_LIMIT));

		TicketVenue ticketVenue = new TicketVenueBuilder("ABC Concert", new Date())
									.numRows(numRows)
									.numSeatsPerRow(seatsPerRow)
									.reserveTimeLimitSecs(reserveTimeLimitSecs)
									.createTicketVenue();
		
		TicketService ticketService = ticketVenue.getTicketService();
		
		Scanner input = new Scanner(System.in);

		while (true) {
			ticketVenue.displayVenueSeats();

			//read number of seats
			int numSeats = readNumSeats(input);
			
			if (numSeats < 1) {
				break;
			}
			
			//read customer email
			String customerEmail = readEmail(input);

			SeatHold seatHold = ticketService.findAndHoldSeats(numSeats, customerEmail);
			
			if (seatHold == null) {
				logger.debug("Unable to find seats at this time.  Please try later.");
				break;
			}
			ticketVenue.displayVenueSeats();
			ticketVenue.displayAssignedSeats(seatHold.getSeatHoldId());
			
			String confirmChoice = readConfirmChoice(input);
			if (confirmChoice.equalsIgnoreCase("Y")) {
				String reservationId = ticketService.reserveSeats(seatHold.getSeatHoldId(), customerEmail);
				if (reservationId != null) {
					logger.debug("Your reservation id is " + reservationId);
				}
			}
		}
		
		input.close();
		logger.debug("Thank you for using Ticket Service!");
	}
	
	private static int readNumSeats(Scanner input) {
		int numSeats = 0;
		boolean validEntry = false;
		logger.debug("Enter number of seats [0 to exit]: ");
		do {
			try {
				numSeats = Integer.parseInt(input.nextLine());
				validEntry = true;
			} catch (Exception ex) {
				logger.debug("Please enter a valid integer for number of seats: ");
			}
		} while (!validEntry);
		return numSeats;
	}

	private static String readEmail(Scanner input) {
		String customerEmail = null;
		boolean validEntry = false;
		logger.debug("Enter customer email: ");
		do {
			try {
				customerEmail = input.nextLine();
				InternetAddress emailAddr = new InternetAddress(customerEmail);
			    emailAddr.validate();
			    validEntry = true;
			} catch (Exception ex) {
				logger.debug("Please enter a valid customer email: ");
			}
		} while (!validEntry);
		return customerEmail;
	}

	private static String readConfirmChoice(Scanner input) {
		String confirmChoiceEntry = null;
		boolean validEntry = false;
		logger.debug("Confirm seats? [Y/N]: ");
		do {
			try {
				confirmChoiceEntry = input.nextLine();
				if (confirmChoiceEntry != null && 
						(confirmChoiceEntry.equalsIgnoreCase("Y") || confirmChoiceEntry.equalsIgnoreCase("N"))) {
					 validEntry = true;
				}
			   
			} catch (Exception ex) {
				logger.debug("Please enter a valid customer email: ");
			}
		} while (!validEntry);
		return confirmChoiceEntry;
	}	
}

