package domain;

public class Potez {
    private Pozicija position;
    private Igrac player;

    // for marshalling, unmarshalling
    public Potez() {
    }

    public Potez(int row, int column, Igrac player) {
        this(new Pozicija(row, column), player);
    }

    public Potez(Pozicija position, Igrac player) {
        super();
        this.position = position;
        this.player = player;
    }

    public Pozicija getPosition() {
        return position;
    }

    public Igrac getPlayer() {
        return player;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        result = prime * result
                + ((position == null) ? 0 : position.hashCode());
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
        Potez other = (Potez) obj;
        if (player != other.player)
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Move [position=" + position + ", player=" + player + "]";
    }

}
