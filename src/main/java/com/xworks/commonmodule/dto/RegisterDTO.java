package com.xworks.commonmodule.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import com.xworks.commonmodule.validator.IsValidPassword;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Validated
public class RegisterDTO {

	@NotEmpty
	@Pattern(regexp = "[^0-9]+")
	private String user_name;

	@NotEmpty
	@Size(min = 3, max = 10)
	private String user_id;

	@NotEmpty
	@Email
	private String email;

	@Pattern(regexp = "(^$|[0-9]{10})")
	@NotEmpty
	private String ph_no;

	@NotEmpty
	private String course;

	@NotEmpty
	private String password;

	@NotEmpty
	@IsValidPassword
	private String newPassword;

	private String entry;

	public RegisterDTO() {
		System.out.println("created :" + this.getClass().getSimpleName());
	}

}
