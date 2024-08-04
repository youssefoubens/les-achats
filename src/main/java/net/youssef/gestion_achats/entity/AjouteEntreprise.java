package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AjouteEntreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String n;
    private String description;
    private int quantity;
    private float price;
    private float totalprice;
    private String unity;
    private String famille;
    private String numbordereau;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "sarticle_id")
    private sarticle sarticle;

    @ManyToOne
    @JoinColumn(name = "ssarticle_id")
    private ssarticle ssarticle;
//    @ManyToOne
//    @JoinColumn(name = "famille_id")
//    private Famille famille;



}
