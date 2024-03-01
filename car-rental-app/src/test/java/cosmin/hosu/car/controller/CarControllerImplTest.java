package cosmin.hosu.car.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cosmin.hosu.car.dto.CarDTO;
import cosmin.hosu.car.excetion.CarNotFoundException;
import cosmin.hosu.car.service.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CarControllerImplTest {

	private static final String YEAR = "year";
	private static final String BASE_URL = "/api/v1/";
	private static final String CARS_BASE_URL = BASE_URL + "car/";
	private static final String REGISTER_URL = CARS_BASE_URL + "register/";
	private static final String UPDATE_URL = CARS_BASE_URL + "update/";
	private static final String DELETE_URL = CARS_BASE_URL + "delete/";

	private static final String EXT_ID = "123";
	private static final String LICENSE_PLATE = "licensePlate";
	private static final String MODEL = "model";
	private static final String BRAND = "brand";
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@InjectMocks
	private CarControllerImpl controller;
	@Mock
	private CarServiceImpl carService;
	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void whenGetCars_expectStatus200() throws Exception {
		List<CarDTO> carsDto = new ArrayList<>();
		carsDto.add(new CarDTO());
		carsDto.add(new CarDTO());
		carsDto.add(new CarDTO());
		when(carService.getCars()).thenReturn(carsDto);

		mockMvc.perform(get(CARS_BASE_URL)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(status().isOk());
	}

	@Test
	void whenGetCarByExtId_withExtId_expectStatus200() throws Exception {
		CarDTO carDTO = CarDTO.builder().extId(EXT_ID).build();
		when(carService.getCarByExtId(isNotNull())).thenReturn(carDTO);

		mockMvc.perform(get(CARS_BASE_URL + EXT_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void whenGetCarByExtId_withWrongExtId_expectStatus404() throws Exception {
		doThrow(CarNotFoundException.class).when(carService).getCarByExtId(anyString());

		mockMvc.perform(get(CARS_BASE_URL + EXT_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void whenRegisterNewCar_expectStatus200() throws Exception {
		CarDTO carDTO = createNewCar();
		when(carService.addNewCar(carDTO)).thenReturn(ResponseEntity.ok().build());

		mockMvc.perform(post(REGISTER_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(MAPPER.writeValueAsString(carDTO)))
				.andExpect(status().isOk());
	}

	@Test
	void whenRegisterNewCar_withSomeFieldsMissing_expectStatus400() throws Exception {
		CarDTO carDTO = CarDTO.builder().licensePlate(LICENSE_PLATE).build();

		mockMvc.perform(post(REGISTER_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(MAPPER.writeValueAsString(carDTO)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void whenUpdateCar_expectStatus200() throws Exception {
		CarDTO carDTO = createNewCar();
		when(carService.updateCar(carDTO)).thenReturn(ResponseEntity.ok().build());

		mockMvc.perform(put(UPDATE_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(MAPPER.writeValueAsString(carDTO)))
				.andExpect(status().isOk());
	}

	@Test
	void whenUpdateCar_withSomeFieldsMissing_expectStatus400() throws Exception {
		CarDTO carDTO = CarDTO.builder().licensePlate(LICENSE_PLATE).build();

		mockMvc.perform(put(UPDATE_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(MAPPER.writeValueAsString(carDTO)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void deleteCar() throws Exception {
		when(carService.deleteCar(EXT_ID)).thenReturn(ResponseEntity.ok().build());

		mockMvc.perform(delete(DELETE_URL + EXT_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private CarDTO createNewCar() {
		return CarDTO
				.builder()
				.licensePlate(LICENSE_PLATE)
				.model(MODEL)
				.brand(BRAND)
				.year(YEAR)
				.build();
	}
}