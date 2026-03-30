package com.uade.productos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productos")
public class ProductosController {

    @GetMapping("/test")
    public String test() {
        return "Productos Service is up and running!";
    }
}
