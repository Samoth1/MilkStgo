package com.milkstgo.MilkStgoPayment;

import com.milkstgo.MilkStgoPayment.entities.LecheEntity;
import com.milkstgo.MilkStgoPayment.repositories.LecheRepository;
import com.milkstgo.MilkStgoPayment.services.LecheService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LecheTest {

    @Autowired
    LecheService lecheService;

    @Autowired
    LecheRepository lecheRepository;

    @Test
    public void testObtenerDataProveedor(){
        LecheEntity leche = new LecheEntity();
        leche.setProveedor("010101");
        lecheRepository.save(leche);
        assertNotNull(lecheService.obtenerDataProveedor("010101"));
        lecheRepository.delete(leche);
    }

    @Test
    public void testObtenerGrasa(){
        LecheEntity leche = new LecheEntity();
        leche.setProveedor("010101");
        leche.setGrasa("10");
        lecheRepository.save(leche);
        assertEquals("10", lecheService.obtenerGrasa("010101"));
        lecheRepository.delete(leche);
    }

    @Test
    public void testObtenerSolidoTotal(){
        LecheEntity leche = new LecheEntity();
        leche.setProveedor("010101");
        leche.setSolido_total("10");
        lecheRepository.save(leche);
        assertEquals("10", lecheService.obtenerSolidoTotal("010101"));
        lecheRepository.delete(leche);
    }

    @Test
    public void testGuardarData(){
        LecheEntity leche = new LecheEntity();
        leche.setProveedor("010101");
        lecheService.guardarData(leche);
        assertNotNull(lecheRepository.findAll());
        lecheRepository.delete(leche);
    }

    @Test
    public void testGuardarDataDB(){
        lecheService.guardarDataDB("20", "10", "010101");
        LecheEntity leche = lecheService.obtenerDataProveedor("010101");
        lecheRepository.delete(leche);
    }
}
