package com.blog.exceptions;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalException {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(Exception ex){
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message, false);
		return new ResponseEntity<>(apiResponse ,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArugumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> map=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(x->{
			String key=((FieldError)x).getField();
			String value=x.getDefaultMessage();
			map.put(key, value);
		});
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<Map<String, String>> loginException(Exception ex){
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message, false);
		return new ResponseEntity(apiResponse,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse> accessDenied(){
		return new ResponseEntity<ApiResponse>(new ApiResponse("UnAuthorized request", false), HttpStatus.FORBIDDEN);
	}

}
