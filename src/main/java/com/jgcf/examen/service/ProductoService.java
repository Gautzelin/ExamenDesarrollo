package com.jgcf.examen.service;

import com.jgcf.examen.entity.Categoria;
import com.jgcf.examen.entity.Producto;
import com.jgcf.examen.repository.CategoriaRepository;
import com.jgcf.examen.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Metodo para crear un producto con reglas de descuento y envío
    public Producto crearProducto(double precioOriginal, String nombreCategoria) {
        // Buscar la categoría desde la base de datos
        Categoria categoria = categoriaRepository.findByNombreIgnoreCase(nombreCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Aplicar descuento según la categoría
        double descuento = 0;
        if (categoria.getNombre().equalsIgnoreCase("Electrónicos")) {
            descuento = 0.10; // 10% descuento para Electrónicos
        } else if (categoria.getNombre().equalsIgnoreCase("Ropa")) {
            descuento = 0.05; // 5% descuento para Ropa
        }

        // Calcular precio con descuento
        double precioConDescuento = precioOriginal - (precioOriginal * descuento);
        double costoEnvio = precioConDescuento > 50 ? 0 : 5; // Envío gratis si es mayor a $50
        double precioFinal = precioConDescuento + costoEnvio;

        // Crear y guardar el producto
        Producto producto = new Producto();
        producto.setPrecioOriginal(precioOriginal);
        producto.setCategoria(categoria);
        producto.setPrecioFinal(precioFinal); // Establecemos el precio final con descuento y envío
        // Guardamos el producto en la base de datos
        return productoRepository.save(producto);
    }
}
