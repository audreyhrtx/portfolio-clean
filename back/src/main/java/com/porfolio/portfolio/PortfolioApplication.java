package com.porfolio.portfolio;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PortfolioApplication {

	public static void main(String[] args) {
		// Charger le fichier .env si il existe
		try {
			Dotenv dotenv = Dotenv.configure()
					.ignoreIfMissing() // Ne pas planter si .env n'existe pas
					.load();

			// Définir les variables d'environnement pour Spring
			dotenv.entries().forEach(entry -> {
				System.setProperty(entry.getKey(), entry.getValue());
			});
		} catch (Exception e) {
			System.out.println(
					"Fichier .env non trouvé ou erreur de lecture, utilisation des variables d'environnement système");
		}

		SpringApplication.run(PortfolioApplication.class, args);
	}

}
