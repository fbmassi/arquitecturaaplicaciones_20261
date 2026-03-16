package com.example.demo.controller;

import com.example.demo.repository.HelloRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    private final HelloRepository repository;

    public HelloController(HelloRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/nombres")
    public List<String> getNombres() {
        return repository.findAll();
    }

    @PostMapping("/nombres")
    public String addNombre(@RequestParam String nombre) {
        repository.add(nombre);
        return "Nombre agregado: " + nombre;
    }

    @DeleteMapping("/nombres")
    public String deleteNombre(@RequestParam String nombre) {
        if (repository.remove(nombre)) {
            return "Nombre eliminado: " + nombre;
        } else {
            return "Nombre no encontrado: " + nombre;
        }
    }

    @PostMapping("/nombres/body")
    public String addNombreBody(@RequestBody NombreRequest request) {
        repository.add(request.nombre());
        return "Nombre agregado: " + request.nombre();
    }

    @DeleteMapping("/nombres/body")
    public String deleteNombreBody(@RequestBody NombreRequest request) {
        if (repository.remove(request.nombre())) {
            return "Nombre eliminado: " + request.nombre();
        } else {
            return "Nombre no encontrado: " + request.nombre();
        }
    }

    @GetMapping("/hola")
    public String hola(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hola " + name;
    }
}