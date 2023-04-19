package com.milkstgo.MilkStgoPayment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "data_leche")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LecheEntity {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String grasa;
    private String solido_total;
    private String proveedor;
}
