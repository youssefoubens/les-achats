package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String article;
    private String sarticle;
    private String ssarticle;
    private String ajouteEntreprise;
    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "bordereau_id")
    private BORDEREAU bordereau;

    private LocalDateTime dateConsultation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "consultation_piecejointe",
            joinColumns = @JoinColumn(name = "consultation_id"),
            inverseJoinColumns = @JoinColumn(name = "piecejointe_id")
    )
    private Set<PieceJointe> piecesJointes;
    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        consultation other = (consultation) obj;
        return (id != null) ? id.equals(other.id) : false;
    }

    // Store paths or URLs to the files
}
