package ua.softserve.rv_028.issuecitymonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This class, depending on the link, returns the corresponding page.
 * When a link of page will be opened in a new tab, it will return the
 * sought-for page.
 *
 * @author gefasim
 *
 */

@Controller
public class ViewController {

    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String dashboard(){
        return "index.html";
    }

    @RequestMapping(value = "issues", method = RequestMethod.GET)
    public String issues(){
        return "index.html";
    }

    @RequestMapping(value = "petitions", method = RequestMethod.GET)
    public String petitions(){
        return "index.html";
    }

    @RequestMapping(value = "events", method = RequestMethod.GET)
    public String events(){
        return "index.html";
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String users(){
        return "index.html";
    }

    @RequestMapping(value = "settings", method = RequestMethod.GET)
    public String settings(){
        return "index.html";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(){
        return "index.html";
    }

}
