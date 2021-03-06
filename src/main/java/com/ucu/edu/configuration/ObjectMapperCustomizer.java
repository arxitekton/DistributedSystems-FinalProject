package com.ucu.edu.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ObjectMapperCustomizer {

	private static final String SPRING_HATEOAS_OBJECT_MAPPER = "_halObjectMapper";

	@Autowired
	@Qualifier(SPRING_HATEOAS_OBJECT_MAPPER)
	private ObjectMapper springHateoasObjectMapper;

	@Autowired
	private Jackson2ObjectMapperBuilder springBootObjectMapperBuilder;

	@Bean(name = "objectMapper")
	ObjectMapper objectMapper() {
		this.springBootObjectMapperBuilder.configure(this.springHateoasObjectMapper);

		return springHateoasObjectMapper;
	}

}
