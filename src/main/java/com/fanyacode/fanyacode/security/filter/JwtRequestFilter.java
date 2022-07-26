package com.fanyacode.fanyacode.security.filter;

import com.fanyacode.fanyacode.Constants;
import com.fanyacode.fanyacode.security.JwtUtil;
import com.fanyacode.fanyacode.security.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private MyUserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

    String email = null;
    String jwtToken = null;
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String[] authHeaderArr = authHeader.split("Bearer ");
      if(authHeaderArr.length > 1 && authHeaderArr[1] != null) {
        jwtToken = authHeaderArr[1];
        try {
          Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY)
              .parseClaimsJws(jwtToken).getBody();
          request.setAttribute("userId", Integer.parseInt(claims.get("userId").toString()));
        }catch (Exception e) {
          response.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
          return;
        }
      }
      email = jwtUtil.extractUsername(jwtToken);
    } else {
      response.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
      return;
    }


    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(email);

      if (jwtUtil.validateToken(jwtToken, userDetails)) { //more validation I guess

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    chain.doFilter(request, response);
  }

}
