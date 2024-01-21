package com.blog.services.impl;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepo postRepo;

	@Autowired 
	private ModelMapper modelMapper;
	
	@Autowired 
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub

		User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category id",categoryId));
		
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date(17012024));
		post.setCategory(category);
		post.setUser(user);
		//return post;
		Post newPost= this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		Post post =this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
		if(postDto.getTitle()!=null)
		post.setTitle(postDto.getTitle());
		if(postDto.getContent()!=null)
		post.setContent(postDto.getContent());
		Post updatedPost =this.postRepo.save(post);
		 return this.modelMapper.map(updatedPost, PostDto.class);
		
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post post =this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		// TODO Auto-generated method stub
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post id",postId));
		PostDto postDto=this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize, String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		/*
		 * if(sortDir.equalsIgnoreCase("asc")) sort=Sort.by(sortBy).ascending(); else
		 * sort=Sort.by(sortBy).descending();
		 */
		PageRequest p=PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).ascending());
		
		Page <Post> pagePostPage =this.postRepo.findAll(p);
		
		List<Post> allPostsPage=pagePostPage.getContent();
		
		//List <Post> allPosts=this.postRepo.findAll();
		List <PostDto> postDtos =allPostsPage.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse =new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePostPage.getNumber());
		postResponse.setPageSize(pagePostPage.getSize());
		postResponse.setTotalElements(pagePostPage.getTotalElements());
		postResponse.setTotalPages(pagePostPage.getTotalPages());
		postResponse.setLastPage(pagePostPage.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category id",categoryId));
		
			List<Post> posts=	this.postRepo.findByCategory(cat);
			
			List <PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
			
		return postDtos;
	}
	
	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User id",userId));
		
		List<Post> posts=	this.postRepo.findByUser(user);
		
		List <PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
	return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}


}
