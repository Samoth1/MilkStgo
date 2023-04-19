package com.milkstgo.MilkStgoPayment.controllers;

import com.milkstgo.MilkStgoPayment.entities.LecheEntity;
import com.milkstgo.MilkStgoPayment.services.LecheService;
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
public class LecheController {
    @Autowired
    private LecheService lecheService;

    @GetMapping("/carga-archivo-leche")
    public String main() {
        return "carga-archivo-leche";
    }

    @PostMapping("/carga-archivo-leche")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        lecheService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        lecheService.leerCsv("AcopioLeche.csv");
        return "redirect:/carga-archivo-leche";
    }

    @GetMapping("/datos-leche")
    public String listar(Model model) {
        ArrayList<LecheEntity> datas = lecheService.obtenerData();
        model.addAttribute("datas", datas);
        return "datos-leche";
    }

}
