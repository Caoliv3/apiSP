package br.com.boavista.apitubo.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/bvs-health/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {

        String user = System.getenv("LOGIN_ENDPOINT") == null ? "nouser" : System.getenv("LOGIN_ENDPOINT");
        String pass = System.getenv("PASSWD_ENDPOINT") == null ? "nopass" : System.getenv("PASSWD_ENDPOINT");

        auth.inMemoryAuthentication()
                .withUser(user)
                .password("{noop}" + pass)
                .roles("USER");
    }
}
