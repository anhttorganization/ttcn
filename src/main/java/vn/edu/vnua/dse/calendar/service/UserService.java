package vn.edu.vnua.dse.calendar.service;

import java.util.List;

import vn.edu.vnua.dse.calendar.model.User;

public interface UserService {
	public void init(User user);

	public void setRole(User user, String role);

	public void save(User user);

	public User findById(long id);

	public User findByEmail(String email);

	public User findByConfirmToken(String resetToken);

	public List<User> findByLastName(String lastName);

	/**
	 * Lay tat ca nguoi dung
	 * 
	 * @return
	 */
	public List<User> findAll();

	public boolean checkIfValidOldPassword(User user, String oldPassword);

	public void changeUserPass(User user, String password);
}
