package ua.kpi.nc.controller.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import ua.kpi.nc.service.util.UserAuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IO on 23.04.2016.
 */
public class TokenAuthenticationService {


    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    private final TokenHandler tokenHandler;

    private static TokenAuthenticationService tokenAuthenticationService;

    public TokenAuthenticationService(String secret, UserAuthService userAuthService) {
        tokenHandler = new TokenHandler(secret, userAuthService);
    }

    public String addAuthentication(HttpServletResponse response, HttpServletRequest request, UserAuthentication authentication) {
        User user = authentication.getDetails();
        String token = tokenHandler.createTokenForUser(user);
        response.addHeader(AUTH_HEADER_NAME, token);
        return token;
    }


    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final User user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }


}
