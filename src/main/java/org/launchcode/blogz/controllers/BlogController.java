package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.PostDao;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BlogController extends AbstractController {

	@Autowired
	UserDao UserDao;
	
	@Autowired
	PostDao PostDao;
	
	@RequestMapping(value = "/")
	public String index(Model model){
		
		// fetch users and pass to template
		List<User> users = UserDao.findAll();
		model.addAttribute("users", users);
		
		return "index";
	}
	
	@RequestMapping(value = "/blog")
	public String blogIndex(Model model) {
		
		// fetch posts and pass to template
		List<Post> posts = PostDao.findAll();
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
	
	//currently dead code here. This is not implemented anywhere
	@RequestMapping(value = "/blog/index", method = RequestMethod.GET)
	public String newPostPages(HttpServletRequest request, Model model){
		String URL = "redirect:http://localhost:8080/blog/";
		//String name = "brian";
		int post = 1;
		String name = request.getParameter("name");
		URL = URL + name + "/" + post;
		// Fix this stuff
		//I added this route, although a redirect would have taken me here.  
		return URL;
	}
	
}
