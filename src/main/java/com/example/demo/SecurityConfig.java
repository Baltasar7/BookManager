package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
/*
    @Autowired
    private DataSource dataSource;
*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
/*
    private static final String USER_SQL = "SELECT"
            + " user_id,"
            + " password,"
            + " true"
            + " FROM"
            + " m_user"
            + " WHERE"
            + " user_id = ?";

    private static final String ROLE_SQL = "SELECT"
            + " user_id,"
            + " role"
            + " FROM"
            + " m_user"
            + " WHERE"
            + " user_id = ?";
*/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/∗∗", "/css/∗∗");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated();

        http
            .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .failureUrl("/login")
                .usernameParameter("userId")
                .passwordParameter("password")
                .defaultSuccessUrl("/lendingStatus", true);

        http
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login");

        // Debug
        //http.csrf().disable();
    }

/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery(USER_SQL)
            .authoritiesByUsernameQuery(ROLE_SQL)
            .passwordEncoder(passwordEncoder());
    }
*/
}
