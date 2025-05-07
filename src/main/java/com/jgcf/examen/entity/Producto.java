package com.jgcf.examen.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double precioOriginal;

    private double precioFinal;

    @ManyToOne
    private Categoria categoria;
}
