package com.linkedinlearning.batch.jobs;

import com.linkedinlearning.batch.deciders.beans.DeciderBeanImpl;
import com.linkedinlearning.batch.jobs.steps.DeliverySteps;
import com.linkedinlearning.batch.listeners.FlowersSelectionStepExecutionListener;
import com.linkedinlearning.batch.listeners.OnCustomerResponseDeliveryDecider;
import com.linkedinlearning.batch.listeners.CustomerAvailabilityListener;
import com.linkedinlearning.batch.deciders.DeliveryDecider;
import com.linkedinlearning.batch.deciders.ReceiptDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class DeliveryJob {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    DeciderBeanImpl decideBeans;

    @Autowired
    DeliverySteps deliverySteps;

    @Autowired
    ApplicationContext appCtx;

//    @Bean
//    public JobExecutionDecider receiptDecider() {
//        return new ReceiptDecider();
//    }
//
//    @Bean
//    public JobExecutionDecider deliveryDecider(){
//        return new DeliveryDecider();
//    }

    @Autowired
    ReceiptDecider receiptDecider;

    @Autowired
    DeliveryDecider deliveryDecider;

    @Bean
    public Job deliverPackageJob() {
        return this.jobBuilderFactory.get("deliverPackageJob")

                //package the item and drive to address, if lost the address store the package
                .start(deliverySteps.packageItemStep()).next(deliverySteps.driveToAddressStep())
                .on("FAILED").to(deliverySteps.storePackageStep())

                //if reached address, hand over the package to customer if present
                .from(deliverySteps.driveToAddressStep()).on("SUCCESS")
                .to(deliveryDecider).on("PRESENT").to(deliverySteps.givePackageToCustomerStep())

                //thank the customer if satisfied with CORRECT item
                //initiate refund if customer unsatisfied with INCORRECT item
                .next(receiptDecider).on("CORRECT").to(deliverySteps.thankCustomerStep())
                .from(receiptDecider).on("INCORRECT").to(deliverySteps.initiateRefundStep())

                //if customer is not present at the address
                //if delivery.type=PREMIUM check for preferred delivery day with customer and retry
                .from(deliveryDecider).on("NOT_PRESENT").to(deliverySteps.checkDeliveryTypeStep())
                .on("CONTACT_THROUGH_PHONE").to(deliverySteps.waitForCustomerToCollect())
                .on("DELIVERY_RETRY").fail()

                //place the package at the door if customer is not present at the address
                //deliver package anyway if delivery.type=STANDARD
                .next(deliverySteps.checkDeliveryTypeStep()).on("DELIVER_PACKAGE_ANYWAY").to(deliverySteps.leavePackageAtDoorStep())
                .end()
                .build();
    }
}
