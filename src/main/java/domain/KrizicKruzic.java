package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

public class KrizicKruzic {

    @Id
    private String id;

    private List<Potez> moves;
    private Strategija level;
    private Igrac computerPlaysAs;
    private Status status;
    private Igrac winner;
    private Long sequence;

    public KrizicKruzic() {
    }

    public KrizicKruzic(String id, List<Potez> moves, Strategija level,
            Igrac computerPlaysAs, Long sequence) {
        this.moves = moves;
        this.level = level;
        this.computerPlaysAs = computerPlaysAs;
        this.id = id;
        this.status = Status.OPEN;
        this.sequence = sequence;
    }

    public KrizicKruzic(Strategija level, Igrac computerPlaysAs, Long sequence) {
        this(null, new ArrayList<Potez>(), level, computerPlaysAs, sequence);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addMove(Potez move) {
        moves.add(move);
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long visibleId) {
        this.sequence = visibleId;
    }

    public List<Potez> getMoves() {
        return moves;
    }

    public void setMoves(List<Potez> moves) {
        this.moves = moves;
    }

    public Igrac playerAt(int row, int col) {
        return playerAt(new Pozicija(row, col));
    }

    public Igrac playerAt(Pozicija position) {
        return movesAsMap().get(position);
    }

    public Map<Pozicija, Igrac> movesAsMap() {

        Map<Pozicija, Igrac> map = new HashMap<Pozicija, Igrac>();

        for (Potez move : moves) {
            map.put(move.getPosition(), move.getPlayer());
        }
        return map;
    }

    public Strategija getLevel() {
        return level;
    }

    public void setLevel(Strategija level) {
        this.level = level;
    }

    public Igrac getComputerPlaysAs() {
        return computerPlaysAs;
    }

    public void setComputerPlaysAs(Igrac computerPlaysAs) {
        this.computerPlaysAs = computerPlaysAs;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Igrac getWinner() {
        return winner;
    }

    public void setWinner(Igrac winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", moves=" + moves + ", level=" + level
                + ", computerPlaysAs=" + computerPlaysAs + ", status=" + status
                + ", winner=" + winner + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((computerPlaysAs == null) ? 0 : computerPlaysAs.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((moves == null) ? 0 : moves.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((winner == null) ? 0 : winner.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KrizicKruzic other = (KrizicKruzic) obj;
        if (computerPlaysAs != other.computerPlaysAs)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (level != other.level)
            return false;
        if (moves == null) {
            if (other.moves != null)
                return false;
        } else if (!moves.equals(other.moves))
            return false;
        if (status != other.status)
            return false;
        if (winner != other.winner)
            return false;
        return true;
    }

}