package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.PieceJointe;
import net.youssef.gestion_achats.entity.consultation;
import net.youssef.gestion_achats.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    public List<consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    public consultation getConsultationById(Long id) {
        return consultationRepository.findById(id).orElse(null);
    }

    @Transactional
    public consultation saveConsultation(consultation consultation) {
        // Ensure bidirectional relationship is properly set
        if (consultation.getPiecesJointes() != null) {
            for (PieceJointe pieceJointe : consultation.getPiecesJointes()) {
                pieceJointe.getConsultations().add(consultation); // Update the other side of the relationship
            }
        }

        return consultationRepository.save(consultation);
    }

    public void deleteConsultation(Long id) {
        consultationRepository.deleteById(id);
    }
}
