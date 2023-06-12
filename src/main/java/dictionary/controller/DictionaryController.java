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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dictionary.dao.OtpDAO;
import dictionary.dao.UserDAO;
import dictionary.model.OtpBean;
import dictionary.model.UserBean;
import dictionary.services.OtpService;
import dictionary.dto.UserRequestDTO;
import dictionary.dto.UserResponseDTO;
import dictionary.dto.OtpRequestDTO;
import dictionary.dto.OtpResponseDTO;

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
			HttpSession session,
			ModelMap m
			)
			{
		
		String genereatedOtp = OtpService.generateOtp();
		
		UserBean userBean = (UserBean) session.getAttribute("registeredUser");
		
		OtpService.sendEmail(userBean.getEmail(), "OTP", "Your OTP is : " +genereatedOtp);
		
		OtpRequestDTO req = new OtpRequestDTO();
		
		req.setOtpNumber(genereatedOtp);
		req.setRequestedBy(userBean.getEmail());
		req.setExpTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(1)));
		
		int otpStoreResult = otpDao.storeOtp(req);
		int otpCount = otpDao.getOtpCounts(userBean.getEmail());
//		int otpCountStoreResult = userDao.storeOtpCount(otpCount);
		
		session.setAttribute("currentOtp", genereatedOtp);
		
//		m.addAttribute("otp", genereatedOtp);
		m.addAttribute("otpCount", otpCount);
		
		if(otpStoreResult ==0) {
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
			
			if(result==0) {
				System.out.println("Insert user error");
			}
			
		}
		
		if(!isCorrectOTP) {
			m.addAttribute("wrongOtp", "wrong");
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
			session.setAttribute("isAdmin",isAdmin);
			return "AdminView";
		}
		
		if(!isAdmin) {
			for(UserResponseDTO res : usersList) {
				if(res.getEmail().equals(ub.getEmail())&&res.getPassword().equals(ub.getPassword())) 
				{
					isCorrectUser = true;
					isUser = true;
					session.setAttribute("currentUser", res);
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
		session.setAttribute("isLoggedIn", "logged in");
		
		return "UserProfile";
	}
	
	@RequestMapping(value="/Dashboard",method = RequestMethod.GET)
	public String userProfile(
			HttpSession session
			) {
		
		if(session.getAttribute("isUser")!=null) {
			return "UserProfile";
		}
		
		else if (session.getAttribute("isAdmin")!=null) {
			return "AdminView";
		}
		
		if(session.getAttribute("isLoggedIn")==null){
			return "redirect:/Login";
		}
		
		return "UserProfile";
	}
	
	@RequestMapping(value="/UpdateOtpStatus/{otpNumber}",method = RequestMethod.GET)
	public String updateOtpStatus(
		HttpSession session
		) {

		OtpRequestDTO req = new OtpRequestDTO();
		
		String otpNumber = (String) session.getAttribute("currentOtp");

		req.setOtpNumber(otpNumber);
		
		System.out.println(otpNumber);
		System.out.println(req.getOtpNumber());

		int result = otpDao.updateOtpStatus(req);

		if(result == 0) {
			
			System.out.println("Update OTP Status Error");
		}
		
		return "redirect:/otpView";

	}
	
	@RequestMapping(value="/LogOut",method = RequestMethod.GET)
	public String logOut(
			HttpSession session
			) {
		
		session.invalidate();
		
		
		return "DefinitionView";
	}


	
}
