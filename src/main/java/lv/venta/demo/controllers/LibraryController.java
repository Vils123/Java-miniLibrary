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
import lv.venta.demo.models.Author;
import lv.venta.demo.models.Book;
import lv.venta.demo.models.Reader;
import lv.venta.demo.services.impl.LibraryServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;

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


	@GetMapping("/admin/readerByUsername") //localhost:8080/admin/readerByUsername
	public String readerbyusername(Reader reader){
		return "get-username";
	}

	@PostMapping("/admin/readerByUsername")//localhost:8080/admin/readerByUsername
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

	@GetMapping("/reader/booksByTitle")//localhost:8080/reader/booksByTitle
	public String booksByName(Book books){
		return "get-book-by-title";
	}

	@PostMapping("/reader/booksByTitle")//localhost:8080/reader/booksByTitle
	public String booksByAuthorTitle(Model model,@Valid Book book,BindingResult result){
		System.out.println("Working");
		if(!result.hasErrors()){
			model.addAttribute("inner", service.showAllBooksByTitle(book.getTitle()));
			return "books-all-show"; 
		}
		else{
			return "redirect:/reader/booksByTitle";
		}

	} 

	@GetMapping("/reader/showAllBooksByCondition")//localhost:8080/reader/showAllBooksByCondition
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
			return "admin-page";}
		else {
			return "reader-insert";
		}
	}


	@GetMapping("/admin/registerAdmin") //localhost:8080/admin/registerAdmin
	public String insertAdmin(Admin admin){
		return "admin-insert";
	}

	@PostMapping("/admin/registerAdmin")
	public String insertAdmin(@Valid Admin admin, BindingResult result) {
		System.out.println(admin);
		
		if(!result.hasErrors()) {
			service.addAdmin(admin);
			return "admin-page";}
		else {
			return "admin-insert";
		}
	}
	
	@GetMapping("/reader/showAllBooksByGenre")//localhost:8080/reader/showAllBooksByGenre
	public String showAllBooksByGenre(Model model){
		model.addAttribute("type", Genre.values());
		return "get-book-genre";
	}


@PostMapping("/reader/showAllBooksByGenre")
	public String showAllBooksByGenrePost (Model model, @RequestParam(name = "type") Genre genre)
			throws Exception {
	model.addAttribute("inner", service.selectAllBooksByGenre(genre));
	return "books-all-show"; }
	
@GetMapping("/admin/showAllAuthors") //localhost:8080/admin/showAllAuthors
	public String showAllAuthors(Model model){
		model.addAttribute("inner", service.showAllAuthors());
		return "author-all-show";
	}

@GetMapping("/reader/showAllAuthors") //localhost:8080/reader/showAllAuthors
	public String showAllAuthorsForReader(Model model){
		model.addAttribute("inner", service.showAllAuthors());
		return "author-all-show";
	}


@GetMapping("/home") //localhost:8080/home
	public String authoriseAdmin(Admin admin, Reader reader){
		return "home.html";}

@PostMapping("/home")
		public String authoriseAdmin(@Valid Admin admin,@Valid Reader reader, BindingResult result){
			System.out.println(admin.getUsername() + " " + admin.getPassword());
			if(service.authoriseAdmin(admin)){
				return "admin-page";
			}
			else if(service.authoriseReader(reader)){
				service.setCurrentReader(reader);
				return "reader-page";
			}
			else{
				return "redirect:/home";
			}
				
			
		}


@GetMapping("/admin/addNewAuthor") //localhost:8080/admin/addNewAuthor
	public String addNewAuthor(Model model, Author author){
		model.addAttribute("type", Genre.values());
		return "author-insert";
	}
		
	@PostMapping("/admin/addNewAuthor")
	public String addNewAuthor(Model model, @Valid Author author, @RequestParam(name = "type")Genre genre, BindingResult result)
			throws Exception {
			System.out.println("Adding a book");
		if(!result.hasErrors()){
			model.addAttribute("type", Genre.values());
			service.addAuthor(author);
			service.setCurrentAuthor(author);
			return "redirect:/admin/addNewBook";
		}
		else{
			return "author-insert";
			}
		}		

		
@GetMapping("/admin/addNewBook") //localhost:8080/admin/addNewBook
public String addNewBoook(Model model, Book book){
	model.addAttribute("type", Genre.values());
	return "book-insert";
}

@PostMapping("/admin/addNewBook")
public String addNewBook(Model model, @Valid Book book, @RequestParam(name = "type")Genre genre, BindingResult result)
		throws Exception {
	System.out.println("Adding a book");
	if(!result.hasErrors()){
		Book temp = new Book(book.getIsbn(),book.getTitle(),service.currentAuthor(),book.getPublishDate(),book.getGenre(),Condition.MINT);
		model.addAttribute("type", Genre.values());
		service.addNewBook(temp);
		return "redirect:/home";
	}
	else{
		return "author-insert";
	}
}


@GetMapping("/admin/deleteReader")//localhost:8080/admin/deleteReader
public String deleteReader(Reader reader){
	return "delete-by-username";}

@PostMapping("/admin/deleteReader")
public String deleteReaderPost(Model model, @Valid Reader reader, BindingResult result){
	if(!result.hasErrors()){
		model.addAttribute("inner", service.deleteReader(reader.getUsername()));
		return "reader-all-show"; 
	}
	else{
		return "delete-by-username";
}}











}





 
