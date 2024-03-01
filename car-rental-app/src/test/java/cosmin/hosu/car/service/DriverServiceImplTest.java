package cosmin.hosu.car.service;

import cosmin.hosu.car.dto.DriverDTO;
import cosmin.hosu.car.entities.Driver;
import cosmin.hosu.car.excetion.DriverNotFoundException;
import cosmin.hosu.car.mapper.ApplicationMapper;
import cosmin.hosu.car.repository.DriverRepository;
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
class DriverServiceImplTest {

	private static final String EXT_ID = "1";
	public static final String PHONE_NUMBER = "123456789";
	public static final String NAME = "Name";
	public static final String EMAIL = "email@gmail.com";

	private final DriverDTO driverDTO = createDriverDto();
	private final Driver driver = createDriver();

	@InjectMocks
	private DriverServiceImpl driverService;
	@Mock
	private DriverRepository driverRepository;
	@Mock
	private ApplicationMapper mapper;

	@Test
	void whenGetDrivers_expectDriversFound() {
		when(driverRepository.findAll()).thenReturn(List.of(driver));
		when(mapper.mapDto(driver)).thenReturn(driverDTO);

		List<DriverDTO> drivers = driverService.getDrivers();

		assertEquals(1, drivers.size());
		assertEquals(driverDTO.getEmail(), drivers.get(0).getEmail());
		verify(driverRepository).findAll();
		verify(mapper).mapDto(driver);
	}

	@Test
	void whenGetDriverByExtId_withExtId_expectDriverFoundByExtId() {
		when(driverRepository.findByExtId(EXT_ID)).thenReturn(Optional.of(driver));
		when(mapper.mapDto(driver)).thenReturn(driverDTO);

		DriverDTO driverByExtId = driverService.getDriverByExtId(EXT_ID);

		assertEquals(driverDTO.getEmail(), driverByExtId.getEmail());
		verify(driverRepository).findByExtId(EXT_ID);
		verify(mapper).mapDto(driver);
	}

	@Test
	void whenGetDriverByExtId_withoutExtId_expectNoDriverFoundExceptionIsThrown() {
		assertThrows(DriverNotFoundException.class, () -> driverService.getDriverByExtId(null));
	}

	@Test
	void whenAddNewDriver_withPhoneNumberNotRegistered_expectNewDriverIsAdded() {
		when(mapper.mapEntity(driverDTO)).thenReturn(driver);
		when(driverRepository.findByPhoneNumber(driverDTO.getPhone())).thenReturn(Optional.empty());
		when(driverRepository.save(driver)).thenReturn(driver);

		driverService.addNewDriver(driverDTO);

		verify(mapper).mapEntity(driverDTO);
		verify(driverRepository).save(driver);
	}

	@Test
	void whenAddNewDriver_withPhoneNumberRegistered_expectNewDriverIsNotAdded() {
		when(mapper.mapEntity(driverDTO)).thenReturn(driver);
		when(driverRepository.findByPhoneNumber(driverDTO.getPhone())).thenReturn(Optional.of(driver));

		driverService.addNewDriver(driverDTO);

		verify(mapper).mapEntity(driverDTO);
		verify(driverRepository, times(0)).save(driver);
	}

	@Test
	void whenUpdateDriver_expectDriverIsUpdated() {
		when(driverRepository.findByPhoneNumber(driverDTO.getPhone())).thenReturn(Optional.of(driver));
		when(driverRepository.save(driver)).thenReturn(driver);

		driverService.updateDriver(driverDTO);

		verify(driverRepository).save(driver);
	}

	@Test
	void whenUpdateDriver_andDriverNotFound_expectDriverIsNotUpdated() {
		when(driverRepository.findByPhoneNumber(driverDTO.getPhone())).thenReturn(Optional.empty());

		assertThrows(DriverNotFoundException.class, () -> driverService.updateDriver(driverDTO));
	}

	@Test
	void whenDeleteDriver_expectDriverIsDeleted() {
		when(driverRepository.findByExtId(any())).thenReturn(Optional.of(driver));

		driverService.deleteDriver(driverDTO);

		verify(driverRepository).delete(driver);
	}

	@Test
	void whenDeleteDriver_withNullExtId_expectDriverIsNotDeleted() {
		when(driverRepository.findByExtId(driverDTO.getExtId())).thenReturn(Optional.empty());

		driverService.deleteDriver(driverDTO);

		verify(driverRepository, times(0)).delete(driver);
	}

	private static DriverDTO createDriverDto() {
		return DriverDTO
				.builder()
				.phone(PHONE_NUMBER)
				.name(NAME)
				.email(EMAIL)
				.extId(EXT_ID)
				.build();
	}

	private static Driver createDriver() {
		return Driver.builder()
				.phone(PHONE_NUMBER)
				.name(NAME)
				.email(EMAIL)
				.extId(EXT_ID)
				.build();
	}
}