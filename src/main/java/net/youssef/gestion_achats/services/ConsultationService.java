package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.consultation;
import net.youssef.gestion_achats.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public consultation saveConsultation(consultation consultation) {
        return consultationRepository.save(consultation);
    }

    public void deleteConsultation(Long id) {
        consultationRepository.deleteById(id);
    }
}
