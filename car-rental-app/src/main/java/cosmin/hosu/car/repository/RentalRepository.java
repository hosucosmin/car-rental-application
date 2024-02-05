package cosmin.hosu.car.repository;

import cosmin.hosu.car.entities.Car;
import cosmin.hosu.car.entities.Driver;
import cosmin.hosu.car.entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rent, Long> {

	@Query("SELECT s FROM Rent s WHERE s.rentExtId = ?1")
	Optional<Rent> findByExtId(String extId);

	@Query("SELECT s FROM Rent s WHERE (s.car = ?1 OR s.driver = ?2) AND s.endTime is null")
	Optional<Rent> findNotFinishedRentByCarOrDriver(Car car, Driver driver);

	@Query("SELECT s FROM Rent s WHERE s.endTime is null")
	Optional<List<Rent>> findAllNotEnded();

	@Query("SELECT s FROM Rent s WHERE s.endTime is not null")
	Optional<List<Rent>> findAllFinishedRents();

}
