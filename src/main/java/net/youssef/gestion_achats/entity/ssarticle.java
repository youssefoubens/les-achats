package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class ssarticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unity;
    private int quantity;
    private float price;
    private float totalprice;
    @ManyToOne
    @JoinColumn(name = "sarticle_id")
    private sarticle sarticle;


}
