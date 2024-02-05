package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.RentDTO;
import cosmin.hosu.car.dto.RentRequestDTO;
import cosmin.hosu.car.entities.Car;
import cosmin.hosu.car.entities.Driver;
import cosmin.hosu.car.entities.Rent;
import cosmin.hosu.car.mapper.ApplicationMapper;
import cosmin.hosu.car.repository.CarRepository;
import cosmin.hosu.car.repository.DriverRepository;
import cosmin.hosu.car.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentalServiceImpl implements RentService {

    private final RentalRepository rentalRepository;
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final ApplicationMapper mapper;


    @Override
    public ResponseEntity<String> registerNewEntry(RentRequestDTO rentDTO) {
        Optional<Driver> driver = driverRepository.findByNameEmailAndPhone(rentDTO.getDriverName(), rentDTO.getDriverEmail(), rentDTO.getDriverPhone());
        Optional<Car> car = carRepository.findByLicensePlate(rentDTO.getCarLicensePlate());
        if (driver.isPresent() && car.isPresent()) {
            if (!isAnotherRentInProgress(car.get(), driver.get())) {
                saveNewRent(driver.get(), car.get());
                return ok("Success");
            }
        }
        return internalServerError().body("There has been an error");
    }

    @Override
    public ResponseEntity<String> finishARide(Map<String, String> json) {
        String rentExtId = json.get("rentExtId");
        Optional<Rent> optionalRent = rentalRepository.findByExtId(rentExtId);
        if (optionalRent.isPresent()) {
            Rent rent = optionalRent.get();
            saveFinishedRide(rent);
            return ok("Success");
        }
        return internalServerError().body("There has been an error");
    }

    @Override
    public void deleteARent(Map<String, String> json) {
        String rentExtId = json.get("rentExtId");
        Optional<Rent> rent = rentalRepository.findByExtId(rentExtId);
        rent.ifPresent(rentalRepository::delete);
    }

    @Override
    public List<RentDTO> getCurrentRents() {
        return rentalRepository.findAllNotEnded()
                .map(rents -> rents.stream()
                        .map(mapper::mapDto)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<RentDTO> getFinishedRents() {
        return rentalRepository.findAllFinishedRents()
                .map(rents -> rents.stream()
                        .map(mapper::mapDto)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private boolean isAnotherRentInProgress(Car car, Driver driver) {
        return rentalRepository.findNotFinishedRentByCarOrDriver(car, driver).isPresent();
    }

    private void saveNewRent(Driver driver, Car car) {
        Rent rent = Rent.builder()
                .startTime(LocalDateTime.now())
                .driver(driver).car(car)
                .rentExtId(UUID.randomUUID().toString())
                .build();
        rentalRepository.save(rent);
    }

    private void saveFinishedRide(Rent rent) {
        Car car = carRepository.findByExtId(rent.getCar().getExtId()).get();
        rent.setEndTime(LocalDateTime.now());
        rent.setDuration((ChronoUnit.HOURS.between(rent.getStartTime(), LocalDateTime.now()) +
                (ChronoUnit.MINUTES.between(rent.getStartTime(), LocalDateTime.now()) * 0.01)));
        rent.setAmountDue((rent.getDuration() * car.getPrice()) + "$");
        rentalRepository.save(rent);
    }
}
