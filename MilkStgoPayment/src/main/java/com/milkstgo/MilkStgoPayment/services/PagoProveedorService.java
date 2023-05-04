package com.milkstgo.MilkStgoPayment.services;

import com.milkstgo.MilkStgoPayment.entities.LecheEntity;
import com.milkstgo.MilkStgoPayment.entities.PagoProveedorEntity;
import com.milkstgo.MilkStgoPayment.entities.TurnoEntity;
import com.milkstgo.MilkStgoPayment.repositories.PagoProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PagoProveedorService {

    @Autowired
    private LecheService lecheService;
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private PagoProveedorRepository pagoProveedorRepository;

    private ArrayList<TurnoEntity> turnosFilters;
    public void fechasQuincena(String quincena, String mes, String anio, String proveedor){
        String fecha1 = "";
        String fecha2 = "";
        String SLASH = "/";
        if (quincena == "1"){
            fecha1 = anio+SLASH+mes+SLASH+"01";
            fecha2 = anio+SLASH+mes+SLASH+"15";
        } else if (quincena == "2") {
            fecha1 = anio+SLASH+mes+SLASH+"16";
            fecha2 = anio+SLASH+mes+SLASH+"31";
        }
        Date quincena1 = new Date(fecha1);
        Date quincena2 = new Date(fecha2);

        ArrayList<TurnoEntity> turnosByQuincenaAndProveedor = turnoService.obtenerDataByDatesAndProveedor(quincena1, quincena2, proveedor);
        this.turnosFilters = turnosByQuincenaAndProveedor;
        pagoLeche(proveedor, quincena, mes, anio);
    }

    //calculo pago acopio leche ()
    public void pagoLeche(String quincena, String mes, String anio, String proveedor){
        String categoriaProveedor = proveedorService.obtenerCategoria(proveedor);
        Integer categoriaValor = valorCategoriaPago(categoriaProveedor);

        Integer totalKG=0;
        Integer contDias=0;
        Double bonificacion = 0.0;
        Boolean turnoManCheck = false;
        Boolean turnoTarCheck = false;

        for (TurnoEntity turno : turnosFilters) {
            totalKG += Integer.parseInt(turno.getKls_leche());
            contDias += 1;
            if (turno.getTurno().equals("M")) turnoManCheck = true;
            if (turno.getTurno().equals("T")) turnoTarCheck = true;
        }

        //cont dias mal hecho, considera mañana y tarde como dias distintos
        if (turnoManCheck && turnoTarCheck && contDias>1) bonificacion = 1.2;
        else if (turnoManCheck && !turnoTarCheck && contDias>1) bonificacion = 1.12;
        else if (!turnoManCheck && turnoTarCheck && contDias>1) bonificacion = 1.08;
        else bonificacion=1.0;

        Integer pagoKG = categoriaValor * totalKG;

        LecheEntity dataLeche = lecheService.obtenerDataProveedor(proveedor);
        Integer pagoGrasa = valorGrasaPago(dataLeche.getGrasa()) * totalKG;
        Integer pagoST = valorSTPago(dataLeche.getSolido_total()) * totalKG;
        Integer pagoTotalLeche = pagoKG + pagoGrasa + pagoST;
        // total del pago acopio leche
        Double pagoTotalBoni = pagoTotalLeche * bonificacion;

        //calculos de descuentos por variacion
        String quincenaAnt = quincenaAnterior(quincena, mes, anio);
        Integer klsLecheMesAnt = datosQuincenaAnterior(quincenaAnt, proveedor).getKlsLeche();



        System.out.print(pagoTotalBoni);
    }

    //saber cuanto pagar por kg segun categoria del proveedor
    public Integer valorCategoriaPago(String categoria){
        switch (categoria) {
            case "A":
                return 700;
            case "B":
                return 550;
            case "C":
                return 400;
            case "D":
                return 250;
            default:
                return 0;
        }
    }

    public Integer valorGrasaPago(String grasa){
        Integer valorGrasa = Integer.parseInt(grasa);
        if (0<=valorGrasa && valorGrasa<=20) return 30;
        if (21<=valorGrasa && valorGrasa<=45) return 80;
        if (46<=valorGrasa) return 120;
        return 0;
    }

    public Integer valorSTPago(String st){
        Integer valorST = Integer.parseInt(st);
        if (0<=valorST && valorST<=7) return -130;
        if (8<=valorST && valorST<=18) return -90;
        if (19<=valorST && valorST<=35) return 95;
        if (36<=valorST) return 150;
        return 0;
    }

    public PagoProveedorEntity datosQuincenaAnterior(String quincenaAnterior, String proveedor){
        return pagoProveedorRepository.findByCodigoProveedorAndQAndQuincena(proveedor, quincenaAnterior);
        //Integer lecheKlsAnterior = datosMesAnterior.getKlsLeche();
    }

    public String quincenaAnterior(String quincena, String mes, String anio){
        String quincenaAnterior = "";
        String mesAnterior = "";
        String anioAnterior = "";
        String quincencaAntConsulta = "";

        //caso especial cambio de anio
        if (quincena.equals("01") && mes.equals("01")){
            quincenaAnterior = "2";
            mesAnterior = "12";
            anioAnterior = Integer.toString(Integer.parseInt(anio) - 1);
        } else if (quincena.equals("01")) {
            quincenaAnterior = "02";
            mesAnterior = Integer.toString(Integer.parseInt(mes) - 1);
            anioAnterior = anio;
        }
        else{
            quincenaAnterior = "01";
            mesAnterior = mesAnterior;
            anioAnterior = anioAnterior;
        }
        quincencaAntConsulta = quincenaAnterior+"/"+mesAnterior+"/"+anioAnterior;
        return quincencaAntConsulta;
    }

    //captura el codigo de proveedor y la quincena para crear la planilla con datos (calculos con reporte anterior)
    //TO DO: seguir rellenando para setearle todos los datos, historia de usuario 6
    public void reportePago(String quincena, String mes, String anio, String proveedor){
        PagoProveedorEntity reporteFinalPago = new PagoProveedorEntity();
        reporteFinalPago.setCodigoProveedor(proveedor);
        reporteFinalPago.setQuincena(quincena);
    }
}
