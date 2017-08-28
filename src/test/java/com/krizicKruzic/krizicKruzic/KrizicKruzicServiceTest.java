package com.krizicKruzic.krizicKruzic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import domain.KrizicKruzic;
import domain.Strategija;
import domain.Potez;
import domain.Igrac;
import domain.Pozicija;

import service.KrizicKruzicService;


public class KrizicKruzicServiceTest {

	KrizicKruzicService service;
	
	@Before
	public void setup() {
		service = new KrizicKruzicService();
	}
	
	@Test
	public void testNextMove() {
		assertEquals(new Potez( new Pozicija(1,1), Igrac.X),  service.nextMove(emptyGame()));
	}
	
	private KrizicKruzic emptyGame() {
		return new KrizicKruzic(Strategija.HARD, Igrac.X, null);
	}

}
