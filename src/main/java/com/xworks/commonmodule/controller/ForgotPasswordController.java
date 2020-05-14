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
import com.xworks.commonmodule.service.ForgotPasswordService;

@Component
@RequestMapping("/")
public class ForgotPasswordController {

	private static final Logger log = Logger.getLogger(RegisterController.class);

	@Autowired
	private ForgotPasswordService forgotPasswordService;

	public ForgotPasswordController() {
		log.info("Invoked Controller :" + this.getClass().getSimpleName());
	}

	@RequestMapping("/forgot")
	public String toResetPage(Model model) {
		model.addAttribute("user", new RegisterDTO());
		return "forgot";
	}

	@RequestMapping(value = "/forgotPass", method = RequestMethod.POST)
	public String resettingPassword(@Valid @ModelAttribute("user") RegisterDTO registerDTO, BindingResult result,
			Model model) {
		log.info("inside the controller");
		if (result.hasFieldErrors("email")) {

			log.info("inside the errors :");
			return "forgot";
		}
		log.info("outsside the errors: " + result.getErrorCount());

		String resetRequired = this.forgotPasswordService.validateUserForPasswordReset(registerDTO, model);
		try {
			if ("doneReset".equals(resetRequired)) {
				log.info("going forward to validate and register :");
				log.info("model attribute: " + registerDTO);
				model.addAttribute("restMessage",
						"Password reset successfuly and One Time Password has been sent to registered Email adress, please set the new Password");
				return "newDetails";

			} else if ("checkEmail".equals(resetRequired)) {
				log.info("wrong email address: ");
				model.addAttribute("wrongEmail", "Email entered is not present");
				return "forgot";
			}
			return "forgot";
		} catch (Exception e) {
			log.info("found exception in Forgot Password module...");
			log.error(e.getMessage(), e);
		}
		return null;

	}

	@RequestMapping("/reset")
	public String userResetPage(Model model) {
		model.addAttribute("reset", new RegisterDTO());
		return "reset";
	}

	@RequestMapping("/setPass")
	public String userResetDone(Model model) {
		model.addAttribute("reset", new RegisterDTO());
		return "reset";
	}

	@RequestMapping(value = "/setPass", method = RequestMethod.POST)
	public String setUserPassword(@Valid @ModelAttribute("reset") RegisterDTO registerDTO, BindingResult result,
			Model model) {
		log.info("inside the setPassword controller..");
		if (result.hasFieldErrors("email") || result.hasFieldErrors("password")
				|| result.hasFieldErrors("newPassword")) {

			log.info("inside the errors: " + result.getErrorCount());
			// log.info("inside the errors: " + result.getAllErrors());
			return "reset";
		}
		log.info("outsside the errors: " + result.getErrorCount());

		String value = this.forgotPasswordService.setNewPassword(registerDTO, model);
		try {
			if ("userPasswordSet".equals(value)) {
				model.addAttribute("set", "password has been reset, you can now log-in using new password");
				return "login";
			} else if ("invalidPassword".equals(value)) {
				model.addAttribute("invalidPass",
						"The password you have entered is invalid, please try with correct password");
				return "reset";
			} else
				model.addAttribute("invalidEmail", "Please enter valid email id");
			return "reset";
		} catch (Exception e) {
			log.info("found exception in Password set...");
			log.error(e.getMessage(), e);
		}
		return null;

	}

}
