package br.com.esparda.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class AuthServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("asdf").roles("USER")

				.and().withUser("admin").password("asdf").roles("ADMIN")

				.and().withUser("super").password("asdf").roles("ADMIN", "USER")

				.and().withUser("esparda-web-sga").password("asdf").roles("CLIENT")

				.and().withUser("esparda-web-odx").password("asdf").roles("CLIENT")

				.and().withUser("esparda-web-core").password("asdf").roles("CLIENT");

		auth.parentAuthenticationManager(authenticationManager);
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
		http.formLogin().loginPage("/login").permitAll().and().requestMatchers()
				.antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access").and().authorizeRequests()
				.anyRequest().authenticated();
		// @formatter:on
	}
}
