package com.fanyacode.fanyacode.controller;

import com.fanyacode.fanyacode.controller.model.request.UpdateAuthorityRequest;
import com.fanyacode.fanyacode.controller.model.response.SimpleSuccessResponse;
import com.fanyacode.fanyacode.service.AuthorityService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authorities")
public class AuthorityController {
  @Autowired
  AuthorityService authorityService;

  @PutMapping("")
  public ResponseEntity<SimpleSuccessResponse> updateAuthority(
      HttpServletRequest request,
      @RequestBody UpdateAuthorityRequest requestBody
  ) {
    int userId = (Integer) request.getAttribute("userId");
    authorityService.updateAuthority(userId, requestBody.getAuthority());
    return new ResponseEntity<>(new SimpleSuccessResponse(true), HttpStatus.OK);
  }
}
