package com.example.GestionPatientMVC.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity //Définit qu’une classe est une entité JPA équivalent à un model dans le type mvc
@Data
//génère automatiquement des méthodes "getters" et "setters" ainsi que les méthodes "equals", "hashCode" et "toString".
@AllArgsConstructor //constructeur avec tout les arguments
@NoArgsConstructor //constructeur sans argument (par défaut)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//permet de spécifier la stratégie de génération de valeurs pour la clé primaire de l'entité.
    private Long id;
    @NotEmpty
    @Size(min = 4, max = 20)
    private String nom;
    @Temporal(TemporalType.DATE)//représente une valeur de date sans heure spécifiée
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    //le champ  dateNaissance de l'entité est mappé vers un type de données DATE dans la base de données.
    private Date dateNaissance;
    private boolean malade;
    private int score;
}
