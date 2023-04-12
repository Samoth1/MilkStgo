package com.milkstgo.MilkStgoPayment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "data_turno")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TurnoEntity {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fecha;
    private String turno;
    private String proveedor;
    private String kls_leche;
}
