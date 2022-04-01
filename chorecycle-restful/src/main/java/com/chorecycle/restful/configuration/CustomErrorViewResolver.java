package com.chorecycle.restful.configuration;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.chorecycle.restful.view.CustomDefaultJsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Resolves any error with the same view: a {@link CustomDefaultJsonView} that uses an auto-configured 
 * {@link ObjectMapper} and has {@link CustomDefaultJsonView#setPrettyPrint(boolean) pretty-printing} enabled 
 * by default.
 */
@Component
public class CustomErrorViewResolver implements ErrorViewResolver {
	private final ObjectMapper jsonMapper;
	
	/**
	 * Autowired constructor for use by the framework.
	 * @param jacksonObjectMapperBuilder - Spring should inject an auto-configured {@code Jackson2ObjectMapperBuilder}, 
	 * which will be used to obtain an auto-configured {@link ObjectMapper}
	 */
	@Autowired
	protected CustomErrorViewResolver(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		this.jsonMapper = jacksonObjectMapperBuilder.build();
	}
	
	/**
	 * 
	 * @return the auto-configured {@link ObjectMapper} that was obtained from the &commat;autowired 
	 * {@link Jackson2ObjectMapperBuilder}
	 */
	private ObjectMapper getObjectMapper() {
		return this.jsonMapper;
	}
	
	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
		CustomDefaultJsonView jsonView = new CustomDefaultJsonView(this.getObjectMapper());
		return new ModelAndView(jsonView, model);
	}
}