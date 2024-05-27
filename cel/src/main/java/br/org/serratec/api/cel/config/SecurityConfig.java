package br.org.serratec.api.cel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults())
			 .csrf(csrf -> csrf.disable() ) // Não recomendado, mas feito pro POST funcionar!
			;

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		 UserDetails user1 = User.withUsername("andre")
		            .password(passwordEncoder().encode("123456"))
		            .roles("USER")
		            .build();
		        UserDetails user2 = User.withUsername("luciano")
		            .password(passwordEncoder().encode("123456"))
		            .roles("USER")
		            .build();
		        UserDetails admin = User.withUsername("karen")
		            .password(passwordEncoder().encode("123456"))
		            .roles("ADMIN")
		            .build();
		        return new InMemoryUserDetailsManager(user1, user2, admin);
	}
	@Bean 
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}
	

}
