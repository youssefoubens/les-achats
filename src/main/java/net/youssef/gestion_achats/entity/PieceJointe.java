package net.youssef.gestion_achats.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PieceJointe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private String filename;
    @ManyToMany(mappedBy = "piecesJointes")
    private Set<consultation> consultations;

    @ManyToOne
    private offre offre;
    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PieceJointe other = (PieceJointe) obj;
        return (id != null) ? id.equals(other.id) : false;
    }

}
