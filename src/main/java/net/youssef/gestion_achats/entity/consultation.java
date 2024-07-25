package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "sarticle_id")
    private ssarticle ssArticle;

    @ManyToOne
    @JoinColumn(name = "ssarticle_id")
    private ssarticle sousSousArticle;

    private LocalDate dateConsultation;
}
