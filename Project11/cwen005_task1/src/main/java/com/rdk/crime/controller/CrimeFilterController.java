package com.rdk.crime.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrimeFilterController {

	@RequestMapping("/filter")
	@ResponseBody
	public String filter(Model model) {
		String result = null;

		return result;
	}

}
