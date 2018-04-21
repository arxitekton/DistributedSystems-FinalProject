package com.ucu.edu.util;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PlayPhoneNumber {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayPhoneNumber.class);

	public static void main(String[] args) {
		
		String countryCode = "UA";
		
		
		try(Scanner scanner = new Scanner(System.in)){
	        System.out.println("Please enter phone: ");
	        String phone = scanner.nextLine();

	        System.out.println("You phone from console: " + phone);
	        
	        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
	        
	        try {
	        	
	            PhoneNumber phoneNumberProto = phoneUtil.parse(phone, countryCode);
	            
	            boolean isValidUANumber = phoneUtil.isPossibleNumber(phoneNumberProto);
	            LOGGER.debug("is Valid Number: {}", isValidUANumber);
	            
	            if (!isValidUANumber) {
	            	LOGGER.debug("incorrect phone number. terminate process sending SMS");
	            	throw new IllegalArgumentException("incorrect phone number for countryCode: " + countryCode);
	            }

	            LOGGER.debug("phone in INTERNATIONAL format: {}", phoneUtil.format(phoneNumberProto, PhoneNumberFormat.INTERNATIONAL));
	            LOGGER.debug("phone in NATIONAL format: {}", phoneUtil.format(phoneNumberProto, PhoneNumberFormat.NATIONAL));
	            LOGGER.debug("phone in E164 format: {}", phoneUtil.format(phoneNumberProto, PhoneNumberFormat.E164));
	            
	            
	            PhoneNumberUtil.PhoneNumberType phoneType = phoneUtil.getNumberType(phoneNumberProto);
	            
	            if (phoneType.name() != "MOBILE") {
	            	throw new IllegalArgumentException("incorrect mobile number for countryCode: " + countryCode);
	            }
	            
	            String msisdn = phoneUtil.format(phoneNumberProto, PhoneNumberFormat.E164);
            
	            LOGGER.debug("msisdn: {}", msisdn);
		              
	        } catch (NumberParseException e) {
	            System.err.println("NumberParseException was thrown: " + e.toString());
	        }
	        
			
			
		}
	}
}
