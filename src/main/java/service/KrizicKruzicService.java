package service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import domain.KrizicKruzic;
import domain.GameRepository;
import domain.Strategija;
import domain.Potez;
import domain.Igrac;

@Service
public class KrizicKruzicService  {

	private AtomicLong sequence = new AtomicLong(1);
	
	@Autowired
	GameRepository store;
	
	public KrizicKruzicService() {}
	
	public KrizicKruzic addMove(KrizicKruzic game, Potez move) {
		KrizicKruzic modifiedGame = gameLogic(game).addMove(move);
		return store.save(modifiedGame);
	}

	public Potez nextMove(KrizicKruzic game) {
		return gameLogic(game).nextMove();		
	}

	public KrizicKruzic newGame(Strategija level, Igrac computerPlaysAs) {
		return store.save(new KrizicKruzic(level, computerPlaysAs, sequence.getAndIncrement()));
	}
	
	public List<KrizicKruzic> getGames() {
		return store.findAll();
	}

	public Optional<KrizicKruzic> find(String id) {
		return Optional.ofNullable(store.findOne(id));
	}
	
	public void delete(String id) {
		store.delete(id);
	}

	protected KrizicKruzicLogic gameLogic (KrizicKruzic game) {
		return new KrizicKruzicLogic(game);
	}

	public void deleteAll() {
		store.deleteAll();
	}

}
