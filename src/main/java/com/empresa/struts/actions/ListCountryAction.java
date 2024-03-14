package com.empresa.struts.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Get a list of countries
 * 
 * @author joseluis
 *
 */
public class ListCountryAction extends ActionSupport implements ServletResponseAware {
	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * Execute the listcountries action metodo de Struts
	 */
	public String execute() throws Exception {
		// Get list of countries, passing the default location
//		mapCountries = getListOfCountries();
		return SUCCESS;
	}

	/**
	 * Metodo Rest
	 * Get the list of countries by location
	 *
	 *            location
	 * @return a list of countries
	 */
	public String getListOfCountries() throws IOException {
		try {
			buildHttpResponse(createMapOfCountries());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	private void buildHttpResponse(Object objectToReturn) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(objectToReturn);
		response.getWriter().write(json);
	}

	private Map<String, String> createMapOfCountries() {
		// Get an array of string with the list of countries
		String[] locales = Locale.getISOCountries();
		Map<String, String> countries = new HashMap<String, String>();
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			// Store the code and name of country into the countries map
			countries.put(obj.getCountry(), obj.getDisplayCountry(Locale.getDefault()));
		}
		return countries;
	}
}
