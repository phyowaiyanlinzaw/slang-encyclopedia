package dictionary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dictionary.model.UserBean;

@Controller
public class DictionaryController {
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String homeView() {
		return "index";
	}
	
	@RequestMapping(value="/DefinitionView",method=RequestMethod.GET)
	public ModelAndView definitionView() {
		return new ModelAndView("DefinitionView","userBean",new UserBean());
	}
	
	
	

}
