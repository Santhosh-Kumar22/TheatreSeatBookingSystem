package com.theatre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TheatreSeatBookingSystem {
	
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		displayMenu();
	}
	
	public static void displayMenu() throws SQLException {
		System.out.println("Welcome to Theatre Seat Booking System");
		
		System.out.println("1. User");
		System.out.println("2. Admin");
		System.out.println("Choose Role: ");
		
		int choice = scanner.nextInt();
		
		if(choice == 1) {
			User user = new User();
			boolean log = true;
			while(log) {
				System.out.println("\n User Menu: ");
				System.out.println("1. Login");
				System.out.println("2. Register");
				System.out.println("\n Choose Options: ");

				
				int userChoice = scanner.nextInt();
				
				if(userChoice == 1) {
					System.out.println("Please enter your username: ");
					String userName = scanner.next();
					System.out.println("Please enter your password: ");
					String pass = scanner.next();
					
//					Check for user credentials
					if(validateUserCredentials(userName, pass)) {
						while(true) {
							System.out.println("\n User Menu: ");
							System.out.println("1. View Available Seats");
							System.out.println("2. Book Seats");
							System.out.println("3. Cancel Seats");
							System.out.println("4. Exit");
							System.out.println("Choose Option: ");
							
							int userChoice1 = scanner.nextInt();
							
							if(userChoice1 == 1) {
								user.displayAvailableSeats();
							}else if(userChoice1 == 2) {
								user.bookSeat();
							}else if(userChoice1 == 3){
								user.cancelSeat();
							}else {
								break;
							}
						}
					}else {
						System.out.println("There is no such user. Please register.");
					}
					
				}else if(userChoice == 2) {
					System.out.println("Please enter your user name:");
					String registerUser = scanner.next();
					System.out.println("Please enter your password: ");
					String registerPassword = scanner.next();
					System.out.println("Please re-enter your password: ");
					String confirmPassword = scanner.next();

					if(registerPassword.equals(confirmPassword)) {
						
						if(registerUser(registerUser, registerPassword)) {
							System.out.println("User registered successfully");
						}else {
							System.out.println("user registration failed");
						}
					
					}else {
						System.out.println("Passwords do no match");
					}
					
				}else {
					log=false;
				}
			}
			
//			while(true) {
//				System.out.println("\n User Menu: ");
//				System.out.println("1. View Available Seats");
//				System.out.println("2. Book Seats");
//				System.out.println("3. Cancel Seats");
//				System.out.println("4. Exit");
//				System.out.println("Choose Option: ");
//				
//				int userChoice = scanner.nextInt();
//				
//				if(userChoice == 1) {
//					user.displayAvailableSeats();
//				}else if(userChoice == 2) {
//					user.bookSeat();
//				}else if(userChoice == 3){
//					user.cancelSeat();
//				}else {
//					break;
//				}
//			}
		}else if(choice == 2) {
			Admin admin = new Admin();
			boolean flag = true;
			while(flag) {
				
				System.out.println("\n Admin Menu: ");
				System.out.println("1. Please enter admin password: ");
				System.out.println("2. Exit");
				System.out.println("Choose Options: ");
				
				int adminChoice = scanner.nextInt();
				scanner.nextLine();
				
				if(adminChoice == 1) {
					System.out.println("\nPlease enter your password: ");
					String password = scanner.nextLine();
				
				
				if(validateAdminPassword(password)) {
					while(true) {
						System.out.println("\nAdmin Menu:");
                        System.out.println("1. Add New Seat");
                        System.out.println("2. View Available Seats");
                        System.out.println("3. Exit");
                        System.out.print("Choose option: ");
                        
                        int choice2 = scanner.nextInt();
                        scanner.nextLine();
                        
                        if(choice2 == 1) {
                        	admin.addNewSeat();
                        }else if(choice2 == 2) {
                        	admin.viewAvailableSeats();
                        }else {
                        	break;
                        }
					}
				}else {
					System.out.println("Invalid Password. Please try again.");
				}
				}else if(adminChoice == 2) {
					System.out.println("Exiting the program...");
					flag = false;
				}else {
					System.out.println("Invalid Choice. Please select a valid option.");
				}
				
//				Connection connection;
//				try {
//					String query = "select * from admindb where password = ?";
//					connection = DbConnection.getConnection();
//					PreparedStatement preparedStatement = connection.prepareStatement(query);
//					preparedStatement.setString(1, password);
//					ResultSet rows = preparedStatement.executeQuery();
//					
//					if(rows.next()) {
//						
//							System.out.println("1. Add New Seat");
//							System.out.println("2. View Available Seats");
//							System.out.println("3. Exit");
//							System.out.println("Choose option: ");
//						
//						
//
//					}
//
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
								
//				int adminChoice = scanner.nextInt();
//				
//				if(adminChoice == 1) {
//					admin.addNewSeat();
//				}else if(adminChoice == 2) {
//					admin.viewAvailableSeats();
//				}else {
//					break;
//				}
			}
		}
	}
	
	public static boolean validateAdminPassword(String password) {
		String query = "SELECT * FROM admindb WHERE password = ?";
		
		try (Connection connection = DbConnection.getConnection();  // Ensure you have this method implemented
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, password);
	            try (ResultSet rows = preparedStatement.executeQuery()) {
	                return rows.next(); 
	            }
	        } catch (SQLException e) {
	            System.out.println("Database error: " + e.getMessage());
	            e.printStackTrace();
	            return false; 
	        }
	}
	
	public static boolean validateUserCredentials(String userName, String password) throws SQLException {
		String query = "select * from users where userName = ? and password = ?";
		try (
			Connection connection = DbConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				return resultSet.next();
			}catch(SQLException e){
	            System.out.println("Database error: " + e.getMessage());
	            e.printStackTrace();
	            return false; 
	        }
		}
	}
	
	public static boolean registerUser(String userName, String password) throws SQLException {
		String query = "insert into users (userName, password) values(?,?)";
		
		try(
			Connection connection = DbConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			
			int rows = preparedStatement.executeUpdate();
				return rows > 0;
		}catch(SQLException e){
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
            return false; 
        }
	}
	
}
