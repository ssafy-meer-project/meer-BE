package com.meer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-fortune")
@Tag(name = "FortuneRestController", description = "포츈쿠키 정보")
public class fortuneRestController {
	


}
