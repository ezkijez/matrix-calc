package hu.elte.matrix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class IndexController {

    private final String pageTitle = "Kezdőlap";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Map<String, Object> model) {
        model.put("pageTitle", pageTitle);
        return "index";
    }
}
