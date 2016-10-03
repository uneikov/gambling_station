package com.uran.gamblingstation.controller;

import com.uran.gamblingstation.service.StakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StakeRestController {
    @Autowired
    private StakeService service;
}
