package ua.softserve.rv_028.issuecitymonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * The Security class implements user authentication of the system.
 * The user with his role has the opportunity to use only available links.
 * All urls are contained in String[] urls.
 *
 * A class with behavior when a user successfully logged into the system is implemented in :
 * {@link AuthenticationSuccessHandlerImpl}
 *
 * A class {@link UserDetailsServiceImpl} is implemented by the interface UserDetailService from Spring Security.
 *
 * @version     1.0 07 Dec 2017
 * @author      gefasim
 *
 */

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter{

    private final UserDetailsServiceImpl userDetailsService;

    private final static AuthenticationSuccessHandlerImpl authHandler = new AuthenticationSuccessHandlerImpl();

    /** All possible urls must be here*/
    private final String[] urls = new String[]{"/", "/dashboard", "/issues", "/petitions", "/events", "/users",
            "/settings"};

    @Autowired
    public Security(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(urls).permitAll()
                .anyRequest().permitAll()

                .and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .successHandler(authHandler)
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()

                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");
    }
}
