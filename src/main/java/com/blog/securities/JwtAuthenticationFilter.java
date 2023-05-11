package com.blog.securities;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	// before going to hit the api, it is like filter
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// get the token
		String requestToken = request.getHeader("Authorization");
		// Bearer jdl683583793
		System.out.println("request token = "+requestToken);// check the token on console
		String userName = null;
		String token = null;
		if (requestToken != null && requestToken.startsWith("Bearer ")) {
			token = requestToken.substring(7);
			try {
				userName = this.jwtTokenHelper.getUserNameFromToken(token);
				
			} catch (IllegalArgumentException e) {
				System.out.println("unable to get JWT token");
			} catch (ExpiredJwtException e) {
				System.out.println("Expired JWT token");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid JWT token");
			}
		} else {
			System.out.println("JWT token doesn't start with Bearer");
		}
		

		// once get the token, then validate
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			if (this.jwtTokenHelper.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.out.println("Invalid JWT token");
			}

		} else {
			System.out.println("UserName is null or Context is not null");

		}
		filterChain.doFilter(request, response);
	}

}
