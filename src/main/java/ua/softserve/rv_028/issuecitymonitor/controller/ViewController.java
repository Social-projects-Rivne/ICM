package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping(value = {"/dashboard", "/issues", "/petitions", "/events", "/users", "/settings"})
    public String dashboard(){
        return "index.html";
    }

}
