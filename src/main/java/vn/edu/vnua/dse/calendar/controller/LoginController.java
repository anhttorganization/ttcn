package vn.edu.vnua.dse.calendar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.vnua.dse.calendar.common.AppConstant;
import vn.edu.vnua.dse.calendar.common.AppUtils;
import vn.edu.vnua.dse.calendar.model.User;
import vn.edu.vnua.dse.calendar.service.EmailService;
import vn.edu.vnua.dse.calendar.service.SecurityService;
import vn.edu.vnua.dse.calendar.service.UserDetailsServiceImpl;
import vn.edu.vnua.dse.calendar.service.UserService;
import vn.edu.vnua.dse.calendar.validator.UserValidator;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("user", new User());

		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute("user") User user, BindingResult bindingResult, Model model,
			HttpServletRequest request) throws IOException {

		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "register";
		}

		// new user so we create user and send confirmation e-mail
		// Disable user until they click on confirmation link in email
		user.setEnabled(false);
		user.setCreateDate(new Date());
		// Generate random 36-character string token for confirmation link
		user.setConfirmToken(UUID.randomUUID().toString());

//		String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort()
//				+ "/ScheduleAndCalendar";
		Properties prop = AppUtils.MyProperties(AppConstant.APLICATION_PRO);
		String appUrl = prop.getProperty("app.appURL");
		SimpleMailMessage registrationEmail = new SimpleMailMessage();
		registrationEmail.setTo(user.getEmail());// get mail
		registrationEmail.setSubject("Registration Confirmation");
		registrationEmail.setText("To confirm your e-mail address, please click the link below:\n" + appUrl
				+ "/register-confirm?token=" + user.getConfirmToken());
		registrationEmail.setFrom("anhtt@gmail.com");

		emailService.sendEmail(registrationEmail);

		model.addAttribute("confirmationMessage", "Email xác nhận đã được gửi đến email của bạn " + user.getEmail());

		userService.init(user);// save and encode

		return "register";
	}

	// Process confirmation link
	@RequestMapping(value = "/register-confirm", method = RequestMethod.GET)
	public String confirmRegistration(Model model, @RequestParam("token") String token) {

		User user = userService.findByConfirmToken(token);

		if (user == null) { // No token found in DB
			model.addAttribute("invalidToken", "Oops!  This is an invalid confirmation link.");
			return "login";
		} else { // Token found
			// Set user to enabled
			user.setEnabled(true);
			user.setConfirmToken(null);
			// Save user
			userService.save(user);
		}

		return "redirect:/login";
	}
	
	@RequestMapping(value = {"/change_password"}, method = RequestMethod.GET)
	public String changePassword(Model model) {
		return "change_password";
	}
	
	@RequestMapping(value = {"/change_password"}, method = RequestMethod.POST)
	public String loadchangePassword(Model model, @RequestParam String oldPass, @RequestParam String newPass) {
		User user = UserDetailsServiceImpl.getUser();
		if(user == null) {
			model.addAttribute("invalidToken", "Oops!");
			return "change_password";
		}else {
			if(!userService.checkIfValidOldPassword(user, oldPass)) {
				model.addAttribute("invalidToken", "Lỗi! Mật khẩu cũ không đúng");
				return "change_password";
			}
			userService.changeUserPass(user, newPass);
		}
		model.addAttribute("confirmationMessage", "Thay đổi mật khẩu thành công!");
		return "change_password";
	}
	
//	// Process confirmation link
//	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
//	public ModelAndView confirmRegistration(ModelAndView modelAndView, BindingResult bindingResult,
//			@RequestParam Map<String, String> requestParams) {
//
//		modelAndView.setViewName("confirm");
//
////			Zxcvbn passwordCheck = new Zxcvbn();
//
////			Strength strength = passwordCheck.measure(requestParams.get("password"));
//
////			if (strength.getScore() < 3) {
////				//modelAndView.addObject("errorMessage", "Your password is too weak.  Choose a stronger one.");
////				bindingResult.reject("password");
////				
////				redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");
////
////				modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
////				System.out.println(requestParams.get("token"));
////				return modelAndView;
////			}
//
//		// Find the user associated with the reset token
//		User user = userService.findByConfirmToken(requestParams.get("token"));
//
//		// Set user to enabled
//		user.setEnabled(true);
//
//		// Save user
//		userService.save(user);
//
//		securityService.autologin(user.getEmail(), user.getPasswordConfirm());
//		modelAndView.addObject("successMessage", "Your password has been set!");
//		
//		return new ModelAndView("redirect:/home");
//	}

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String welcome(Model model) {
		if (securityService.hasRole(AppConstant.ROLE_USER)) {
			return "index";
		}
		return "admin";
	}

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String Index(Model model) {
		
		return "admin";
	}
	
	@RequestMapping(value= {"/fogot_password"}, method = RequestMethod.GET)
	public String fogotPassword(Model model) {
		return "fogot_password";
	}
	
	
	/**
	 * Controller tra ve trang quan ly nguoi dung
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "admin/quan-ly-nguoi-dung", method = RequestMethod.GET)
	public String loadScreenManagermentUser(Model model) {
		List<User> listResult = new ArrayList<User>();
		try {
			listResult = userService.findAll();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		model.addAttribute("listResult", listResult);
		return "admin/quan-ly-nguoi-dung";
	}

//	@RequestMapping(value = "/change_password", method = RequestMethod.GET)
//	public String changePassword(Model model) {
//		model.addAttribute("user", new User());
//
//		return "change_password";
//	}
}
