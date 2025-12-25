package model;

public class Event {
    private final String id;
    private final String nama;
    private final String tanggal;
    private final String lokasi;

    public Event(String id, String nama, String tanggal, String lokasi) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.lokasi = lokasi;
    }

    public String toCSV() {
        return id + "," + nama + "," + tanggal + "," + lokasi;
    }

    public String[] toTableRow() {
        return new String[]{id, nama, tanggal, lokasi};
    }
}
