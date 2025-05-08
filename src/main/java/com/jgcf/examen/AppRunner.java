package com.jgcf.examen;

import com.jgcf.examen.entity.Categoria;
import com.jgcf.examen.entity.Cliente;
import com.jgcf.examen.entity.Producto;
import com.jgcf.examen.repository.CategoriaRepository;
import com.jgcf.examen.repository.ClienteRepository;
import com.jgcf.examen.repository.ProductoRepository;
import com.jgcf.examen.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired private CategoriaRepository categoriaRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private FacturaService facturaService;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Nuevo Pedido ---");

        // Mostrar categorías existentes
        System.out.println("Categorías disponibles:");
        categoriaRepo.findAll().forEach(c ->
                System.out.println("- " + c.getNombre())
        );

        // Pedir datos por consola
        System.out.print("Ingrese nombre del cliente: ");
        String nombreCliente = scanner.nextLine();

        System.out.print("Ingrese nombre del producto: ");
        String nombreProducto = scanner.nextLine();

        System.out.print("Ingrese precio del producto: ");
        double precioProducto = scanner.nextDouble();
        scanner.nextLine(); // limpiar salto de línea

        System.out.print("Ingrese categoría (Electrónicos, Ropa, etc.): ");
        String nombreCategoria = scanner.nextLine();

        // Buscar la categoría
        Categoria categoria = categoriaRepo.findAll().stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombreCategoria))
                .findFirst()
                .orElse(null);

        if (categoria == null) {
            System.out.println("Categoría no encontrada.");
            return;
        }

        // Crear cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(nombreCliente);
        clienteRepo.save(cliente);

        // Crear producto
        Producto producto = new Producto();
        producto.setNombre(nombreProducto);
        producto.setPrecioOriginal(precioProducto);
        producto.setCategoria(categoria);
        productoRepo.save(producto);

        // Generar factura
        var factura = facturaService.generarFactura(producto, cliente);

        // Mostrar resultado
        System.out.println("\n=== FACTURA GENERADA ===");
        System.out.println("Cliente: " + factura.getCliente().getNombre());
        System.out.println("Producto: " + factura.getProducto().getNombre());
        System.out.println("Precio original: $" + factura.getProducto().getPrecioOriginal());
        System.out.println("Precio con descuento: $" + factura.getPrecioConDescuento());
        System.out.println("Costo de envío: $" + factura.getCostoEnvio());
        System.out.println("Precio final: $" + factura.getPrecioFinal());

            System.out.print("\n¿Desea hacer otro pedido? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (!respuesta.equals("s")) {
                System.out.println("¡Gracias por usar el generador de facturas!");
                break;
            }
        }
    }
}
