package com.futurealgos.admin.security.utils;

//public class TokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {
//
//
//    private final String principalClaimName;
//
//    private final UserService userService;
//
//    private ConstantService constants;
//
//    public TokenConverter(String principalClaimName, UserService userService, ConstantService constants) {
//        this.principalClaimName = principalClaimName;
//        this.userService = userService;
//        this.constants = constants;
//    }
//
//    @Override
//    public AbstractAuthenticationToken convert(Jwt source) {
//        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, jwt.getTokenValue(), jwt.getIssuedAt(), jwt.getExpiresAt());
//        Map<String, Object> claims = jwt.getClaims();
//
//        String username = constants.extractSubject(jwt);
//        User user;
//        try {
//            user = userService.getUser(username);
//        }
//        catch (NotFoundException e) {
//            throw new UsernameNotFoundException("User is not registered in the system. Please contact Administrator");
//        }
//
//        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()));
//        OAuth2AuthenticatedPrincipal principal = new AadOAuth2AuthorizationCodeGrantRequestEntityConverter(jwt.getHeaders(), claims, authorities, jwt.getTokenValue(), (String)claims.get(this.principalClaimName));
//        return new BearerTokenAuthentication(principal, accessToken, authorities);
//    }
//}
