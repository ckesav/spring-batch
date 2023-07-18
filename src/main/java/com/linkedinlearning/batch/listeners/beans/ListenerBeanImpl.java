package com.linkedinlearning.batch.listeners.beans;

import com.linkedinlearning.batch.listeners.CustomerAvailabilityListener;
import com.linkedinlearning.batch.listeners.FlowersSelectionStepExecutionListener;
import com.linkedinlearning.batch.listeners.OnAddressFoundListener;
import com.linkedinlearning.batch.listeners.OnCustomerResponseDeliveryDecider;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ListenerBeanImpl {

    @Bean
    public StepExecutionListener selectFlowerListener() {
        return new FlowersSelectionStepExecutionListener();
    }

    @Bean
    public StepExecutionListener customerAvailabilityListener(){
        return new CustomerAvailabilityListener();
    }

    @Bean
    public StepExecutionListener onCustRespDeliveryDecide(){
        return new OnCustomerResponseDeliveryDecider();
    }

    @Bean
    public StepExecutionListener onCustAddressFound(){
        return new OnAddressFoundListener();
    }
}
