package model;

import java.time.LocalDate;

public class Event {

    private String id;
    private String nama;
    private LocalDate tanggal;
    private String lokasi;

    public Event(String id, String nama, LocalDate tanggal, String lokasi) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.lokasi = lokasi;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public LocalDate getTanggal() { return tanggal; }
    public String getLokasi() { return lokasi; }

    public String toCSV() {
        return id + "," + nama + "," + tanggal.toString() + "," + lokasi;
    }

    public Object[] toTableRow() {
        return new Object[]{id, nama, tanggal.toString(), lokasi};
    }
}
