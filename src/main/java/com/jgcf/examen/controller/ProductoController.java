package com.jgcf.examen.controller;

import com.jgcf.examen.entity.Producto;
import com.jgcf.examen.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/crear")
    public Producto crearProducto(@RequestParam double precioOriginal, @RequestParam String nombreCategoria) {
        return productoService.crearProducto(precioOriginal, nombreCategoria);
    }

}
