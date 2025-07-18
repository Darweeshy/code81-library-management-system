package org.example.librarymanagementsystem.controller;

import org.example.librarymanagementsystem.service.SystemUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class SystemUserController {

	private final SystemUserService systemUserService;

	public SystemUserController(SystemUserService systemUserService)
	{
		this.systemUserService = systemUserService;
	}

	// Endpoints will go here
}
