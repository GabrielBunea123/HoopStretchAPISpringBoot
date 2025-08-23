package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.config.properties.JwtConfigProperties;
import com.HoopStretchApi.service.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtConfigProperties jwtConfigProperties;

    @Override
    public String extractUsername(final String token) {
        return extractClaim(token, DecodedJWT::getSubject);
    }

    @Override
    public <T> T extractClaim(final String token, final Function<DecodedJWT, T> claimsResolver) {
        final DecodedJWT jwt = extractAllClaims(token);
        return claimsResolver.apply(jwt);
    }

    @Override
    public String generateToken(final UserDetails userDetails, final long jwtExpiration) {
        return generateToken(Map.of(), userDetails, jwtExpiration);
    }

    @Override
    public String generateToken(final Map<String, Object> extraClaims, final UserDetails userDetails, final long jwtExpiration) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    @Override
    public boolean isTokenValid(final String token, final UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtConfigProperties.getSecret().getBytes());
    }

    private String buildToken(
            final Map<String, Object> extraClaims,
            final UserDetails userDetails,
            final long expiration
    ) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withPayload(extraClaims)
                .sign(getAlgorithm());
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token, DecodedJWT::getExpiresAt);
    }

    private DecodedJWT extractAllClaims(final String token) {
        final JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }
}
