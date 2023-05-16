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
                                @RequestParam("codigo") String codigo) {

        pagoProveedorService.fechasQuincena(quincena, mes, anio, codigo);
        pagoProveedorService.reportePago(quincena, mes, anio, codigo);

        pagoProveedor = pagoProveedorService.plantillaPago(codigo, quincena+"/"+mes+"/"+anio);
        System.out.print(pagoProveedor);

        return "redirect:/reporte-pago";
    }

    @GetMapping("/reporte-pago")
    public String mostrarPago(Model model){
        model.addAttribute("reporte_pago", pagoProveedor);
        return "reporte-pago";
    }


}
