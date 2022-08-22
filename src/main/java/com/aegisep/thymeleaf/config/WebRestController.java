package com.aegisep.thymeleaf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
public class WebRestController {
    private static final Logger log = LoggerFactory.getLogger(WebRestController.class);

    @RequestMapping(value="/pages")
    public ModelAndView paging(@RequestParam(value="gotoPage") String gotoPage) {

        log.info("gotoPage=" + gotoPage);

        ModelAndView view = new ModelAndView();
        view.setViewName("/" + gotoPage);

        return view;
    }

    @RequestMapping(value={"/index"})
    public ModelAndView index() {

        log.info("goto login");

        ModelAndView view = new ModelAndView();
        view.setViewName("index");

        return view;
    }


    @RequestMapping(value={"/login"})
    public ModelAndView login() {

        log.info("goto login");

        ModelAndView view = new ModelAndView();
        view.setViewName("/login");

        return view;
    }
    @RequestMapping(value={"/logout"})
    public ModelAndView logout() {
        return index();
    }

    @RequestMapping(value={"/api"})
    public String api() {
        log.info("goto api");
        return "hello api";
    }

    @RequestMapping(value="/signin")
    public ModelAndView signin() {
        log.info("goto signin");
        ModelAndView view = new ModelAndView();
        view.setViewName("/signin");
        view.addObject("token", UUID.randomUUID().toString());

        return view;
    }

    @RequestMapping(value="/signing")
    public ModelAndView signing() {
        log.info("goto signing");
        ModelAndView view = new ModelAndView();
        view.setViewName("/login");
        return view;
    }


}
