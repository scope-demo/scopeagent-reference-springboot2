package com.undefinedlabs.scope.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CarPlateDTO {

    private String carPlate;

    @JsonCreator
    public CarPlateDTO(@JsonProperty("carPlate") final String carPlate) {
        this.carPlate = carPlate;
    }

    @JsonGetter
    public String getCarPlate() {
        return carPlate;
    }
}
