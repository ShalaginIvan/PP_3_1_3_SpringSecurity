package springcource.PP_3_1_3_SpringSecurity.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springcource.PP_3_1_3_SpringSecurity.services.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public String getUserById(@RequestParam("id") Long id,
                              ModelMap model) {
        model.addAttribute("user", userService.getById(id));
        return "/user/userPage";
    }

}
