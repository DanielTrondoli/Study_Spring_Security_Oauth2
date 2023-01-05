package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @RestController
    class HttpController {
        @GetMapping("/public")
        String publicRoute() {
            return "<h1> Public Route, fell free to look around! :unlock: </h1>";
        }

        @GetMapping("/private")
        String oauth2Route(@AuthenticationPrincipal OidcUser principal) {
            return String.format("""
                            <h1> OAuth2 Route, only authorized user ! :lock: </h1>
                            <h3></h3>
                            <h3>Principal: %s</h3>
                            <h3>Email: %s</h3>
                            <h3>Authorities: %s</h3>
                            <h3>JWT: %s</h3>
                            """, principal, principal.getAttribute("email"), principal.getAuthorities(),
                    principal.getIdToken().getTokenValue());
        }

        @GetMapping("/jwt")
        String jwtRoute(@AuthenticationPrincipal Jwt jwt) {
            return String.format("""
                            <h1> JWT Route, only authorized user ! :lock: </h1>
                            <h3></h3>
                            <h3>Principal: %s</h3>
                            <h3>Email: %s</h3>                            
                            <h3>JWT: %s</h3>
                            """, jwt.getClaims(), jwt.getClaim("email"),jwt.getTokenValue());
        }
    }
}
