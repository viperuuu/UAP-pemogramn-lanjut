package model;

public class Peserta {
    private final String id;
    private final String nama;
    private final String email;

    public Peserta(String id, String nama, String email) {
        this.id = id;
        this.nama = nama;
        this.email = email;
    }

    public String toCSV() {
        return id + "," + nama + "," + email;
    }

    public String[] toTableRow() {
        return new String[]{id, nama, email};
    }
}
