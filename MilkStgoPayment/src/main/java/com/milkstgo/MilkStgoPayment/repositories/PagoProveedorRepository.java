package com.milkstgo.MilkStgoPayment.repositories;

import com.milkstgo.MilkStgoPayment.entities.PagoProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoProveedorRepository extends JpaRepository<PagoProveedorEntity, Integer> {
    PagoProveedorEntity findByCodigoProveedorAndQAndQuincena(String proveedor, String quincena);
}
