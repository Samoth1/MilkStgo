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

    //NO SE ELIMINA DE BD
    @Test
    public void testGuardarDataDB() {
        TurnoEntity turno1 = new TurnoEntity();
        String fecha = "2023/05/20";
        String turno = "M";
        String proveedor = "Proveedor1";
        String kls_leche = "10";
        turnoService.guardarDataDB(fecha, turno, proveedor, kls_leche);
        assertNotNull(turnoService.obtenerDataTurnos());
    }

    @Test
    public void testGuardarData() {
        TurnoEntity data = new TurnoEntity();
        data.setKls_leche("10");
        data.setProveedor("Proveedor1");
        data.setTurno("M");
        data.setFecha(new Date("2023/05/20"));
        turnoService.guardarData(data);
        assertNotNull(turnoService.obtenerDataTurnos());
        assertNotNull(turnoService.obtenerProveedores());
        turnoRepository.delete(data);
    }

    @Test
    public void testObtenerProveedores() {
        assertNotNull(turnoRepository.findAll());
    }

    @Test
    public void testObtenerDataTurnos() {
        assertNotNull(turnoRepository.findAll());
    }
}
