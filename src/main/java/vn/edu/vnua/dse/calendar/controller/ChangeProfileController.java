package vn.edu.vnua.dse.calendar.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vnua.dse.calendar.model.User;
import vn.edu.vnua.dse.calendar.repository.UserRepository;
import vn.edu.vnua.dse.calendar.service.UserDetailsServiceImpl;
import vn.edu.vnua.dse.calendar.service.UserService;

@Controller
public class ChangeProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "change_profile", method = RequestMethod.GET)
	public String loadChangeprofile(Model model) {
		User user = UserDetailsServiceImpl.getUser();
		model.addAttribute("user", user);
		return "change_profile";
	}

	@RequestMapping(value = "change_profile", method = RequestMethod.POST)
	public String changrProfile(Model model) {
		User user = UserDetailsServiceImpl.getUser();

		return "change_profile";
	}

	@RequestMapping(value = "change_profile/email/update", method = RequestMethod.POST)
	public String changeEmail(HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		long id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id").toString()) : 0;
		User user = null;
		user = userRepository.findById(id);

		if (user != null) {
			try {
				User u = userRepository.findByEmail(request.getParameter("email"));
				if (u == null) {
					//Gửi email xác nhận,xác nhận xong thì lưu lại
				} else {
					redirectAttributes.addFlashAttribute("msg", "Emai đã được đăng ký!");
				}
//				user.setEmail(request.getParameter("email"));
//				userService.save(user);
//				redirectAttributes.addFlashAttribute("msgSuccess", "Thay đổi thành công");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("msg",
						"Có lỗi xảy ra, vui lòng liên hệ với quản trị viên để biết thêm chi tiết");
			}
		}
		return "redirect:/change_profile";
	}
	@RequestMapping(value = "change_profile/firstname/update", method = RequestMethod.POST)
	public String changeFirstName(HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		long id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id").toString()) : 0;
		User user = null;
		user = userRepository.findById(id);

		if (user != null) {
			try {
				user.setFirstName(request.getParameter("firstName"));

				userService.save(user);
				redirectAttributes.addFlashAttribute("msgSuccess", "Thay đổi thành công");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("msg",
						"Có lỗi xảy ra, vui lòng liên hệ với quản trị viên để biết thêm chi tiết");
			}
		}
		return "redirect:/change_profile";
	}
	
	@RequestMapping(value = "change_profile/lastname/update", method = RequestMethod.POST)
	public String changeLastName(HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		long id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id").toString()) : 0;
		User user = null;
		user = userRepository.findById(id);
			if (user != null) {
				try {
					user.setLastName(request.getParameter("lastName"));
					userService.save(user);
					redirectAttributes.addFlashAttribute("msgSuccess", "Thay đổi thành công");
				} catch (Exception e) {
					redirectAttributes.addFlashAttribute("msg",
							"Có lỗi xảy ra, vui lòng liên hệ với quản trị viên để biết thêm chi tiết");
				}
			}
		return "redirect:/change_profile";
	}
}
