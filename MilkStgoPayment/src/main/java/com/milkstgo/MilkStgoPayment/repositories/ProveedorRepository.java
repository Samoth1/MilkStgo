package com.milkstgo.MilkStgoPayment.repositories;

import com.milkstgo.MilkStgoPayment.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, String> {
    ProveedorEntity findByCodigo(String codigo);
}
