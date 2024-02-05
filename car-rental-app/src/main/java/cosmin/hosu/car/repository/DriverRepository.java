package cosmin.hosu.car.repository;

import cosmin.hosu.car.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

	@Query("SELECT s FROM Driver s WHERE s.extId = ?1")
	Optional<Driver> findByExtId(String extId);

	@Query("SELECT s FROM Driver s WHERE s.name = ?1 AND s.email = ?2 AND s.phone = ?3")
	Optional<Driver> findByNameEmailAndPhone(String name, String email, String phone);

	@Query("SELECT s FROM Driver s WHERE s.phone = ?1")
	Optional<Driver> findByPhoneNumber(String phone);
}
