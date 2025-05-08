package com.jgcf.examen.service;

import com.jgcf.examen.entity.Categoria;
import com.jgcf.examen.entity.Producto;
import com.jgcf.examen.repository.CategoriaRepository;
import com.jgcf.examen.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private CategoriaRepository categoriaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    public Producto crearProducto(double precio, String nombreCategoria) {
        Categoria categoria = categoriaRepo.findByNombreIgnoreCase(nombreCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setNombre("Generado");
        producto.setPrecioOriginal(precio);
        producto.setCategoria(categoria);
        return productoRepo.save(producto);
    }
}
