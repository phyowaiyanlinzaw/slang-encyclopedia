package dictionary.controller;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import javax.naming.ReferralException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import dictionary.dao.DefinitionDAO;
import dictionary.dao.OtpDAO;
import dictionary.dao.TermDAO;
import dictionary.dao.UserDAO;
import dictionary.dao.VoteDAO;
import dictionary.model.DefinitionAndTermBean;
import dictionary.model.OtpBean;
import dictionary.model.UserBean;
import dictionary.services.OtpService;
import dictionary.dto.UserRequestDTO;
import dictionary.dto.UserResponseDTO;
import dictionary.dto.VoteRequestDTO;
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
	@Autowired
	private VoteDAO voteDao;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String indexView(@RequestParam(value = "term", required = false) String searchTerm, ModelMap m) {
	    ArrayList<DefandTermResponseDTO> defList;

		if (searchTerm != null && !searchTerm.isEmpty()) {
		      defList = definitionDao.searchDefinitionsByTerm(searchTerm);
		      System.out.println("asdasd");
		        return "redirect:/DefinitionView?term=" + searchTerm;

		    } else {
		      defList = definitionDao.getAllDefwithTermOrderByAttribute("id", "desc");
		      System.out.println("asdads");
		    }
		m.addAttribute("defList",defList);
		return "index";
	}
	
	@RequestMapping(value="/DefinitionView", method=RequestMethod.GET)
	public String definitionView(@RequestParam(value = "term", required = false) String searchTerm, ModelMap m,HttpSession session) {
		
		ArrayList<DefandTermResponseDTO> defList = definitionDao.getAllDefwithTermOrderByAttribute("id", "desc");
		m.addAttribute("defList", defList);
		
		UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("currentUser");

		m.addAttribute("currentUser", currentUser);
		

		    if (searchTerm != null && !searchTerm.isEmpty()) {
		        defList = definitionDao.searchDefinitionsByTerm(searchTerm);
		    } else {
		        defList = definitionDao.getAllDefwithTermOrderByAttribute("id", "desc");
		    }
		    
		    m.addAttribute("defList", defList);
		    return "DefinitionView";
		
	}
	
	@RequestMapping (value = "/UserDefsView",method=RequestMethod.GET)
	public String userDefsView(
			@RequestParam("createdBy") String createdBy,
			ModelMap m,
			HttpSession session
			) {
		UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("currentUser");

		m.addAttribute("currentUser", currentUser);
		ArrayList<DefandTermResponseDTO> defList = definitionDao.getDefsByUser(createdBy);
		
		m.addAttribute("defList", defList);
		m.addAttribute("createdBy", createdBy);
		
		return "UserDefinitionView";
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
			return "Register";
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
					m.addAttribute("errorMsg", "This email already exists");
					return "Register";
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
					m.addAttribute("errorMsg", "Error While Registering User");
					return "Register";
				}
			}
			
			
		}
		if(!isSamePw) {
			m.addAttribute("errorMsg","Passwords Don't Match");
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
			m.addAttribute("errorMsg","Error While Registering OTP");
			return "otp";
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
				m.addAttribute("errorMsg", "Error While Deleting OTP");
				return "otp";
			}
			
			req.setUserId(requestedUserId);
			req.setRequestedBy(registeredUser.getEmail());
			req.setRestrictTime(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));
			
			int resTimeResult = otpDao.addRestrictionTime(req);
			if(resTimeResult==0) {
				m.addAttribute("errorMsg", "Error While Adding OTP Restriction Time");
				return "otp";
			}
			int updateUserLockedStatusResult = userDao.updateUserLockedStatus("Yes", registeredUser.getEmail());
			if(updateUserLockedStatusResult==0) {
				m.addAttribute("errorMsg", "Error While Updating User Status");
				return "otp";
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
						m.addAttribute("errorMsg", "Error while updating user locked status");
						return "otp";
					}
					int deleteRestrictionTimeResult = otpDao.deleteRestrictionTime(req);
					if(deleteRestrictionTimeResult==0) {
						m.addAttribute("errorMsg", "Error While deleting restriction time");
						return "otp";
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
				m.addAttribute("errorMsg", "Error while storing OTP");
				return "otp";
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
				m.addAttribute("errorMsg", "Updating Verfied Status error");
				return "otp";
			}
		}
		
		if(!isCorrectOTP) {
			m.addAttribute("errorMsg", "Wrong OTP");
			return "otp";
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
			UserBean ub,
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
					ArrayList<Integer> userLikedDefIds = voteDao.getUserVotedDefId(res.getUserId());
					res.setLikedDefIds(userLikedDefIds);			
					
					session.setAttribute("currentUser", res);
					System.out.println(res);
					break;
				}
			}
			
			if(!isCorrectUser) {
				m.addAttribute("errorMsg", "Wrong Authentication");
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
	
		ArrayList<DefandTermResponseDTO> defList = definitionDao.getAllDefwithTermOrderByAttribute("id", "asc");
		m.addAttribute("defList", defList);

		return "AdminView";
		}
	
	@RequestMapping(value = "/DeleteDefsByAdmin", method = RequestMethod.GET)
	public String deleteDefinition(@RequestParam("definitionId") int definitionId) {
	    voteDao.deleteVotesByDefinitionId(definitionId);
	    definitionDao.deleteDefinitionById(definitionId);

	    return "redirect:/AdminView";
	}
	
	@RequestMapping(value="/UserProfile", method = RequestMethod.GET)
	
	public ModelAndView userProfileView(ModelMap m,HttpSession session) {
		
		 UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("currentUser");

		 UserRequestDTO req = new UserRequestDTO();

		    String currentUserId = String.valueOf(userDao.getUserId(userDTO.getEmail()));
		    int defCount =definitionDao.getDefinitionCountForCurrentUser(currentUserId);
		    int totalLike = voteDao.getTotalLikesForUser(currentUserId);
	        m.addAttribute("defCount", defCount);
	        m.addAttribute("totalLike",totalLike);
	        System.out.println(currentUserId);


	        return new ModelAndView("UserProfile", "user", userDao.getOneUser(req));
	}
	 @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	    public String updateProfile(@ModelAttribute("user") 
	    @Validated UserBean ub, BindingResult br, ModelMap m,HttpSession session) {
	        if (br.hasErrors()) {
	            System.out.println("asd");
	        }
	        UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("currentUser");
	        UserRequestDTO req = new UserRequestDTO();
		    String currentUserId = String.valueOf(userDao.getUserId(userDTO.getEmail()));
		    boolean isSamePsw=false;
	        
		    req.setId(Integer.parseInt(currentUserId));
		    req.setUsername(ub.getUsername());
		    req.setEmail(ub.getEmail());
		    req.setPassword(ub.getPassword());
		    req.setConfirm_password(ub.getConfirm_password());
		    
		    
		    if(ub.getPassword().equals(ub.getConfirm_password())) {
	        isSamePsw=true;
	        int updateResult = userDao.updateUser(req);
	        
	        
	        if (updateResult > 0) {
	            UserResponseDTO updatedUser = userDao.getOneUser(req);
	            m.addAttribute("user", updatedUser);
	            session.setAttribute("currentUser", updatedUser);

	            m.addAttribute("success", "Profile updated successfully");
	        } else {
	            m.addAttribute("errorMsg", "Failed to update profile");
	            return "UserProfile";
	        }
		    }
		    if(!isSamePsw) {
				m.addAttribute("errorMsg","Passwords Don't Match");
				return "UserProfile";
		    }
	        return "UserProfile";	
	    }
	

	@RequestMapping(value="/UpdateOtpStatus",method = RequestMethod.GET)
	public String updateOtpStatus(
		HttpSession session,
		ModelMap m
		) {

		OtpRequestDTO req = new OtpRequestDTO();
		
		String otpNumber = (String) session.getAttribute("currentOtp");

		req.setOtpNumber(otpNumber);
		
		System.out.println(otpNumber);
		System.out.println(req.getOtpNumber());

		int result = otpDao.updateOtpStatus(req);

		if(result == 0) {
			m.addAttribute("errorMsg","Error While Updating OTP Status");
			return "otp";
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
			DefinitionAndTermBean dat,
			ModelMap m,
			HttpSession session) {
	    
		if(session.getAttribute("isLoggedIn")==null){
			return "redirect:/Login";
		}
		
		DefandTermRequestDTO upldt = new DefandTermRequestDTO(); 
		
	    upldt.setTerm(dat.getTerm());
	    upldt.setDefinition_text(dat.getDefinition_text());
	    upldt.setExample(dat.getExample());
	    UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("currentUser");

	    int userId = userDao.getUserId(currentUser.getEmail());
	    
	    if(userId ==0) {
			m.addAttribute("errorMsg","Invalid User");
			return "UploadForm";
		}
		upldt.setUserId(String.valueOf(userId));
		upldt.setCreatedBy(currentUser.getUsername());
	    upldt.setUpdatedBy(currentUser.getUsername());

		ArrayList<DefandTermResponseDTO> defList = definitionDao.getAllDef();
		
		boolean isDupe = false;
		
		for(DefandTermResponseDTO res:defList ) {
			if(dat.getDefinition_text().equalsIgnoreCase(res.getDefinition_text())) {
				isDupe = true;
				m.addAttribute("errorMsg", "This definition already exists");
				return "UploadForm";
			}
		} 
		
		if(!isDupe) {
			int termStoreResult = termDao.storeTerm(upldt);
			if(termStoreResult==0) {
				m.addAttribute("errorMsg", "Error While Storing Term");
				return "UploadForm";
			}
			int termId = termDao.getTermId(dat.getTerm());
			upldt.setTermId(termId);
			
			int definitionStoreResult = definitionDao.storeDefinition(upldt);
			
			if(definitionStoreResult ==0) {
				m.addAttribute("errorMsg", "Error While Storing Definition");
				return "UploadForm";
			}
			
			VoteRequestDTO voteReq = new VoteRequestDTO();
			voteReq.setDefinitionId(definitionDao.getDefId(upldt));
			
			int likeStoreResult = voteDao.storeLikeVote(voteReq);
			
			if (likeStoreResult==0) {
				m.addAttribute("errorMsg", "Error While Storing Vote");
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
		
		ArrayList<DefandTermResponseDTO> defList = definitionDao.getAllDefwithTermOrderByAttribute("id", "desc");
		Collections.shuffle(defList);
		m.addAttribute("defList", defList);
		
		return "DefinitionView";
	}
	
	
	@RequestMapping(value="/Search", method = RequestMethod.GET)
	public String search(@RequestParam("term")String searchTerm,ModelMap m,HttpSession session) {
		
		session.removeAttribute("searchTerm");
		session.setAttribute("searchTerm", searchTerm);
		
	    ArrayList<DefandTermResponseDTO> defList;
	    
	    UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("currentUser");

		m.addAttribute("currentUser", currentUser);
		if (searchTerm != null && !searchTerm.isEmpty()) {
		      defList = definitionDao.searchDefinitionsByTerm(searchTerm);
		      System.out.println("asdasd");

  
		    } else {
		        defList = definitionDao.getAllDefwithTermOrderByAttribute("id", "desc");
		      System.out.println("asdads");
		    }
		    m.addAttribute("defList", defList);
		    session.setAttribute("defList", defList);
	        return "redirect:/DefinitionView?term=" + searchTerm;
	}
	
	
	@RequestMapping(value="/UpdateLike",method=RequestMethod.GET)
	public String updateLikeCount(
			@RequestParam("definitionId") String definitionId,
			ModelMap m,
			HttpSession session,
			HttpServletRequest request
			) {
		
		if(session.getAttribute("isLoggedIn")==null){
			return "redirect:/Login";
		}
		
		UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("currentUser");
		int userId = userDao.getUserId(currentUser.getEmail());

		VoteRequestDTO voteReq = new VoteRequestDTO();

		voteReq.setUser_id(userId);
		voteReq.setDefinitionId(Integer.parseInt(definitionId));
		voteReq.setUpdatedBy(currentUser.getUsername());
		
		int storeNewLikeVoteResult = voteDao.giveVote(voteReq);

		if(storeNewLikeVoteResult==0) {
				System.out.println("Store New Like Error");
			}


		ArrayList<Integer> userLikedDefIds = voteDao.getUserVotedDefId(userId);
		currentUser.setLikedDefIds(userLikedDefIds);
		session.setAttribute("currentUser", currentUser);
		
		String searchTerm = (String) session.getAttribute("searchTerm");

		if (searchTerm != null && !searchTerm.isEmpty()) {
			// Redirect back to the search page with the search term included in the URL
			return "redirect:/Search?term=" + searchTerm;
		} else {
			return "redirect:/DefinitionView";
		}
		
//		return "redirect:/DefinitionView";
		
	}
	
	@RequestMapping(value = "/RemoveLike",method=RequestMethod.GET)
	public String removeLike(
			@RequestParam("definitionId") String definitionId,
			HttpSession session
			) {
		
		if(session.getAttribute("isLoggedIn")==null){
			return "redirect:/Login";
		}
		
		UserResponseDTO currentUser = (UserResponseDTO) session.getAttribute("currentUser");
		int userId = userDao.getUserId(currentUser.getEmail());

		VoteRequestDTO voteReq = new VoteRequestDTO();
		voteReq.setUser_id(userId);
		voteReq.setDefinitionId(Integer.parseInt(definitionId));
		
		int removeLikeResult = voteDao.removeVote(voteReq);
		
		if(removeLikeResult==0) {
			System.out.println("Error while removing Like");
		}
			
		ArrayList<Integer> userLikedDefIds = voteDao.getUserVotedDefId(userId);
		currentUser.setLikedDefIds(userLikedDefIds);
		session.setAttribute("currentUser", currentUser);
		
		
		String searchTerm = (String) session.getAttribute("searchTerm");

		if (searchTerm != null && !searchTerm.isEmpty()) {
			// Redirect back to the search page with the search term included in the URL
			return "redirect:/Search?term=" + searchTerm;
		} else {
			return "redirect:/DefinitionView";
		}
	}
	
	@RequestMapping(value = "/ResetPassword",method = RequestMethod.GET)
	public ModelAndView resetPasswordView() {
		return new ModelAndView("ResetPassword","resetPwBean",new UserBean());
	}
	
	@RequestMapping(value = "/ProcessResetPassword",method = RequestMethod.POST)
	public String resetPasswordProcess(
			@ModelAttribute("resetPwBean")
			@Validated
			UserBean ub,
			BindingResult br,
			ModelMap m,
			HttpSession session
			) {
		
		
		boolean exist = false;
		boolean isSamePw = false;
		
		ArrayList<UserResponseDTO> allUsers = userDao.getAllUsers();
		
		UserRequestDTO req = new UserRequestDTO();
		req.setPassword(ub.getPassword());
		req.setConfirm_password(ub.getConfirm_password());
		req.setEmail(ub.getEmail());
		
		if(ub.getPassword().equals(ub.getConfirm_password())) {
			isSamePw = true;
			for(UserResponseDTO user:allUsers) {
				if(user.getEmail().equals(ub.getEmail())) {
					exist = true;

					
					int updatePasswordResult = userDao.resetPassword(req);
					if(updatePasswordResult==0) {
						System.out.println("update password error.");
						return "ResetPassword";
					}
					break;
				}
			}
			
			if(!exist) {
				m.addAttribute("errorMsg","User with this email does not exist");
				return "ResetPassword";
			}
			
		}
		
		if(!isSamePw) {
			m.addAttribute("errorMsg","Passwords Don't Match");
			return "ResetPassword";
		}
		
		
		
		return "redirect:/Login";
	}
}
