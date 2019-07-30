package vn.edu.vnua.dse.calendar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

//import org.mindrot.jbcrypt.BCrypt;
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
		registrationEmail.setFrom("ttcnmail@gmail.com");

		emailService.sendEmail(registrationEmail);

		model.addAttribute("confirmationMessage", "Email xác nhận đã được gửi tới email của bạn, vui lòng kiểm tra email " + user.getEmail());

		userService.init(user);// save and encode

		return "register";
	}

	// Process confirmation link
	@RequestMapping(value = "/register-confirm", method = RequestMethod.GET)
	public String confirmRegistration(Model model, @RequestParam("token") String token) {
		
		User user = userService.findByConfirmToken(token);

		if (user == null) { // No token found in DB
			model.addAttribute("invalidToken", true);
			return "login";
		} else { // Token found
			// Set user to enabled
			if(!user.isEnabled()) {
				user.setEnabled(true);
			}
			user.setConfirmToken(null);
			// Save user
			userService.save(user);
			model.addAttribute("success", true);
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
		//model.addAttribute("confirmationMessage", "Thay đổi mật khẩu thành công!");
		return "redirect:/home";
	}
	

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Tài khoản hoặc mật khẩu không chính xác");

		if (logout != null)
			model.addAttribute("message", "Tài khoản đã được đăng xuất");

		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String welcome(Model model) {
		if (securityService.hasRole(AppConstant.ROLE_USER)) {
			return "index";//trang chủ người dùng
		}
		return "redirect:/admin";
	}

		@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
		public String Index(Model model) {
			
			return "admin";
		}
	
	@RequestMapping(value= {"/fogot_password"}, method = RequestMethod.GET)
	public String fogotPassword() {
		return "fogot_password";
	}
	
	@RequestMapping(value= {"/fogot_password"}, method = RequestMethod.POST)
	public String fogotPassword(@RequestParam String email, Model model) throws IOException {
		//tim user
		User user = userService.findByEmail(email);
		if(user != null) {
			//set token
			user.setConfirmToken(UUID.randomUUID().toString());
			//gui email
			Properties prop = AppUtils.MyProperties(AppConstant.APLICATION_PRO);
			String appUrl = prop.getProperty("app.appURL");
			SimpleMailMessage registrationEmail = new SimpleMailMessage();
			registrationEmail.setTo(user.getEmail());// get mail
			registrationEmail.setSubject("Registration Confirmation");
			registrationEmail.setText("To confirm your e-mail address, please click the link below:\n" + appUrl
					+ "/reset-password?token=" + user.getConfirmToken());
			registrationEmail.setFrom("ttcnmail@gmail.com");
			
			emailService.sendEmail(registrationEmail);
			
			model.addAttribute("success", "Email xác nhận đã được gửi tới email " + user.getEmail());
			
			userService.save(user);// save and encode
		}else {
			model.addAttribute("message", "Vui lòng nhập đúng email tài khoản!");
		}
		
		return "fogot_password";
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.GET)
	public String ResetPassword(Model model, @RequestParam("token") String token) {
		
		User user = userService.findByConfirmToken(token);
		if(user != null) {
			model.addAttribute("email", user.getEmail() );
			
			return "reset_password";
		}
		return "login";
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public String ResetPassword(Model model,@RequestParam("email") String email, @RequestParam("password") String password) {
		
		User user = userService.findByEmail(email);
		if(user != null) {
			userService.changeUserPass(user, password);
			user.setConfirmToken(null);
			model.addAttribute("message", "Thay đổi mật khẩu thành công!");
			userService.save(user);
			return "login";
		}
		model.addAttribute("message", "Có lỗi xảy ra!");
		return "login";
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

}
