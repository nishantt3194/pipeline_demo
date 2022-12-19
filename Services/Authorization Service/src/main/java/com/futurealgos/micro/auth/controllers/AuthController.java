/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.controllers;

import com.futurealgos.micro.auth.config.AuthServerConfig;
import com.futurealgos.micro.auth.utils.enums.PlatformScopes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AuthController {

    private final RegisteredClientRepository registeredClientRepository;

    private final OAuth2AuthorizationConsentService authorizationConsentService;


    public AuthController(RegisteredClientRepository registeredClientRepository,
                          OAuth2AuthorizationConsentService authorizationConsentService) {
        this.registeredClientRepository = registeredClientRepository;
        this.authorizationConsentService = authorizationConsentService;
    }

    @GetMapping({"", "/"})
    public String home() {

//        EntityStore platform = EntityStore.builder()
//                .entity("PLATFORM")
//                .description("Represent the complete Platform")
//                .build();
//
//        platform = entityStoreRepo.save(platform);
//
//
//        Role uiAccess = Role.builder()
//                .tag("UI_ACCESS")
//                .description("Access to Platform via Web Console")
//                .permission(Permission.ALL)
//                .store(platform)
//                .build();
//
//        roleRepo.save(uiAccess, "superadmin@industrialdata.in");
//
//        Role apiAccess = Role.builder()
//                .tag("API_ACCESS")
//                .description("Access to Platform via API")
//                .permission(Permission.ALL)
//                .store(platform)
//                .build();
//
//        roleRepo.save(apiAccess, "superadmin@industrialdata.in");

//        List<EntityStore> store = entityStoreRepo.findAll().stream()
//                .filter(s -> !s.getEntity().equalsIgnoreCase("AUTHORIZATION")).toList();
//
//        Permission[] permissions = Permission.values();
//
//        for (EntityStore s : store) {
//            for (Permission p : permissions) {
//                Role role = Role.builder()
//                        .store(s)
//                        .permission(p)
//                        .tag(s.getEntity() + "_" + p.name())
//                        .description(StringUtils.capitalize(p.name().toLowerCase())
//                                + " Access To " + StringUtils.capitalize(s.getEntity().toLowerCase()) + " Entity")
//                        .build();
//
//                roleRepo.save(role, "superadmin@industrialdata.in");
//            }
//        }
//
//        Group admins = Group.builder()
//                .groupName("Super Administrators")
//                .description("Group to represent Super Administrators")
//                .build();
//
//        List<Role> roles = new ArrayList<>();
//        roleRepo.findAll().stream()
//                .filter(r -> !r.getTag().equalsIgnoreCase("IDP_ACCESS") && r.getPermission() == Permission.ALL).forEach(roles::add);
//
//        admins.setRoles(roles);

//        groupRepo.save(admins, "superadmin@industrialdata.in");

//        RegisteredClient partner = RegisteredClient.withId("temp")
//                .clientId("partner_ui")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("http://127.0.0.1:4401/")
//                .redirectUri("https://partner.prasadpsycho.industrialdata.in/")
//                .scope(OidcScopes.OPENID)
//                .scope(PlatformScopes.EXAMINEE_READ.tag())
//                .scope(PlatformScopes.EXAMINEE_WRITE.tag())
//                .scope(PlatformScopes.ASSESSMENT_READ.tag())
//                .scope(PlatformScopes.ASSESSMENT_WRITE.tag())
//                .scope(PlatformScopes.REPORTS_WRITE.tag())
//                .scope(PlatformScopes.REPORTS_READ.tag())
//                .scope(PlatformScopes.LIBRARY_READ.tag())
//                .scope(PlatformScopes.DOCS_WRITE.tag())
//                .scope(PlatformScopes.DOCS_READ.tag())
//                .scope(PlatformScopes.NOTIFICATIONS_READ.tag())
//                .scope(PlatformScopes.TEST_RESTRICTED_READ.tag())
//                .scope(PlatformScopes.MESSAGES_READ.tag())
//                .scope(PlatformScopes.MESSAGES_WRITE.tag())
//                .clientSettings(ClientSettings.builder()
//                        .requireAuthorizationConsent(true).build())
//                .tokenSettings(TokenSettings.builder()
//                        .accessTokenTimeToLive(Duration.ofHours(8))
//                        .build())
//                .build();
//
//        RegisteredClient admin = RegisteredClient.withId("temp")
//                .clientId("admin_ui")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("http://127.0.0.1:4200/")
//                .redirectUri("https://admin.prasadpsycho.industrialdata.in/")
//                .scope(OidcScopes.OPENID)
//                .scope(PlatformScopes.PROFILE.tag())
//                .scope(PlatformScopes.TEST_WRITE.tag())
//                .scope(PlatformScopes.TEST_READ.tag())
//                .scope(PlatformScopes.ADMIN_READ.tag())
//                .scope(PlatformScopes.ADMIN_WRITE.tag())
//                .scope(PlatformScopes.PARTNER_WRITE.tag())
//                .scope(PlatformScopes.PARTNER_READ.tag())
//                .scope(PlatformScopes.LIBRARY_READ.tag())
//                .scope(PlatformScopes.LIBRARY_WRITE.tag())
//                .scope(PlatformScopes.NOTIFICATIONS_READ.tag())
//                .scope(PlatformScopes.MESSAGES_READ.tag())
//                .scope(PlatformScopes.MESSAGES_WRITE.tag())
//                .clientSettings(ClientSettings.builder()
//                        .requireAuthorizationConsent(true).build())
//                .tokenSettings(TokenSettings.builder()
//                        .accessTokenTimeToLive(Duration.ofHours(8))
//                        .build())
//                .build();

//        clientService.save(admin);
//        clientService.save(partner);
        return "index";
    }

    @GetMapping({"/login"})
    public String loginPage(@RequestParam(value = "client_id", required = false) String clientId, Model model) {
        model.addAttribute("client_id", clientId);
        return "login";
    }

    @GetMapping(value = AuthServerConfig.CONSENT_ENDPOINT)
    public String consent(
            Principal principal, Model model,
            @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
            @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
            @RequestParam(OAuth2ParameterNames.STATE) String state) {
        Set<PlatformScopes> toBeApproved = new HashSet<>();
        Set<PlatformScopes> previouslyApproved = new HashSet<>();

        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);

        if(registeredClient == null) throw new RuntimeException("Client not found");

        OAuth2AuthorizationConsent currentAuthorizationConsent =
                this.authorizationConsentService.findById(registeredClient.getId(), principal.getName());

        Set<String> scopes = currentAuthorizationConsent != null ? currentAuthorizationConsent.getScopes() : new HashSet<>();

        for(String requestedScopes: StringUtils.delimitedListToStringArray(scope, " ")) {
            if(OidcScopes.OPENID.equals(requestedScopes)) continue;

            PlatformScopes platformScopes = PlatformScopes.fromTag(requestedScopes);

            if(!scopes.contains(requestedScopes)) {
                toBeApproved.add(platformScopes);
            } else {
                previouslyApproved.add(platformScopes);
            }
        }

        model.addAttribute("clientId", clientId);
        model.addAttribute("scopes", toBeApproved);
        model.addAttribute("approved", previouslyApproved);
        model.addAttribute("state", state);
        model.addAttribute("clientName", registeredClient.getClientName());
        model.addAttribute("principalName", principal.getName());

        return "consent";

    }

    @GetMapping("/clients/list")
    public String clients() {
        return "clients";
    }

    @GetMapping("/clients/create")
    public String newClient() {
        return "clients-create";
    }

    @GetMapping("/clients/info")
    public String clientInfo() {
        return "clientinfo";
    }

}
