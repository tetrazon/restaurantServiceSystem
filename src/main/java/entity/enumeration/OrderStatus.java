package entity.enumeration;

public enum OrderStatus {

    NEW("new"),
    IS_REGISTERED("is registered"),
    IS_ORDERING("is ordering"),
    IS_FINISHED("is finished"),
    IS_PAID("is paid"),
    IS_REGECTED("is rejected");

    private String status;

    OrderStatus(String setStatus){
        this.status = setStatus;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
