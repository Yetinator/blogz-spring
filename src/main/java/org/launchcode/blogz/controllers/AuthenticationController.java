package org.launchcode.blogz.controllers;

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
		
		// TODO - implement signup
		//username and password verify_error are part of template
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		User Bob;
		if(password.equals(verify)){
			System.out.println("Password verify check");
			if(User.isValidPassword(password)){
				System.out.println("Password is valid.");
				if(User.isValidUsername(username)){
					System.out.println("Username is valid.");
					Bob = new User(username, password);
					UserDao.save(Bob);
					System.out.println("Past Save Point");
				}
			}
		}
		//User sue = new User(username, password);
		//UserDao.save(sue);
		//validate username (no duplicates)
		//validate password / create password hash / this  is done in user.java class
		
		//save all that stuff
		
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
