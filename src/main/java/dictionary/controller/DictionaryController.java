package dictionary.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.cj.Session;

import dictionary.dao.DefinitionDAO;
import dictionary.dao.OtpDAO;
import dictionary.dao.TermDAO;
import dictionary.dao.UserDAO;
import dictionary.model.DefinitionAndTermBean;
import dictionary.model.OtpBean;
import dictionary.model.UserBean;
import dictionary.services.OtpService;
import dictionary.dto.UserRequestDTO;
import dictionary.dto.UserResponseDTO;
import dictionary.dto.DefandTermRequestDTO;
import dictionary.dto.DefandTermResponseDTO;
import dictionary.dto.OtpRequestDTO;
import dictionary.dto.OtpResponseDTO;

@Controller
public class DictionaryController {
	
	@Autowired
	private UserDAO userDao;
	@Autowired
	private OtpDAO otpDao;
	@Autowired
	private DefinitionDAO definitionDao;
	@Autowired
	private TermDAO termDao;
	
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
		boolean isDupe = false;
		
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
					isDupe = true;
					m.addAttribute("emailDupe", "This email already exists");
					return "redirect:/Register";
				}
				
			}
			if(!isDupe) {
				int result = userDao.storeUsers(req);
				session.setAttribute("registeredEmail", ub.getEmail());
				if(result==0) {
					m.addAttribute("insertUserError", "Error While Registering User");
					return "redirect:/Register";
				}
			}
			
			
		}
		if(!isSamePw) {
			m.addAttribute("pwError","Passwords Don't Match");
			return "Register";
		}
		
		return "redirect:/RequestOTP";
	}

	@RequestMapping(value="/RequestOTP",method = RequestMethod.GET)
	public String requestOTP(
			HttpSession session,
			ModelMap m
			) {
				
				
				boolean isLimit = false;
				String registeredEmail = (String) session.getAttribute("registeredEmail");
				int otpCount = 0;
				int userId = userDao.getUserId(registeredEmail);
				
				if(otpCount > 5) {
					isLimit = true;
					int deleteResult = otpDao.deleteOtps(req);
					if(deleteResult==0) {
						System.out.println("Error while deleting OTP");
					}
					req.setRestrictTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));
					req.setOtpCount(0);
					int addRestrictionTimeResult = otpDao.addRestrictionTime(req);
					if(addRestrictionTimeResult==0) {
						System.out.println("Error while adding restriction time");
					}
					session.setAttribute("otpLimit", "true");

					if(req.getRestrictTime().after(Timestamp.valueOf(LocalDateTime.now()))) {
						isLimit =false;
						session.setAttribute("otpLimit", "false");

					}
				}
				if(!isLimit) {
					String genereatedOtp = OtpService.generateOtp();
					session.setAttribute("currentOtp", genereatedOtp);
					
					otpCount = otpCount > 0 ? otpCount + 1 : 1;
					req.setOtpNumber(genereatedOtp);
					req.setRequestedBy(registeredEmail);
					req.setOtpCount(otpCount);
					req.setUserId(userId);
					int result = otpDao.storeOtp(req);
					if(result==0) {
						System.out.println("Error while storing OTP");
					}
					OtpService.sendEmail(registeredEmail, "OTP", "Your OTP is : " +genereatedOtp);
				}

				
		
		// String genereatedOtp = OtpService.generateOtp();
		// session.setAttribute("currentOtp", genereatedOtp);
		// String registeredEmail = (String) session.getAttribute("registeredEmail");
		
		// OtpRequestDTO req = new OtpRequestDTO();
		// int userId = userDao.getUserId(registeredEmail);
		// int otpCount = otpDao.getOtpCounts(registeredEmail);
		
		// if(otpCount > 0 && otpCount <6) {
		// 	otpCount++;
		// 	req.setOtpNumber(genereatedOtp);
		// 	req.setRequestedBy(registeredEmail);
		// 	req.setOtpCount(otpCount);
		// 	req.setUserId(userId);
			
		// 	int result = otpDao.storeOtp(req);
			
		// 	if(result==0) {
		// 		System.out.println("Error while storing OTP");
		// 	}
			
		// 	OtpService.sendEmail(registeredEmail, "OTP", "Your OTP is : " +genereatedOtp);
		// }else if(otpCount>5) {
		// 	int deleteResult = otpDao.deleteOtps(req);
		// 	if(deleteResult==0) {
		// 		System.out.println("Error while deleting OTPs");
		// 	}
		// 	int aiResult = otpDao.alterIncrement(1);
		// 	if(aiResult==0) {
		// 		System.out.println("Error while resetting auto_increment");
		// 	}
		// 	req.setRestrictTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(3)));
		// 	int addRTimeResult = otpDao.addRestrictionTime(req);
		// 	if(addRTimeResult==0) {
		// 		System.out.println("Error while adding restriction query");
		// 	}
		// 	m.addAttribute("otpLimit", "true");
			
		// }else {
		// 	otpCount = 1;
		// 	req.setOtpNumber(genereatedOtp);
		// 	req.setRequestedBy(registeredEmail);
		// 	req.setOtpCount(otpCount);
		// 	req.setUserId(userId);
			
		// 	int result = otpDao.storeOtp(req);
			
		// 	if(result==0) {
		// 		System.out.println("Error while storing OTP");
		// 	}
			
		// 	OtpService.sendEmail(registeredEmail, "OTP", "Your OTP is : " +genereatedOtp);
		// }
		

		
		return "redirect:/otpView";
	}
	
	@RequestMapping(value ="/otpView", method=RequestMethod.GET)
	public ModelAndView otpView(ModelMap m,HttpSession session)
			{
		
		m.addAttribute("otpLimit", session.getAttribute("otpLimit"));
		
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
	
	@RequestMapping(value="/UpdateOtpStatus",method = RequestMethod.GET)
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
		
		return "redirect:/RequestOTP";

	}
	
	@RequestMapping(value="/LogOut",method = RequestMethod.GET)
	public String logOut(
			HttpSession session
			) {
		
		session.invalidate();
		
		
		return "DefinitionView";
	}
	
	@RequestMapping(value = "/UploadForm", method = RequestMethod.GET)
	public ModelAndView uploadFormView() {
		return new ModelAndView("UploadForm", "termandDefBean", new DefinitionAndTermBean());
	}
	
	@RequestMapping(value="/ProcessUpload", method=RequestMethod.POST)
	public String processUpload(@ModelAttribute ("termandDefBean") @Validated DefinitionAndTermBean dat,BindingResult br,ModelMap m,HttpSession session) {
	    
	    
	    
		DefandTermRequestDTO upldt = new DefandTermRequestDTO(); 
	    upldt.setTerm(dat.getTerm());
	    upldt.setDefinition_text(dat.getDefinition_text());
	    UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("currentUser");
	    
		if (userDTO == null) {
	        // User not logged in, handle the error
	        m.addAttribute("error", "User not logged in");
	        
	        return "UploadForm";
	    }
		
	    int userId = userDao.getUserId(userDTO.getEmail());

		
		if(userId ==0) {
			m.addAttribute("IdError", "Invlaid User");
			System.out.println("ball");
			return "UploadForm";
		}
		
		upldt.setUserId(String.valueOf(userId));
		
	    upldt.setCreatedBy(userDTO.getUsername());
	    upldt.setUpdatedBy(userDTO.getUsername());

		ArrayList<DefandTermResponseDTO> defList = definitionDao.getAllDef();
		
		boolean isDupe = false;
		
		for(DefandTermResponseDTO res: defList ) {
			if(dat.getDefinition_text().equalsIgnoreCase(res.getDefinition_text())) {
				isDupe = true;
				m.addAttribute("dupeDef", "this definition already exists");
				return "UploadForm";
			}
		}
		
		if(!isDupe) {
			int result = termDao.storeTerm(upldt);
			int termId = termDao.getTermId(dat.getTerm());
			upldt.setTermId(termId);
			result = definitionDao.storeDefinition(upldt);
			
			
			if(result ==0) {
				System.out.println("Insert Error");
				return "UploadForm";
			}
		}
		
		
		return "DefinitionView";
	}


	
}
