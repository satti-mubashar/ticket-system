
package com.delivery.system.model.payload;

import com.delivery.system.model.dto.LoginDetailsDto;

public class JwtAuthenticationResponse {

    private String accessToken;


    private String tokenType;

    private Long expiryDuration;

    private LoginDetailsDto userDetails;

    public JwtAuthenticationResponse(String accessToken,  Long expiryDuration, LoginDetailsDto loginDto) {
        this.accessToken = accessToken;
        this.expiryDuration = expiryDuration;
        this.tokenType = "Bearer ";
        this.userDetails = loginDto;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiryDuration() {
        return expiryDuration;
    }

    public void setExpiryDuration(Long expiryDuration) {
        this.expiryDuration = expiryDuration;
    }

    public LoginDetailsDto getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(LoginDetailsDto userDetails) {
        this.userDetails = userDetails;
    }
}
