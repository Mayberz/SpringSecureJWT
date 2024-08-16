	package com.yo.security;
	
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.web.SecurityFilterChain;
	import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
	import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
	
	
	@Configuration
	@EnableWebSecurity
	@EnableMethodSecurity
	public class SecurityConfig {
	    
		@Autowired
		private  JwtAuthFilter jwtAuthFiter;
	
		@Bean
		UserDetailsService getUserDetailsService() {
	
			return new CustomUserDetailsService();
		}
	
		@Bean
		BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	
		@Bean
		DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setUserDetailsService(getUserDetailsService());
			provider.setPasswordEncoder(passwordEncoder());
			return provider;
		}
		
		 @Bean
		     AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		        return config.getAuthenticationManager();
		    }
	
	    @Bean
	    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        HttpSessionRequestCache ReqCache = new HttpSessionRequestCache();
	        ReqCache.setMatchingRequestParameterName(null);
	
	        http.csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
	                .requestMatchers("/Nigma/register","/Nigma/authenticate").permitAll()
							
					 .anyRequest().authenticated() 
	            ).sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	              )
					/*
					 * .formLogin(form -> form .loginPage("/login")
					 * .loginProcessingUrl("/perform_login")
					 * 
					 * .permitAll() ) .logout(logout -> logout .logoutUrl("/logout")
					 * .logoutSuccessUrl("/login?logout") .permitAll() )
					 */
					
	            .requestCache(cache -> cache
	                .requestCache(ReqCache)
	            );
	
	        http.authenticationProvider(authenticationProvider())
	        
	        .addFilterBefore(jwtAuthFiter, UsernamePasswordAuthenticationFilter.class );
	        
	        
	
	        return http.build();
	    }
	
	
		
	
	}
