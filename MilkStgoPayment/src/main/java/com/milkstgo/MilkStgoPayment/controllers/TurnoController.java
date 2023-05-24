package com.milkstgo.MilkStgoPayment.controllers;

import com.milkstgo.MilkStgoPayment.services.PagoProveedorService;
import com.milkstgo.MilkStgoPayment.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PagoProveedorService pagoProveedorService;

    @GetMapping("/carga-archivo-turno")
    public String main() {
        return "carga-archivo-turno";
    }

    @PostMapping("/carga-archivo-turno")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        turnoService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "datos quincena guardados correctamente!");
        redirectAttributes.addFlashAttribute("volver", true);
        turnoService.leerCsv("AcopioTurno.csv");

        pagoProveedorService.plantillaPagos();

        return "redirect:/carga-archivo-turno";
    }

}