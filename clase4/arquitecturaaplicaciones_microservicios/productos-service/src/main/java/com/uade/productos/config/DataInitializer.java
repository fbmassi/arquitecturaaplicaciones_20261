package com.uade.productos.config;

import com.uade.productos.model.Producto;
import com.uade.productos.service.ProductoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductoService productoService;

    public DataInitializer(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Override
    public void run(String... args) {
        productoService.crear(new Producto("Laptop Pro 15", "Laptop de alto rendimiento 15 pulgadas", 1500.00, 10));
        productoService.crear(new Producto("Mouse Inalámbrico", "Mouse ergonómico sin cable", 35.00, 50));
        productoService.crear(new Producto("Teclado Mecánico", "Teclado mecánico retroiluminado", 89.99, 30));
        productoService.crear(new Producto("Monitor 27\"", "Monitor Full HD 27 pulgadas", 320.00, 15));
        productoService.crear(new Producto("Auriculares Bluetooth", "Auriculares inalámbricos con cancelación de ruido", 120.00, 25));
    }
}