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
public class Article_type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    private List<sarticle> sousArticles;


}
