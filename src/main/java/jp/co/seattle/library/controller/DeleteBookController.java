package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.service.BooksService;

@Controller
public class DeleteBookController {
	final static Logger logger = LoggerFactory.getLogger(DeleteBookController.class);

	@Autowired
	private BooksService booksService;

	@RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
	public String deleteBook(Locale locale, int bookId, Model model) {
		logger.info("Welcome deleteBook! The client locale is {}.", locale);

		booksService.deleteBook(bookId);

		return "redirect:/home";
	}
}