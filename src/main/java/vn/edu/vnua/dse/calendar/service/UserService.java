package vn.edu.vnua.dse.calendar.service;

import java.util.List;

import vn.edu.vnua.dse.calendar.model.User;

public interface UserService {
	public void init(User user);

	public void setRole(User user, String role);

	public void save(User user);

	public User findByEmail(String email);

	public User findByConfirmToken(String resetToken);

	/**
	 * Lay tat ca nguoi dung
	 * 
	 * @return
	 */
	public List<User> findAll();
}
