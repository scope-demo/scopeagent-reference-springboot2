package com.undefinedlabs.scope.controller;

import com.undefinedlabs.scope.model.dto.CarOrderDTO;
import com.undefinedlabs.scope.service.CarOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carorder")
public class CarOrderController {

    private final CarOrderService carOrderService;

    @Autowired
    public CarOrderController(final CarOrderService carOrderService) {
        this.carOrderService = carOrderService;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public CarOrderDTO sendOrder(@RequestBody CarOrderDTO carOrder, @RequestParam boolean forceFailure) {
        return this.carOrderService.sendOrder(carOrder);
    }
}
