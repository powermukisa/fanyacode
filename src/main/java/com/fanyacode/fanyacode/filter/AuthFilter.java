package com.fanyacode.fanyacode.filter;

import com.fanyacode.fanyacode.authorization.Constants;
import com.fanyacode.fanyacode.authorization.model.Input;
import com.fanyacode.fanyacode.authorization.OpaClient;
import com.fanyacode.fanyacode.authorization.model.OpaRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class AuthFilter extends GenericFilterBean {

  @Autowired
  OpaClient opaClient;

  @Value("${app.client.baseUrl}")
  private String appBaseUrl;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

    String authHeader = httpRequest.getHeader("Authorization");
    if(authHeader != null) {
      String[] authHeaderArr = authHeader.split("Bearer ");
      if(authHeaderArr.length > 1 && authHeaderArr[1] != null) {
        String token = authHeaderArr[1];
        int userId;
        try {
          Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY)
              .parseClaimsJws(token).getBody();
          userId = Integer.parseInt(claims.get("userId").toString());
        }catch (Exception e) {
          httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
          return;
        }
        //if token is valid, forward request to OPA to check if action is authorised
        if (userIsAuthorized(httpRequest, token)) {
          httpRequest.setAttribute("userId", userId); //set userId attribute for use in consequent functions
        } else {
          httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "User is not authorized to perform this action");
          return;
        }
      } else {
        httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be Bearer [token]");
        return;
      }
    } else {
      httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
      return;
    }
    filterChain.doFilter(servletRequest, servletResponse); //todo I think we have to pass httpRequest and httpResponse here
  }

  private boolean userIsAuthorized(HttpServletRequest httpRequest, String token) {
    OpaRequest request = new OpaRequest();
    request.setInput(new Input(httpRequest.getRequestURI(), token, httpRequest.getMethod()));
    return opaClient.getAuthorization(request).getBody().getResult().getFanyacodeapi().getAuthz().getAllow();
  }
}
