package com.isuper.soft.home.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Value("${spring.jpa.database}")
	String database;

	@RequestMapping("/show")
	public String show() {
		return "The database  is: " + database;
	}
}
