package com.ucu.edu.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.ucu.edu.configuration.BulkSMSProperties;


public class SMSManager {
	
	private String username;
	
	private String password;
	
	private String countryCode;

	
    @Autowired
    public SMSManager(BulkSMSProperties bulkSMSProperties) {
        this.username = bulkSMSProperties.getUsername();
        this.password = bulkSMSProperties.getPassword();        
        this.countryCode = bulkSMSProperties.getCountryCode();        
   }
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SMSManager.class);

	static public String stringToHex(String s) {
		char[] chars = s.toCharArray();
		String next;
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			next = Integer.toHexString((int) chars[i]);
			// Unfortunately, toHexString doesn't pad with zeroes, so we have
			// to.
			for (int j = 0; j < (4 - next.length()); j++) {
				output.append("0");
			}
			output.append(next);
		}
		return output.toString();
	}

	public void sendSMS(String phone, String msg) {
		
		LOGGER.debug("Attempt to send SMS via BulkSMS with username: " + username);

		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        
		try {

            PhoneNumber phoneNumberProto = phoneUtil.parse(phone, countryCode);
            
            boolean isValidILNumber = phoneUtil.isPossibleNumber(phoneNumberProto);
            LOGGER.debug("is {} PossibleNumber: {}", phone, isValidILNumber);
            
            if (!isValidILNumber) {
            	LOGGER.debug("incorrect phone number. terminate process sending SMS");
            	throw new IllegalArgumentException("incorrect phone number for countryCode: " + countryCode);
            }

            LOGGER.debug("phone in INTERNATIONAL format: {}", phoneUtil.format(phoneNumberProto, PhoneNumberFormat.INTERNATIONAL));
            LOGGER.debug("phone in NATIONAL format: {}", phoneUtil.format(phoneNumberProto, PhoneNumberFormat.NATIONAL));
            LOGGER.debug("phone in E164 format: {}", phoneUtil.format(phoneNumberProto, PhoneNumberFormat.E164));
            
            
            PhoneNumberUtil.PhoneNumberType phoneType = phoneUtil.getNumberType(phoneNumberProto);
            
            if (phoneType.name() != "MOBILE") {    	
                LOGGER.debug("incorrect mobile number for countryCode: {}", countryCode);
            	throw new IllegalArgumentException("incorrect mobile number for countryCode: " + countryCode);
            }
            
            String msisdn = phoneUtil.format(phoneNumberProto, PhoneNumberFormat.E164);
           
            LOGGER.debug("msisdn: {}", msisdn);
            
			// Construct data
			String data = "";
			/*
			 * Note the suggested encoding for certain parameters, notably the
			 * username and password.
			 *
			 * Please remember that 16bit support is a route-specific feature.
			 * Please contact us if you need to confirm the status of a
			 * particular route.
			 *
			 * Also, mobile handsets only implement partial support for non-
			 * Latin characters in various languages and will generally only
			 * support languages of the area of their distribution. Please do
			 * not expect e.g. a handset sold in South America to display Arabic
			 * text.
			 */
			data += "username=" + URLEncoder.encode(username, "ISO-8859-1");
			data += "&password=" + URLEncoder.encode(password, "ISO-8859-1");
			data += "&message=" + stringToHex(msg);
			data += "&dca=16bit";
			data += "&want_report=1";
			data += "&msisdn=" + msisdn;

			// Send data
			// Please see the FAQ regarding HTTPS (port 443) and HTTP (port
			// 80/5567)
			URL url = new URL("https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");

			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				// Print the response output...
				// System.out.println(line);
				LOGGER.debug("SMSManager: " + line);
			}
			wr.close();
			rd.close();
		} catch (NumberParseException e) {
            LOGGER.debug("NumberParseException was thrown: {}", e.toString());
        } catch (Exception e) {
			e.printStackTrace();
		}
	}

}
