package lv.venta.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.venta.demo.enums.Condition;
import lv.venta.demo.enums.Genre;
import lv.venta.demo.models.Reader;
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


	@GetMapping("/admin/showAllReaders")//localhost:8080/admin/showAllReaders
	public String showAllReaders(Model model){
		model.addAttribute("inner", service.showAllReaders());
		return "reader-all-show";//reader-all-show.html
	}

	@GetMapping("/admin/showAllBooks")//localhost:8080/admin/showAllBooks
	public String showAllBooks(Model model){
		model.addAttribute("inner", service.showAllBooks());
		return "books-all-show";//books-all-show.html
	}


	@GetMapping("/admin/readerbyusername") //localhost:8080/admin/readerbyusername
	public String readerbyusername(Reader reader){
		return "get-username";
	}

	@PostMapping("/admin/readerbyusername")//localhost:8080/admin/readerbyusername
	public String readerbyusernamePost(Model model, @Valid Reader reader, BindingResult result){
		System.out.println("kkkk");
		if(!result.hasErrors()){
			model.addAttribute("inner", service.selectReaderByUsername(reader.getUsername()));
			return "reader-all-show"; 
		}
		else{
			return "get-username";
		}

	} 

	@GetMapping(reader)

	@GetMapping("/reader/showAllBooksByCondition")
		public String showAllBooksByCondition(Model model){
			model.addAttribute("type", Condition.values());
			return "get-book-condition";
		}
	

	@PostMapping("/reader/showAllBooksByCondition")
		public String showAllBooksByCondition (Model model, @RequestParam(name = "type") Condition condition)
				throws Exception {
		model.addAttribute("inner", service.selectAllBooksByCondition(condition));
		return "books-all-show"; }

	

	
}
