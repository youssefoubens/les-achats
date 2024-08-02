package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class sarticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String N;
    private String unity;
    private int quantity;
    private float price;
    private float totalprice;
    @ManyToOne
    @JoinColumn(name = "Article_type_id")
    private Article_type type;
    @ManyToOne
    @JoinColumn(name = "Article_id")
    private Article article;
    @OneToMany(mappedBy = "sarticle", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ssarticle> ssarticles;
@Override
    public String toString() {
        return name; // Display name in the ComboBox
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
