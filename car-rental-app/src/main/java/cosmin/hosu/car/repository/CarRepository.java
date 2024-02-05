package cosmin.hosu.car.repository;

import cosmin.hosu.car.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

	@Query("SELECT s FROM Car s WHERE s.extId = ?1")
	Optional<Car> findByExtId(String extId);

	@Query("SELECT s FROM Car s WHERE s.licensePlate = ?1")
	Optional<Car> findByLicensePlate(String plateNumber);
}
