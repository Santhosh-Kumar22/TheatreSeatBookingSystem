package com.theatre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeatBooking implements SeatBookingInterface, AdminOperations{
	
	public Connection connection;
	List<String> availableSeats;
	Set<String> totalSeats;

	
	public SeatBooking() {
		try {
			connection = DbConnection.getConnection();
		}catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		availableSeats = new ArrayList<String>();
		totalSeats = new HashSet<>();
	}
	
	// Method to show available seats
	@Override
	public void showAvailableSeats() throws SQLException {
		String query = "SELECT * from seats WHERE is_booked=false";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		
			while(resultSet.next()) {
				availableSeats.add(resultSet.getString("seat_number"));
				System.out.println(resultSet.getString("seat_number"));
			}
			if(availableSeats.isEmpty()) {
				System.out.println("No seats available.");
			}
			
		
	}
	
	// Method to book a seat
	@Override
	public void bookSeat(String seatNumber) throws SQLException {
		if(availableSeats.contains(seatNumber)) {
			String query = "UPDATE seats SET is_booked = true WHERE seat_number = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, seatNumber);
			
			int rowsUpdated = preparedStatement.executeUpdate();
			
			if(rowsUpdated > 0) {
				System.out.println("Seat booked sucessfully.");
				availableSeats.remove(seatNumber);
				
			}else {
				System.out.println("Seat is already booked.");
			}
		}else {
			System.out.println("There is no such seat.");
		}
		
	}
	
	
	// Method for admin to add a new seat
	@Override
	public void addSeat(String seatNumber) throws SQLException {
		String query = "INSERT into seats (seat_number, is_booked) values (?, false)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, seatNumber);
		preparedStatement.executeUpdate();
		
//		String query2 = "SELECT * from seats";
//		Statement statement = connection.createStatement();
//		ResultSet resultSet = statement.executeQuery(query2);
//		
//		while(resultSet.next()) {
			totalSeats.add(seatNumber);
			availableSeats.add(seatNumber);
//		}
		System.out.println("Seat " + seatNumber + " added successfully.");
	}
	
	@Override
	public void cancelSeat(String seatNumber) throws SQLException {
//		if(!availableSeats.contains(seatNumber)) {
			String query = "UPDATE seats SET is_booked = false WHERE seat_number = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, seatNumber);
			
			int rowsUpdated = preparedStatement.executeUpdate();
			
			if(rowsUpdated > 0) {
				System.out.println("Seat cancelled sucessfully.");
				availableSeats.add(seatNumber);
			}else {
				System.out.println("Seat cancellation failed.");
			}
//		}else {
//			System.out.println("There is no such seat.");
//		}
		
	}

	
	
}
