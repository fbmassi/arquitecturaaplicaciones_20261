package ar.edu.uade.clase2.controller;

import ar.edu.uade.clase2.model.Producto;
import ar.edu.uade.clase2.service.ProductoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/productos")
    public List<Producto> getProductos() {
        return productoService.obtenerProductos();
    }
}
