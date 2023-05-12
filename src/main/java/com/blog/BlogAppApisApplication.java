package com.blog;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepository;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	// used to map/convert one class object with another class
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("password encoder = " + this.passwordEncoder.encode("12345"));
		// password for 12345: bcrypt is generated in consol-> update that in db; send
		// 12345 in postman
		try {
			Role r1 = new Role();
			r1.setId(AppConstants.NORMAL_USER);
			r1.setName("ROLE_NORMAL");

			Role r2 = new Role();
			r2.setId(AppConstants.ADMIN_USER);
			r2.setName("ROLE_ADMIN");
			if(r1.getId()==521 && r2.getId()==522) {

			List<Role> role = List.of(r1, r2);
			List<Role> result = this.roleRepository.saveAll(role);
			result.forEach(x -> {
				System.out.println(x.getName());
				;
			});
			}
			else
			{
				System.out.println("already their,so not updated");
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
