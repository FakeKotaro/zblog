package com.zjw.myblog.controller.admin;

import com.zjw.myblog.pojo.User;
import com.zjw.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model) {
        return "admin-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {

        User user = userService.checkUser(username , password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user" , user);
            if (session.getAttribute("message") != null) {
                session.removeAttribute("message");
            }
            return "redirect:/admin/index";
        } else {
            if (session.getAttribute("message") == null) {
                session.setAttribute("message" , "用户名或密码错误");
            }
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @GetMapping("/index")
    public String index() {
        return "admin-index";
    }
}
