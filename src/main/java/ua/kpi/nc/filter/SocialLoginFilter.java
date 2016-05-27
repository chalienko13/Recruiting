package ua.kpi.nc.filter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ua.kpi.nc.controller.auth.UserAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IO on 27.05.2016.
 */
public class SocialLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final static String ACCESS_TOKEN_TITLE = "accessToken";
    private final static String INFO_OBJECT = "info";
    private final static String EMAIL_TITLE = "email";

    public SocialLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher);
        setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Long id = getSocialNetworkId(request.getRequestURI());
        JsonObject obj = (JsonObject) new JsonParser().parse(request.getReader());
        return getAuthenticationManager().authenticate(new UserAuthentication(getEmail(obj), getSocialNetworkId(request.getRequestURI())));

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println();
    }

    private Long getSocialNetworkId(String requestURL) {
        String array[] = requestURL.split("/");
        return choice(array[array.length - 1]);
    }

    private Long choice(String string) {
        switch (string) {
            case "facebookAuth":
                return 1L;
            default:
                return -1L;
        }
    }

    private String getAccessToken(JsonObject jsonObject) {
        return jsonObject.getAsJsonObject(INFO_OBJECT).get(ACCESS_TOKEN_TITLE).getAsString();
    }

    private String getEmail(JsonObject jsonObject) {
        return jsonObject.getAsJsonObject(INFO_OBJECT).get(EMAIL_TITLE).getAsString();
    }
}
