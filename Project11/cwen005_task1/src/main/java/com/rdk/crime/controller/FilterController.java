package com.rdk.crime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rdk.crime.domain.Crime;
import com.rdk.crime.repository.CrimeRepository;

@RestController()
public class FilterController {

	@Autowired
	private CrimeRepository repository;

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public List<Crime> filter(
			@RequestParam(value = "year", defaultValue = "2001") String year,
			@RequestParam(value = "crimeType") String crimeType, Model model) {

		List<Crime> crimes = repository.findFirst100ByPrimaryTypeAndYear(
				crimeType, year);

		return crimes;
	}

}
