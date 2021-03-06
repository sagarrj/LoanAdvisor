package com.finance.loanadvisorr.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@EnableJpaRepositories(basePackages = {"com.finance.LoanAdvisor.entities"})
public class ApplicationConfig {

	@Bean
	public ModelMapper modelMapper(){
	    return new ModelMapper();
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
}
