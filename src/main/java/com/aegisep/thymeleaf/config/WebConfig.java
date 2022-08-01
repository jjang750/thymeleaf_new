package com.aegisep.thymeleaf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WebConfig {
    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @RequestMapping(value="/pages")
    public ModelAndView paging(@RequestParam(value="gotoPage") String gotoPage) {

        log.info("gotoPage=" + gotoPage);

        ModelAndView view = new ModelAndView();
        view.setViewName("/" + gotoPage);

        return view;
    }

}
