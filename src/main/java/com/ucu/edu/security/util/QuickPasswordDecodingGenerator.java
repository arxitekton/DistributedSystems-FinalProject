package com.ucu.edu.security.util;

import java.util.Scanner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * program generates the encoded password we used in DB for default admin (hardcode)
 */
public class QuickPasswordDecodingGenerator {
	
	public static void main(String[] args) {
	
		try(Scanner scanner = new Scanner(System.in)){
	        System.out.println("Please enter your text: ");
	        String password = scanner.nextLine();

	        System.out.println("You input from console: " + password);

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			System.out.println("After encode: ");
			System.out.println(passwordEncoder.encode(password));
		}
		
	}
}
