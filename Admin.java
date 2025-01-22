package com.theatre;

import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
	private SeatBooking seatBooking;
	
	public Admin() {
		seatBooking = new SeatBooking();
	}
	
	public void addNewSeat() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the seat number to add: ");
		
		String seatNumber = scanner.nextLine();
		
		try {
			seatBooking.addSeat(seatNumber);
		}catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void viewAvailableSeats() {
		try {
			seatBooking.showAvailableSeats();
		}catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
}
