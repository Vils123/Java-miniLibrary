package lv.venta.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lv.venta.demo.services.impl.LibraryServiceImpl;
@Controller
public class LibraryController {
	@Autowired
	LibraryServiceImpl service;
	
	@GetMapping("/admin/inputdata")  //localhost:8080/admin/inputdata
	public String callInputDataService()
	{
		System.out.println("not done");
		service.inputdata();
		System.out.println("done");
		return "ok";
	}
	
}
