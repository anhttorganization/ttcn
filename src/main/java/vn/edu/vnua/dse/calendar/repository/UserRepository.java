 package vn.edu.vnua.dse.calendar.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.dse.calendar.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String var1);

   User findByEmailAndEnabled(String var1, boolean var2);

   List<User> findByLastName(String var1);

   User findById(long var1);

   User findByConfirmToken(String var1);

   User findByConfirmTokenAndEnabled(String var1, boolean var2);
}