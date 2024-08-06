package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.PieceJointe;
import net.youssef.gestion_achats.repository.PieceJointeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PieceJointeService {

    @Autowired
    private PieceJointeRepository pieceJointeRepository;

    public PieceJointe savePieceJointe(PieceJointe pieceJointe) {
        return pieceJointeRepository.save(pieceJointe);
    }

    public List<PieceJointe> getAllPieceJointes() {
        return pieceJointeRepository.findAll();
    }

    public PieceJointe getPieceJointeById(Long id) {
        return pieceJointeRepository.findById(id).orElse(null);
    }

    public void deletePieceJointe(Long id) {
        pieceJointeRepository.deleteById(id);
    }
}
