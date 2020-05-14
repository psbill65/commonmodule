package com.xworks.commonmodule.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<IsValidPassword, String> {

	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
	private Pattern pattern;
	private Matcher matcher;

	public PasswordValidator() {
		 pattern = Pattern.compile(PASSWORD_PATTERN);
	}

	@Override
	public void initialize(IsValidPassword isValidPassword) {
		isValidPassword.message();
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext cxt) {
		if (password == null) {
			return false;
		}
		matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
