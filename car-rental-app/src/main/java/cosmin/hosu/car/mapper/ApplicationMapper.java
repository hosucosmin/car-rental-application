package cosmin.hosu.car.mapper;

import cosmin.hosu.car.dto.CarDTO;
import cosmin.hosu.car.dto.DriverDTO;
import cosmin.hosu.car.dto.RentDTO;
import cosmin.hosu.car.entities.Car;
import cosmin.hosu.car.entities.Driver;
import cosmin.hosu.car.entities.Rent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "phone", source = "dto.phone")
    @Mapping(target = "extId", source = "dto.extId")
    Driver mapEntity(DriverDTO dto);

    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "email", source = "entity.email")
    @Mapping(target = "phone", source = "entity.phone")
    @Mapping(target = "extId", source = "entity.extId")
    DriverDTO mapDto(Driver entity);

    @Mapping(target = "startTime", source = "entity.startTime")
    @Mapping(target = "endTime", source = "entity.endTime")
    @Mapping(target = "driverName", source = "entity.driver.name")
    @Mapping(target = "driverEmail", source = "entity.driver.email")
    @Mapping(target = "driverPhone", source = "entity.driver.phone")
    @Mapping(target = "carLicensePlate", source = "entity.car.licensePlate")
    @Mapping(target = "carBrand", source = "entity.car.brand")
    @Mapping(target = "rentExtId", source = "entity.rentExtId")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "amountDue", source = "entity.amountDue")
    RentDTO mapDto(Rent entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", source = "dto.brand")
    @Mapping(target = "model", source = "dto.model")
    @Mapping(target = "year", source = "dto.year")
    @Mapping(target = "licensePlate", source = "dto.licensePlate")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "extId", source = "dto.extId")
    Car mapEntity(CarDTO dto);

    @Mapping(target = "brand", source = "entity.brand")
    @Mapping(target = "model", source = "entity.model")
    @Mapping(target = "year", source = "entity.year")
    @Mapping(target = "licensePlate", source = "entity.licensePlate")
    @Mapping(target = "price", source = "entity.price")
    @Mapping(target = "extId", source = "entity.extId")
    CarDTO mapDto(Car entity);
}
