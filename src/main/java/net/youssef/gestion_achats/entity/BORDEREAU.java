package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class BORDEREAU {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idB;
    @OneToMany(mappedBy = "bordereau", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Article> articles;
    @Override

    public String toString() {
        return "Bordereau"+idB; // Display name in the ComboBox
    }
}
