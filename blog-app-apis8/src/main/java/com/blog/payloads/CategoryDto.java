package com.blog.payloads;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;
	@NotBlank
	@Size(min=4)
	private String categoryTittle;
	
	@NotBlank
	@Size(min=10)
	private String categoryDescription;
}
