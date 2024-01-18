package com.rdk.crime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rdk.crime.importer.ImportService;

@Controller
public class HomeController {

	@Autowired
	private ImportService importService;

	@RequestMapping("/")
	public String index() {
        return "index";
	}

	@RequestMapping("/import")
	public String load() {

		importService.readAndStoreCrimes();

		return "imported";
	}

}
