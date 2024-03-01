package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.CarDTO;
import cosmin.hosu.car.dto.LicensePlateChangeRequest;
import cosmin.hosu.car.entities.Car;
import cosmin.hosu.car.excetion.CarNotFoundException;
import cosmin.hosu.car.mapper.ApplicationMapper;
import cosmin.hosu.car.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ApplicationMapper mapper;

    @Override
    public List<CarDTO> getCars() {
        return carRepository.findAll().stream().map(mapper::mapDto).toList();
    }

    @Override
    public CarDTO getCarByExtId(String extId) {
        return Optional.ofNullable(extId)
                .flatMap(carRepository::findByExtId)
                .map(mapper::mapDto)
                .orElseThrow(() -> new CarNotFoundException("Couldn't find any car with the provided external id."));
    }

    @Override
    public ResponseEntity<String> updateCarLicensePlate(LicensePlateChangeRequest licensePlateChangeRequest, String extId) {
        Car car = carRepository.findByExtId(extId)
                .orElseThrow(() -> new CarNotFoundException("Couldn't find any car with the provided external id."));
        car.setLicensePlate(licensePlateChangeRequest.getNewLicensePlate());
        carRepository.save(car);
        return ok("Successfully updated the car entity.");
    }

    @Override
    public ResponseEntity<String> addNewCar(CarDTO carDTO) {
        Car car = mapper.mapEntity(carDTO);
        boolean alreadyAddedCar = carRepository.findByLicensePlate(car.getLicensePlate()).isPresent();
        if (!alreadyAddedCar) {
            car.setExtId(UUID.randomUUID().toString());
            carRepository.save(car);
            return ok("Successfully saved the car entity.");
        }
        return internalServerError().body("The car is already registered. Please try again!");
    }

    @Override
    public ResponseEntity<String> updateCar(CarDTO carDTO) {
        Car carEntity = Optional.ofNullable(carDTO.getLicensePlate())
                .flatMap(carRepository::findByLicensePlate)
                .map(car -> updateCar(car, carDTO))
                .orElseThrow(() -> new CarNotFoundException("Couldn't find any car with the provided license plate."));

        if (carEntity != null) {
            return ResponseEntity.ok("Successfully updated driver entity");
        }

        return internalServerError().body("There has been an error updating the car entity. Please try again!");
    }

    @Override
    public ResponseEntity<String> deleteCar(String extId) {
        Optional<Car> car = carRepository.findByExtId(extId);
        car.ifPresent(carRepository::delete);
        return ok().body("Successfully deleted the car entity.");
    }

    private Car updateCar(Car car, CarDTO carDTO) {
        car.setBrand(carDTO.getBrand());
        car.setPrice(carDTO.getPrice());
        car.setModel(carDTO.getModel());
        car.setLicensePlate(carDTO.getLicensePlate());
        car.setYear(carDTO.getYear());
        return carRepository.save(car);
    }
}
