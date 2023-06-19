package dictionary.controller;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

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

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

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
	
	@RequestMapping(value="/DefinitionView", method=RequestMethod.GET)
	public String definitionView(ModelMap m) {
		
		ArrayList<DefandTermResponseDTO> defList = definitionDao.getAllDefwithTerm();
		m.addAttribute("defList", defList);

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
				if(res.getEmail().equals(ub.getEmail())&&res.getIsVerified().equalsIgnoreCase("Yes")) {
					isDupe = true;
					m.addAttribute("emailDupe", "This email already exists");
					return "redirect:/Register";
				}
				
			}
			if(!isDupe) {
				session.setAttribute("registeredUser", ub);
				for(UserResponseDTO res:resList) {
					if(res.getEmail().equals(ub.getEmail())&&res.getIsVerified().equalsIgnoreCase("No")) {
						System.out.println("Need to verify OTP - User");
						session.removeAttribute("otpLimit");
						return "redirect:/ResendOTP";
					}
				}	
				
				session.removeAttribute("otpLimit");
				int result = userDao.storeUsers(req);

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
		
		return "redirect:/RegisterOTP";
	}

	@RequestMapping(value="/RegisterOTP",method = RequestMethod.GET)
	public String requestOTP(
			HttpSession session,
			ModelMap m
			) {
		
		String generatedOtp = OtpService.generateOtp();
		session.setAttribute("currentOtp", generatedOtp);
		UserBean registeredUser = (UserBean) session.getAttribute("registeredUser");
		int requestedUserId = userDao.getUserId(registeredUser.getEmail());
		
		OtpRequestDTO req = new OtpRequestDTO();
		req.setOtpNumber(generatedOtp);
		req.setOtpCount(1);
		req.setRequestedBy(registeredUser.getEmail());
		req.setUserId(requestedUserId);
		
		int storeOtpResult = otpDao.storeOtp(req);
		
		if(storeOtpResult==0) {
			System.out.println("Error while storing OTP");
		}
		
		OtpService.sendEmail(registeredUser.getEmail(),"OTP","Your OTP Code is : "+generatedOtp);

		return "redirect:/otpView";
	}
	
	@RequestMapping(value="/ResendOTP",method = RequestMethod.GET)
	public String resendOtp(
			HttpSession session,
			ModelMap m
			) {
		
		session.removeAttribute("currentOtp");
		
		UserBean registeredUser = (UserBean) session.getAttribute("registeredUser");
		int requestedUserId = userDao.getUserId(registeredUser.getEmail());
		
		int otpCount = otpDao.getOtpCount(requestedUserId);
		OtpRequestDTO req = new OtpRequestDTO();
		
		boolean isLimit = false;
		
		if(otpCount>=5) {
			isLimit = true;
			
			int deleteOtpResult = otpDao.deleteOtps(requestedUserId);
			if(deleteOtpResult==0) {
				System.out.println("Error while deleting OTPs");
			}
			
			req.setUserId(requestedUserId);
			req.setRequestedBy(registeredUser.getEmail());
			req.setRestrictTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));
			
			int resTimeResult = otpDao.addRestrictionTime(req);
			if(resTimeResult==0) {
				System.out.println("Error while adding restriction time");
			}
			int updateUserLockedStatusResult = userDao.updateUserLockedStatus("Yes", registeredUser.getEmail());
			if(updateUserLockedStatusResult==0) {
				System.out.println("Error while updating user locked status");
			}
			
			session.setAttribute("otpLimit", "true");
			return "redirect:/otpView";
		}
		
		if(!isLimit) {
			 
			req.setUserId(requestedUserId);

			OtpResponseDTO res = otpDao.getRestrictionTime(req);
			
			try {
				if(res.getRestrictionTimestamp().after(Timestamp.valueOf(LocalDateTime.now()))) {
					session.setAttribute("otpLimit", "true");
					System.out.println("Times not up yet");
					return "redirect:/otpView";
				}else {
					System.out.println("Time is before");
					int updateUserLockedStatusResult = userDao.updateUserLockedStatus("No", registeredUser.getEmail());
					if(updateUserLockedStatusResult==0) {
						System.out.println("Error while updating user locked status");
					}
					int deleteRestrictionTimeResult = otpDao.deleteRestrictionTime(req);
					if(deleteRestrictionTimeResult==0) {
						System.out.println("Error While deleting restriction time");
					}
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
			String generatedOtp = OtpService.generateOtp();
			session.setAttribute("currentOtp", generatedOtp);

			req.setOtpNumber(generatedOtp);
			req.setOtpCount(otpCount+1);
			req.setRequestedBy(registeredUser.getEmail());

			
			int storeOtpResult = otpDao.storeOtp(req);
			
			if(storeOtpResult==0) {
				System.out.println("Error while storing OTP");
			}
			
			OtpService.sendEmail(registeredUser.getEmail(),"OTP","Your OTP Code is : "+generatedOtp);
		}
		
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
		
		UserBean registeredUser = (UserBean) session.getAttribute("registeredUser");
		
		otpReq.setRequestedBy(registeredUser.getEmail());
		
		OtpResponseDTO res = otpDao.getOtp(otpReq);
		
		boolean isCorrectOTP = false;
		
		if(ob.getOtpNumber().equals(res.getOtpNumber())){
			isCorrectOTP = true;
			int result = userDao.updateUserVerifiedStatus(registeredUser.getEmail());
			if(result==0) {
				System.out.println("Updating Verfied Status error");
				return "redirect:/otpView";
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
			return "redirect:/AdminView";
		}
		
		if(!isAdmin) {
			for(UserResponseDTO res : usersList) {
				if(res.getEmail().equals(ub.getEmail())&&res.getPassword().equals(ub.getPassword())) 
				{
					isCorrectUser = true;
					isUser = true;
					session.setAttribute("currentUser", res);
					System.out.println(res);
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
		
		return "redirect:/UserProfile";
	}
	
	@RequestMapping(value="/Dashboard",method = RequestMethod.GET)
	public String userProfile(
			HttpSession session,
			ModelMap m
			) {
		
		if(session.getAttribute("isUser")!=null) {
		   
			return "redirect:/UserProfile";
		}
		
		else if (session.getAttribute("isAdmin")!=null) {
			
		
			return "redirect:/AdminView";
		}
		
		if(session.getAttribute("isLoggedIn")==null){
			return "redirect:/Login";
		}
		
		return "redirect:/UserProfile";
	}
	
	@RequestMapping(value = "/AdminView", method = RequestMethod.GET)
		public String adminView(ModelMap m) {
		ArrayList<UserResponseDTO> userList = userDao.getAllUsersWithDT();
		m.addAttribute("userList",userList);
		return "AdminView";
		}
	
	@RequestMapping(value="/UserProfile", method = RequestMethod.GET)
	public String userProfileView(ModelMap m,HttpSession session) {
		
		 UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("currentUser");
		    
		    if (userDTO == null) {
		        m.addAttribute("error", "User not logged in");
		        
		        return "redirect:/Login";
		    }
		    		    
		    String currentUserId = String.valueOf(userDao.getUserId(userDTO.getEmail()));
		    int defCount =definitionDao.getDefinitionCountForCurrentUser(currentUserId);
	        m.addAttribute("defCount", defCount); // Pass defCount to the view
	        
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
		
		return "redirect:/ResendOTP";

	}
	
	@RequestMapping(value="/LogOut",method = RequestMethod.GET)
	public String logOut(
			HttpSession session
			) {
		
		session.removeAttribute("isLoggedIn");
		session.removeAttribute("isUser");
		session.removeAttribute("isAdmin");
		session.removeAttribute("currentUser");
		
		
		return "redirect:/DefinitionView";
	}
	
	@RequestMapping(value = "/UploadForm", method = RequestMethod.GET)
	public ModelAndView uploadFormView() {
		return new ModelAndView("UploadForm", "termandDefBean", new DefinitionAndTermBean());
	}
	
	@RequestMapping(value="/ProcessUpload", method=RequestMethod.POST)
	public String processUpload(
			@ModelAttribute ("termandDefBean") 
			@Validated DefinitionAndTermBean dat,
			BindingResult br,
			ModelMap m,
			HttpSession session) {
	    
		if(session.getAttribute("isLoggedIn")==null){
			return "redirect:/Login";
		}
	    
		DefandTermRequestDTO upldt = new DefandTermRequestDTO(); 
	    upldt.setTerm(dat.getTerm());
	    upldt.setDefinition_text(dat.getDefinition_text());
	    UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("currentUser");
		if (userDTO == null) {
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
		
		for(DefandTermResponseDTO res:defList ) {
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
			
			  	String currentUserId = String.valueOf(userId);
		        int defCount = definitionDao.getDefinitionCountForCurrentUser(currentUserId);
		        defCount++; 
		        m.addAttribute("defCount", defCount);
		}
		
		
		return "redirect:/DefinitionView";
	}

	@RequestMapping(value = "/Shuffle",method = RequestMethod.GET)
	public String shuffleHomeView(
			ModelMap m
			) {
		
		ArrayList<DefandTermResponseDTO> defList = definitionDao.getAllDefwithTerm();
		Collections.shuffle(defList);
		m.addAttribute("defList", defList);
		
		return "DefinitionView";
	}

	
}
