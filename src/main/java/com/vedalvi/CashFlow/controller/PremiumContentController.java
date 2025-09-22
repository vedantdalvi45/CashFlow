package com.vedalvi.CashFlow.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content")
public class PremiumContentController {

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public String getUserContent() {
        return "This is regular content for any USER.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAdminContent() {
        return "This is top-secret content for ADMINs only.";
    }

    @GetMapping("/premium")
    @PreAuthorize("hasAuthority('USER') and principal.accountType == 'PREMIUM'")
    public String getPremiumUserContent() {
        return "This is exclusive content for PREMIUM account holders!";
    }

    @GetMapping("/premium-or-admin")
    @PreAuthorize("hasAuthority('ADMIN') or (hasAuthority('USER') and principal.accountType == 'PREMIUM')")
    public String getPremiumOrAdminContent() {
        return "This content is for ADMINs or PREMIUM users.";
    }
}
