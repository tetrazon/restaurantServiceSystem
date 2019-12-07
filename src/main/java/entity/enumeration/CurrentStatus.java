package entity.enumeration;

public enum CurrentStatus {
    BUSY("busy"),
    READY_TO_SERVE("ready to serve");

    private String status;
    CurrentStatus(String setStatus) {
        status = setStatus;
    }

    @Override
    public String toString() {
        return status;
    }
}
