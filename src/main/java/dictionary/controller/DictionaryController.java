package dictionary.controller;

import org.springframework.stereotype.Controller;

@Controller
public class DictionaryController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }

}
