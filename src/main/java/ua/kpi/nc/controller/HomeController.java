package ua.kpi.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.domain.dao.UserDao;

/**
 * Created by dima on 12.04.16.
 */
@Controller
public class HomeController{

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }

}
