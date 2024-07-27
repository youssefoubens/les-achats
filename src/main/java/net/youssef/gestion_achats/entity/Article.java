package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unity;
    private int quantity;
    private float price;
    private float totalprice;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    public List<Article_type> types;

    // getters and setters
    @Override
   
    public String toString() {
        return name; // Display name in the ComboBox
    }
}
