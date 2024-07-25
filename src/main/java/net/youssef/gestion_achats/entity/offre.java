package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "sarticle_id")
    private sarticle sousArticle;

    @ManyToOne
    @JoinColumn(name = "ssarticle_id")
    private ssarticle sousSousArticle;

    // getters and setters
}
