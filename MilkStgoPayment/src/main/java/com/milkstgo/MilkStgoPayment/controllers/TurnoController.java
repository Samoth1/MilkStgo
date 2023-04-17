package com.milkstgo.MilkStgoPayment.controllers;

import com.milkstgo.MilkStgoPayment.entities.TurnoEntity;
import com.milkstgo.MilkStgoPayment.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @GetMapping("/carga-archivo")
    public String main() {
        return "carga-archivo";
    }

    @PostMapping("/carga-archivo")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        turnoService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        turnoService.leerCsv("Acopio.csv");
        return "redirect:/carga-archivo";
    }

    @GetMapping("/datos-turno")
    public String listar(Model model) {
        ArrayList<TurnoEntity> datas = turnoService.obtenerData();
        model.addAttribute("datas", datas);
        return "datos-turno";
    }
}
