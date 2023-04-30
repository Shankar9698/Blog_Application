package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser=userRepository.save(user);
		UserDto userDto2=this.userToDto(savedUser);
		return userDto2;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "id", userId));
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		UserDto updatedUser=this.userToDto(user);
		return updatedUser;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "id", userId));
		UserDto userDto=this.userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> user=userRepository.findAll();
		List<UserDto> userDto=user.stream().map(x->this.userToDto(x)).collect(Collectors.toList());
		
		
		return userDto;
	}

	@Override
	public void deleteUsers(Integer userId) {
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "id", userId));
		this.userRepository.delete(user);
	}
	public User dtoToUser(UserDto userDto) {
		User user=new User();
		user.setId(userDto.getId());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		return user;
	}
	public UserDto userToDto(User user) {
		UserDto userDto= new UserDto();
		userDto.setId(user.getId());
		userDto.setAbout(user.getAbout());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());
		return userDto;
	}

}
