package com.xworks.commonmodule.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xworks.commonmodule.dto.RegisterDTO;
import com.xworks.commonmodule.service.ServiceRegister;

@Component
@RequestMapping("/")
public class LoginController {
	private static final Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	private ServiceRegister serviceRegister;

	public LoginController() {
		log.info("invoked Controller :" + this.getClass().getSimpleName());
	}

	@RequestMapping(value = "/loginPage")
	public String toLogin(Model model) {
		model.addAttribute("user", new RegisterDTO());
		return "login";

	}

	@RequestMapping(value = "/loginSuccess", method = RequestMethod.POST)
	public String loginSuccess(@Valid @ModelAttribute("user") RegisterDTO registerDTO, BindingResult result,
			Model model) {

		if (result.hasFieldErrors("email") || result.hasFieldErrors("password")) {

			log.info("inside the errors: " + result.getErrorCount());
			return "login";
		}
		log.info("outsside the errors: " + result.getErrorCount());

		String emailPasswordCheck = null;
		emailPasswordCheck = this.serviceRegister.validateLogin(registerDTO, model);
		log.info("value of emailCheck at controller: " + emailPasswordCheck);
		try {
			if ("allow".equals(emailPasswordCheck)) {
				log.info("invoking login:");
				log.info("model attribute: " + registerDTO);
				return "home";

			} else if ("somethingWrong".equals(emailPasswordCheck)) {
				log.info("either password or email is wrong...");
				return "login";

			} else
				log.info("3 attempts done...");
			return "login";

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;

	}

}
