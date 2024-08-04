package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class ssarticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String n;
    private String unity;
    private int quantity;
    private float price;
    private float totalprice;
    private String famille;
    @ManyToOne
    @JoinColumn(name = "sarticle_id")
    private sarticle sarticle;

    @OneToMany(mappedBy = "ssarticle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AjouteEntreprise> ajouteEntreprises;
//    @ManyToOne
//    @JoinColumn(name = "famille_id")
//    private Famille famille;
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
