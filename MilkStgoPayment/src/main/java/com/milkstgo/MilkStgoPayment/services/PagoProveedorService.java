package com.milkstgo.MilkStgoPayment.services;

import com.milkstgo.MilkStgoPayment.entities.LecheEntity;
import com.milkstgo.MilkStgoPayment.entities.PagoProveedorEntity;
import com.milkstgo.MilkStgoPayment.entities.TurnoEntity;
import com.milkstgo.MilkStgoPayment.repositories.PagoProveedorRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public String quincenaAnterior(String quincena, String mes, String anio) {
        String quincenaAnterior = "";
        String mesAnterior = "";
        String anioAnterior = "";
        String quincencaAntConsulta = "";

        //caso especial cambio de anio
        if (quincena.equals("1") && mes.equals("01")) {
            quincenaAnterior = "2";
            mesAnterior = "12";
            anioAnterior = Integer.toString(Integer.parseInt(anio) - 1);
        } else if (quincena.equals("1")) {
            quincenaAnterior = "2";
            mesAnterior = Integer.toString(Integer.parseInt(mes) - 1);
            anioAnterior = anio;
        } else {
            quincenaAnterior = "1";
            mesAnterior = mes;
            anioAnterior = anio;
        }
        quincencaAntConsulta = quincenaAnterior + "/" + mesAnterior + "/" + anioAnterior;
        return quincencaAntConsulta;
    }

    public Integer valorCategoriaPago(String categoria) {
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

    public Integer valorGrasaPago(String grasa) {
        Integer valorGrasa = Integer.parseInt(grasa);
        if (0 <= valorGrasa && valorGrasa <= 20) return 30;
        if (21 <= valorGrasa && valorGrasa <= 45) return 80;
        if (46 <= valorGrasa) return 120;
        return 0;
    }

    public Integer valorSTPago(String st) {
        Integer valorST = Integer.parseInt(st);
        if (0 <= valorST && valorST <= 7) return -130;
        if (8 <= valorST && valorST <= 18) return -90;
        if (19 <= valorST && valorST <= 35) return 95;
        if (36 <= valorST) return 150;
        return 0;
    }

    public Double dctoVariacionLeche(String proveedor, String quincena) {
        PagoProveedorEntity pagoProveedorData = pagoProveedorRepository.findByCodigoProveedorAndQuincena(proveedor, quincena);
        Double porcentajeVariacionLeche = 0.0;
        if (pagoProveedorData == null) return 0.0;
        else porcentajeVariacionLeche = pagoProveedorData.getVariacionLeche();

        if (porcentajeVariacionLeche == null || (0 <= porcentajeVariacionLeche && porcentajeVariacionLeche <= 8))
            return 0.0;
        else if (9 <= porcentajeVariacionLeche && porcentajeVariacionLeche <= 25) return 0.07;
        else if (26 <= porcentajeVariacionLeche && porcentajeVariacionLeche <= 40) return 0.15;
        else return 0.3;
    }

    public Double dctoVariacionGrasa(String proveedor, String quincena) {
        PagoProveedorEntity pagoProveedorData = pagoProveedorRepository.findByCodigoProveedorAndQuincena(proveedor, quincena);
        Double porcentajeVariacionGrasa = 0.0;
        if (pagoProveedorData == null) return 0.0;
        else porcentajeVariacionGrasa = pagoProveedorData.getVariacionGrasa();

        if (porcentajeVariacionGrasa == null || (0 <= porcentajeVariacionGrasa && porcentajeVariacionGrasa <= 15))
            return 0.0;
        else if (16 <= porcentajeVariacionGrasa && porcentajeVariacionGrasa <= 25) return 0.12;
        else if (26 <= porcentajeVariacionGrasa && porcentajeVariacionGrasa <= 40) return 0.2;
        else return 0.3;
    }

    public Double dctoVariacionST(String proveedor, String quincena) {
        PagoProveedorEntity pagoProveedorData = pagoProveedorRepository.findByCodigoProveedorAndQuincena(proveedor, quincena);
        Double porcentajeVariacionST = 0.0;
        if (pagoProveedorData == null) return 0.0;
        else porcentajeVariacionST = pagoProveedorData.getVariacionST();

        if (porcentajeVariacionST == null || 0 <= porcentajeVariacionST && porcentajeVariacionST <= 6) return 0.0;
        else if (7 <= porcentajeVariacionST && porcentajeVariacionST <= 12) return 0.18;
        else if (13 <= porcentajeVariacionST && porcentajeVariacionST <= 35) return 0.27;
        else return 0.45;
    }

    public Double dctoRetencion(String proveedor, String quincena, ArrayList<TurnoEntity> turnos) {
        String retencion = proveedorService.obtenerRetencion(proveedor);
        Double totalPago = pagoTotal(proveedor, quincena, turnos);
        if (retencion.equals("Si") && totalPago > 950000.0) return 0.13;
        else return 0.0;
    }

    public Integer totalKlsLeche(ArrayList<TurnoEntity> turnosfiltrados) {
        this.turnosFilters = turnosfiltrados;
        Integer totalKG = 0;
        for (TurnoEntity turno : turnosfiltrados) {
            totalKG += Integer.parseInt(turno.getKls_leche());
        }
        return totalKG;
    }

    public Integer diasEnvioLeche(ArrayList<TurnoEntity> turnos) {
        Integer contDias = 0;
        Date tempFecha = new Date();
        for (TurnoEntity turno : turnos) {
            if (!(tempFecha.equals(turno.getFecha()))) {
                contDias += 1;
            }
            tempFecha = turno.getFecha();
        }
        return contDias;
    }

    public Double pagoLeche(String proveedor, ArrayList<TurnoEntity> turnos) {
        Integer categoriaValor = valorCategoriaPago(proveedorService.obtenerCategoria(proveedor));
        double klsLeche = totalKlsLeche(turnos);
        Double pagoKGLeche = categoriaValor * klsLeche;
        return pagoKGLeche;
    }

    public Double pagoGrasa(String proveedor, ArrayList<TurnoEntity> turnos) {
        LecheEntity dataLeche = lecheService.obtenerDataProveedor(proveedor);
        double klsLeche = totalKlsLeche(turnos);
        Double pagoPorGrasa = valorGrasaPago(dataLeche.getGrasa()) * klsLeche;
        return pagoPorGrasa;
    }

    public Double pagoST(String proveedor, ArrayList<TurnoEntity> turnos) {
        LecheEntity dataLeche = lecheService.obtenerDataProveedor(proveedor);
        double klsLeche = totalKlsLeche(turnos);
        Double pagoST = valorSTPago(dataLeche.getSolido_total()) * klsLeche;
        return pagoST;
    }

    public Double pagoBonificacion(ArrayList<TurnoEntity> turnos) {
        Double bonificacion = 0.0;
        Integer diasEnvio = diasEnvioLeche(turnos);
        Boolean turnoManCheck = false;
        Boolean turnoTarCheck = false;
        for (TurnoEntity turno : turnos) {
            if (turno.getTurno().equals("M")) turnoManCheck = true;
            if (turno.getTurno().equals("T")) turnoTarCheck = true;
        }
        if (turnoManCheck && turnoTarCheck && diasEnvio > 10) bonificacion = 20.0;
        else if (turnoManCheck && !turnoTarCheck && diasEnvio > 10) bonificacion = 12.0;
        else if (!turnoManCheck && turnoTarCheck && diasEnvio > 10) bonificacion = 8.0;
        else bonificacion = 0.0;

        return bonificacion;
    }

    public Double pagoAcopioLeche(String proveedor, String quincena, ArrayList<TurnoEntity> turnos) {
        Double pagoTotalLeche = pagoLeche(proveedor, turnos) + pagoGrasa(proveedor, turnos) + pagoST(proveedor, turnos);
        Double pagoAcopioLeche = pagoTotalLeche + (pagoTotalLeche * (pagoBonificacion(turnosFilters) / 100));
        return pagoAcopioLeche;
    }

    public Double pagoTotal(String proveedor, String quincena, ArrayList<TurnoEntity> turnos) {
        Double pagoLeche = pagoAcopioLeche(proveedor, quincena, turnos);
        Double pagoDctoLeche = pagoLeche * dctoVariacionLeche(proveedor, quincena);
        Double pagoDctoGrasa = pagoLeche * dctoVariacionGrasa(proveedor, quincena);
        Double pagoDctoST = pagoLeche * dctoVariacionST(proveedor, quincena);
        Double pagoDcto = pagoLeche - pagoDctoLeche - pagoDctoGrasa - pagoDctoST;
        return pagoDcto;
    }

    public Double pagoFinal(String proveedor, String quincena, ArrayList<TurnoEntity> turnos) {
        Double pago = pagoTotal(proveedor, quincena, turnos);
        Double dcto = montoRetenido(proveedor, quincena, turnos);
        Double pagoFinalProveedor = pago - dcto;
        return pagoFinalProveedor;
    }

    public Double montoRetenido(String proveedor, String quincena, ArrayList<TurnoEntity> turnos) {
        Double pago = pagoTotal(proveedor, quincena, turnos);
        Double dcto = dctoRetencion(proveedor, quincena, turnos);
        Double retencion = pago * dcto;
        return retencion;
    }

    public Double varLecheProov(String quincena, String mes, String anio, String proveedor, ArrayList<TurnoEntity> turnos) {
        String quincenaMesAnt = quincenaAnterior(quincena, mes, anio);
        PagoProveedorEntity pagoProveedorData = pagoProveedorRepository.findByCodigoProveedorAndQuincena(proveedor, quincenaMesAnt);
        if (pagoProveedorData == null) return 0.0;

        double klSLecheMes = totalKlsLeche(turnos);
        double klsLecheMesAnt = pagoProveedorData.getKlsLeche();
        Double porcentajeVariacionLeche = (((klsLecheMesAnt - klSLecheMes) / klSLecheMes) * 100);
        return porcentajeVariacionLeche;
    }

    public Double varGrasaProov(String quincena, String mes, String anio, String proveedor) {
        String quinAnt = quincenaAnterior(quincena, mes, anio);
        PagoProveedorEntity pagoProveedorData = pagoProveedorRepository.findByCodigoProveedorAndQuincena(proveedor, quinAnt);
        if (pagoProveedorData == null) return 0.0;

        double grasaQuin = Integer.parseInt(lecheService.obtenerGrasa(proveedor));
        double grasaQuinAnt = pagoProveedorData.getPorcentajeGrasa();
        Double porcentajeVarGrasa = ((grasaQuinAnt - grasaQuin) / grasaQuin) * 100;
        return porcentajeVarGrasa;
    }

    public Double varSTProov(String quincena, String mes, String anio, String proveedor) {
        String quinAnt = quincenaAnterior(quincena, mes, anio);
        PagoProveedorEntity pagoProveedorData = pagoProveedorRepository.findByCodigoProveedorAndQuincena(proveedor, quinAnt);
        if (pagoProveedorData == null) return 0.0;

        double stQuin = Integer.parseInt(lecheService.obtenerSolidoTotal(proveedor));
        double stQuinAnt = pagoProveedorData.getPorcentajeST();
        Double porcentajeVarST = ((stQuinAnt - stQuin) / stQuin) * 100;
        return porcentajeVarST;
    }

    //captura el codigo de proveedor y la quincena para crear la planilla con datos (calculos con reporte anterior)
    @Generated
    public void reportePago(String quincena, String mes, String anio, String proveedor) {
        PagoProveedorEntity reporteFinalPago = new PagoProveedorEntity();
        reporteFinalPago.setQuincena(quincena + "/" + mes + "/" + anio);
        reporteFinalPago.setCodigoProveedor(proveedor);
        reporteFinalPago.setNombreProveedor(proveedorService.obtenerNombre(proveedor));
        reporteFinalPago.setKlsLeche(totalKlsLeche(this.turnosFilters));
        reporteFinalPago.setDiasEnvioLeche(diasEnvioLeche(this.turnosFilters));
        //colocar bien promedio dias leche
        reporteFinalPago.setPromedioLeche(0.0);
        reporteFinalPago.setVariacionLeche(varLecheProov(quincena, mes, anio, proveedor, this.turnosFilters));
        reporteFinalPago.setPorcentajeGrasa(Integer.parseInt(lecheService.obtenerGrasa(proveedor)));
        reporteFinalPago.setVariacionGrasa(varGrasaProov(quincena, mes, anio, proveedor));
        reporteFinalPago.setPorcentajeST(Integer.parseInt(lecheService.obtenerSolidoTotal(proveedor)));
        reporteFinalPago.setVariacionST(varSTProov(quincena, mes, anio, proveedor));
        reporteFinalPago.setPagoLeche(pagoLeche(proveedor, this.turnosFilters));
        reporteFinalPago.setPagoGrasa(pagoGrasa(proveedor, this.turnosFilters));
        reporteFinalPago.setPagoST(pagoST(proveedor, this.turnosFilters));
        reporteFinalPago.setPagoBono(pagoBonificacion(this.turnosFilters));
        reporteFinalPago.setDctoVarLeche(dctoVariacionLeche(proveedor, quincena + "/" + mes + "/" + anio));
        reporteFinalPago.setDctoVarGrasa(dctoVariacionGrasa(proveedor, quincena + "/" + mes + "/" + anio));
        reporteFinalPago.setDctoVarST(dctoVariacionST(proveedor, quincena + "/" + mes + "/" + anio));
        reporteFinalPago.setPagoTotal(pagoTotal(proveedor, quincena + "/" + mes + "/" + anio, this.turnosFilters));
        reporteFinalPago.setMontoRetencion(montoRetenido(proveedor, quincena + "/" + mes + "/" + anio, this.turnosFilters));
        reporteFinalPago.setMontoFinal(pagoFinal(proveedor, quincena + "/" + mes + "/" + anio, this.turnosFilters));
        pagoProveedorRepository.save(reporteFinalPago);
    }

    public PagoProveedorEntity plantillaPagoFiltrado(String proveedor, String quincena) {
        return pagoProveedorRepository.findByCodigoProveedorAndQuincena(proveedor, quincena);
    }

    @Generated
    public void plantillaPagos() {
        ArrayList<String> quincena = obtenerQuincena();
        ArrayList<String> proveedores = turnoService.obtenerProveedores();
        this.turnosFilters = turnoService.obtenerDataTurnos();
        for (String proveedor : proveedores) {
            reportePago(quincena.get(0), quincena.get(1), quincena.get(2), proveedor);
        }
    }

    //obtiene la fecha y retorno la quincena q/m/aaaa
    @Generated
    public ArrayList<String> obtenerQuincena(){
        ArrayList<String> fechaQuincena = new ArrayList<>();
        Date fecha = turnoService.obtenerDataTurnos().get(0).getFecha();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        fechaQuincena.add(calendar.get(Calendar.DAY_OF_MONTH) >15? "2":"1");
        Integer mes = calendar.get(Calendar.MONTH);
        Integer anio = calendar.get(Calendar.YEAR);
        fechaQuincena.add(mes.toString());
        fechaQuincena.add(anio.toString());
        return fechaQuincena;
    }
}