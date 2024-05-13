package com.meer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-calendar")
@Tag(name = "CalendarRestController", description = "달력 정보")
public class CalendarRestController {

}
