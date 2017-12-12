package ua.softserve.rv_028.issuecitymonitor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.softserve.rv_028.issuecitymonitor.service.UserDetailsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * The Security class implements user authentication of the system.
 * The user with his role has the opportunity to use only available links.
 * All urls are contained in String[] urls.
 *
 *
 * A class {@link UserDetailsServiceImpl} is implemented by the interface UserDetailService from Spring Security.
 *
 * @version     1.0 07 Dec 2017
 * @author      gefasim
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final UserDetailsServiceImpl userDetailsService;

    private final BCryptPasswordEncoder passwordEncoder;

    /** All possible urls must be here*/
    private final String[] urls = new String[]{"/", "/dashboard", "/issues", "/petitions", "/events", "/users",
            "/settings"};

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(urls).permitAll()
                .anyRequest().permitAll()

                .and().csrf().disable().formLogin()
                .loginPage("/login")
                //.failureUrl("/login?error=true")
                .failureHandler(new AuthenticationFailureHandler(){

                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        //httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);

                            httpServletResponse.getWriter().print(
                                    "{'login': 'FAILURE'}");
                            httpServletResponse.getWriter().flush();

                    }
                })
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()

                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");
    }
}
