package model;

public class Kehadiran {
    private String event;
    private String peserta;
    private String status;

    public Kehadiran(String event, String peserta, String status) {
        this.event = event;
        this.peserta = peserta;
        this.status = status;
    }

    public String toCSV() {
        return event + "," + peserta + "," + status;
    }

    public String[] toTableRow() {
        return new String[]{event, peserta, status};
    }
}
