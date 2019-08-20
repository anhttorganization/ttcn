package vn.edu.vnua.dse.calendar.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vnua.dse.calendar.model.User;
import vn.edu.vnua.dse.calendar.repository.UserRepository;
import vn.edu.vnua.dse.calendar.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Controller tra ve trang quan ly nguoi dung
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "admin/user", method = RequestMethod.GET)
	public String loadScreenManagermentUser(Model model) {
		String msgSucess = (String) model.asMap().get("msgSucess");
		String msg = (String) model.asMap().get("msg");
		List<User> listResult = new ArrayList<User>();
		try {
			listResult = userService.findAll();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		model.addAttribute("listResult", listResult);
		model.addAttribute("msgSucess", msgSucess);
		model.addAttribute("msg", msg);
		model.addAttribute("user", new User());
		return "admin/user";
	}

//	@RequestMapping(value = "admin/quan-ly-nguoi-dung/user.do", method = RequestMethod.POST)
//	public String doActions(@ModelAttribute User user, BindingResult result, @RequestParam String action,
//			ModelMap model) {
//		String work = action.toLowerCase().toString();
//
//		List<User> listUser = new ArrayList<User>();
//		if (work.equals("search") && !user.getFirstName().equals("")) {
//			listUser = userService.findByLastName(user.getFirstName());
//			model.addAttribute("listResult", listUser);
//		} else {
//			listUser = userService.findAll();
//			model.addAttribute("listResult", userService.findAll());
//		}
//		return "redirect:/admin/quan-ly-nguoi-dung";
//	}

	@RequestMapping(value = "admin/user/add", method = RequestMethod.POST)
	public String loadAddUser(Model model, @RequestParam String email, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String password, @RequestParam String role,
			final RedirectAttributes redirectAtribute) {
		User user = userService.findByEmail(email);

		if (user != null) {
			redirectAtribute.addFlashAttribute("msg", "Email đã được đăng ký tài khoản");
		} else {
			user = new User();
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEnabled(true);
			user.setCreateDate(new Date());

			userService.setRole(user, role);
			userService.changeUserPass(user, password);

			userService.save(user);
			redirectAtribute.addFlashAttribute("msgSuccess", "Thêm người dùng thành công");

		}
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "admin/user/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, final RedirectAttributes redirectAttrs) {
		long id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id").toString()) : 0;
		User user = null;
		user = userRepository.findById(id);

		if (user != null) {
			try {
				userRepository.delete(user);
				redirectAttrs.addFlashAttribute("msgSuccess", "Xóa thành công");
			} catch (Exception e) {
				redirectAttrs.addFlashAttribute("msg",
						"Có lỗi xảy ra, vui lòng liên hệ với quản trị viên để biết thêm chi tiết");
			}
		}
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "admin/user/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		long id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id").toString()) : 0;
		User user = null;
		user = userRepository.findById(id);

		if (user != null) {
			try {
				user.setEmail(request.getParameter("email"));
				user.setFirstName(request.getParameter("firstName"));
				user.setLastName(request.getParameter("lastName"));
				userService.setRole(user, request.getParameter("role"));

				userService.save(user);
				redirectAttributes.addFlashAttribute("msgSuccess", "Thay đổi thành công");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("msg",
						"Có lỗi xảy ra, vui lòng liên hệ với quản trị viên để biết thêm chi tiết");
			}
		}
		return "redirect:/admin/user";
	}
	
	@ResponseBody
	@RequestMapping(value = "admin/user/changeStatusUser", method = RequestMethod.POST)
	public boolean changeStatusUser(HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		long id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id").toString()) : 0;

		boolean status = request.getParameter("status") != null
				? "true".equals(request.getParameter("status")) ? false : true
				: false;
		User user = null;
		user = userRepository.findById(id);
		if (user != null) {
			try {
				user.setEnabled(status);
				userService.save(user);
				return  status;
			} catch (Exception e) {
				return status;
			}
		}
		return status;
	}
}