package view;

import model.Event;
import util.FileUtil;
import util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventFrame extends JFrame {

    DefaultTableModel model;
    List<String> fileData = new ArrayList<>();
    String filePath = "data/event.csv";
    DashboardFrame dashboard;

    public EventFrame(DashboardFrame dashboard) {
        this.dashboard = dashboard;

        setTitle("Kelola Event");
        setSize(950, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG);

        // ===== HEADER =====
        JLabel title = new JLabel("Kelola Data Event");
        title.setFont(Theme.TITLE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        add(title, BorderLayout.NORTH);

        // ===== TABEL =====
        model = new DefaultTableModel(
                new String[]{"ID Event", "Nama Event", "Tanggal", "Lokasi"}, 0
        );
        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(Theme.NORMAL);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBorder(BorderFactory.createEmptyBorder());

        // ===== FORM CARD =====
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfId = field();
        JTextField tfNama = field();
        JTextField tfTanggal = field();
        JTextField tfLokasi = field();

        addField(card, c, 0, "ID Event", tfId);
        addField(card, c, 1, "Nama Event", tfNama);
        addField(card, c, 2, "Tanggal", tfTanggal);
        addField(card, c, 3, "Lokasi", tfLokasi);

        JButton btnTambah = primary();
        JButton btnHapus = danger();
        JButton btnKembali = secondary();

        c.gridy = 4; c.gridx = 0;
        card.add(btnTambah, c);
        c.gridx = 1;
        card.add(btnHapus, c);
        c.gridx = 2;
        card.add(btnKembali, c);

        JPanel center = new JPanel(new BorderLayout(20, 20));
        center.setBackground(Theme.BG);
        center.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        center.add(tablePane, BorderLayout.CENTER);
        center.add(card, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        // ===== ACTION =====
        btnTambah.addActionListener(e -> {
            Event ev = new Event(
                    tfId.getText(),
                    tfNama.getText(),
                    tfTanggal.getText(),
                    tfLokasi.getText()
            );

            model.addRow(ev.toTableRow());
            fileData.add(ev.toCSV());
            FileUtil.writeCSV(filePath, fileData);

            tfId.setText("");
            tfNama.setText("");
            tfTanggal.setText("");
            tfLokasi.setText("");
        });

        btnHapus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            model.removeRow(row);
            fileData.remove(row);
            FileUtil.writeCSV(filePath, fileData);
        });

        btnKembali.addActionListener(e -> {
            dashboard.setVisible(true);
            dispose();
        });

        loadData();
        setVisible(true);
    }

    // ===== HELPER =====
    private JTextField field() {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(200, 36));
        tf.setFont(Theme.NORMAL);
        return tf;
    }

    private void addField(JPanel p, GridBagConstraints c, int y,
                          String label, JTextField f) {
        c.gridy = y; c.gridx = 0;
        p.add(new JLabel(label), c);
        c.gridx = 1; c.gridwidth = 2;
        p.add(f, c);
        c.gridwidth = 1;
    }

    private JButton primary() {
        JButton b = new JButton("Tambah");
        b.setBackground(new Color(37, 99, 235));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private JButton danger() {
        JButton b = new JButton("Hapus");
        b.setBackground(new Color(220, 38, 38));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private JButton secondary() {
        JButton b = new JButton("Kembali");
        b.setBackground(new Color(203, 213, 225));
        b.setFocusPainted(false);
        return b;
    }

    void loadData() {
        var rows = FileUtil.readCSV(filePath);
        for (String[] r : rows) {
            model.addRow(r);
            fileData.add(String.join(",", r));
        }
    }
}
