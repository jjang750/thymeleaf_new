package com.aegisep.thymeleaf.config;

import com.aegisep.thymeleaf.repository.CustomUserRepository;
import com.aegisep.thymeleaf.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
public class WebRestController {
    private static final Logger log = LoggerFactory.getLogger(WebRestController.class);

    @Autowired
    CustomUserRepository customUserRepository;

    @RequestMapping(value="/pages")
    public ModelAndView paging(@RequestParam(value="gotoPage") String gotoPage) {

        log.info("gotoPage=" + gotoPage);

        ModelAndView view = new ModelAndView();
        view.setViewName("/" + gotoPage);

        return view;
    }

    @RequestMapping(value={"/index"})
    public ModelAndView index() {

        log.info("goto index");

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
    @RequestMapping(value="/signin/checkuserid")
    public String checkuserid(@RequestParam(value="userid") String userid) {
        log.info("goto /signin/checkuserid");

        User user =  customUserRepository.findByUserid(userid);

        if (user == null) {
            return "<li class='on' id='useridcheck'>" + userid + "???(???) ???????????????.</li>";
        }else{
            return "<li id='useridcheck'>" + userid + "???(???) ?????? ??????????????????.</li>";
        }
    }

    @RequestMapping(value="/signing")
    public ModelAndView signing(User userDTO) {
        log.info("goto signing");
        ModelAndView view = new ModelAndView();
        view.setViewName("/login");
        log.info(">>>>>>>>>>>>>>>>>>>>> " + userDTO);
        userDTO.setAuth_group("USER");
        userDTO.setAuth_id("USER");
        User insert = customUserRepository.saveAndFlush(userDTO);
        log.info(">>>>>>>>>>>>>>>>>>>>> " + insert);
        return view;
    }

    @RequestMapping(value="/admin")
    public ModelAndView admin() {
        log.info("goto signing");
        ModelAndView view = new ModelAndView();
        view.setViewName("/admin");
        return view;
    }

    @RequestMapping(value="/manager")
    public ModelAndView manager() {
        log.info("goto signing");
        ModelAndView view = new ModelAndView();
        view.setViewName("/manager");
        return view;
    }

}
