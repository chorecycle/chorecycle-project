package com.chorecycle.restful.view;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.json.AbstractJackson2View;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;

/**
 * Mostly similar to {@link org.springframework.web.servlet.view.json.MappingJackson2JsonView MappingJackson2JsonView}, 
 * except that {@link #filterModel(Map)} uses a {@link java.util.LinkedHashMap LinkedHashMap} instead of a regular 
 * {@link java.util.HashMap HashMap}, so as not to change the order of the keys.
 * <br>
 * <br>This class also provides a public accessor method ({@link #getExtractValueFromSingleKeyModel()}) to go along 
 * with the mutator method {@link #setExtractValueFromSingleKeyModel(boolean)}.
 * 
 * Custom prefixes for the JSON output are not supported. Pretty printing is enabled by default.
 */
public class CustomDefaultJsonView extends AbstractJackson2View {
	
	/**
	 * Default content type: "application/json".
	 * Overridable through {@link org.springframework.web.servlet.view.AbstractView#setContentType(String) 
	 * setContentType(String)}.
	 */
	public static final String DEFAULT_CONTENT_TYPE = MimeTypeUtils.APPLICATION_JSON_VALUE;
	
	private final Set<String> modelKeys;
	private boolean extractValueFromSingleKeyModel = false;

	/**
	 * Construct a new {@code CustomDefaultJsonView} using the provided {@link ObjectMapper}. Pretty printing is 
	 * enabled on the {@code ObjectMapper}, and the content type is set to the default value.
	 */
	public CustomDefaultJsonView(ObjectMapper objectMapper) {
		super(objectMapper, DEFAULT_CONTENT_TYPE);
		this.modelKeys = new HashSet<>();
		this.setPrettyPrint(true);
	}

	/**
	 * {@inheritDoc}
	 * @param modelKey - the attribute to render
	 */
	@Override
	public void setModelKey(String modelKey) {
		this.setModelKeys(Collections.singleton(modelKey));
	}
	
	/**
	 * Set the attributes in the model that should be rendered by this view. When set, all other model attributes will 
	 * be ignored.
	 * @param modelKeys - the attributes to render
	 */
	public void setModelKeys(Set<String> modelKeys) {
		this.modelKeys.clear();
		this.modelKeys.addAll(modelKeys);
	}
	
	/**
	 * Set whether to serialize models containing a single attribute as a map or whether to extract the single value 
	 * from the model and serialize it directly.
	 * <br>
	 * <br>The effect of setting this flag is similar to using 
	 * {@link org.springframework.http.converter.json.MappingJackson2HttpMessageConverter 
	 * MappingJackson2HttpMessageConverter} with an 
	 * {@link org.springframework.web.bind.annotation.ResponseBody @ResponseBody} request-handling method.
	 * <br>
	 * <br>Default is {@code false}.
	 * @param extractValueFromSingleKeyModel - true if single-attribute models should have the single value extracted and 
	 * serialized directly; false otherwise
	 */
	public void setExtractValueFromSingleKeyModel(boolean extractValueFromSingleKeyModel) {
		this.extractValueFromSingleKeyModel = extractValueFromSingleKeyModel;
	}
	
	/**
	 * Return the attributes in the model that should be rendered by this view.
	 * @return the attributes
	 */
	public Set<String> getModelKeys() {
		return new HashSet<>(this.modelKeys);
	}
	
	/**
	 * Checks whether the option to extract the value of a single-key model and encode it by itself is enabled.
	 * @return true if the option is enabled; false otherwise
	 */
	public boolean getExtractValueFromSingleKeyModel() {
		return this.extractValueFromSingleKeyModel;
	}
	
	/**
	 * Whether to use the default pretty printer when writing the output. This is a shortcut for configuring this 
	 * class's {@code ObjectMapper} (with the appropriate 
	 * {@link ObjectMapper#configure(com.fasterxml.jackson.databind.SerializationFeature, boolean) configure} method) 
	 * to set {@link com.fasterxml.jackson.databind.SerializationFeature#INDENT_OUTPUT 
	 * SerializationFeature.INDENT_OUTPUT} to {@code true}.
	 * <br>
	 * <br>{@code CustomDefaultJsonView} has pretty printing enabled by default.
	 * @param prettyPrint - true if pretty printing should be used when writing the output; false otherwise
	 */
	@Override
	public void setPrettyPrint(boolean prettyPrint) {
		super.setPrettyPrint(prettyPrint);
	}

	/**
	 * {@inheritDoc}
	 * <br>
	 * <br>Like {@link org.springframework.web.servlet.view.json.MappingJackson2JsonView MappingJackson2JsonView}, this 
	 * implementation removes {@link BindingResult} instances and entries not included in the 
	 * {@link #setModelKeys modelKeys} property. Uses a {@link java.util.LinkedHashMap LinkedHashMap} instead of a 
	 * regular {@link java.util.HashMap HashMap}, so as not to change the order of the keys.
	 * @param model - the model, as passed on to {@link AbstractJackson2View#renderMergedOutputModel(
	 * Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) renderMergedOutputModel}
	 */
	@Override
	protected Object filterModel(Map<String, Object> model) {
		Map<String, Object> result = CollectionUtils.newLinkedHashMap(model.size());
		Set<String> modelKeys = (!CollectionUtils.isEmpty(this.getModelKeys()) ? this.getModelKeys() : model.keySet());
		model.forEach((key, value) -> {
			if (!(value instanceof BindingResult) && modelKeys.contains(key) &&
					!key.equals(JsonView.class.getName()) &&
					!key.equals(FilterProvider.class.getName())) {
				result.put(key, value);
			}
		});
		return (this.getExtractValueFromSingleKeyModel() && result.size() == 1 ?
				result.values().iterator().next() : result);
	}
}