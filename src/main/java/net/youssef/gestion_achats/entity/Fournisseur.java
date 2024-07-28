package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity

public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;


    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<offre> offres;

    // getters and setters
}
