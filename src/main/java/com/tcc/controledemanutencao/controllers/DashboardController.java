package com.tcc.controledemanutencao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tcc.controledemanutencao.interfaces.DashboardServiceInterface;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardServiceInterface dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute(
                "dashboard",
                dashboardService.getDashboard());

        return "dashboard/index";
    }
}
