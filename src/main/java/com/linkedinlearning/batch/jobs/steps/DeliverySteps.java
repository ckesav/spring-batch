package com.linkedinlearning.batch.jobs.steps;

import com.linkedinlearning.batch.listeners.beans.ListenerBeanImpl;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class DeliverySteps {

    @Autowired
    ListenerBeanImpl listenerBean;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step thankCustomerStep() {
        return this.stepBuilderFactory.get("thankCustomerStep").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Thanking the customer.");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
    public Step refundStep() {
        return this.stepBuilderFactory.get("refundStep").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Refunding customer money.");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }
    @Bean
    public Step leaveAtDoorStep() {
        return this.stepBuilderFactory.get("leaveAtDoorStep").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Leaving the package at the door.");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
    public Step storePackageStep() {
        return this.stepBuilderFactory.get("storePackageStep").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Storing the package while the customer address is located.");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
    public Step givePackageToCustomerStep() {
        return this.stepBuilderFactory.get("givePackageToCustomer").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Given the package to the customer.");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
//    @Autowired
    public Step driveToAddressStep() {

        boolean GOT_LOST = false;
        return this.stepBuilderFactory.get("driveToAddressStep").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                if(GOT_LOST) {
                    throw new RuntimeException("Got lost driving to the address");
                }

                System.out.println("Successfully arrived at the address.");
                return RepeatStatus.FINISHED;
            }
        }).listener(listenerBean.onCustAddressFound()).build();
    }

    @Bean
    public Step packageItemStep() {
        return this.stepBuilderFactory.get("packageItemStep").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String item = chunkContext.getStepContext().getJobParameters().get("item").toString();
                String date = chunkContext.getStepContext().getJobParameters().get("run.date").toString();

                System.out.println(String.format("The %s has been packaged on %s.", item, date));
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
    public Step initiateRefundStep(){
        return this.stepBuilderFactory.get("stepToInitiateRefund").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Initiate refund as incorrect package");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
    public Step waitForCustomerToCollect(){
        return this.stepBuilderFactory.get("stepToWaitForCustomerToCollect").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Wait for customer to collect premium delivery package  ");
                return RepeatStatus.FINISHED;
            }
        }).listener(listenerBean.onCustRespDeliveryDecide()).build();
    }

    @Bean
    public Step leavePackageAtDoorStep(){
        return this.stepBuilderFactory.get("stepToLeavePackageAtDoor").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Leave package at door when customer not at home");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
    public Step checkDeliveryTypeStep(){
        return this.stepBuilderFactory.get("stepToCheckDeliveryType").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                return RepeatStatus.FINISHED;
            }
        }).listener(listenerBean.customerAvailabilityListener()).build();
    }
}
