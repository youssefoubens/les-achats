//package net.youssef.gestion_achats.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//public class Famille {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @OneToMany(mappedBy = "famille")
//    private List<Article> articles;
//
//    @OneToMany(mappedBy = "famille")
//    private List<sarticle> sarticles;
//
//    @OneToMany(mappedBy = "famille")
//    private List<ssarticle> ssarticles;
//
//    @OneToMany(mappedBy = "famille")
//    private List<AjouteEntreprise> ajouteEntreprises;
//}
