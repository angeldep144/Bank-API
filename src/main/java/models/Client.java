package models;

import java.util.Objects;

public class Client {

    private Integer client_id;
    private String first_name;
    private String last_name;

    public Client(){}

    public Client(Integer client_id, String first_name, String last_name) {
        this.client_id = client_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "client_id=" + client_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return client_id.equals(client.client_id) && first_name.equals(client.first_name) && last_name.equals(client.last_name);
    }
}
