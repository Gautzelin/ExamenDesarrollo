package com.jgcf.examen.controller;

import com.jgcf.examen.entity.Categoria;
import com.jgcf.examen.entity.Cliente;
import com.jgcf.examen.entity.Factura;
import com.jgcf.examen.entity.Producto;
import com.jgcf.examen.repository.CategoriaRepository;
import com.jgcf.examen.repository.ClienteRepository;
import com.jgcf.examen.repository.FacturaRepository;
import com.jgcf.examen.repository.ProductoRepository;
import com.jgcf.examen.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FacturaController {

    @Autowired private ProductoRepository productoRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private FacturaRepository facturaRepo;
    @Autowired private FacturaService facturaService;
    @Autowired private CategoriaRepository categoriaRepo;

    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("categorias", categoriaRepo.findAll());
        return "form";
    }

    @PostMapping("/generar")
    public String generarFactura(@RequestParam String nombreProducto,
                                 @RequestParam double precio,
                                 @RequestParam String categoria,
                                 @RequestParam String clienteNombre,
                                 Model model) {

        Categoria cat = categoriaRepo.findAll()
                .stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(categoria))
                .findFirst()
                .orElse(null);

        if (cat == null) {
            model.addAttribute("error", "Categoría no encontrada");
            model.addAttribute("categorias", categoriaRepo.findAll());
            return "form";
        }

        Producto producto = new Producto();
        producto.setNombre(nombreProducto); ;
        producto.setPrecioOriginal(precio);
        producto.setCategoria(cat);
        producto = productoRepo.save(producto);

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteNombre);
        cliente = clienteRepo.save(cliente);

        Factura factura = facturaService.generarFactura(producto, cliente);
        facturaRepo.save(factura);

        model.addAttribute("factura", factura);
        return "resultado";
    }
}
