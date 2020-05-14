package com.xworks.commonmodule.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.log4j.Logger;

public class OTPGenerator {
	private Logger log = Logger.getLogger(OTPGenerator.class);

	public OTPGenerator() {
		log.info(this.getClass().getSimpleName() + ":created");
	}

	public String generateOTP() {

		StringBuilder otp = new StringBuilder();
		int length = 5;

		SecureRandom random = new SecureRandom();
		try {
			random = SecureRandom.getInstance(random.getAlgorithm());
			for (int i = 0; i < length; i++) {
				otp.append(random.nextInt(9));
			}
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		}
		System.out.println("Generated OTP :" + otp);

		return otp.toString();
	}

}
