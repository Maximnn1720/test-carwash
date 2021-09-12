package com.aisa.itservice.testcarwash.Controller;

import com.aisa.itservice.testcarwash.Entites.ModelOrderAttributes;
import com.aisa.itservice.testcarwash.Entites.Order;
import com.aisa.itservice.testcarwash.Entites.User;
import com.aisa.itservice.testcarwash.Services.IOrderService;
import com.aisa.itservice.testcarwash.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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
        var user = new User();
        user.setAdmin(false);
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user,
                                   Model model,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        try {
            User registered = userService.registerNewUser(user);
        } catch (Exception ex) {
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
            User user = userService.getUserByUserEmail(principal.getName());
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
