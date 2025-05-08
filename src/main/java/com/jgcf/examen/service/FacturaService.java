package com.jgcf.examen.service;

import com.jgcf.examen.entity.Cliente;
import com.jgcf.examen.entity.Factura;
import com.jgcf.examen.entity.Producto;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {

    public double calcularDescuento(double precio, String categoria) {
        switch (categoria.toLowerCase()) {
            case "electrónicos": return precio * 0.90;
            case "ropa": return precio * 0.95;
            default: return precio;
        }
    }

    public double calcularEnvio(double precioConDescuento) {
        return precioConDescuento > 50 ? 0 : 5;
    }

    public Factura generarFactura(Producto producto, Cliente cliente) {
        double precioConDescuento = calcularDescuento(producto.getPrecioOriginal(), producto.getCategoria().getNombre());
        double envio = calcularEnvio(precioConDescuento);
        double total = precioConDescuento + envio;

        Factura factura = new Factura();
        factura.setProducto(producto);
        factura.setCliente(cliente);
        factura.setPrecioConDescuento(precioConDescuento);
        factura.setCostoEnvio(envio);
        factura.setPrecioFinal(total);
        return factura;
    }
}
