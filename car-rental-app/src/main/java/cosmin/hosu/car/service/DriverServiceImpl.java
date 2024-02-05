package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.DriverDTO;
import cosmin.hosu.car.entities.Driver;
import cosmin.hosu.car.mapper.ApplicationMapper;
import cosmin.hosu.car.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.internalServerError;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final ApplicationMapper mapper;

    @Override
    public List<DriverDTO> getDrivers() {
        return driverRepository.findAll().stream().map(mapper::mapDto).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addNewDriver(DriverDTO driverDTO) {
        Driver driver = mapper.mapEntity(driverDTO);
        boolean alreadyAddedDriver = driverRepository.findByPhoneNumber(driver.getPhone()).isPresent();
        if (!alreadyAddedDriver) {
            driver.setExtId(UUID.randomUUID().toString());
            driverRepository.save(driver);
            return ResponseEntity.ok("Success");
        }
        return internalServerError().body("There has been an error");
    }

    @Override
    public ResponseEntity<String> updateDriver(DriverDTO driverDTO) {
        Driver driverEntity = Optional.ofNullable(driverDTO.getPhone())
                .flatMap(driverRepository::findByPhoneNumber)
                .map(driver -> updateDriver(driver, driverDTO))
                .orElse(null);

        if (driverEntity != null) {
            return ResponseEntity.ok("Successfully updated driver entity");
        }

        return internalServerError().body("There has been an error");
    }

    @Override
    public void deleteDriver(DriverDTO driverDTO) {
        Optional<Driver> driver = driverRepository.findByExtId(driverDTO.getExtId());
        driver.ifPresent(driverRepository::delete);
    }

    private Driver updateDriver(Driver driver, DriverDTO driverDTO) {
        driver.setEmail(driverDTO.getEmail());
        driver.setName(driverDTO.getName());
        driver.setPhone(driverDTO.getPhone());
        driver.setExtId(driverDTO.getExtId());
        return driverRepository.save(driver);
    }
}
