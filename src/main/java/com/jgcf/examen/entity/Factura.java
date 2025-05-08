package com.jgcf.examen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Cliente cliente;

    private Double precioConDescuento;
    private Double costoEnvio;
    private Double precioFinal;
}
