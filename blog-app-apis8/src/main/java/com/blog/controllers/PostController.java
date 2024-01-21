package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.payloads.UserDto;
import com.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	PostService postService;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto userDto,@PathVariable Integer userId, @PathVariable Integer categoryId )
	{
		
		PostDto createPost =this.postService.createPost(userDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> posts=this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{cateogryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer cateogryId){
		
		List<PostDto> posts=this.postService.getPostByUser(cateogryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	//get All Posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam (value="pageNumber",defaultValue =AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue =AppConstants.PAGE_SIZE,required=false) Integer pageSize ,
			@RequestParam(value="sortBy",defaultValue =AppConstants.SORT_BY,required=false) String sortBy ,
			@RequestParam(value="sortDir",defaultValue =AppConstants.SORT_DIR,required=false) String sortDir)

	{
		
		PostResponse allPosts = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
	}
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		PostDto postDto =this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);

	}
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ApiResponse("Post is successfully deleted", true);
	}
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		PostDto updatedPost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity <List<PostDto>> searchPostbyTitle(
			@PathVariable String keywords)
	{
		List<PostDto> seachPosts= this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(seachPosts,HttpStatus.OK);
		
	}
}
