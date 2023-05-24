package com.milkstgo.MilkStgoPayment.services;

import com.milkstgo.MilkStgoPayment.entities.ProveedorEntity;
import com.milkstgo.MilkStgoPayment.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public String obtenerCategoria(String codigo){
        return proveedorRepository.findByCodigo(codigo).getCategoria();
    }

    public String obtenerNombre(String codigo){ return proveedorRepository.findByCodigo(codigo) != null? proveedorRepository.findByCodigo(codigo).getNombre():"no name";}

    public String obtenerRetencion(String codigo){ return proveedorRepository.findByCodigo(codigo).getRetencion();}

    public void guardarProveedor(String codigo, String nombre, String categoria, String retencion){
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria);
        proveedor.setRetencion(retencion);
        proveedorRepository.save(proveedor);
    }

    public ArrayList<ProveedorEntity> obtenerProveedores(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }
}
