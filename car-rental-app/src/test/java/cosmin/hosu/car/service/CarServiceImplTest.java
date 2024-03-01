package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.CarDTO;
import cosmin.hosu.car.dto.LicensePlateChangeRequest;
import cosmin.hosu.car.entities.Car;
import cosmin.hosu.car.excetion.CarNotFoundException;
import cosmin.hosu.car.mapper.ApplicationMapper;
import cosmin.hosu.car.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

	private static final String LICENSE_PLATE = "A-123-ABC";
	private static final String EXT_ID = "1";

	private final CarDTO carDTO = createCarDTO();
	private final LicensePlateChangeRequest licensePlateChangeRequest = createLicensePlateRequest();
	private final Car car = createCar();

	@InjectMocks
	private CarServiceImpl carService;
	@Mock
	private CarRepository carRepository;
	@Mock
	private ApplicationMapper mapper;

	@Test
	void whenGetCars_expectCarsFound() {
		when(carRepository.findAll()).thenReturn(List.of(car));
		when(mapper.mapDto(car)).thenReturn(carDTO);

		List<CarDTO> cars = carService.getCars();

		assertEquals(1, cars.size());
		assertEquals(carDTO.getLicensePlate(), cars.get(0).getLicensePlate());
		verify(carRepository).findAll();
		verify(mapper).mapDto(car);
	}

	@Test
	void whenGetCarByExtId_withExtId_expectCarFoundByExtId() {
		when(carRepository.findByExtId(EXT_ID)).thenReturn(Optional.of(car));
		when(mapper.mapDto(car)).thenReturn(carDTO);

		CarDTO carByExtId = carService.getCarByExtId(EXT_ID);

		assertEquals(carDTO.getLicensePlate(), carByExtId.getLicensePlate());
		verify(carRepository).findByExtId(EXT_ID);
		verify(mapper).mapDto(car);
	}

	@Test
	void whenGetCarByExtId_withoutExtId_expectNoCarFoundExceptionIsThrown() {
		assertThrows(CarNotFoundException.class, () -> carService.getCarByExtId(null));
	}

	@Test
	void whenAddNewCar_withLicensePlateNotRegistered_expectNewCarIsAdded() {
		when(mapper.mapEntity(carDTO)).thenReturn(car);
		when(carRepository.findByLicensePlate(carDTO.getLicensePlate())).thenReturn(Optional.empty());
		when(carRepository.save(car)).thenReturn(car);

		carService.addNewCar(carDTO);

		verify(mapper).mapEntity(carDTO);
		verify(carRepository).save(car);
	}

	@Test
	void whenAddNewCar_withLicensePlateRegistered_expectNewCarIsNotAdded() {
		when(mapper.mapEntity(carDTO)).thenReturn(car);
		when(carRepository.findByLicensePlate(carDTO.getLicensePlate())).thenReturn(Optional.of(car));

		carService.addNewCar(carDTO);

		verify(mapper).mapEntity(carDTO);
		verify(carRepository, times(0)).save(car);
	}

	@Test
	void whenUpdateCar_expectCarIsUpdated() {
		when(carRepository.findByLicensePlate(carDTO.getLicensePlate())).thenReturn(Optional.of(car));
		when(carRepository.save(car)).thenReturn(car);

		carService.updateCar(carDTO);

		verify(carRepository).save(car);
	}

	@Test
	void whenUpdateCar_andCarNotFound_expectCarIsNotUpdated() {
		when(carRepository.findByLicensePlate(carDTO.getLicensePlate())).thenReturn(Optional.empty());

		assertThrows(CarNotFoundException.class, () -> carService.updateCar(carDTO));
	}

	@Test
	void whenUpdateCarLicensePlate_expectCarLicensePlateIsUpdated() {
		when(carRepository.findByExtId(EXT_ID)).thenReturn(Optional.of(car));
		when(carRepository.save(car)).thenReturn(car);

		carService.updateCarLicensePlate(licensePlateChangeRequest, EXT_ID);

		verify(carRepository).save(car);
	}

	@Test
	void whenUpdateCarLicensePlate_andCarNotFound_expectCarLicensePlateIsNotUpdated() {
		when(carRepository.findByExtId(EXT_ID)).thenReturn(Optional.empty());

		assertThrows(CarNotFoundException.class, () -> carService.updateCarLicensePlate(licensePlateChangeRequest, EXT_ID));
	}

	@Test
	void whenDeleteCar_expectCarIsDeleted() {
		when(carRepository.findByExtId(EXT_ID)).thenReturn(Optional.of(car));

		carService.deleteCar(EXT_ID);

		verify(carRepository).delete(car);
	}

	@Test
	void whenDeleteCar_withNullExtId_expectCarIsNotDeleted() {
		when(carRepository.findByExtId(EXT_ID)).thenReturn(Optional.empty());

		carService.deleteCar(EXT_ID);

		verify(carRepository, times(0)).delete(car);
	}

	private CarDTO createCarDTO() {
		return CarDTO
				.builder()
				.licensePlate(LICENSE_PLATE)
				.extId(EXT_ID)
				.build();
	}

	private LicensePlateChangeRequest createLicensePlateRequest() {
		return LicensePlateChangeRequest
				.builder()
				.newLicensePlate(LICENSE_PLATE)
				.build();
	}

	private Car createCar() {
		return Car.builder()
				.licensePlate(LICENSE_PLATE)
				.extId(EXT_ID)
				.build();
	}
}