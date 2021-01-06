package net.sabercrafts.aopspringboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AopSpringBootApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(AopSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		System.out.println("inside run method");
		
		Client client = new Client();
		client.setNom("John");
		
		client.setCp(new Compte());
		
		Compte compte = client.getCp();
		
		compte.setSolde(2000);

		
		// Operation retrait avec solde insuffisant, echec
		client.retirer(200000);
		
		// Operation de retrait avec solde suffisant
		client.retirer(600);
		
		// Operation de versement
		client.verser(400);
		
		System.out.println(client.getCp().getSolde());
		
	}

}
