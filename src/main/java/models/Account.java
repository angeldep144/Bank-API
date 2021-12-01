package models;

import java.util.Objects;

public class Account {
    private Integer account_id;
    private Double account_balance;
    private Integer client_id;

    public Account(){};

    public Account(Integer account_id, Double account_balance, Integer client_id) {
        this.account_id = account_id;
        this.account_balance = account_balance;
        this.client_id = client_id;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public Double getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(Double account_balance) {
        this.account_balance = account_balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "client_id=" + client_id +
                ", account_id=" + account_id +
                ", account_balance=" + account_balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return client_id.equals(account.client_id) && account_id.equals(account.account_id) && Objects.equals(account_balance, account.account_balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client_id, account_id, account_balance);
    }
}
