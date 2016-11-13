package org.launchcode.blogz.controllers;

import java.util.List;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.PostDao;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController extends AbstractController {

	@Autowired
	UserDao UserDao;
	
	@Autowired
	PostDao PostDao;
	
	@RequestMapping(value = "/")
	public String index(Model model){
		
		// TODO - fetch users and pass to template
		List<User> users = UserDao.findAll();
		model.addAttribute("users", users);
		
		return "index";
	}
	
	@RequestMapping(value = "/blog")
	public String blogIndex(Model model) {
		
		// TODO - fetch posts and pass to template
		List<Post> posts = PostDao.findAll();
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
	@RequestMapping(value = "/index")
	public String newPostPage(Model model){
		String URL = "redirect:/";
		
		return URL;
	}
	
}
