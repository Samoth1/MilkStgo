package com.milkstgo.MilkStgoPayment.repositories;

import com.milkstgo.MilkStgoPayment.entities.TurnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public interface TurnoRepository extends JpaRepository<TurnoEntity, Integer> {
    ArrayList<TurnoEntity> findByFechaGreaterThanEqualAndFechaLessThanEqual(Date startDate, Date endDate);
    ArrayList<TurnoEntity> findByFechaGreaterThanEqualAndFechaLessThanEqualAndProveedorEquals(Date startDate, Date endDate, String proveedor);
}
