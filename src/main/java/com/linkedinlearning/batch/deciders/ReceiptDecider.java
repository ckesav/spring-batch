package com.linkedinlearning.batch.deciders;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ReceiptDecider  implements JobExecutionDecider {
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String exitcode = new Random().nextFloat() < .70f ? "CORRECT" : "INCORRECT";
        System.out.println("Item delivered with exit code "+exitcode);
        return new FlowExecutionStatus(exitcode);
    }
}
