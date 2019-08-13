package vn.edu.vnua.dse.calendar.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.vnua.dse.calendar.co.CustomUserDetails;
import vn.edu.vnua.dse.calendar.common.AppUtils;
import vn.edu.vnua.dse.calendar.ggcalendar.jsonobj.GoogleToken;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;
import vn.edu.vnua.dse.calendar.model.User;
import vn.edu.vnua.dse.calendar.repository.UserRepository;

@Controller
public class AuthorizationController {

	@Autowired
	APIWrapper aPIWrapper;

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/authorization", method = RequestMethod.GET)
	public String Authorization(@RequestParam Map<String, String> requestParams, Model model) throws IOException {
		// get auth code
		String code = requestParams.get("code");
		// get refresh token
	
		GoogleToken token = aPIWrapper.getRefreshToken(code);
		String accessToken = token.getAccess_token();
		String refreshToken = token.getRefresh_token();
		
		if(AppUtils.isNotNullOrEmpty(accessToken) && AppUtils.isNullOrEmpty(refreshToken)) { 
			//truong hop da cap quyen - da lay refresh token nhung het han hoac sai
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			String userEmail;
			if (principal instanceof CustomUserDetails) {
				userEmail = ((CustomUserDetails) principal).getEmail();
			} else {
				userEmail = "";
			}

			User user = userRepository.findByEmail(userEmail);
			user.setRefreshToken(null);
			userRepository.save(user);
			
			model.addAttribute("setPermision", "https://myaccount.google.com/permissions");
			return "index";
		}
		// compare email
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userEmail;
		if (principal instanceof CustomUserDetails) {
			userEmail = ((CustomUserDetails) principal).getEmail();
		} else {
			userEmail = "";
		}

		// lay email ma nguoi dung vua cap quyen
		aPIWrapper = new APIWrapper(refreshToken);
		String grantedEmail = aPIWrapper.getEmailAddress();

		if (AppUtils.isNullOrEmpty(userEmail) || AppUtils.isNullOrEmpty(grantedEmail)) {
			model.addAttribute("error", "Có lỗi xảy ra");
			return "index";
		}
		
		if (grantedEmail.equals(userEmail)) {
			model.addAttribute("success", "Cấp quyền thành công");
		
			//cập nhật user details;
			User user = userRepository.findByEmail(userEmail);
			user.setRefreshToken(refreshToken);
			
			if (principal instanceof CustomUserDetails) {
				((CustomUserDetails) principal).setRefreshToken(refreshToken);
			} else {
				
			}
			//cập nhập user vào db
			userRepository.save(user);
		} else {
			model.addAttribute("error", "Vui lòng cấp quyền cho ứng dụng với tài khoản gmail đã đăng ký!");
		}
		
		return "index";
	}
}
