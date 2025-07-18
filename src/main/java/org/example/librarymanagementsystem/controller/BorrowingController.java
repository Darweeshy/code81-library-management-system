package org.example.librarymanagementsystem.controller;

import org.example.librarymanagementsystem.service.BorrowingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class BorrowingController {

	private final BorrowingService borrowingService;

	public BorrowingController(BorrowingService borrowingService) {
		this.borrowingService = borrowingService;
	}

	// Endpoints will go here
}
