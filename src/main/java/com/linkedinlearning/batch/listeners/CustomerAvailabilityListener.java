package com.linkedinlearning.batch.listeners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class CustomerAvailabilityListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Intimate Customer about before delivery ");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("decide status on delivery-type");
        String deliveryType = stepExecution.getJobParameters().getString("delivery.type");
        return deliveryType.equals("PREMIUM") ? new ExitStatus("CONTACT_THROUGH_PHONE")
                :new ExitStatus("DELIVER_PACKAGE_ANYWAY");
    }
}
