package com.linkedinlearning.batch.jobs;

//import com.linkedinlearning.batch.listeners.OnCustomerResponseDeliveryDecider;
//import com.linkedinlearning.batch.listeners.CustomerAvailabilityListener;
//import com.linkedinlearning.batch.deciders.beans.DeciderBeanImpl;
//import com.linkedinlearning.batch.jobs.steps.DeliverySteps;
//import com.linkedinlearning.batch.jobs.steps.BukeyPackageSteps;
//import com.linkedinlearning.batch.listeners.beans.ListenerBeanImpl;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.StepExecutionListener;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.job.builder.FlowBuilder;
//import org.springframework.batch.core.job.flow.Flow;
//import org.springframework.batch.core.job.flow.support.SimpleFlow;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

//@Configuration
public class FlowerBuckeyJob {


//    @Autowired
//    public JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    DeciderBeanImpl decideBeans;
//
//    @Autowired
//    ListenerBeanImpl listenerBeans;
//
//    @Autowired
//    DeliverySteps deliverySteps;
//
//    @Autowired
//    BukeyPackageSteps buckeySteps;
//
//    @Bean
//    public Flow deliveryFlow(){
//        System.out.println("----------------start delivery flow");
//        return new FlowBuilder<SimpleFlow>("deliveryFlow")
//                .start(deliverySteps.driveToAddressStep())
//                .on("FAILED").to(deliverySteps.storePackageStep())
//
//                //if reached address, hand over the package to customer if present
//                .from(deliverySteps.driveToAddressStep()).on("COMPLETED")
//                .to(decideBeans.deliveryDecider()).on("PRESENT").to(deliverySteps.givePackageToCustomerStep())
//
//                //thank the customer if satisfied with CORRECT item
//                //initiate refund if customer unsatisfied with INCORRECT item
//                .next(decideBeans.receiptDecider()).on("CORRECT").to(deliverySteps.thankCustomerStep())
//                .from(decideBeans.receiptDecider()).on("INCORRECT").to(deliverySteps.initiateRefundStep())
//
//                //if customer is not present at the address
//                //if delivery.type=PREMIUM check for preferred delivery day with customer and retry
//                .from(decideBeans.deliveryDecider()).on("NOT_PRESENT").to(deliverySteps.checkDeliveryTypeStep())
//                .on("CONTACT_THROUGH_PHONE").to(deliverySteps.waitForCustomerToCollect())
//                .on("DELIVERY_RETRY").fail()
//
//                //place the package at the door if customer is not present at the address
//                //deliver package anyway if delivery.type=STANDARD
//                .next(deliverySteps.checkDeliveryTypeStep())
//                .on("DELIVER_PACKAGE_ANYWAY").to(deliverySteps.leavePackageAtDoorStep()).build();
//    }

//    @Bean
//    public Job prepareFlowers() {
//        System.out.println("-------running prepareFlowes--------");
//        return this.jobBuilderFactory.get("prepareFlowersJob")
//                .start(buckeySteps.selectFlowersStep())
//                .on("TRIM_REQUIRED").to(buckeySteps.removeThornsStep()).
//                next(buckeySteps.arrangeFlowersStep())
//                .from(buckeySteps.selectFlowersStep())
//                .on("NO_TRIM_REQUIRED").to(buckeySteps.arrangeFlowersStep())
//                .from(buckeySteps.arrangeFlowersStep()).on("*").to(deliveryFlow())
//                .end()
//                .build();
//    }

//    @Bean
//    public Job deliverPackageJob() {
//        return this.jobBuilderFactory.get("deliverPackageJob")
//                .start(deliverySteps.packageItemStep())
//                .on("*").to(deliveryFlow())
//                .end()
//                .build();
//    }
}
