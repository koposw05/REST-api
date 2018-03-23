package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuideController {
	 @Autowired
	    private testDao testDao;
	 	
	@RequestMapping("/guide")
	public String guide() {
		int count = testDao.getCount();
		System.out.println("count \n"+count);
		if(count < 2) {
		testDao.insertNewContent();
		}
		return "guide";
	}
	
	@RequestMapping("/")
	public String root() {
		
		
		return "redirect:/guide";
	}
	
}
