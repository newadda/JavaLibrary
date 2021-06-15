package org.onecell.example.spring.batch;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;

public interface IBatchConfig {

    JobRepository getJobRepository();

    JobLauncher getJobLauncher();

    JobExplorer getJobExplorer();

    StepBuilderFactory getStepBuilderFactory();

    JobBuilderFactory getJobBuilderFactory();

    JobRegistry getJobRegistry();

    JobOperator getJobOperator();

}
