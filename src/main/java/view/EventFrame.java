package view;

import model.Event;
import util.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class EventFrame extends JFrame {

    DefaultTableModel model;
    List<String> fileData = new ArrayList<>();
    String filePath = "data/event.csv";
    DashboardFrame dashboard;

    JTextField tfId, tfNama, tfTanggal, tfLokasi, tfSearch;

    public EventFrame(DashboardFrame dashboard) {
        this.dashboard = dashboard;

        setTitle("Kelola Event");
        setSize(750, 480);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("KELOLA DATA EVENT", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        model = new DefaultTableModel(new String[]{"ID", "Nama Event", "Tanggal", "Lokasi"}, 0);
        JTable table = new JTable(model);

        // ===== SEARCH BAR =====
        tfSearch = new JTextField();
        tfSearch.setPreferredSize(new Dimension(200, 30));

        JButton btnSearch = new JButton("Cari");
        btnSearch.addActionListener(e -> search());

        JButton btnSort = new JButton("Urutkan Tanggal");
        btnSort.addActionListener(e -> sortByDate());

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Cari Event: "));
        searchPanel.add(tfSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnSort);

        // ===== INPUT FORM =====
        tfId = new JTextField();
        tfNama = new JTextField();
        tfTanggal = new JTextField("2025-01-01");
        tfLokasi = new JTextField();

        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        JButton btnKembali = new JButton("Kembali");

        btnTambah.addActionListener(e -> tambah());
        btnEdit.addActionListener(e -> edit(table));
        btnHapus.addActionListener(e -> hapus(table));
        btnKembali.addActionListener(e -> {
            dashboard.setVisible(true);
            dispose();
        });

        // klik tabel → isi textbox
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tfId.setText(model.getValueAt(row, 0).toString());
                tfNama.setText(model.getValueAt(row, 1).toString());
                tfTanggal.setText(model.getValueAt(row, 2).toString());
                tfLokasi.setText(model.getValueAt(row, 3).toString());
            }
        });

        // form layout
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("ID Event"));
        form.add(tfId);
        form.add(new JLabel("Nama Event"));
        form.add(tfNama);
        form.add(new JLabel("Tanggal (YYYY-MM-DD)"));
        form.add(tfTanggal);
        form.add(new JLabel("Lokasi"));
        form.add(tfLokasi);
        form.add(btnTambah);
        form.add(btnEdit);
        form.add(btnHapus);
        form.add(btnKembali);

        loadData();

        add(title, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(form, BorderLayout.SOUTH);

        setVisible(true);
    }

    void loadData() {
        var rows = FileUtil.readCSV(filePath);
        model.setRowCount(0);
        fileData.clear();

        for (String[] r : rows) {
            model.addRow(r);
            fileData.add(String.join(",", r));
        }
    }

    void tambah() {
        try {
            LocalDate tgl = LocalDate.parse(tfTanggal.getText());

            Event e = new Event(
                    tfId.getText(),
                    tfNama.getText(),
                    tgl,
                    tfLokasi.getText()
            );

            model.addRow(e.toTableRow());
            fileData.add(e.toCSV());
            FileUtil.writeCSV(filePath, fileData);

            JOptionPane.showMessageDialog(this, "Event berhasil ditambahkan");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Tanggal harus format YYYY-MM-DD");
        }
    }

    void edit(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu");
            return;
        }

        try {
            LocalDate tgl = LocalDate.parse(tfTanggal.getText());

            model.setValueAt(tfId.getText(), row, 0);
            model.setValueAt(tfNama.getText(), row, 1);
            model.setValueAt(tgl.toString(), row, 2);
            model.setValueAt(tfLokasi.getText(), row, 3);

            fileData.set(row,
                    tfId.getText() + "," +
                            tfNama.getText() + "," +
                            tgl + "," +
                            tfLokasi.getText()
            );

            FileUtil.writeCSV(filePath, fileData);

            JOptionPane.showMessageDialog(this, "Event berhasil diupdate");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Format tanggal salah");
        }
    }

    void hapus(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) return;

        model.removeRow(row);
        fileData.remove(row);
        FileUtil.writeCSV(filePath, fileData);
    }

    void search() {
        String key = tfSearch.getText().toLowerCase();

        loadData();

        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            String nama = model.getValueAt(i, 1).toString().toLowerCase();
            if (!nama.contains(key)) {
                model.removeRow(i);
                fileData.remove(i);
            }
        }
    }

    void sortByDate() {
        List<String[]> rows = FileUtil.readCSV(filePath);

        rows.sort(Comparator.comparing(r -> LocalDate.parse(r[2])));

        model.setRowCount(0);
        fileData.clear();

        for (String[] r : rows) {
            model.addRow(r);
            fileData.add(String.join(",", r));
        }

        FileUtil.writeCSV(filePath, fileData);
    }
}
