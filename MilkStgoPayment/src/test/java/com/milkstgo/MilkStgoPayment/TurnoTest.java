package com.milkstgo.MilkStgoPayment;

import com.milkstgo.MilkStgoPayment.entities.TurnoEntity;
import com.milkstgo.MilkStgoPayment.repositories.TurnoRepository;
import com.milkstgo.MilkStgoPayment.services.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

@SpringBootTest
public class TurnoTest {

    @Autowired
    TurnoService turnoService;
    @Autowired
    TurnoRepository turnoRepository;

    @Test
    public void testObtenerProveedores() {
        assertNotNull(turnoRepository.findAll());
    }

    @Test
    public void testObtenerDataTurnos() {
        assertNotNull(turnoRepository.findAll());
    }
}
