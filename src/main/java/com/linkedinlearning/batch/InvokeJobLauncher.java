package com.linkedinlearning.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class InvokeJobLauncher {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job deliverPackageJob;

    @PostConstruct
    public void init() throws Exception{
        System.out.println("launching job from init method");
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(deliverPackageJob, jobParameters);
    }

}
