package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String n;
    private String name;
    private String unity;
    private int quantity;
    private float price;
    private float totalprice;
    private String famille;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Article_type> types = new ArrayList<>();
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<sarticle> sarticles = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "bordereau_id")
    private BORDEREAU bordereau;


    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AjouteEntreprise> ajouteEntreprises;
//    @ManyToOne
//    @JoinColumn(name = "famille_id")
//    private Famille famille;

    // getters and setters
    @Override
    public String toString() {
        return name; // Display name in the ComboBox
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

