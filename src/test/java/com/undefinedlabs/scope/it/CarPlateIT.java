package com.undefinedlabs.scope.it;

import com.undefinedlabs.scope.CarLocationApplication;
import com.undefinedlabs.scope.model.dto.CarPlateDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CarLocationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarPlateIT {

    private static final String SUCCESS_CAR_UUID = "9E219725-490E-4509-A42D-D0388DF317D4";
    private static final String NOT_FOUND_CAR_UUID = "asd";
    private static final String ERROR_CAR_UUID = "9E219725-490E-4509-A42D-D0388DF317DG";

    @LocalServerPort
    private int randomServerPort;

    @Test
    public void should_obtain_car_plate_by_uuid() {
        //Given
        final RestTemplate restTemplate = new RestTemplate();

        //When
        final CarPlateDTO carPlate = restTemplate.getForObject("http://localhost:" + randomServerPort + "/carplate/"+SUCCESS_CAR_UUID+"?q=foobar", CarPlateDTO.class);

        //Then
        assertThat(carPlate).isNotNull();
        assertThat(carPlate.getCarPlate()).isNotNull();
    }

    @Test
    public void should_obtain_car_plate_by_multiple_uuid() {
        //Given
        final RestTemplate restTemplate = new RestTemplate();

        //When
        final ResponseEntity<List<CarPlateDTO>> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/carplate/multiple/"+SUCCESS_CAR_UUID+"?q=foobar", HttpMethod.GET,null, new ParameterizedTypeReference<List<CarPlateDTO>>(){});
        final List<CarPlateDTO> carPlates = response.getBody();

        //Then
        assertThat(carPlates).isNotNull();
        for(CarPlateDTO carPlate : carPlates) {
            assertThat(carPlate.getCarPlate()).isNotNull();
        }
    }

}
