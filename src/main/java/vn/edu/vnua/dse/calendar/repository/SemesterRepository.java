package vn.edu.vnua.dse.calendar.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.vnua.dse.calendar.model.Semester;

@Repository("semesterRepository")
public interface SemesterRepository extends JpaRepository<Semester, String> {
	Semester findById(String id);
	
	@Query(
	value = "SELECT * FROM semester ORDER BY startDate DESC LIMIT 3", 
	nativeQuery = true)
	ArrayList<Semester> fin3CurrentSemester();

}
