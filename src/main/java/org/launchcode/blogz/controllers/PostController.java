package org.launchcode.blogz.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.PostDao;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {
	
	@Autowired
	PostDao PostDao;
	
	@Autowired
	UserDao UserDao;

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		// TODO - implement newPost
		//value, body, error
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
		
		// TODO - error check for empty fields
		
		//find User...
		HttpSession thi = request.getSession();
		User author = getUserFromSession(thi);		
		
		//create a data object post
		Post nowPost = new Post(title, body, author);
		PostDao.save(nowPost);
		
		
		return "redirect:index"; // TODO - this redirect should go to the new post's page  		
	}
	
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		String name = username;
		String error = "";
		Post currentPost = PostDao.findByUid(uid);
		System.out.println(name + " = " + currentPost.getAuthor().getUsername());
		//check to see if the name and post id belong together
		if(name.equals(currentPost.getAuthor().getUsername())){
			model.addAttribute("currentPost", currentPost);
		
			
		}else {
			
			model.addAttribute("error", "This path does not exist!");
			return "error";
		}
			
		
		
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		//String username = username;
		// TODO - implement userPosts
		User currentUser = UserDao.findByUsername((String) username);
		int authorId = currentUser.getUid();
		//System.out.println("authorid = " + authorId);
		List<Post> posts = PostDao.findByauthor_uid(authorId);
		
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
}
