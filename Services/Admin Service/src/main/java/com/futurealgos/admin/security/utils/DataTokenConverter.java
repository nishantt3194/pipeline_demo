package com.futurealgos.admin.security.utils;


import org.springframework.stereotype.Service;

@Service
public class DataTokenConverter {

//    //TODO : Verify this
//    private final String SECRET_KEY = "NaZiHSHJxhLDS7xGcA1JNsNaxj7Ln9uI8KV8AmWX4ORyUNThtTUMgFbf4F0wxDE9";
//    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // Expiration time: 24hrs
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//    }
//
//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public String generateToken(String username , Role role, boolean assigned, List<String> associations, Date exp) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("role", role);
//        claims.put("assigned", assigned);
//        claims.put("association",associations);
//        ;
//        return createToken(claims, username, exp);
//    }
//
//    private String createToken(Map<String, Object> claims, String subject, Date expiration) {
//
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(expiration)
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//    }
//
//    public Boolean validateToken(String token, UserDetails adminDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(adminDetails.getUsername()) && !isTokenExpired(token));
//    }


}
