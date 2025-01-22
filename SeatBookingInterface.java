package com.theatre;

import java.sql.SQLException;

public interface SeatBookingInterface {
	void bookSeat(String seatId) throws SQLException;
	void cancelSeat(String seatNumber) throws SQLException;
	void showAvailableSeats() throws SQLException;
}
