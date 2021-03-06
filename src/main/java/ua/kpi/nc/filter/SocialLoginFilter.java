package ua.kpi.nc.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ua.kpi.nc.controller.auth.AuthenticationSuccessHandlerService;
import ua.kpi.nc.controller.auth.TokenAuthenticationService;
import ua.kpi.nc.controller.auth.UserAuthentication;
import ua.kpi.nc.persistence.model.SocialNetwork;
import ua.kpi.nc.persistence.model.enums.SocialNetworkEnum;

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
    private final static String SOCiAL_NETWORK_INFO = "info";
    private final TokenAuthenticationService tokenAuthenticationService;


    public SocialLoginFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager, AuthenticationSuccessHandler authenticationSuccessHandler, TokenAuthenticationService tokenAuthenticationService) {
        super(requiresAuthenticationRequestMatcher);
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        this.tokenAuthenticationService = tokenAuthenticationService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        JsonObject obj = (JsonObject) new JsonParser().parse(request.getReader());
        return getAuthenticationManager().authenticate(new UserAuthentication(getEmail(obj), getSocialNetworkId(request.getRequestURI()), getSocialNetworkInfo(obj), getUserSocialId(obj)));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserAuthentication userAuthentication  = ((UserAuthentication) authResult);
        tokenAuthenticationService.addAuthentication(response, authResult);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        AuthenticationSuccessHandlerService.getInstance().onAuthenticationSuccess(request,response,authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    private SocialNetwork getSocialNetworkId(String requestURL) {
        String array[] = requestURL.split("/");
        return SocialNetworkEnum.getSocialNetwork(array[array.length - 1]);
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

    private String getSocialNetworkInfo(JsonObject jsonObject){
        return jsonObject.getAsJsonObject(SOCiAL_NETWORK_INFO).toString();
    }

    private Long getUserSocialId(JsonObject jsonObject){
        return jsonObject.getAsJsonObject(INFO_OBJECT).get("userID").getAsLong();
    }
}
