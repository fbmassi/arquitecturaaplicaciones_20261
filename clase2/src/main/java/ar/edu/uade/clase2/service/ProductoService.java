package ar.edu.uade.clase2.service;

import ar.edu.uade.clase2.model.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    /**
     * Devuelve una lista fija de 3 productos.
     * Simula latencia de una base de datos externa con Thread.sleep(200).
     */
    public List<Producto> obtenerProductos() {
        // Simula latencia de consulta a BD externa
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return List.of(
                new Producto(1L, "Notebook Lenovo ThinkPad", 1299.99, "Electrónica"),
                new Producto(2L, "Monitor Samsung 27\"", 449.99, "Periféricos"),
                new Producto(3L, "Teclado Mecánico Keychron", 89.99, "Periféricos")
        );
    }
}
