package vn.edu.vnua.dse.calendar.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import vn.edu.vnua.dse.calendar.common.AppUtils;
import vn.edu.vnua.dse.calendar.ggcalendar.wrapperapi.APIWrapper;
import vn.edu.vnua.dse.calendar.model.User;

public class AuthorizationService {
	public static String isauthorized() throws FileNotFoundException, IOException {

		// 1.kiểm tra xem đã cấp quyền(có refreshToken) chưa
		User user = UserDetailsServiceImpl.getUser();

		String refreshToken = user.getRefreshToken();

		// 2.nếu chưa => yêu cầu cấp quyền
		APIWrapper apiWrapper = new APIWrapper();
		String accessToken = apiWrapper.getAccessToken(refreshToken);
		if (AppUtils.isNullOrEmpty(refreshToken) || AppUtils.isNullOrEmpty(accessToken)) {
			String email = user.getEmail();
			String authUrl;
			if (AppUtils.isNullOrEmpty(email)) {
				authUrl = APIWrapper.getAuthUrl();
			} else {
				authUrl = APIWrapper.getAuthUrl(email);
			}

			return authUrl;
		}
		return null;
	}
}
