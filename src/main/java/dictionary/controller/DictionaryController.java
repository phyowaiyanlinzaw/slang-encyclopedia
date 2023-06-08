package dictionary.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dictionary.dao.UserDAO;
import dictionary.model.OtpBean;
import dictionary.model.UserBean;
import dictionary.services.OtpService;
import dictionary.dto.*;

@Controller
public class DictionaryController {
	
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String homeView() {
		return "index";
	}
	
	@RequestMapping(value="/DefinitionView",method=RequestMethod.GET)
	public String definitionView() {
		return "DefinitionView";
	}
	
	@RequestMapping(value="/Register", method=RequestMethod.GET)
	public ModelAndView registerView() {
		return new ModelAndView("Register", "registerBean" , new UserBean());
	}
	
	@RequestMapping(value="/Login",method=RequestMethod.GET)
	public ModelAndView loginView() {
		return new ModelAndView("Login", "loginBean" , new UserBean());
	}
	
	
	@RequestMapping(value="/ProcessRegister", method=RequestMethod.POST)
	public String register(
			@ModelAttribute ("registerBean") 
			@Validated UserBean ub,
			BindingResult br,
			ModelMap m,
			HttpSession session) {
		
		if(br.hasErrors()) {
			return "redirect:/Register";
		}
		
		boolean isSamePw = false;
		
		UserRequestDTO req = new UserRequestDTO();
		req.setUsername(ub.getUsername());
		req.setEmail(ub.getEmail());
		req.setPassword(ub.getPassword());
		req.setConfirm_password(ub.getConfirm_password());
		
		ArrayList<UserResponseDTO> resList = userDao.getAllUsers();
		
		if(ub.getPassword().equals(ub.getConfirm_password()) ) {
			isSamePw = true;
			for(UserResponseDTO res : resList) {
				if(res.getEmail().equals(ub.getEmail())) {
					m.addAttribute("emailDupe", "This email already exists");
					return "redirect:/Register";
				}
				
			}
			
			if(!isSamePw) {
				m.addAttribute("pwError","Passwords Don't Match");
				return "Register";
			}
		}
		
		session.setAttribute("registeredUser", ub);
		
		return "DefinitionView";
	}
	
	@RequestMapping(value ="/otpView", method=RequestMethod.GET)
	public ModelAndView otpView(
			HttpSession session
			) {
		
		String genereatedOtp = OtpService.generateOtp();
		
		UserBean userBean = (UserBean) session.getAttribute("registeredUser");
		
		OtpService.sendEmail(userBean.getEmail(), "OTP", "Your OTP is : " +genereatedOtp);
		
		return new ModelAndView("otp", "otpBean", new OtpBean());
	}
	
	
	
	
}
