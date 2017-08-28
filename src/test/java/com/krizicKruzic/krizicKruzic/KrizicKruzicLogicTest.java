package com.krizicKruzic.krizicKruzic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import domain.KrizicKruzic;
import domain.Strategija;
import domain.Potez;
import domain.Igrac;
import domain.Pozicija;
import service.KrizicKruzicLogic;


public class KrizicKruzicLogicTest {

	KrizicKruzicLogic logic;
	
	@Test
	public void testNextMoveFork() throws Exception {
		
		KrizicKruzic game = new KrizicKruzic(Strategija.HARD, Igrac.X, null);
		
		game.addMove(new Potez(2,1, Igrac.X));
		game.addMove(new Potez(2,2, Igrac.O));
		game.addMove(new Potez(3,3, Igrac.X));
		game.addMove(new Potez(2,3, Igrac.O));
		
		logic = new KrizicKruzicLogic(game);
		
		assertEquals(new Potez(3, 1, Igrac.X), logic.nextMove());
		
	}

	@Test
	public void testNextMoveBlockFork() throws Exception {
		
		KrizicKruzic game = new KrizicKruzic(Strategija.HARD, Igrac.O, null);
		
		game.addMove(new Potez(1,1, Igrac.X));
		game.addMove(new Potez(2,2, Igrac.O));
		game.addMove(new Potez(3,3, Igrac.X));
		
		logic = new KrizicKruzicLogic(game);
		
		assertEquals(new Potez(1, 2, Igrac.O), logic.nextMove());
		
	}
	
	@Test
	public void testNextMoveOppositeCorner() throws Exception {
		
		KrizicKruzic game = new KrizicKruzic(Strategija.HARD, Igrac.O, null);
		
		game.addMove(new Potez(1,1, Igrac.X));
		game.addMove(new Potez(2,2, Igrac.O));
		game.addMove(new Potez(3,2, Igrac.X));
		
		logic = new KrizicKruzicLogic(game);
		
		assertEquals(new Potez(3, 3, Igrac.O), logic.nextMove());
		
	}
	
	@Test
	public void testTurn() throws Exception {
		
		KrizicKruzic game = emptyGame();
		logic = new KrizicKruzicLogic(game);
		assertEquals(Igrac.X, logic.turn());
		
		List<Potez> moves = new ArrayList<Potez>();
		moves.add(new Potez( new Pozicija(2,2), Igrac.X));
		game = new KrizicKruzic(null, moves, Strategija.HARD, Igrac.X, null);
		
		logic = new KrizicKruzicLogic(game);
		assertEquals(Igrac.O, logic.turn());
		
	}

	@Test
	public void testOpenPositions() {
		logic = new KrizicKruzicLogic(emptyGame());
		List<Pozicija> positions = logic.getOpenPositions();
		assertEquals(9, positions.size());
	}
	
	private KrizicKruzic emptyGame() {
		return new KrizicKruzic(Strategija.HARD, Igrac.X, null);
	}
}
