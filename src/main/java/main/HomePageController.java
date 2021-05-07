package main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomePageController {

    @RequestMapping("/")
    public ModelAndView home(Model model) {
        return new ModelAndView("redirect:/h2-console");
    }
}
