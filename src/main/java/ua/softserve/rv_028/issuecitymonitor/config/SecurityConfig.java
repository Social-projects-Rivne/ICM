package ua.softserve.rv_028.issuecitymonitor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
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
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final String ADMIN_URL = "/admin/**";
    private static final String[] AUTHENTICATED_USER_URLS = new String[]{"/settings/**"};

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(ADMIN_URL).hasAnyAuthority(UserRole.ADMIN.getAuthority(), UserRole.MODERATOR.getAuthority())
                .antMatchers(AUTHENTICATED_USER_URLS).hasAnyAuthority(UserRole.ADMIN.getAuthority(),
                    UserRole.MODERATOR.getAuthority(), UserRole.USER.getAuthority())
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