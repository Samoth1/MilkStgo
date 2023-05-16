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
    private Double promedioLeche;
    private Double variacionLeche;
    private Integer porcentajeGrasa;
    private Double variacionGrasa;
    private Integer porcentajeST;
    private Double variacionST;
    private Double pagoLeche;
    private Double pagoGrasa;
    private Double pagoST;
    private Double pagoBono;
    private Double dctoVarLeche;
    private Double dctoVarGrasa;
    private Double dctoVarST;
    private Double pagoTotal;
    private Double montoRetencion;
    private Double montoFinal;
}
