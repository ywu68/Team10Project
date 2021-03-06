package edu.uiowa.projectteam10.controllers;

import javax.validation.Valid;

import edu.uiowa.projectteam10.forms.LoginForm;
import edu.uiowa.projectteam10.forms.RegisterForm;
import edu.uiowa.projectteam10.model.Rides;
import edu.uiowa.projectteam10.model.User;
import edu.uiowa.projectteam10.services.RidesService;
import edu.uiowa.projectteam10.services.UserService;
import edu.uiowa.projectteam10.converter.UsertoRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.Principal;
import java.util.List;

@Controller
public class LoginController extends WebMvcConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private UsertoRegisterForm usertoRegisterForm;
    @Autowired
    private RidesService rideService;
    private User currentUser;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
    }

    @RequestMapping({"/", "/homePage"})
    public String homePage(){return "homePage";}

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@Valid LoginForm loginForm, BindingResult bindingResult){
        if(bindingResult.hasErrors() || !userService.userExistsPasswordCorrect(loginForm)){
            return "login";
        }
        return "home";
    }

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid RegisterForm registerForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors() || userService.userExists(registerForm) || !userService.passwordsMatch(registerForm)){
            return "registration";
        }
        currentUser = userService.saveForm(registerForm);
        return "home";
    }

    @GetMapping("/driver")
    public String driverPage(){return "driver"; }

    @GetMapping("/home")
    public String goHome(Model model, Principal principal){
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        System.out.println(loginedUser);
        return "home";
    }

    @GetMapping("/driver")
    public String driverPage(){return "driver"; }

    @GetMapping("/admin")
    public String adminPage(){return "admin"; }

    @GetMapping("/rides")
    public String ridesPage(Model model){
        List<Rides> rides = this.rideService.getRides();
        model.addAttribute("availableRides", rides);
        return "rides";
    }
}