package com.milkstgo.MilkStgoPayment;

import com.milkstgo.MilkStgoPayment.entities.LecheEntity;
import com.milkstgo.MilkStgoPayment.entities.PagoProveedorEntity;
import com.milkstgo.MilkStgoPayment.entities.ProveedorEntity;
import com.milkstgo.MilkStgoPayment.entities.TurnoEntity;
import com.milkstgo.MilkStgoPayment.repositories.LecheRepository;
import com.milkstgo.MilkStgoPayment.repositories.PagoProveedorRepository;
import com.milkstgo.MilkStgoPayment.repositories.ProveedorRepository;
import com.milkstgo.MilkStgoPayment.services.LecheService;
import com.milkstgo.MilkStgoPayment.services.PagoProveedorService;
import com.milkstgo.MilkStgoPayment.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PagoProveedorTest {
    @Autowired
    PagoProveedorService pagoProveedorService;

    @Autowired
    PagoProveedorRepository pagoProveedorRepository;

    @Autowired
    LecheService lecheService;

    @Autowired
    LecheRepository lecheRepository;

    @Autowired
    ProveedorService proveedorService;

    @Autowired
    ProveedorRepository proveedorRepository;

    @Test
    public void testQuincenaAnteriorA() {
        String quincena = "1";
        String mes = "01";
        String anio = "2023";
        String resultado = pagoProveedorService.quincenaAnterior(quincena, mes, anio);
        assertEquals("2/12/2022", resultado);
    }

    @Test
    public void testQuincenaAnteriorB() {
        String quincena = "1";
        String mes = "11";
        String anio = "2023";
        String resultado = pagoProveedorService.quincenaAnterior(quincena, mes, anio);
        assertEquals("2/10/2023", resultado);
    }

    @Test
    public void testQuincenaAnteriorC() {
        String quincena = "2";
        String mes = "10";
        String anio = "2023";
        String resultado = pagoProveedorService.quincenaAnterior(quincena, mes, anio);
        assertEquals("1/10/2023", resultado);
    }

    @Test
    public void testValorCategoriaPagoA(){
        String categoria = "A";
        Integer resultado = pagoProveedorService.valorCategoriaPago(categoria);
        assertEquals(700, resultado);
    }

    @Test
    public void testValorCategoriaPagoB(){
        String categoria = "B";
        Integer resultado = pagoProveedorService.valorCategoriaPago(categoria);
        assertEquals(550, resultado);
    }

    @Test
    public void testValorCategoriaPagoC(){
        String categoria = "C";
        Integer resultado = pagoProveedorService.valorCategoriaPago(categoria);
        assertEquals(400, resultado);
    }

    @Test
    public void testValorCategoriaPagoD(){
        String categoria = "D";
        Integer resultado = pagoProveedorService.valorCategoriaPago(categoria);
        assertEquals(250, resultado);
    }

    @Test
    public void testValorCategoriaPagoE(){
        String categoria = "Z";
        Integer resultado = pagoProveedorService.valorCategoriaPago(categoria);
        assertEquals(0, resultado);
    }

    @Test
    public void testValorGrasaPagoA() {
        String grasa = "15";
        Integer resultado = pagoProveedorService.valorGrasaPago(grasa);
        assertEquals(30, resultado);
    }

    @Test
    public void testValorGrasaPagoB() {
        String grasa = "30";
        Integer resultado = pagoProveedorService.valorGrasaPago(grasa);
        assertEquals(80, resultado);
    }

    @Test
    public void testValorGrasaPagoC() {
        String grasa = "50";
        Integer resultado = pagoProveedorService.valorGrasaPago(grasa);
        assertEquals(120, resultado);
    }

    @Test
    public void testValorGrasaPagoD() {
        String grasaInvalida = "-1";
        Integer resultado = pagoProveedorService.valorGrasaPago(grasaInvalida);
        assertEquals(0, resultado);
    }

    @Test
    public void testValorSTPago() {
        String st1 = "5";
        String st2 = "15";
        String st3 = "25";
        String st4 = "40";
        String stInvalido = "-10";

        Integer resultado1 = pagoProveedorService.valorSTPago(st1);
        Integer resultado2 = pagoProveedorService.valorSTPago(st2);
        Integer resultado3 = pagoProveedorService.valorSTPago(st3);
        Integer resultado4 = pagoProveedorService.valorSTPago(st4);
        Integer resultadoInvalido = pagoProveedorService.valorSTPago(stInvalido);

        assertEquals(-130, resultado1);
        assertEquals(-90, resultado2);
        assertEquals(95, resultado3);
        assertEquals(150, resultado4);
        assertEquals(0, resultadoInvalido);
    }

    @Test
    public void testDctoVariacionLecheA() {
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionLeche(5.0);
        proveedor.setCodigoProveedor("00000");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionLeche("00000", "1/02/2023");
        assertEquals(0.0, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testDctoVariacionLecheB() {
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionLeche(15.0);
        proveedor.setCodigoProveedor("10000");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionLeche("10000", "1/02/2023");
        assertEquals(0.07, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testDctoVariacionLecheC() {
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionLeche(30.0);
        proveedor.setCodigoProveedor("20000");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionLeche("20000", "1/02/2023");
        assertEquals(0.15, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }
    @Test
    public void testDctoVariacionLecheD() {
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionLeche(50.0);
        proveedor.setCodigoProveedor("30000");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionLeche("30000", "1/02/2023");
        assertEquals(0.3, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testDctoVariacionGrasaA(){
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionGrasa(5.0);
        proveedor.setCodigoProveedor("01000");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionGrasa("01000", "1/02/2023");
        assertEquals(0.0, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testDctoVariacionGrasaB(){
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionGrasa(20.0);
        proveedor.setCodigoProveedor("02000");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionGrasa("02000", "1/02/2023");
        assertEquals(0.12, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testDctoVariacionGrasaC(){
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionGrasa(30.0);
        proveedor.setCodigoProveedor("03000");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionGrasa("03000", "1/02/2023");
        assertEquals(0.2, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testDctoVariacionGrasaD(){
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionGrasa(50.0);
        proveedor.setCodigoProveedor("04000");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionGrasa("04000", "1/02/2023");
        assertEquals(0.3, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testDctoVariacionSTA(){
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionST(5.0);
        proveedor.setCodigoProveedor("00100");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionGrasa("00100", "1/02/2023");
        assertEquals(0.0, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testDctoVariacionSTB(){
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionST(10.0);
        proveedor.setCodigoProveedor("00200");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionST("00200", "1/02/2023");
        assertEquals(0.18, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testDctoVariacionSTC(){
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionST(30.0);
        proveedor.setCodigoProveedor("00300");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionST("00300", "1/02/2023");
        assertEquals(0.27, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testDctoVariacionSTD(){
        PagoProveedorEntity proveedor = new PagoProveedorEntity();
        proveedor.setVariacionST(40.0);
        proveedor.setCodigoProveedor("00400");
        proveedor.setQuincena("1/02/2023");
        pagoProveedorRepository.save(proveedor);
        Double resultado1 = pagoProveedorService.dctoVariacionST("00400", "1/02/2023");
        assertEquals(0.45, resultado1);
        pagoProveedorRepository.delete(proveedor);
    }

    @Test
    public void testTotalKlsLeche() {
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setKls_leche("10");
        TurnoEntity turno2 = new TurnoEntity();
        turno2.setKls_leche("20");
        TurnoEntity turno3 = new TurnoEntity();
        turno3.setKls_leche("15");
        turnosFilters.add(turno1);
        turnosFilters.add(turno2);
        turnosFilters.add(turno3);

        Integer resultado = pagoProveedorService.totalKlsLeche(turnosFilters);
        Integer expected = 45;
        assertEquals(expected, resultado);
    }

    @Test
    public void testPagoLeche(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setKls_leche("10");
        TurnoEntity turno2 = new TurnoEntity();
        turno2.setKls_leche("20");
        TurnoEntity turno3 = new TurnoEntity();
        turno3.setKls_leche("15");
        turnosFilters.add(turno1);
        turnosFilters.add(turno2);
        turnosFilters.add(turno3);

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCategoria("A");
        proveedor.setCodigo("00001");
        proveedorRepository.save(proveedor);

        Double resultado = pagoProveedorService.pagoLeche("00001", turnosFilters);
        Double expected = 31500.0;
        assertEquals(expected, resultado);

        proveedorRepository.delete(proveedor);
    }

    @Test
    public void testPagoGrasa(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setKls_leche("10");
        TurnoEntity turno2 = new TurnoEntity();
        turno2.setKls_leche("20");
        TurnoEntity turno3 = new TurnoEntity();
        turno3.setKls_leche("15");
        turnosFilters.add(turno1);
        turnosFilters.add(turno2);
        turnosFilters.add(turno3);

        LecheEntity leche = new LecheEntity();
        leche.setGrasa("30");
        leche.setProveedor("00001");
        lecheRepository.save(leche);

        Double resultado = pagoProveedorService.pagoGrasa("00001", turnosFilters);
        Double expected = 3600.0;
        assertEquals(expected, resultado);

        lecheRepository.delete(leche);
    }

    @Test
    public void testPagoST(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setKls_leche("10");
        TurnoEntity turno2 = new TurnoEntity();
        turno2.setKls_leche("20");
        TurnoEntity turno3 = new TurnoEntity();
        turno3.setKls_leche("15");
        turnosFilters.add(turno1);
        turnosFilters.add(turno2);
        turnosFilters.add(turno3);

        LecheEntity leche = new LecheEntity();
        leche.setSolido_total("30");
        leche.setProveedor("00001");
        lecheRepository.save(leche);

        Double resultado = pagoProveedorService.pagoST("00001", turnosFilters);
        Double expected = 4275.0;
        assertEquals(expected, resultado);

        lecheRepository.delete(leche);
    }

    @Test
    public void testPagoBonificacionA(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setTurno("M");
        turno1.setFecha(new Date(2023/01/1));
        TurnoEntity turno2 = new TurnoEntity();
        turno2.setTurno("T");
        turno2.setFecha(new Date(2023/01/2));
        TurnoEntity turno3 = new TurnoEntity();
        turno3.setTurno("T");
        turno3.setFecha(new Date(2023/01/3));
        TurnoEntity turno4 = new TurnoEntity();
        turno4.setTurno("T");
        turno4.setFecha(new Date(2023/01/4));
        TurnoEntity turno5 = new TurnoEntity();
        turno5.setTurno("T");
        turno5.setFecha(new Date(2023/01/5));
        TurnoEntity turno6 = new TurnoEntity();
        turno6.setTurno("T");
        turno6.setFecha(new Date(2023/01/6));
        TurnoEntity turno7 = new TurnoEntity();
        turno7.setFecha(new Date(2023/01/7));
        turno7.setTurno("T");
        TurnoEntity turno8 = new TurnoEntity();
        turno8.setTurno("T");
        turno8.setFecha(new Date(2023/01/8));
        TurnoEntity turno9 = new TurnoEntity();
        turno9.setTurno("T");
        turno9.setFecha(new Date(2023/01/9));
        TurnoEntity turno10 = new TurnoEntity();
        turno10.setTurno("T");
        turno10.setFecha(new Date(2023/01/10));
        TurnoEntity turno11 = new TurnoEntity();
        turno11.setTurno("T");
        turno11.setFecha(new Date(2023/01/11));
        turnosFilters.add(turno1);
        turnosFilters.add(turno2);
        turnosFilters.add(turno3);
        turnosFilters.add(turno4);
        turnosFilters.add(turno5);
        turnosFilters.add(turno6);
        turnosFilters.add(turno7);
        turnosFilters.add(turno8);
        turnosFilters.add(turno9);
        turnosFilters.add(turno10);
        turnosFilters.add(turno11);

        Double resultado = pagoProveedorService.pagoBonificacion(turnosFilters);
        Double expected = 20.0;
        assertEquals(expected, resultado);
    }

    @Test
    public void testPagoBonificacionB(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setTurno("M");
        turno1.setFecha(new Date(2023/01/1));
        TurnoEntity turno2 = new TurnoEntity();
        turno2.setTurno("M");
        turno2.setFecha(new Date(2023/01/2));
        TurnoEntity turno3 = new TurnoEntity();
        turno3.setTurno("M");
        turno3.setFecha(new Date(2023/01/3));
        TurnoEntity turno4 = new TurnoEntity();
        turno4.setTurno("M");
        turno4.setFecha(new Date(2023/01/4));
        TurnoEntity turno5 = new TurnoEntity();
        turno5.setTurno("M");
        turno5.setFecha(new Date(2023/01/5));
        TurnoEntity turno6 = new TurnoEntity();
        turno6.setTurno("M");
        turno6.setFecha(new Date(2023/01/6));
        TurnoEntity turno7 = new TurnoEntity();
        turno7.setFecha(new Date(2023/01/7));
        turno7.setTurno("M");
        TurnoEntity turno8 = new TurnoEntity();
        turno8.setTurno("M");
        turno8.setFecha(new Date(2023/01/8));
        TurnoEntity turno9 = new TurnoEntity();
        turno9.setTurno("M");
        turno9.setFecha(new Date(2023/01/9));
        TurnoEntity turno10 = new TurnoEntity();
        turno10.setTurno("M");
        turno10.setFecha(new Date(2023/01/10));
        TurnoEntity turno11 = new TurnoEntity();
        turno11.setTurno("M");
        turno11.setFecha(new Date(2023/01/11));
        turnosFilters.add(turno1);
        turnosFilters.add(turno2);
        turnosFilters.add(turno3);
        turnosFilters.add(turno4);
        turnosFilters.add(turno5);
        turnosFilters.add(turno6);
        turnosFilters.add(turno7);
        turnosFilters.add(turno8);
        turnosFilters.add(turno9);
        turnosFilters.add(turno10);
        turnosFilters.add(turno11);

        Double resultado = pagoProveedorService.pagoBonificacion(turnosFilters);
        Double expected = 12.0;
        assertEquals(expected, resultado);
    }

    @Test
    public void testPagoBonificacionC(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setTurno("T");
        turno1.setFecha(new Date(2023/01/1));
        TurnoEntity turno2 = new TurnoEntity();
        turno2.setTurno("T");
        turno2.setFecha(new Date(2023/01/2));
        TurnoEntity turno3 = new TurnoEntity();
        turno3.setTurno("T");
        turno3.setFecha(new Date(2023/01/3));
        TurnoEntity turno4 = new TurnoEntity();
        turno4.setTurno("T");
        turno4.setFecha(new Date(2023/01/4));
        TurnoEntity turno5 = new TurnoEntity();
        turno5.setTurno("T");
        turno5.setFecha(new Date(2023/01/5));
        TurnoEntity turno6 = new TurnoEntity();
        turno6.setTurno("T");
        turno6.setFecha(new Date(2023/01/6));
        TurnoEntity turno7 = new TurnoEntity();
        turno7.setFecha(new Date(2023/01/7));
        turno7.setTurno("T");
        TurnoEntity turno8 = new TurnoEntity();
        turno8.setTurno("T");
        turno8.setFecha(new Date(2023/01/8));
        TurnoEntity turno9 = new TurnoEntity();
        turno9.setTurno("T");
        turno9.setFecha(new Date(2023/01/9));
        TurnoEntity turno10 = new TurnoEntity();
        turno10.setTurno("T");
        turno10.setFecha(new Date(2023/01/10));
        TurnoEntity turno11 = new TurnoEntity();
        turno11.setTurno("T");
        turno11.setFecha(new Date(2023/01/11));
        turnosFilters.add(turno1);
        turnosFilters.add(turno2);
        turnosFilters.add(turno3);
        turnosFilters.add(turno4);
        turnosFilters.add(turno5);
        turnosFilters.add(turno6);
        turnosFilters.add(turno7);
        turnosFilters.add(turno8);
        turnosFilters.add(turno9);
        turnosFilters.add(turno10);
        turnosFilters.add(turno11);

        Double resultado = pagoProveedorService.pagoBonificacion(turnosFilters);
        Double expected = 8.0;
        assertEquals(expected, resultado);
    }

    @Test
    public void testPagoBonificacionD(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setTurno("T");
        turno1.setFecha(new Date(2023/01/1));
        turnosFilters.add(turno1);

        Double resultado = pagoProveedorService.pagoBonificacion(turnosFilters);
        Double expected = 0.0;
        assertEquals(expected, resultado);
    }

    @Test
    public void testPagoAcopioLeche(){
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo("00009");
        proveedor.setCategoria("A");
        proveedorRepository.save(proveedor);

        LecheEntity leche = new LecheEntity();
        leche.setProveedor("00009");
        leche.setGrasa("20");
        leche.setSolido_total("10");
        lecheRepository.save(leche);

        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setTurno("T");
        turno1.setFecha(new Date(2023/01/1));
        turno1.setKls_leche("10");
        turno1.setProveedor("00009");
        turnosFilters.add(turno1);

        Double resultado = pagoProveedorService.pagoAcopioLeche("00009", "1/01/2023",turnosFilters);
        Double expected = 6400.0;
        assertEquals(expected, resultado);

        proveedorRepository.delete(proveedor);
        lecheRepository.delete(leche);
    }

    @Test
    public void testDctoRetencionA(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setTurno("T");
        turno1.setFecha(new Date(2023/01/1));
        turno1.setProveedor("00009");
        turno1.setKls_leche("10000");
        turnosFilters.add(turno1);

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo("00009");
        proveedor.setCategoria("A");
        proveedor.setRetencion("Si");
        proveedorRepository.save(proveedor);

        LecheEntity leche = new LecheEntity();
        leche.setProveedor("00009");
        leche.setGrasa("10000");
        leche.setSolido_total("1000");
        lecheRepository.save(leche);

        Double resultado = pagoProveedorService.dctoRetencion("00009", "1/01/2023", turnosFilters);
        Double expected = 0.13;
        assertEquals(expected, resultado);

        proveedorRepository.delete(proveedor);
        lecheRepository.delete(leche);
    }

    @Test
    public void testDctoRetencionB(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setTurno("T");
        turno1.setFecha(new Date(2023/01/1));
        turno1.setProveedor("00009");
        turno1.setKls_leche("10000");
        turnosFilters.add(turno1);

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo("00009");
        proveedor.setCategoria("A");
        proveedor.setRetencion("No");
        proveedorRepository.save(proveedor);

        LecheEntity leche = new LecheEntity();
        leche.setProveedor("00009");
        leche.setGrasa("10000");
        leche.setSolido_total("1000");
        lecheRepository.save(leche);

        Double resultado = pagoProveedorService.dctoRetencion("00009", "1/01/2023", turnosFilters);
        Double expected = 0.0;
        assertEquals(expected, resultado);

        proveedorRepository.delete(proveedor);
        lecheRepository.delete(leche);
    }

    @Test
    public void testMontoRetenido(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setTurno("T");
        turno1.setFecha(new Date(2023/01/1));
        turno1.setProveedor("00009");
        turno1.setKls_leche("10000");
        turnosFilters.add(turno1);

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo("00009");
        proveedor.setCategoria("A");
        proveedor.setRetencion("Si");
        proveedorRepository.save(proveedor);

        LecheEntity leche = new LecheEntity();
        leche.setProveedor("00009");
        leche.setGrasa("10000");
        leche.setSolido_total("1000");
        lecheRepository.save(leche);

        Double resultado = pagoProveedorService.montoRetenido("00009","1/01/2023", turnosFilters);
        Double expected = 1261000.0;
        assertEquals(expected, resultado);


        proveedorRepository.delete(proveedor);
        lecheRepository.delete(leche);
    }

    @Test
    public void testPagoTotal(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setTurno("T");
        turno1.setFecha(new Date(2023/01/1));
        turno1.setProveedor("00009");
        turno1.setKls_leche("10000");
        turnosFilters.add(turno1);

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo("00009");
        proveedor.setCategoria("A");
        proveedor.setRetencion("Si");
        proveedorRepository.save(proveedor);

        LecheEntity leche = new LecheEntity();
        leche.setProveedor("00009");
        leche.setGrasa("10000");
        leche.setSolido_total("1000");
        lecheRepository.save(leche);

        Double resultado = pagoProveedorService.pagoTotal("00009", "1/01/2023", turnosFilters);
        Double expected = 9700000.0;
        assertEquals(expected, resultado);

        proveedorRepository.delete(proveedor);
        lecheRepository.delete(leche);
    }

    @Test
    public void testDiasEnvioLeche(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setFecha(new Date(2023/01/1));
        turnosFilters.add(turno1);
        assertEquals(1, pagoProveedorService.diasEnvioLeche(turnosFilters));
    }

    @Test
    public void testPagoFinal(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setTurno("T");
        turno1.setFecha(new Date(2023/01/1));
        turno1.setProveedor("00009");
        turno1.setKls_leche("10000");
        turnosFilters.add(turno1);

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo("00009");
        proveedor.setCategoria("A");
        proveedor.setRetencion("Si");
        proveedorRepository.save(proveedor);

        LecheEntity leche = new LecheEntity();
        leche.setProveedor("00009");
        leche.setGrasa("10000");
        leche.setSolido_total("1000");
        lecheRepository.save(leche);

        Double resultado = pagoProveedorService.pagoFinal("00009", "1/01/2023", turnosFilters);
        Double expected = 8439000.0;
        assertEquals(expected, resultado);

        proveedorRepository.delete(proveedor);
        lecheRepository.delete(leche);
    }

    @Test
    public void testVarLecheProovA(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setKls_leche("10");
        turnosFilters.add(turno1);

        PagoProveedorEntity proovQuinAnterior = new PagoProveedorEntity();
        proovQuinAnterior.setQuincena("1/01/2023");
        proovQuinAnterior.setCodigoProveedor("00009");
        proovQuinAnterior.setKlsLeche(20);
        pagoProveedorRepository.save(proovQuinAnterior);

        Double resultado = pagoProveedorService.varLecheProov("2", "01", "2023", "00009", turnosFilters);
        Double expected = 100.0;
        assertEquals(expected, resultado);

        pagoProveedorRepository.delete(proovQuinAnterior);
    }

    @Test
    public void testVarLecheProovB(){
        ArrayList<TurnoEntity> turnosFilters = new ArrayList<>();
        TurnoEntity turno1 = new TurnoEntity();
        turno1.setKls_leche("10");
        turnosFilters.add(turno1);

        Double resultado = pagoProveedorService.varLecheProov("2", "01", "2023", "00009", turnosFilters);
        Double expected = 0.0;
        assertEquals(expected, resultado);
    }

    @Test
    public void testVarGrasaProovA(){
        LecheEntity leche = new LecheEntity();
        leche.setProveedor("00009");
        leche.setGrasa("10");
        lecheRepository.save(leche);

        PagoProveedorEntity proovQuinAnterior = new PagoProveedorEntity();
        proovQuinAnterior.setQuincena("1/01/2023");
        proovQuinAnterior.setCodigoProveedor("00009");
        proovQuinAnterior.setPorcentajeGrasa(20);
        pagoProveedorRepository.save(proovQuinAnterior);

        Double resultado = pagoProveedorService.varGrasaProov("2", "01", "2023", "00009");
        Double expected = 100.0;
        assertEquals(expected, resultado);

        pagoProveedorRepository.delete(proovQuinAnterior);
        lecheRepository.delete(leche);
    }

    @Test
    public void testVarGrasaProovB(){
        Double resultado = pagoProveedorService.varGrasaProov("2", "01", "2023", "00009");
        Double expected = 0.0;
        assertEquals(expected, resultado);
    }

    @Test
    public void testVarSTProovA(){
        LecheEntity leche = new LecheEntity();
        leche.setProveedor("00009");
        leche.setSolido_total("10");
        lecheRepository.save(leche);

        PagoProveedorEntity proovQuinAnterior = new PagoProveedorEntity();
        proovQuinAnterior.setQuincena("1/01/2023");
        proovQuinAnterior.setCodigoProveedor("00009");
        proovQuinAnterior.setPorcentajeST(20);
        pagoProveedorRepository.save(proovQuinAnterior);

        Double resultado = pagoProveedorService.varSTProov("2", "01", "2023", "00009");
        Double expected = 100.0;
        assertEquals(expected, resultado);

        pagoProveedorRepository.delete(proovQuinAnterior);
        lecheRepository.delete(leche);
    }

    @Test
    public void testVarSTProovB(){
        Double resultado = pagoProveedorService.varSTProov("2", "01", "2023", "00009");
        Double expected = 0.0;
        assertEquals(expected, resultado);
    }

    @Test
    public void testPlantillaPagoFiltrado(){
        PagoProveedorEntity proovQuinAnterior = new PagoProveedorEntity();
        proovQuinAnterior.setQuincena("1/01/2023");
        proovQuinAnterior.setCodigoProveedor("00009");
        pagoProveedorRepository.save(proovQuinAnterior);

        pagoProveedorService.plantillaPagoFiltrado("00009", "1/01/2023");

        pagoProveedorRepository.delete(proovQuinAnterior);
    }

}
