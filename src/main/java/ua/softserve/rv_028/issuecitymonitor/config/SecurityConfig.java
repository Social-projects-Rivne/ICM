package ua.softserve.rv_028.issuecitymonitor.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.softserve.rv_028.issuecitymonitor.service.UserDetailsServiceImpl;

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
@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    UserDetailsServiceImpl userDetailsService;

    BCryptPasswordEncoder passwordEncoder;

    /** All possible urls must be here*/
    String[] urls = new String[]{"/", "/dashboard", "/issues", "/petitions", "/events", "/users",
            "/settings","/maps"};

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
                .loginPage("/login").failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()

                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");
    }
}