package vn.edu.vnua.dse.calendar.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

//import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String register(@ModelAttribute("user") User user, Model model,RedirectAttributes ra,
			HttpServletRequest request) throws IOException, MessagingException {

		 if (userService.findByEmail(user.getEmail()) != null) {
	            ra.addFlashAttribute("error", "Emai đã được đăng ký!");
	            return "redirect:/register";
		 }
		// new user so we create user and send confirmation e-mail
		// Disable user until they click on confirmation link in email
		user.setEnabled(false);
		user.setCreateDate(new Date());
		// Generate random 36-character string token for confirmation link
		user.setConfirmToken(UUID.randomUUID().toString());
		//
		Properties prop = AppUtils.MyProperties(AppConstant.APLICATION_PRO);
		String emailFrom = prop.getProperty("email.username");
		String pass = prop.getProperty("email.password");
		String appUrl = prop.getProperty("app.appURL");
		String confirmLink = appUrl	+ "/register-confirm?token=" + user.getConfirmToken();
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
 
		sender.setUsername(emailFrom);
		sender.setPassword(pass);
 
        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.setProperty("mail.smtp.allow8bitmime", "true");
        props.setProperty("mail.smtps.allow8bitmime", "true");
		//mail html
        String html = "<!DOCTYPE html>\r\n" + 
        		"<html>\r\n" + 
        		"	<head>\r\n" + 
        		"		<meta charset=\"UTF-8\">\r\n" + 
        		"		</head>\r\n" + 
        		"		<body>\r\n" + 
        		"			<div class=\"container\" style=\"text-align: center;background-color: #f0fbfa;padding: 10px 40px 10px 40px;\">\r\n" + 
        		"				<h1>Xác nhận email</h1>\r\n" + 
        		"				<p>Cảm ơn bạn đã tạo tài khoản trên \r\n" + 
        		"					<a href=\"%s\" id=\"stcalendar\" style=\"color: #007bff;text-decoration: none !important;\"><b>STCalendar</b></a>. Xác minh địa chỉ email của bạn đảm bảo rằng chỉ bạn mới có quyền truy cập vào thông tin tài khoản của mình. Để hoàn tất quá trình đăng ký, vui lòng nhấn vào nút dưới đây:\r\n" + 
        		"				</p>\r\n" + 
        		"				<a href=\"%s\" class=\"button\" onMouseOver=\"this.style.color='#0F0'\"   onMouseOut=\"this.style.color='#fff'\" style=\"text-align: center;display: inline-block;margin: 10px auto;padding: 10px 20px 10px 20px;color: white;background-color: #007bff;box-shadow: 2px 2px 2px grey;border-radius: 10px;text-decoration: none !important;\">Xác nhận</a>\r\n" + 
        		"			</div>\r\n" + 
        		"		</body>\r\n" + 
        		"	</html>\r\n" + 
        		"";
        
        String htmlText = String.format(html, appUrl, confirmLink);
		
		MimeMessage message = sender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(user.getEmail());
		helper.setSubject("Xác nhận đăng ký tài khoản STCalendar");
		// use the true flag to indicate the text included is HTML
		helper.setText(htmlText, true);
		
		sender.send(message);
		//
		
		
		userService.init(user);// save and encode
		
		ra.addFlashAttribute("success", "Email xác nhận đã được gửi tới địa chỉ email " + user.getEmail() + "\nVui lòng kiểm tra email và xác nhận đăng ký tài khoản!") ;
		
		return "redirect:/register";
	}

	// Process confirmation link
	@RequestMapping(value = "/register-confirm", method = RequestMethod.GET)
	public String confirmRegistration(Model model, @RequestParam("token") String token, RedirectAttributes ra) {
		User user = userService.findByConfirmToken(token);

		if (user == null) { // No token found in DB
			ra.addFlashAttribute("error", "Confirm token không hợp lệ. Xác nhận tài khoản không thành công!");
			return "redirect:/login";
		} else { // Token found
			// Set user to enabled
			if(!user.isEnabled()) {
				user.setEnabled(true);
			}
			user.setConfirmToken(null);
			// Save user
			userService.save(user);
			ra.addFlashAttribute("success", "Xác nhận tài khoản thành công!");
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
		if (error != null) {
			model.addAttribute("error", "Tài khoản hoặc mật khẩu không chính xác");
		}
		
		if (logout != null) {
			model.addAttribute("success", "Tài khoản đã được đăng xuất");
		}

		return "login";
	}
	
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String welcome(Model model) {
		if (securityService.hasRole(AppConstant.ROLE_USER)) {
			return "index";//trang chủ người dùng
		}
		return "redirect:/admin/user";
	}

		@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
		public String Index(Model model) {
			
			return "admin/user";
		}
	
	@RequestMapping(value= {"/fogot_password"}, method = RequestMethod.GET)
	public String fogotPassword() {
		return "fogot_password";
	}
	
	@RequestMapping(value= {"/fogot_password"}, method = RequestMethod.POST)
	public String fogotPassword(@RequestParam String email, Model model) throws IOException, MessagingException {
		//tim user
		User user = userService.findByEmail(email);
		if(user != null) {
			//set token
			user.setConfirmToken(UUID.randomUUID().toString());

			Properties prop = AppUtils.MyProperties(AppConstant.APLICATION_PRO);
			String emailFrom = prop.getProperty("email.username");
			String pass = prop.getProperty("email.password");
			String appUrl = prop.getProperty("app.appURL");
			String confirmLink = appUrl	+ "/reset-password?token=" + user.getConfirmToken();
			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			
			sender.setHost("smtp.gmail.com");
			sender.setPort(587);
	 
			sender.setUsername(emailFrom);
			sender.setPassword(pass);
	 
	        Properties props = sender.getJavaMailProperties();
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.debug", "true");
	        props.setProperty("mail.smtp.allow8bitmime", "true");
	        props.setProperty("mail.smtps.allow8bitmime", "true");
			//mail html
	        String html = "<!DOCTYPE html>\r\n" + 
	        		"<html>\r\n" + 
	        		"	<head>\r\n" + 
	        		"		<meta charset=\"UTF-8\">\r\n" + 
	        		"		</head>\r\n" + 
	        		"		<body>\r\n" + 
	        		"			<div class=\"container\" style=\"text-align: center;background-color: #f0fbfa;padding: 10px 40px 10px 40px;\">\r\n" + 
	        		"				<h1>Xác nhận email</h1>\r\n" + 
	        		"				<p>Cảm ơn bạn đã sử dụng \r\n" + 
	        		"					<a href=\"%s\" id=\"stcalendar\" style=\"color: #007bff;text-decoration: none !important;\"><b>STCalendar</b></a>. Xác minh địa chỉ email của bạn đảm bảo rằng chỉ bạn mới có quyền đổi mật khẩu tài khoản của mình. Để hoàn tiến hành đổi mật khẩu, vui lòng nhấn vào nút dưới đây:\r\n" + 
	        		"				</p>\r\n" + 
	        		"				<a href=\"%s\" class=\"button\" onMouseOver=\"this.style.color='#0F0'\"   onMouseOut=\"this.style.color='#fff'\" style=\"text-align: center;display: inline-block;margin: 10px auto;padding: 10px 20px 10px 20px;color: white;background-color: #007bff;box-shadow: 2px 2px 2px grey;border-radius: 10px;text-decoration: none !important;\">Đổi mật khẩu</a>\r\n" + 
	        		"			</div>\r\n" + 
	        		"		</body>\r\n" + 
	        		"	</html>\r\n" + 
	        		"";
	        
	        String htmlText = String.format(html, appUrl, confirmLink);
			
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(user.getEmail());
			helper.setSubject("Đổi mật khẩu STCalendar");
			// use the true flag to indicate the text included is HTML
			helper.setText(htmlText, true);
			sender.send(message);
			
			model.addAttribute("success", "Email xác nhận đổi mật khẩu đã được gửi tới email " + user.getEmail());
			
			//userService.save(user);// save and encode
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
	public String ResetPassword(Model model,@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes ra) {
		
		User user = userService.findByEmail(email);
		if(user != null) {
			userService.changeUserPass(user, password);
			user.setConfirmToken(null);
			ra.addFlashAttribute("success", "Thay đổi mật khẩu thành công!");
			userService.save(user);
			return "redirect:/login";
		}
		ra.addFlashAttribute("error", "Có lỗi xảy ra!");
		return "redirect:/login";
	}
}
