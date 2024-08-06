package net.youssef.gestion_achats;

import net.youssef.gestion_achats.entity.AjouteEntreprise;

public class AjouteEntrepriseResult {
    private AjouteEntreprise ajouteEntreprise;
    private String ds;
    private String nBrd;

    public AjouteEntrepriseResult(AjouteEntreprise ajouteEntreprise, String ds, String nBrd) {
        this.ajouteEntreprise = ajouteEntreprise;
        this.ds = ds;
        this.nBrd = nBrd;
    }

    // Getters and setters
    public AjouteEntreprise getAjouteEntreprise() {
        return ajouteEntreprise;
    }

    public void setAjouteEntreprise(AjouteEntreprise ajouteEntreprise) {
        this.ajouteEntreprise = ajouteEntreprise;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getNbrd() {
        return nBrd;
    }

    public void setNbrd(String nBrd) {
        this.nBrd = nBrd;
    }
}
