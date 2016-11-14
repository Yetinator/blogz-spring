package org.launchcode.blogz.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.blogz.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PostDao extends CrudRepository<Post, Integer> {
    
    List<Post> findByauthor_uid(int author_uid);
    
    Post findByUid(int uid);
    
    List<Post> findAll();
    
    List<Post> findByTitle(String title);
    
    // TODO - add method signatures as needed
	
}
