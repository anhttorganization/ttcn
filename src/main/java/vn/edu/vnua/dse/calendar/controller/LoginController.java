package vn.edu.vnua.dse.calendar.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vnua.dse.calendar.common.AppUtils;
import vn.edu.vnua.dse.calendar.model.User;
import vn.edu.vnua.dse.calendar.repository.UserRepository;
import vn.edu.vnua.dse.calendar.service.SecurityService;
import vn.edu.vnua.dse.calendar.service.UserDetailsServiceImpl;
import vn.edu.vnua.dse.calendar.service.UserService;

@Controller
public class LoginController {
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private UserService userService;
   @Autowired
   private SecurityService securityService;
   
   @RequestMapping(
      value = {"/register"},
      method = {RequestMethod.GET}
   )
   public String register(Model model) {
      model.addAttribute("user", new User());
      return "register";
   }

   @RequestMapping(
      value = {"/register"},
      method = {RequestMethod.POST}
   )
   public String register(@ModelAttribute("user") User user, Model model, RedirectAttributes ra, HttpServletRequest request) throws IOException, MessagingException {
      String email = user.getEmail().toLowerCase();
      if (this.userService.findByEmail(email) != null) {
         ra.addFlashAttribute("error", "Emai đã được đăng ký!");
         return "redirect:/register";
      } else {
         user.setEnabled(false);
         user.setCreateDate(new Date());
         user.setConfirmToken(UUID.randomUUID().toString());
         Properties prop = AppUtils.MyProperties("application.properties");
         String emailFrom = prop.getProperty("email.username");
         String pass = prop.getProperty("email.password");
         String appUrl = prop.getProperty("app.appURL");
         String confirmLink = appUrl + "/register-confirm?token=" + user.getConfirmToken();
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
         String html = "<!DOCTYPE html>\r\n<html>\r\n\t<head>\r\n\t\t<meta charset=\"UTF-8\">\r\n\t\t</head>\r\n\t\t<body>\r\n\t\t\t<div class=\"container\" style=\"text-align: center;background-color: #f0fbfa;padding: 10px 40px 10px 40px;\">\r\n\t\t\t\t<h1>Xác nhận email</h1>\r\n\t\t\t\t<p>Cảm ơn bạn đã tạo tài khoản trên \r\n\t\t\t\t\t<a href=\"%s\" id=\"stcalendar\" style=\"color: #007bff;text-decoration: none !important;\"><b>STCalendar</b></a>. Xác minh địa chỉ email của bạn đảm bảo rằng chỉ bạn mới có quyền truy cập vào thông tin tài khoản của mình. Để hoàn tất quá trình đăng ký, vui lòng nhấn vào nút dưới đây:\r\n\t\t\t\t</p>\r\n\t\t\t\t<a href=\"%s\" class=\"button\" onMouseOver=\"this.style.color='#0F0'\"   onMouseOut=\"this.style.color='#fff'\" style=\"text-align: center;display: inline-block;margin: 10px auto;padding: 10px 20px 10px 20px;color: white;background-color: #007bff;box-shadow: 2px 2px 2px grey;border-radius: 10px;text-decoration: none !important;\">Xác nhận</a>\r\n\t\t\t</div>\r\n\t\t</body>\r\n\t</html>\r\n";
         String htmlText = String.format(html, appUrl, confirmLink);
         MimeMessage message = sender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(message, true);
         helper.setTo(email);
         helper.setSubject("Xác nhận đăng ký tài khoản STCalendar");
         helper.setText(htmlText, true);
         sender.send(message);
         this.userService.init(user);
         ra.addFlashAttribute("sendmail", "Vui lòng kiểm tra gmail " + user.getEmail() + " và kích hoạt tài khoản!<br/><br/><button type=\"button\" class=\"btn clear\" id=\"ok_register\">OK</button>");
         return "redirect:/register";
      }
   }

   @RequestMapping(
      value = {"/register-confirm"},
      method = {RequestMethod.GET}
   )
   public String confirmRegistration(Model model, @RequestParam("token") String token, RedirectAttributes ra) {
      User user = this.userService.findByConfirmToken(token);
      if (user == null) {
         ra.addFlashAttribute("error", "Confirm token không hợp lệ. Xác nhận tài khoản không thành công!");
         return "redirect:/login";
      } else {
         if (!user.isEnabled()) {
            user.setEnabled(true);
         }

         user.setConfirmToken((String)null);
         this.userService.save(user);
         ra.addFlashAttribute("success", "Kích hoạt tài khoản thành công!");
         return "redirect:/login";
      }
   }

   @RequestMapping(
      value = {"/change_password"},
      method = {RequestMethod.GET}
   )
   public String changePassword(Model model) {
      return "change_password";
   }

   @RequestMapping(
      value = {"/change_password"},
      method = {RequestMethod.POST}
   )
   public String loadchangePassword(Model model, @RequestParam String oldPass, @RequestParam String newPass) {
      User user = UserDetailsServiceImpl.getUser();
      if (user == null) {
         model.addAttribute("invalidToken", "Oops!");
         return "change_password";
      } else if (!this.userService.checkIfValidOldPassword(user, oldPass)) {
         model.addAttribute("invalidToken", "Lỗi! Mật khẩu cũ không đúng");
         return "change_password";
      } else {
         this.userService.changeUserPass(user, newPass);
         return "redirect:/home";
      }
   }

   @RequestMapping(
      value = {"/login"},
      method = {RequestMethod.GET}
   )
   public String login(Model model, String error, String logout, RedirectAttributes ra) {
      if (error != null) {
         ra.addFlashAttribute("error", "Tài khoản hoặc mật khẩu không chính xác");
         return "redirect:/login";
      } else if (logout != null) {
         ra.addFlashAttribute("success", "Tài khoản đã được đăng xuất");
         return "redirect:/login";
      } else {
         return "login";
      }
   }

   @RequestMapping(
      value = {"/home"},
      method = {RequestMethod.GET}
   )
   public String welcome(Model model) {
      return this.securityService.hasRole("ROLE_USER") ? "index" : "redirect:/admin/user";
   }

   @RequestMapping(
      value = {"/admin"},
      method = {RequestMethod.GET}
   )
   public String Index(Model model) {
      return "admin/user";
   }

   @RequestMapping(
      value = {"/fogot_password"},
      method = {RequestMethod.GET}
   )
   public String fogotPassword() {
      return "fogot_password";
   }

   @RequestMapping(
      value = {"/fogot_password"},
      method = {RequestMethod.POST}
   )
   public String fogotPassword(@RequestParam String email, Model model) throws IOException, MessagingException {
      User user = this.userRepository.findByEmailAndEnabled(email, true);
      if (user != null) {
         user.setConfirmToken(UUID.randomUUID().toString());
         Properties prop = AppUtils.MyProperties("application.properties");
         String emailFrom = prop.getProperty("email.username");
         String pass = prop.getProperty("email.password");
         String appUrl = prop.getProperty("app.appURL");
         String confirmLink = appUrl + "/reset-password?token=" + user.getConfirmToken();
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
         String html = "<!DOCTYPE html>\r\n<html>\r\n\t<head>\r\n\t\t<meta charset=\"UTF-8\">\r\n\t\t</head>\r\n\t\t<body>\r\n\t\t\t<div class=\"container\" style=\"text-align: center;background-color: #f0fbfa;padding: 10px 40px 10px 40px;\">\r\n\t\t\t\t<h1>Xác nhận đổi mật khẩu</h1>\r\n\t\t\t\t<p>Cảm ơn bạn đã sử dụng \r\n\t\t\t\t\t<a href=\"%s\" id=\"stcalendar\" style=\"color: #007bff;text-decoration: none !important;\"><b>STCalendar</b></a>. Chúng tôi nhận được yêu cầu đổi mật khẩu tài khoản trên STCalendar của bạn. Để hoàn tiến hành đổi mật khẩu, vui lòng nhấn vào nút dưới đây:\r\n\t\t\t\t</p>\r\n\t\t\t\t<a href=\"%s\" class=\"button\" onMouseOver=\"this.style.color='#0F0'\"   onMouseOut=\"this.style.color='#fff'\" style=\"text-align: center;display: inline-block;margin: 10px auto;padding: 10px 20px 10px 20px;color: white;background-color: #007bff;box-shadow: 2px 2px 2px grey;border-radius: 10px;text-decoration: none !important;\">Đổi mật khẩu</a>\r\n\t\t\t</div>\r\n\t\t</body>\r\n\t</html>\r\n";
         String htmlText = String.format(html, appUrl, confirmLink);
         MimeMessage message = sender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(message, true);
         helper.setTo(user.getEmail());
         helper.setSubject("Đổi mật khẩu STCalendar");
         helper.setText(htmlText, true);
         sender.send(message);
         model.addAttribute("sendmail", "Email xác nhận đổi mật khẩu đã được gửi tới email " + user.getEmail() + "<br/><br/><button type=\"button\" class=\"btn clear\" id=\"ok_register\">OK</button>");
         this.userService.save(user);
      } else {
         model.addAttribute("error", "Vui lòng nhập đúng email tài khoản!");
      }

      return "fogot_password";
   }

   @RequestMapping(
      value = {"/reset-password"},
      method = {RequestMethod.GET}
   )
   public String ResetPassword(Model model, @RequestParam("token") String token) {
      User user = this.userService.findByConfirmToken(token);
      if (user != null) {
         model.addAttribute("email", user.getEmail());
         return "reset_password";
      } else {
         model.addAttribute("error", "token không hợp lệ!");
         return "login";
      }
   }

   @RequestMapping(
      value = {"/reset-password"},
      method = {RequestMethod.POST}
   )
   public String ResetPassword(Model model, @RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes ra) {
      User user = this.userService.findByEmail(email);
      if (user != null) {
         this.userService.changeUserPass(user, password);
         user.setConfirmToken((String)null);
         ra.addFlashAttribute("success", "Thay đổi mật khẩu thành công!");
         this.userService.save(user);
         return "redirect:/login";
      } else {
         ra.addFlashAttribute("error", "Có lỗi xảy ra!");
         return "redirect:/login";
      }
   }
}
    