package vn.edu.vnua.dse.calendar.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import vn.edu.vnua.dse.calendar.common.AppConstant;
import vn.edu.vnua.dse.calendar.model.Role;
import vn.edu.vnua.dse.calendar.model.User;
import vn.edu.vnua.dse.calendar.repository.RoleRepository;
import vn.edu.vnua.dse.calendar.repository.UserRepository;

@Service("userService")
@SuppressWarnings("unused")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void init(User user) {
		encode(user);
		setRole(user, AppConstant.ROLE_USER);

		userRepository.save(user);
	}

	public void setRole(User user, String role) {
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(role));
		user.setRoles(roles);
	}

	private void encode(User user) {
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(11)));
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByConfirmToken(String resetToken) {
		return userRepository.findByConfirmToken(resetToken);
	}

	/**
	 * Lay danh sach tat ca nguoi dung
	 * 
	 */
	@Override
	public List<User> findAll() {
		List<User> listResult = null;
		try {
			listResult = userRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return listResult;
	}
	
	/**
	 * Kiem tra pass
	 */
	@Override
	public boolean checkIfValidOldPassword(User user, String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getPassword());		
	}
	
	/**
	 * Thay doi pass
	 */
	@Override
	public void changeUserPass(User user,String password) {
		user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
	}

	@Override
	public List<User> findByLastName(String lastName) {
		List<User> listResult = null;
		try {
			listResult = userRepository.findByLastName(lastName);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return listResult;
	}

	@Override
	public User findById(long id) {
		User user = null;
		try {
			user = userRepository.findById(id);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return user;
	}

}
