package com.aisa.itservice.testcarwash.Controller;

import com.aisa.itservice.testcarwash.Entites.ModelOrderAttributes;
import com.aisa.itservice.testcarwash.Entites.Order;
import com.aisa.itservice.testcarwash.Entites.User;
import com.aisa.itservice.testcarwash.Entites.UserDto;
import com.aisa.itservice.testcarwash.Exceptions.UserAlreadyExistException;
import com.aisa.itservice.testcarwash.Services.IOrderService;
import com.aisa.itservice.testcarwash.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MainController {

    @Autowired
    @Qualifier("customUserDetailService")
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/")
    public String homePage(WebRequest request, Model model) {
        return "login";
    }

    @PostMapping("/")
    public String homePage(Model model) {
        return "login";
    }


    @GetMapping("/registration")
    public String registrationPage(WebRequest request, Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPage(@ModelAttribute("user") @Valid UserDto user,
                                   Errors errors,
                                   Model model) throws IOException {
        if (errors.hasErrors()) {
            if (errors.getAllErrors().stream().anyMatch(err -> err.getDefaultMessage().equals("Passwords don't match"))) {
                model.addAttribute("message", "Пароли не совпадают");
            }
            return "registration";
        }
        try {
            userService.registerNewUser(new User(user));
        } catch (UserAlreadyExistException ex) {
            model.addAttribute("message", "Пользователь с таким e-mail уже существует");
            return "registration";
        }
        return "login";
    }

    @GetMapping("/user/orders")
    public String ordersUserPage(WebRequest request, Model model) {
        ModelOrderAttributes modelOrderAttributes = getModelOrderAttributes();
        model.addAttribute("modelOrderAttributes", modelOrderAttributes);
        return "/user/orders";
    }

    @ModelAttribute("modelOrderAttributes")
    public ModelOrderAttributes getModelOrderAttributes() {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        var modelOrderAttr = new ModelOrderAttributes();
        orderService.getAll().stream()
                .forEach(order -> modelOrderAttr.getOrdersTime()
                        .add(formatter.format(order.getTimeExecution())));
        return modelOrderAttr;
    }

    @PostMapping("/user/orders")
    public String ordersUserPage(@ModelAttribute("modelOrderAttributes") ModelOrderAttributes modelOrderAttributes,
                                 Model model,
                                 Principal principal,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        try {
            User user = userService.getUserByUserEmail(principal.getName());

            Date dateExecute = getDate(modelOrderAttributes.getTime());

            Order order = new Order();
            order.setTimeExecution(dateExecute);
            order.setTimeCreation(new Date());
            order.setUser(user);
            orderService.saveOrder(order);

        } catch (ParseException ex) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user/orders"));
        }
        var modelOrderAttr = getModelOrderAttributes();
        model.addAttribute("modelOrderAttributes", modelOrderAttr);
        return "/user/orders";
    }

    @GetMapping("/admin/orders")
    public String ordersAdminPage(WebRequest request, Model model) {
        var modelOrderAttr = getModelOrderAttributes();
        model.addAttribute("modelOrderAttributes", modelOrderAttr);
        return "/admin/orders";
    }

    @PostMapping("/admin/orders")
    public String ordersAdminPage(@ModelAttribute("modelOrderAttributes") ModelOrderAttributes modelOrderAttributes,
                                  Model model,
                                  Principal principal,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
        try {
            Date dateExecute = getDate(modelOrderAttributes.getTime());
            orderService.deleteOrderByTime(dateExecute);
        } catch (ParseException ex) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin/orders"));
        }
        var modelOrderAttr = getModelOrderAttributes();
        model.addAttribute("modelOrderAttributes", modelOrderAttr);
        return "/admin/orders";
    }

    @GetMapping("/success")
    public void successPage(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        String role = authResult.getAuthorities().toString();

        if (role.contains("ROLE_ADMIN")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin/orders"));
        } else if (role.contains("ROLE_USER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user/orders"));
        }
    }

    @GetMapping("/logout")
    public void logoutPage() {

    }

    private Date getDate(String stringDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        stringDate += " " + formatter.format(new Date());
        Date dateExecute = new SimpleDateFormat("HH:mm dd.MM.yyyy").parse(stringDate);
        return dateExecute;
    }
}
