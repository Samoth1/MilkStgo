package com.milkstgo.MilkStgoPayment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Entity
@Table(name = "pago_proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagoProveedorEntity {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String quincena;
    private Date fechaInicioQuin;
    private Date fechaTerminoQuin;
    private String codigoProveedor;
    private String nombreProveedor;
    private Integer klsLeche;
    private Integer diasEnvioLeche;
    private Integer promedioLeche;
    private Integer variacionLeche;
    private Integer variacionGrasa;
    private Integer variacionST;
    private Integer pagoLeche;
    private Integer pagoGrasa;
    private Integer pagoST;
    private Integer pagoBono;
    private Integer dctoVarLeche;
    private Integer dctoVarGrasa;
    private Integer dctoVarST;
    private Integer pagoTotal;
    private Integer montoRetencion;
    private Integer montoFinal;
}
