package springcource.PP_3_1_3_SpringSecurity.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcource.PP_3_1_3_SpringSecurity.model.User;
import springcource.PP_3_1_3_SpringSecurity.repositories.RoleRepository;
import springcource.PP_3_1_3_SpringSecurity.services.UserService;

import java.util.Set;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    private final RoleRepository roleRepository;

    @GetMapping("/users")
    public String getUsersList(ModelMap model) {
        model.addAttribute("users", userService.getAll());
        return "/admin/usersList";
    }

    @GetMapping("/user")
    public String getUserById(@RequestParam("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.getById(id));
        return "/admin/userById";
    }

    @GetMapping("/users/new")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAllByNameIn(Set.of("ROLE_USER", "ROLE_ADMIN")).get());
        return "/admin/new";
    }

    @PostMapping("/users")
    public String create(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

        // проверяем ошибки заполнения полей User-а
        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAllByNameIn(Set.of("ROLE_USER", "ROLE_ADMIN")).get());
            return "/admin/new";
        }

        // Проверяем, что выбрана хотя бы одна роль
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            result.rejectValue("roles", "error.user", "Please select at least one role.");
            model.addAttribute("roles", roleRepository.findAllByNameIn(Set.of("ROLE_USER", "ROLE_ADMIN")).get());
            return "/admin/new";
        }

        // Проверяем Password and Confirm_Password
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            result.rejectValue("passwordConfirm", "error.user", "Password and Confirm_Password do not match");
            model.addAttribute("roles", roleRepository.findAllByNameIn(Set.of("ROLE_USER", "ROLE_ADMIN")).get());
            return "/admin/new";
        }

        // Сохраняем пользователя с проверкой уникальности
        if (!userService.save(user)) {
            result.rejectValue("firstName", "error.user", "User with that name already exists");
            model.addAttribute("roles", roleRepository.findAllByNameIn(Set.of("ROLE_USER", "ROLE_ADMIN")).get());
            return "/admin/new";
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/user/edit")
    public String edit(@RequestParam("id") Long id, ModelMap model) {

        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", roleRepository.findAllByNameIn(Set.of("ROLE_USER", "ROLE_ADMIN")).get());
        return "/admin/edit";
    }

    @PostMapping("/users/update")
    public String update(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

        // проверяем ошибки заполнения полей User-а
        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAllByNameIn(Set.of("ROLE_USER", "ROLE_ADMIN")).get());
            return "/admin/edit";
        }

        // Проверяем, что выбрана хотя бы одна роль
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            result.rejectValue("roles", "error.user", "Please select at least one role.");
            model.addAttribute("roles", roleRepository.findAllByNameIn(Set.of("ROLE_USER", "ROLE_ADMIN")).get());
            return "/admin/edit";
        }

        // Проверяем Password and Confirm_Password
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            result.rejectValue("passwordConfirm", "error.user", "Password and Confirm_Password do not match");
            model.addAttribute("roles", roleRepository.findAllByNameIn(Set.of("ROLE_USER", "ROLE_ADMIN")).get());
            return "/admin/edit";
        }

        // Сохраняем пользователя без проверки уникальности (имя может остаться прежним)
        userService.update(user);

        return "redirect:/admin/users";
    }

    @PostMapping("/user/delete")
    public String delete(@RequestParam("id") Long id) {

        userService.delete(id);
        return "redirect:/admin/users";
    }

}
