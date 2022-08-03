package com.fanyacode.fanyacode.authorization;

import com.fanyacode.fanyacode.authorization.model.OpaRequest;
import com.fanyacode.fanyacode.authorization.model.OpaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
    value = "opaclient",
    url = "${opa.client.url}"
)
public interface OpaClient {

    @RequestMapping(
        method = RequestMethod.POST,
        value = "",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<OpaResponse> getAuthorization(OpaRequest request);
}
