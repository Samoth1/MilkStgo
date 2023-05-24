package com.milkstgo.MilkStgoPayment;

import com.milkstgo.MilkStgoPayment.entities.ProveedorEntity;
import com.milkstgo.MilkStgoPayment.repositories.ProveedorRepository;
import com.milkstgo.MilkStgoPayment.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProveedorTest {

    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    ProveedorService proveedorService;

    // NO SE ELIMINA DE DB
    @Test
    public void testGuardarProveedor(){
        proveedorService.guardarProveedor("0", "DIINF","A", "Si");
        assertNotNull(proveedorRepository.findAll());
    }

    @Test
    public void testObtenerCategoria(){
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCategoria("A");
        proveedor.setCodigo("010101");
        proveedorRepository.save(proveedor);
        assertEquals("A", proveedorRepository.findByCodigo("010101").getCategoria());
        proveedorRepository.delete(proveedor);
    }

    @Test
    public void testObtenerNombreA(){
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo("010101");
        proveedor.setNombre("DIINF");
        proveedorRepository.save(proveedor);
        assertEquals("DIINF", proveedorService.obtenerNombre("010101"));
        proveedorRepository.delete(proveedor);
    }

    @Test
    public void testObtenerRetencion(){
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo("010101");
        proveedor.setRetencion("Si");
        proveedorRepository.save(proveedor);
        assertEquals("Si", proveedorRepository.findByCodigo("010101").getRetencion());
        proveedorRepository.delete(proveedor);
    }

    @Test
    public void testObtenerProveedores(){
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo("010101");
        proveedorRepository.save(proveedor);
        assertNotNull(proveedorService.obtenerProveedores());
        proveedorRepository.delete(proveedor);
    }
}
