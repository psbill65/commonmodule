package com.xworks.commonmodule.dao;

import org.springframework.ui.Model;

import com.xworks.commonmodule.dto.RegisterDTO;
import com.xworks.commonmodule.entity.RegisterEntity;

public interface RegisterDAO {
	public boolean saveUser(RegisterEntity registerEntity);

	public RegisterEntity validateUserID(RegisterDTO registerDTO, Model model);

	public RegisterEntity validateEmail(RegisterDTO registerDTO, Model model);

	Integer addAttempts(String loginEmail, int noOfAttempts);

}
