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
import lv.venta.demo.models.Admin;
import lv.venta.demo.models.Book;
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


	@GetMapping("/admin/readerByUsername") //localhost:8080/admin/readerbyusername
	public String readerbyusername(Reader reader){
		return "get-username";
	}

	@PostMapping("/admin/readerByUsername")//localhost:8080/admin/readerbyusername
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

	@GetMapping("/reader/booksByTitle")//localhost:8080/reader/booksbytitle
	public String booksByName(Book books){
		return "get-book-by-title";
	}

	@PostMapping("/reader/booksByTitle")//localhost:8080/reader/booksbytitle
	public String booksByAuthorTitle(Model model,@Valid Book book,BindingResult result){
		System.out.println("Working");
		if(!result.hasErrors()){
			model.addAttribute("inner", service.showAllBooksByTitle(book.getTitle()));
			return "books-all-show"; 
		}
		else{
			return "get-book-by-title";
		}

	} 

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

	@GetMapping("/admin/registerReader") //localhost:8080/admin/registerReader
	public String insertReader(Reader reader){
		return "reader-insert";
	}

	@PostMapping("/admin/registerReader")
	public String insertPatient(@Valid Reader reader, BindingResult result) {
		System.out.println(reader);
		
		if(!result.hasErrors()) {
			service.addReader(reader);
			return "ok";}
		else {
			return "reader-insert";
		}
	}


	@GetMapping("/admin/registerAdmin") //localhost:8080/admin/registerAdmin
	public String insertAdmin(Admin admin){
		return "admin-insert";
	}

	@PostMapping("/admin/registerAdmin")
	public String insertPatient(@Valid Admin admin, BindingResult result) {
		System.out.println(admin);
		
		if(!result.hasErrors()) {
			service.addAdmin(admin);
			return "ok";}
		else {
			return "admin-insert";
		}
	}
	
	@GetMapping("/reader/showAllBooksByGenre")
	public String showAllBooksByGenre(Model model){
		model.addAttribute("type", Genre.values());
		return "get-book-genre";
	}


@PostMapping("/reader/showAllBooksByGenre")
	public String showAllBooksByGenrePost (Model model, @RequestParam(name = "type") Genre genre)
			throws Exception {
	model.addAttribute("inner", service.selectAllBooksByGenre(genre));
	return "books-all-show"; }
	



}
