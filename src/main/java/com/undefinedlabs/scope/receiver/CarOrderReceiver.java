package com.undefinedlabs.scope.receiver;

import com.undefinedlabs.scope.model.dto.CarOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CarOrderReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarOrderReceiver.class);

    @JmsListener(destination = "orderQueue", containerFactory = "carOrderJMSFactory")
    public void receiveMessage(CarOrderDTO carOrder) {
        LOGGER.info("Received <" + carOrder + ">");
    }
}
