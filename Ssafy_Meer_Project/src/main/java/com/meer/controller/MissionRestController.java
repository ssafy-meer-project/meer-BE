package com.meer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-mission")
@Tag(name = "MissionRestController", description = "미션정보")
public class MissionRestController {

}
