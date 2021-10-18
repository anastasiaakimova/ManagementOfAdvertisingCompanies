package by.akimova.ManagementOfAdvertisingCompanies.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwtToken;

    public AuthenticationResponse(String jwtToken) {

    }
}