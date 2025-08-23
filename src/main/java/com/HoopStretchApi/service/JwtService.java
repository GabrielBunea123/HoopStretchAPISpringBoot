package com.HoopStretchApi.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(final String token);

    <T> T extractClaim(final String token, final Function<DecodedJWT, T> claimsResolver);

    String generateToken(final UserDetails userDetails, final long jwtExpiration);

    String generateToken(final Map<String, Object> extraClaims, final UserDetails userDetails, final long jwtExpiration);

    boolean isTokenValid(final String token, final UserDetails userDetails);
}
