package com.undefinedlabs.scope.it;

import com.undefinedlabs.scope.CarLocationApplication;
import com.undefinedlabs.scope.model.dto.CarOrderDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CarLocationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarOrderIT {

    @LocalServerPort
    private int randomServerPort;

    @Test
    public void should_enqueue_car_order_successfully() {
        //Given
        final RestTemplate restTemplate = new RestTemplate();

        //When
        final CarOrderDTO carOrder = new CarOrderDTO(UUID.randomUUID().toString());
        final CarOrderDTO carOrderDTO = restTemplate.postForObject("http://localhost:" + randomServerPort + "/carorder/send?forceFailure=false", carOrder, CarOrderDTO.class);

        //Then
        assertThat(carOrderDTO).isNotNull();
    }

}
