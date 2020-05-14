package com.xworks.commonmodule.service;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.xworks.commonmodule.dao.RegisterDAO;
import com.xworks.commonmodule.dto.RegisterDTO;
import com.xworks.commonmodule.entity.RegisterEntity;

@Component
public class ServiceRegisterImpl implements ServiceRegister {

	private static final Logger log = Logger.getLogger(ServiceRegisterImpl.class);

	@Autowired
	private MailSender mail;

	@Autowired
	private RegisterDAO registerDAO;

	@Autowired
	private RandomPassGenerator randomPassGenerator;

	public ServiceRegisterImpl() {
		// System.out.println("created: " + this.getClass().getSimpleName());
		log.info("created" + this.getClass().getSimpleName());
	}

	public String validateUser(RegisterDTO registerDTO, Model model) {

		try {

			log.info("under validateUser method");

			RegisterEntity registerEntity = new RegisterEntity();
			log.info("here registerDTO objects are send to registerEntity");
			BeanUtils.copyProperties(registerDTO, registerEntity);

			if (registerDTO.getEntry().equals("no")) {
				model.addAttribute("agree", "  Please agree Terms to proceed");
				log.info("please agree to terms and conditions and proceed");
				return "register";
			}

			else if ((registerDAO.validateUserID(registerDTO, model)) != null) {
				log.info("THIS IS TO CHECK WHETHER UserID EXISTANCE SAME OR NO");
				model.addAttribute("existingUser", "user ID already exist try with another");
				return "register";
			} else if ((registerDAO.validateEmail(registerDTO, model)) != null) {
				log.info("THIS IS TO CHECK WHETHER EMAIL EXISTANCE SAME OR NO");
				model.addAttribute("existingEmail", "email already exist try with another");
				return "register";

			} else

				log.info("all the feilds are valid and ready to save in DB");

			String decodePassword = this.randomPassGenerator.passGenerator();
			log.info("random password from randomPassGenerator" + decodePassword);

			BCryptPasswordEncoder toEncoded = new BCryptPasswordEncoder();
			String passEncoded = toEncoded.encode(decodePassword);

			log.info("encoded pass :   " + passEncoded);

			registerEntity.setPassword(passEncoded);
			registerEntity.setNoOfAttempts(0);
			registerEntity.setDecodedPass(decodePassword);

			// to send credentials to the registered mail

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(registerDTO.getEmail());
			log.info(mailMessage.getTo() + ": mail id to which details are sent");

			mailMessage.setSubject("Thanks for your Interest :" + registerDTO.getUser_name());
			log.info(mailMessage.getSubject() + ": subject which is been added");

			mailMessage.setText("Dear customer below are the credentials to log in :\n\n" + "User ID" + "       : "
					+ registerDTO.getUser_id() + "\n\nPassword" + "     : " + decodePassword + "\n" + "\t" + "\t"
					+ "(please do not share password with anyone)" + "\n\nMobile No" + "     : "
					+ registerDTO.getPh_no() + "\n\nCourse Chosen" + "   : " + registerDTO.getCourse());
			log.info(mailMessage.getText() + ":Deatails which are been sent");

			try {
				log.info("about to send MAIL:");
				mail.send(mailMessage);
			} catch (MailException e) {
				log.error(e.getMessage(), e);
			}

			model.addAttribute("user_id", registerEntity.getUser_id());
			model.addAttribute("email", registerEntity.getEmail());
			model.addAttribute("ph_no", registerEntity.getPh_no());
			model.addAttribute("course", registerEntity.getCourse());
			model.addAttribute("sent", "Password has been sent to the mail");
			System.out.println();
			log.info("passed to entity for hashed:" + passEncoded);
			log.info("passed to entity for normal:" + decodePassword);

			this.registerDAO.saveUser(registerEntity);
			return "userDetails";
		} catch (Exception e) {
			log.info("Exception occured while saving details into the dataBase :");
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String validateLogin(RegisterDTO registerDTO, Model model) {
		log.info("inside the Validate login under ServiceDAOImpl :" + this.getClass().getSimpleName());
		Integer attemptCount = 0;
		try {
			log.info("inside validateLogin under Login Service" + this.getClass().getSimpleName());
			RegisterEntity registerEntity = null;
			boolean isPassMatch = false;

			registerEntity = this.registerDAO.validateEmail(registerDTO, model);
			if (Objects.isNull(registerEntity)) {

				// THIS IS DONE PURPOSELY DUE TO PRIVACY
				model.addAttribute("Message", "Please check entered Email and Password ");
				log.info("\n");
				log.info("\tHERE ACTUALLY EMAIL ID DOESNT EXIST BUT DUE TO SECURITY REASON WE SHOW MESSAGE LIKE THIS");
				return "somethingWrong";
			}

			attemptCount = registerEntity.getNoOfAttempts();
			log.info("thsi is the login attempts from the DB :" + attemptCount);

			log.info("this is under service got from DAO registerEntity :" + registerEntity);
			System.out.println();

			log.info("details entered by USER :" + registerDTO);
			BCryptPasswordEncoder decoder = new BCryptPasswordEncoder();

			isPassMatch = decoder.matches(registerDTO.getPassword(), registerEntity.getPassword());
			log.info("value of isPassMatch in case of invalid email :" + isPassMatch);

			if (attemptCount < 3) {
				if (registerEntity.getEmail().equals(registerDTO.getEmail()) && isPassMatch) {
					log.info("entered email and passwrod is a Match");
					model.addAttribute("course", registerEntity.getCourse());
					model.addAttribute("email", registerEntity.getEmail());
					model.addAttribute("Message",
							"Hello, " + "'" + registerEntity.getUser_id() + "'" + " you have successfully logged in");
					System.out.println();
					log.info("course got from DB :" + registerEntity.getCourse());
					return "allow";
				} else if (registerEntity.getEmail() != registerDTO.getEmail()
						&& registerEntity.getPassword() != registerDTO.getPassword()) {

					log.info("inside validateLogin in ServiceDAOImpl with email and password..."
							+ registerDTO.getEmail() + "/t" + registerDTO.getPassword() + "/n");
					log.info("Check Email or Password......");

					attemptCount++;
					log.info("number of attempts :" + attemptCount);
					this.registerDAO.addAttempts(registerDTO.getEmail(), attemptCount);
					if (attemptCount == 2) {
						model.addAttribute("Message", "This is your last attempt your account will be blocked");
						return "somethingWrong";
					} else if (attemptCount == 3) {
						model.addAttribute("Message",
								"your account has been blocked please try resetting the password");
						return "somethingWrong";
					}
					model.addAttribute("Message", "Please check entered Email and Password ");
					return "somethingWrong";
				}

			}

			log.info("you have attempted more than 3 times..");
			model.addAttribute("Message", "You have made already 3 attempts, Please try resetting password");
			return null;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
