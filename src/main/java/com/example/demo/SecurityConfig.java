package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ユーザーIDとパスワードを取得するSQL文
    private static final String USER_SQL = "SELECT"
            + " user_id,"
            + " password,"
            + " true"
            + " FROM"
            + " m_user"
            + " WHERE"
            + " user_id = ?";

    // ユーザーのロールを取得するSQL文
    private static final String ROLE_SQL = "SELECT"
            + " user_id,"
            + " role"
            + " FROM"
            + " m_user"
            + " WHERE"
            + " user_id = ?";

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/∗∗", "/css/∗∗");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/webjars/**").permitAll() //webjarsへアクセス許可
                .antMatchers("/css/**").permitAll() //cssへアクセス許可
                .antMatchers("/login").permitAll() //ログインページは直リンクOK
                .antMatchers("/signup").permitAll() //ユーザー登録画面は直リンクOK
                .antMatchers("/rest/**").permitAll() //RESTは直リンクOK
                .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated(); //それ以外は直リンク禁止

        http
            .formLogin()
                .loginProcessingUrl("/login") //
                .loginPage("/login") //ログインページの指定
                .failureUrl("/login") //ログイン失敗時の遷移先
                .usernameParameter("userId") //ログインページのユーザーID
                .passwordParameter("password") //ログインページのパスワード
                .defaultSuccessUrl("/home", true); //ログイン成功後の遷移先

        http
	          .logout()
	          .logoutUrl("/logout") //ログアウトのURL
	          .logoutSuccessUrl("/login"); //ログアウト成功後のURL

        RequestMatcher csrfMatcher = new RestMatcher("/rest/**");
        http.csrf().requireCsrfProtectionMatcher(csrfMatcher);

        // temp
        //http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
		        .dataSource(dataSource)
		        .usersByUsernameQuery(USER_SQL)
		        .authoritiesByUsernameQuery(ROLE_SQL)
		        .passwordEncoder(passwordEncoder());
    }
}
