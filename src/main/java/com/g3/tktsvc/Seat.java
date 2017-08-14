package com.g3.tktsvc;

public class Seat {

	int rowNum;
	int seatNumInRow;
	
	public Seat(int rowNum, int seatNumInRow) {
		this.rowNum = rowNum;
		this.seatNumInRow = seatNumInRow;
	}
	public String getSeatInfo() {
		return "[Row: " + rowNum + " Seat: " + seatNumInRow + "] ";
	}
	
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getSeatNumInRow() {
		return seatNumInRow;
	}
	public void setSeatNumInRow(int seatNumInRow) {
		this.seatNumInRow = seatNumInRow;
	}
}
 