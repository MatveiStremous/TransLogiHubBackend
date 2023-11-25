package com.example.commonservice.db;

import com.example.commonservice.dto.ConvoyRequest;
import com.example.commonservice.dto.OrderRequest;
import com.example.commonservice.dto.TrailerRequest;
import com.example.commonservice.dto.TrailerTypeRequest;
import com.example.commonservice.dto.TruckRequest;
import com.example.commonservice.model.User;
import com.example.commonservice.model.enums.Role;
import com.example.commonservice.service.AuthService;
import com.example.commonservice.service.AuthUserService;
import com.example.commonservice.service.ConvoyService;
import com.example.commonservice.service.OrderService;
import com.example.commonservice.service.TrailerService;
import com.example.commonservice.service.TrailerTypeService;
import com.example.commonservice.service.TransportationService;
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
    private final AuthService authService;
    private final TrailerTypeService trailerTypeService;
    private final TrailerService trailerService;
    private final TruckService truckService;
    private final OrderService orderService;
    private final TransportationService transportationService;
    private final ConvoyService convoyService;
    private final AuthUserService authUserService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initDb() {
        //TrailerType
        TrailerTypeRequest trailerTypeRequest = new TrailerTypeRequest();
        trailerTypeRequest.setName("Шторный");
        trailerTypeService.add(trailerTypeRequest);
        TrailerTypeRequest trailerTypeRequest2 = new TrailerTypeRequest();
        trailerTypeRequest2.setName("Рефрижератор");
        trailerTypeService.add(trailerTypeRequest2);
        //Trailer
        TrailerRequest trailerRequest = new TrailerRequest();
        trailerRequest.setTypeId(1);
        trailerRequest.setNote("Полностью обслужен");
        trailerRequest.setMaxCargoWeight(25000);
        trailerRequest.setStateNumber("AB3030-1");
        trailerRequest.setWeight(8000);
        trailerRequest.setBrand("Schmitz");
        trailerRequest.setModel("9084");
        trailerService.add(trailerRequest);
        TrailerRequest trailerRequest2 = new TrailerRequest();
        trailerRequest2.setTypeId(2);
        trailerRequest2.setNote("Полностью обслужен");
        trailerRequest2.setMaxCargoWeight(25000);
        trailerRequest2.setStateNumber("AB3031-1");
        trailerRequest2.setWeight(8000);
        trailerRequest.setBrand("Schmitz");
        trailerRequest.setModel("SCS BSEB");
        trailerService.add(trailerRequest2);
        //Truck
        TruckRequest truckRequest = new TruckRequest();
        truckRequest.setNote("Нет техосмотра");
        truckRequest.setStateNumber("AB4030-1");
        truckRequest.setWeight(15000);
        truckRequest.setMaxCargoWeight(30000);
        truckRequest.setMileage(198234);
        truckRequest.setYearOfIssue(Year.of(2018));
        truckRequest.setBrand("Volvo");
        truckRequest.setModel("FH12");
        truckService.add(truckRequest);
        TruckRequest truckRequest2 = new TruckRequest();
        truckRequest2.setNote("Нет техосмотра");
        truckRequest2.setStateNumber("AB4031-1");
        truckRequest2.setWeight(15000);
        truckRequest2.setMaxCargoWeight(30000);
        truckRequest2.setMileage(56234);
        truckRequest2.setYearOfIssue(Year.of(2019));
        truckRequest.setBrand("DAF");
        truckRequest.setModel("XF105");
        truckService.add(truckRequest2);
        //Convoy
        ConvoyRequest convoyRequest = new ConvoyRequest();
        convoyRequest.setName("1 колонна");
        convoyService.add(convoyRequest);
        ConvoyRequest convoyRequest2 = new ConvoyRequest();
        convoyRequest2.setName("2 колонна");
        convoyService.add(convoyRequest2);
        //Order
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setNote("Стандартная загрузка");
        orderRequest.setArrivalAddress("Минск");
        orderRequest.setDepartureAddress("Барановичи");
        orderRequest.setCargoName("Металл");
        orderRequest.setNumberOfTrucks(5);
        orderRequest.setLoadingDate(LocalDateTime.parse("2023-11-29T19:00:00"));
        orderRequest.setUnloadingDate(LocalDateTime.parse("2023-11-30T12:00:00"));
        orderService.add(orderRequest);
        //user
        User user = User.builder()
                .login("manager@gmail.com")
                .firstName("Михаил")
                .middleName("Михайлович")
                .lastName("Михайлов")
                .role(Role.ROLE_MANAGER)
                .isActive(true)
                .phone("+375448765632")
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
                .build();
        user3.setPassword(passwordEncoder.encode("Logiest123"));
        User savedUser3 = authUserService.save(user3);
    }
}
