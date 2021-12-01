package models;

public class Payout {
    private Double withdraw;
    private Double deposit;
    private Double transfer;

    public Double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Double amount) {
        this.withdraw = amount;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double amount) {
        this.deposit = amount;
    }

    public Double getTransfer() {
        return transfer;
    }

    public void setTransfer(Double transfer) {
        this.transfer = transfer;
    }
}
