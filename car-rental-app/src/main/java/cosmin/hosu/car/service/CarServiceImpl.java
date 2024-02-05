package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.CarDTO;
import cosmin.hosu.car.entities.Car;
import cosmin.hosu.car.mapper.ApplicationMapper;
import cosmin.hosu.car.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ApplicationMapper mapper;

    @Override
    public List<CarDTO> getCars() {
        return carRepository.findAll().stream().map(mapper::mapDto).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addNewCar(CarDTO carDTO) {
        Car car = mapper.mapEntity(carDTO);
        boolean alreadyAddedCar = carRepository.findByLicensePlate(car.getLicensePlate()).isPresent();
        if (!alreadyAddedCar) {
            car.setExtId(UUID.randomUUID().toString());
            carRepository.save(car);
            return ok("Success");
        }
        return internalServerError().body("There has been an error.");
    }

    @Override
    public ResponseEntity<String> updateCar(CarDTO carDTO) {
        Car carEntity = Optional.ofNullable(carDTO.getLicensePlate())
                .flatMap(carRepository::findByLicensePlate)
                .map(car -> updateCar(car, carDTO))
                .orElse(null);

        if (carEntity != null) {
            return ResponseEntity.ok("Successfully updated driver entity");
        }

        return internalServerError().body("There has been an error");
    }

    private Car updateCar(Car car, CarDTO carDTO) {
       car.setBrand(carDTO.getBrand());
       car.setPrice(carDTO.getPrice());
       car.setModel(carDTO.getModel());
       car.setLicensePlate(carDTO.getLicensePlate());
       car.setYear(carDTO.getYear());
       return carRepository.save(car);
    }

    @Override
    public void deleteCar(CarDTO carDTO) {
        Optional<Car> car = carRepository.findByExtId(carDTO.getExtId());
        car.ifPresent(carRepository::delete);
    }
}
