package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.UserDto;
import com.blog.services.CategoryService;



@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto userDto)
	{
		CategoryDto createCategoryDto = this.categoryService.createCategory(userDto);
		return new ResponseEntity<>(createCategoryDto,HttpStatus.CREATED);
	}
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto userDto, @PathVariable Integer catId)
	{
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(userDto,catId);
		return new ResponseEntity<CategoryDto>(updatedCategoryDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId)
	{
		this.categoryService.deleteCategory(catId);
		//return ResponseEntity.ok(Map.of("message","Category Deleted Successfully"));
		return new ResponseEntity(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity <List<CategoryDto>> getAllUsers() {
		return ResponseEntity.ok(this.categoryService.getCategories());
		
	}
	@GetMapping("/{catId}")
	public ResponseEntity <CategoryDto> getSingleUser(@PathVariable Integer catId) {
		return ResponseEntity.ok(this.categoryService.getCategory(catId));
	}
	
	
}
