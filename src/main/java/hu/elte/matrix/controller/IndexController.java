package hu.elte.matrix.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {

    private final String message = "Hello boyzzzzz and girlz!4";
    private final String pageTitle = "Kezdőlap";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Map<String, Object> model) {
        model.put("message", message);
        model.put("pageTitle", pageTitle);
        return "index";
    }
}
