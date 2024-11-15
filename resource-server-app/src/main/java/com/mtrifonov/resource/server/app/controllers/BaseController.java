package com.mtrifonov.resource.server.app.controllers;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @Mikhail Trifonov
 */

@Controller
public class BaseController {
    
    @GetMapping("/user-resource")
    public String getUserResource(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "authenticated";
    }
    
    @GetMapping("/admin-resource")
    public String getAdminResource(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "admin";
    }
}
