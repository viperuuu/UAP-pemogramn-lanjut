package view;

import model.Peserta;
import util.FileUtil;
import util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PesertaFrame extends JFrame {

    DefaultTableModel model;
    List<String> fileData = new ArrayList<>();
    String filePath = "data/peserta.csv";
    DashboardFrame dashboard;

    public PesertaFrame(DashboardFrame dashboard) {
        this.dashboard = dashboard;

        setTitle("Kelola Peserta");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG);

        // ================= HEADER =================
        JLabel title = new JLabel("Kelola Data Peserta");
        title.setFont(Theme.TITLE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        add(title, BorderLayout.NORTH);

        // ================= TABEL =================
        model = new DefaultTableModel(
                new String[]{"ID", "Nama Peserta", "Email"}, 0
        );
        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(Theme.NORMAL);
        table.getTableHeader().setFont(Theme.NORMAL);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBorder(BorderFactory.createEmptyBorder());

        // ================= FORM CARD =================
        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(8, 8, 8, 8);

        JTextField tfId = modernField();
        JTextField tfNama = modernField();
        JTextField tfEmail = modernField();

        addField(card, c, 0, "ID Peserta", tfId);
        addField(card, c, 1, "Nama Peserta", tfNama);
        addField(card, c, 2, "Email", tfEmail);

        // ================= BUTTON =================
        JButton btnTambah = primaryButton();
        JButton btnHapus = dangerButton();
        JButton btnKembali = secondaryButton();

        c.gridy = 3;
        c.gridx = 0;
        card.add(btnTambah, c);

        c.gridx = 1;
        card.add(btnHapus, c);

        c.gridx = 2;
        card.add(btnKembali, c);

        // ================= LAYOUT TENGAH =================
        JPanel center = new JPanel(new BorderLayout(20, 20));
        center.setBackground(Theme.BG);
        center.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        center.add(tablePane, BorderLayout.CENTER);
        center.add(card, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        // ================= ACTION =================
        btnTambah.addActionListener(e -> {
            try {
                if (!tfEmail.getText().contains("@"))
                    throw new Exception("Email tidak valid");

                Peserta p = new Peserta(
                        tfId.getText(),
                        tfNama.getText(),
                        tfEmail.getText()
                );

                model.addRow(p.toTableRow());
                fileData.add(p.toCSV());
                FileUtil.writeCSV(filePath, fileData);

                tfId.setText("");
                tfNama.setText("");
                tfEmail.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
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

    // ================= HELPER =================

    private JTextField modernField() {
        JTextField tf = new JTextField();
        tf.setFont(Theme.NORMAL);
        tf.setPreferredSize(new Dimension(200, 36));
        return tf;
    }

    private void addField(JPanel panel, GridBagConstraints c, int y,
                          String label, JTextField field) {
        c.gridy = y;
        c.gridx = 0;
        panel.add(new JLabel(label), c);

        c.gridx = 1;
        c.gridwidth = 2;
        panel.add(field, c);
        c.gridwidth = 1;
    }

    private JButton primaryButton() {
        JButton b = new JButton("Tambah");
        b.setBackground(new Color(37, 99, 235));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private JButton dangerButton() {
        JButton b = new JButton("Hapus");
        b.setBackground(new Color(220, 38, 38));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    private JButton secondaryButton() {
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
