package vn.edu.vnua.dse.calendar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.vnua.dse.calendar.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	List<User> findByLastName(String lastName);

	User findById(long id);

	// List<User> findByFirstName(String firstName);
	User findByConfirmToken(String resetToken);
}
