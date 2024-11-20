package springcource.PP_3_1_3_SpringSecurity.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import springcource.PP_3_1_3_SpringSecurity.model.User;
import springcource.PP_3_1_3_SpringSecurity.services.UserService;

@Component
@RequiredArgsConstructor
public class CustomInterceptor implements HandlerInterceptor {
    private final UserService userService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
        if (modelAndView != null) {
            String viewName = modelAndView.getViewName();
            if ("/admin/usersList".equals(viewName)) {
                // Передаем список пользователей в модель для usersList
                modelAndView.addObject("users", userService.getAll());
            } else if ("/user/userPage".equals(viewName)) {
                // Передаем текущего пользователя в модель для userPage
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.getPrincipal() instanceof User currentUser) {
                    modelAndView.addObject("user", currentUser);
                }
            }
        }
    }
}

