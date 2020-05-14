package com.xworks.commonmodule.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xworks.commonmodule.dto.RegisterDTO;
import com.xworks.commonmodule.service.ServiceRegister;

@Component
@RequestMapping("/")
public class RegisterController {

	private static final Logger log = Logger.getLogger(RegisterController.class);

	@Autowired
	private ServiceRegister serviceRegister;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String toRegister(Model model) {
		model.addAttribute("user", new RegisterDTO());
		return "index";

	}

	@RequestMapping(value = "/registerPage")
	public String fromHome(Model model) {
		model.addAttribute("user", new RegisterDTO());
		return "register";

	}

	@RequestMapping(value = "/registerSuccess", method = RequestMethod.POST)
	public String registerSuccess(@Valid @ModelAttribute("user") RegisterDTO registerDTO, BindingResult result,
			Model model) {

		if (result.hasFieldErrors("email") || result.hasFieldErrors("ph_no") || result.hasFieldErrors("user_name")
				|| result.hasFieldErrors("user_id") || result.hasFieldErrors("course")) {
			log.info("inside the errors: " + result.getErrorCount());
			return "register";
		}
		log.info("outsside the errors: " + result.getErrorCount());

		System.out.println();
		String returnValue = this.serviceRegister.validateUser(registerDTO, model);
		try {
			if (returnValue.equals("userDetails")) {
				log.info("invoking register from controller:");
				log.info("model attribute :" + registerDTO);
				model.addAttribute("Message",
						"Hello,  " + registerDTO.getUser_name() + "  you are now Registered successfuly");
				return "userDetails";
			}
			return "register";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;

	}

}
