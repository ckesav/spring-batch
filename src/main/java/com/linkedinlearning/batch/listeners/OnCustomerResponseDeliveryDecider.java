package com.linkedinlearning.batch.listeners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class OnCustomerResponseDeliveryDecider implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String preferredDay = stepExecution.getJobParameters().getString("delivery.preferred.day");
        System.out.println("customer preferred delivery day "+preferredDay);
        return (preferredDay == "SUNDAY")? new ExitStatus("DELIVERY_RETRY") :
                new ExitStatus("DELIVERY_RETRY");
    }
}
