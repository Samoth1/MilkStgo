package com.milkstgo.MilkStgoPayment.repositories;

import com.milkstgo.MilkStgoPayment.entities.TurnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends JpaRepository<TurnoEntity, Integer> {

}
