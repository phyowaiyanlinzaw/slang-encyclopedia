package dictionary.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

import dictionary.dao.OtpDAO;
import dictionary.dao.UserDAO;
import dictionary.model.OtpBean;
import dictionary.model.UserBean;
import dictionary.services.OtpService;
import dictionary.dto.*;

@Controller
public class DictionaryController {
	
	@Autowired
	private UserDAO userDao;
	@Autowired
	private OtpDAO otpDao;
	
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
		
		return "redirect:/otpView";
	}
	
	@RequestMapping(value ="/otpView", method=RequestMethod.GET)
	public ModelAndView otpView(
			HttpSession session
			) {
		
		String genereatedOtp = OtpService.generateOtp();
		
		UserBean userBean = (UserBean) session.getAttribute("registeredUser");
		
		OtpService.sendEmail(userBean.getEmail(), "OTP", "Your OTP is : " +genereatedOtp);
		
		OtpRequestDTO req = new OtpRequestDTO();
		
		req.setOtpNumber(genereatedOtp);
		req.setRequestedBy(userBean.getEmail());
		req.setExpTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(1)));
		
		int result = otpDao.storeOtp(req);
		
		if(result ==0) {
			System.out.println("Insert OTP Error");
		}
		
		return new ModelAndView("otp", "otpBean", new OtpBean());
	}
	
	
	@RequestMapping(value = "/ProcessOtp",method = RequestMethod.POST)
	public String processOtp(
			@ModelAttribute("otpBean")
			@Validated
			OtpBean ob,
			BindingResult br,
			ModelMap m,
			HttpSession session
			) {
		
		OtpRequestDTO otpReq = new OtpRequestDTO();
		
		UserBean userBean = (UserBean) session.getAttribute("registeredUser");
		
		otpReq.setRequestedBy(userBean.getEmail());
		
		OtpResponseDTO res = otpDao.getOtp(otpReq);
		
		boolean isCorrectOTP = false;
		
		if(ob.getOtpNumber().equals(res.getOtpNumber())){
			isCorrectOTP = true;
			UserRequestDTO uReq = new UserRequestDTO();
			uReq.setEmail(userBean.getEmail());
			uReq.setUsername(userBean.getUsername());
			uReq.setPassword(userBean.getPassword());
			uReq.setConfirm_password(userBean.getConfirm_password());
			int result = userDao.storeUsers(uReq);
			
		}
		
		if(!isCorrectOTP) {
			return "redirect:/otpView";
		}
		
		return "redirect:/Login";
		
	}
	
	@RequestMapping(value="/Login",method=RequestMethod.GET)
	public ModelAndView loginView() {
		return new ModelAndView("Login", "loginBean" , new UserBean());
	}
	
	@RequestMapping(value="/ProcessLogin",method = RequestMethod.POST)
	public String processLogin(
			@ModelAttribute("loginBean")
			@Validated
			UserBean ub,
			BindingResult br,
			ModelMap m,
			HttpSession session
			) {
		
		ArrayList<UserResponseDTO> usersList = userDao.getAllUsers();
		UserResponseDTO adminAccount = userDao.getAdminAccount();
		
		
		boolean isCorrectUser = false;
		boolean isAdmin = false;
		boolean isUser = false;
		
		if(adminAccount.getEmail().equals(ub.getEmail())&&adminAccount.getPassword().equals(ub.getPassword())) {
			isAdmin = true;
			return "AdminView";
			
		}
		
		if(!isAdmin) {
			for(UserResponseDTO res : usersList) {
				if(res.getEmail().equals(ub.getEmail())&&res.getPassword().equals(ub.getPassword())) 
				{
					isCorrectUser = true;
					isUser = true;
					m.addAttribute("currentUser",res);
					break;
				}
			}
			
			if(!isCorrectUser) {
				m.addAttribute("incorrectUser", "Wrong Authentication");
				System.out.println("Wrong");
				return "Login";
			}
			
		}	
		
		session.setAttribute("isUser", isUser);
		session.setAttribute("isAdmin",isAdmin);
		
		
		return "UserProfile";
	}
	
	@RequestMapping(value="/Dashboard",method = RequestMethod.GET)
	public String userProfile(
			HttpSession session
			) {
		
		boolean isUser = (boolean) session.getAttribute("isUser");
		boolean isAdmin = (boolean) session.getAttribute("isAdmin");
		
		if(isAdmin) {
			return "AdminView";
		}
		
		if(isUser) {
			return "UserProfile";
		}
		
		if(!isAdmin && !isUser){
			return "redirect:/LogIn";
		}
		
		return "UserProfile";
	}
	
	
}
