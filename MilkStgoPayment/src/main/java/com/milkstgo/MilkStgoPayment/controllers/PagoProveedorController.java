package com.milkstgo.MilkStgoPayment.controllers;

import com.milkstgo.MilkStgoPayment.entities.TurnoEntity;
import com.milkstgo.MilkStgoPayment.services.PagoProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class PagoProveedorController {
    @Autowired
    private PagoProveedorService pagoProveedorService;

    @GetMapping("/test-fecha")
    public String main() {
        pagoProveedorService.fechasQuincena("2", "03", "2023", "01025");
        pagoProveedorService.pagoLeche("01025", "2", "03", "2023");
        return "main";
    }
}
