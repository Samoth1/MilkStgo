package com.milkstgo.MilkStgoPayment.controllers;

import com.milkstgo.MilkStgoPayment.entities.PagoProveedorEntity;
import com.milkstgo.MilkStgoPayment.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class PagoProveedorController {

    private PagoProveedorEntity pagoProveedor;

    @Autowired
    private PagoProveedorService pagoProveedorService;

    @GetMapping("/test-fecha")
    public String main() {
        return "main";
    }

    @PostMapping("/proveedor-data")
    public String capturarDatos(@RequestParam("quincena") String quincena,
                                @RequestParam("mes") String mes,
                                @RequestParam("anio") String anio,
                                @RequestParam("codigo") String codigo,
                                RedirectAttributes redirectAttributes) {

        PagoProveedorEntity pago = pagoProveedorService.plantillaPagoFiltrado(codigo, quincena+"/"+mes+"/"+anio);
        redirectAttributes.addFlashAttribute("reporte_pago", pago);
        return "redirect:/reporte-pago";
    }

    @GetMapping("/reporte-pago")
    public String mostrarPago(){
        return "reporte-pago";
    }

    @GetMapping("/consulta-quincena")
    public String mostrarPlantilla(){
        return "consulta-quincena";
    }

}
