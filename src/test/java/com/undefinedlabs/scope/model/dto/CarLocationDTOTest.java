package com.undefinedlabs.scope.model.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CarLocationDTOTest {

    private static final String SAMPLE_UUID = "sampleUUID";
    private static final double SAMPLE_LATITUDE = 0.0;
    private static final double SAMPLE_LONGITUDE = 1.0;

    @Test
    public void should_create_correct_object() {
        //Given

        //When
        final CarLocationDTO carLocation = new CarLocationDTO(SAMPLE_UUID, SAMPLE_LATITUDE, SAMPLE_LONGITUDE);

        //Then
        assertThat(carLocation.getUuid()).isEqualTo(SAMPLE_UUID);
        assertThat(carLocation.getLatitude()).isEqualTo(SAMPLE_LATITUDE);
        assertThat(carLocation.getLongitude()).isEqualTo(SAMPLE_LONGITUDE);
    }
}
