package entity.order;

public class Table {
    private int id;
    private  int seats;
    private boolean isReserved;

    public Table(){
    }

    public Table(int seats, boolean isReserved) {
        this.seats = seats;
        this.isReserved = isReserved;
    }

    public int getId() {
        return id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}
