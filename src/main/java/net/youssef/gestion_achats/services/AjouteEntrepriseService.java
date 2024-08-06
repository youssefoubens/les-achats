package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.AjouteEntreprise;
import net.youssef.gestion_achats.repository.AjouteEntrepriseRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AjouteEntrepriseService {

    @Autowired
    private AjouteEntrepriseRepository ajouteEntrepriseRepository;

    public AjouteEntreprise save(AjouteEntreprise ajout) {
        return ajouteEntrepriseRepository.save(ajout);
    }

    public void deleteById(Long id) {
        ajouteEntrepriseRepository.deleteById(id);
    }

    public void deleteAll() {
        ajouteEntrepriseRepository.deleteAll();
    }

    public List<AjouteEntreprise> getAll() {
        return ajouteEntrepriseRepository.findAll();
    }


    public List<AjouteEntreprise> getAllAjouteEntreprises() {
        return ajouteEntrepriseRepository.findAll();
    }
}

