package com.krizicKruzic.krizicKruzic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import domain.KrizicKruzic;
import domain.Strategija;
import domain.Potez;
import domain.Igrac;
import service.KrizicKruzicService;

@SpringBootApplication
public class KrizicKruzicApplication implements CommandLineRunner {
	
	Logger logger = LoggerFactory.getLogger(KrizicKruzicApplication.class);

	@Autowired
    KrizicKruzicService service;
	
	public static void main(String[] args) {
		SpringApplication.run(KrizicKruzicApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception {

        logger.debug("Brisanje postojecih igara");
        service.deleteAll();

        // save a couple of games
        KrizicKruzic game1 = service.newGame(Strategija.EASY, Igrac.X);
        service.addMove(game1, new Potez(3, 3, Igrac.X));

        KrizicKruzic game2 = service.newGame(Strategija.HARD, Igrac.O);
        service.addMove(game2, new Potez(1, 3, Igrac.X));
        service.addMove(game2, new Potez(2, 2, Igrac.O));

        logger.debug("init done");
    }
}
