package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@Autowired
	private UserDao UserDao;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		//username and password verify_error are part of template
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		User Bob;
		String username_error ="";
		String password_error = "";
		String verify_error = "";
		Boolean username_good = false;
		Boolean password_good = false;
		Boolean verify_good = false;
		
		//Check if password equals verify
		if(password.equals(verify)){
			//System.out.println("Password verify check");
			verify_good = true;
		}else
			verify_error = "Your passwords don't match.";
			
			
		if(User.isValidPassword(password)){
			//System.out.println("Password is valid.");
			password_good = true;
		}else
			password_error = "Your password should be between 6 and 20 characters long";
		
		//see if name exists
		User temp =null;
		temp = UserDao.findByUsername(username);
		Boolean nameExists = false;
		if(temp != null){
			nameExists = true;
			username_error = "Your username already exists...";
		}else if(User.isValidUsername(username)){
			//System.out.println("Username is valid.");
			username_good = true;
			model.addAttribute("username", username);
		}else
			username_error = "Your username should be 5 and 11 characters and contain...";
		
		//if above tests pass
		if(username_good && password_good && verify_good){
			Bob = new User(username, password);
			UserDao.save(Bob);
			//System.out.println("Past Save Point");
			HttpSession thi = request.getSession();
			setUserInSession(thi, Bob);
				
			}else{
				//redirect to signup with errors
				model.addAttribute("username_error", username_error);
				model.addAttribute("password_error", password_error);
				model.addAttribute("verify_error", verify_error);
				return "signup";
			}
				
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		//implement login
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String error = "";

		//does user exist?
		User currentUser = null;
		currentUser = UserDao.findByUsername(username);
		if(currentUser == null){
			error = "You are not a user.  Please sign up or spell your stuff correctly.  ";
			model.addAttribute("error", error);
			return "login";
		}
		//check password
		if(currentUser.isMatchingPassword(password)){
			HttpSession thi = request.getSession();
			setUserInSession(thi, currentUser);
		}else{
			//else not matching password or invalid user
			error = "This password doesn't what we have on file here...";
			model.addAttribute("error", error);
			return "login";
		}
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
