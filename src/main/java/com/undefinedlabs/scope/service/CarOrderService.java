package com.undefinedlabs.scope.service;

import com.undefinedlabs.scope.model.dto.CarOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class CarOrderService {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public CarOrderService(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public CarOrderDTO sendOrder(CarOrderDTO carOrder) {
        for(int i = 0; i < 10; i++){
            jmsTemplate.convertAndSend("orderQueue", carOrder);
        }
        return carOrder;
    }
}
