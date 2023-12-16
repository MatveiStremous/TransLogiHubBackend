package com.example.commonservice.db;

import com.example.commonservice.dto.ConvoyRequest;
import com.example.commonservice.dto.OrderRequest;
import com.example.commonservice.dto.TrailerRequest;
import com.example.commonservice.dto.TrailerTypeRequest;
import com.example.commonservice.dto.TruckRequest;
import com.example.commonservice.model.User;
import com.example.commonservice.model.enums.Role;
import com.example.commonservice.service.AuthUserService;
import com.example.commonservice.service.ConvoyService;
import com.example.commonservice.service.OrderService;
import com.example.commonservice.service.TrailerService;
import com.example.commonservice.service.TrailerTypeService;
import com.example.commonservice.service.TruckService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Year;

@Component
@RequiredArgsConstructor
public class Init {
    private final TrailerTypeService trailerTypeService;
    private final TrailerService trailerService;
    private final TruckService truckService;
    private final OrderService orderService;
    private final ConvoyService convoyService;
    private final AuthUserService authUserService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initDb() {
        initConvoys();
        initTrailerTypes();
        initTrailers();
        initTrucks();
        //initOrders();
        initUsers();
    }

    private void initUsers() {
        User user = User.builder()
                .login("manager@gmail.com")
                .firstName("Михаил")
                .middleName("Михайлович")
                .lastName("Михайлов")
                .role(Role.ROLE_MANAGER)
                .isActive(true)
                .phone("+375448765632")
                .convoyId(1)
                .build();
        user.setPassword(passwordEncoder.encode("Admin123"));
        User savedUser = authUserService.save(user);
        User user2 = User.builder()
                .login("driver@gmail.com")
                .firstName("Митяй")
                .middleName("Батькович")
                .lastName("Сватов")
                .role(Role.ROLE_DRIVER)
                .isActive(true)
                .phone("+375448765632")
                .convoyId(1)
                .build();
        user2.setPassword(passwordEncoder.encode("Driver123"));
        User savedUser2 = authUserService.save(user2);
        User user3 = User.builder()
                .login("logiest@gmail.com")
                .firstName("Иван")
                .middleName("Степанович")
                .lastName("Бутько")
                .role(Role.ROLE_LOGIEST)
                .isActive(true)
                .phone("+375448765632")
                .convoyId(1)
                .build();
        user3.setPassword(passwordEncoder.encode("Logiest123"));
        User savedUser3 = authUserService.save(user3);
    }

    private void initTrailerTypes() {
        trailerTypeService.add(TrailerTypeRequest.builder()
                .name("Шторный")
                .build());
        trailerTypeService.add(TrailerTypeRequest.builder()
                .name("Фургон")
                .build());
        trailerTypeService.add(TrailerTypeRequest.builder()
                .name("Трал")
                .build());
        trailerTypeService.add(TrailerTypeRequest.builder()
                .name("Рефрижератор")
                .build());
        trailerTypeService.add(TrailerTypeRequest.builder()
                .name("Контейнеровоз")
                .build());
    }

    private void initTrailers() {
        trailerService.add(TrailerRequest.builder()
                .trailerTypeId(1)
                .model("Schmitz")
                .brand("9084")
                .maxCargoWeight(25000)
                .stateNumber("MH2432-5")
                .yearOfIssue(Year.of(2019))
                .weight(7500)
                .note("-")
                .convoyId(1)
                .build());
        trailerService.add(TrailerRequest.builder()
                .trailerTypeId(1)
                .model("Schmitz")
                .brand("SCS BSEB")
                .maxCargoWeight(24000)
                .stateNumber("AB4823-1")
                .yearOfIssue(Year.of(2019))
                .weight(7300)
                .note("Примечаний нет")
                .convoyId(1)
                .build());
        trailerService.add(TrailerRequest.builder()
                .trailerTypeId(2)
                .model("Schmitz")
                .brand("9076")
                .maxCargoWeight(22000)
                .stateNumber("HB1332-7")
                .yearOfIssue(Year.of(2015))
                .weight(7900)
                .note("-")
                .convoyId(1)
                .build());
        trailerService.add(TrailerRequest.builder()
                .trailerTypeId(4)
                .model("Krone")
                .brand("SD")
                .maxCargoWeight(21500)
                .stateNumber("KT8492-1")
                .yearOfIssue(Year.of(2021))
                .weight(8100)
                .note("-")
                .convoyId(1)
                .build());
    }

    private void initTrucks() {
        truckService.add(TruckRequest.builder()
                .model("Volvo")
                .brand("FH12")
                .maxCargoWeight(25000)
                .weight(6900)
                .mileage(198234)
                .yearOfIssue(Year.of(2019))
                .note("-")
                .stateNumber("AB3042-1")
                .convoyId(1)
                .build());
        truckService.add(TruckRequest.builder()
                .model("Daf")
                .brand("XF105")
                .maxCargoWeight(25000)
                .weight(7100)
                .mileage(100234)
                .yearOfIssue(Year.of(2022))
                .note("Тех.осмотр до 30.11.2023")
                .stateNumber("AB3043-1")
                .convoyId(1)
                .build());
        truckService.add(TruckRequest.builder()
                .model("Daf")
                .brand("XF105")
                .maxCargoWeight(25000)
                .weight(7100)
                .mileage(98234)
                .yearOfIssue(Year.of(2022))
                .note("Тех.осмотр до 30.11.2023")
                .stateNumber("AB3044-1")
                .convoyId(1)
                .build());
        truckService.add(TruckRequest.builder()
                .model("Volvo")
                .brand("FH12")
                .maxCargoWeight(25000)
                .weight(6900)
                .mileage(198234)
                .yearOfIssue(Year.of(2019))
                .note("-")
                .stateNumber("AB3045-1")
                .convoyId(1)
                .build());
    }

    private void initConvoys() {
        convoyService.add(ConvoyRequest.builder()
                .name("1 колонна")
                .build());
        convoyService.add(ConvoyRequest.builder()
                .name("2 колонна")
                .build());
    }

    private void initOrders() {
        orderService.add(OrderRequest.builder()
                .note("Стандартная загрузка")
                .arrivalAddress("Минск, ул.Я.Коласа 123")
                .departureAddress("Барановичи, ул.Ленина 13")
                .cargoName("Металлические слитки")
                .totalWeight(150000)
                .loadingDate(LocalDateTime.parse("2023-11-29T19:00:00"))
                .unloadingDate(LocalDateTime.parse("2023-11-30T12:00:00"))
                .numberOfTrucks(5)
                .clientEmail("test@gmail.com")
                .build());
    }
}
