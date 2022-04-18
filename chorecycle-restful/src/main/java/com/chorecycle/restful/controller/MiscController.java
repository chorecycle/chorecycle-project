package com.chorecycle.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/** Miscellaneous mappings. */
@Controller
public class MiscController {
	
	/** Gracefully refuse to display a favicon. */
	@GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }
}