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

        JLabel title = new JLabel("Kelola Data Peserta");
        title.setFont(Theme.TITLE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Nama Peserta", "Email"}, 0
        );

        JTable table = new JTable(model);
        table.setRowHeight(26);
        JScrollPane scroll = new JScrollPane(table);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfId = field();
        JTextField tfNama = field();
        JTextField tfEmail = field();

        addField(card, c, 0, "ID Peserta", tfId);
        addField(card, c, 1, "Nama Peserta", tfNama);
        addField(card, c, 2, "Email", tfEmail);

        JButton btnTambah = primary("Tambah");
        JButton btnEdit = primary("Edit");
        JButton btnHapus = danger("Hapus");
        JButton btnKembali = secondary("Kembali");

        c.gridy = 3;

        c.gridx = 0; card.add(btnTambah, c);
        c.gridx = 1; card.add(btnEdit, c);
        c.gridx = 2; card.add(btnHapus, c);
        c.gridx = 3; card.add(btnKembali, c);

        JPanel center = new JPanel(new BorderLayout(20, 20));
        center.setBackground(Theme.BG);
        center.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        center.add(scroll, BorderLayout.CENTER);
        center.add(card, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        // ========== AKSI ==========

        btnTambah.addActionListener(e -> {
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
        });

        btnEdit.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) return;

            model.setValueAt(tfId.getText(), r, 0);
            model.setValueAt(tfNama.getText(), r, 1);
            model.setValueAt(tfEmail.getText(), r, 2);

            fileData.set(r, tfId.getText() + "," + tfNama.getText() + "," + tfEmail.getText());
            FileUtil.writeCSV(filePath, fileData);
        });

        btnHapus.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) return;

            model.removeRow(r);
            fileData.remove(r);
            FileUtil.writeCSV(filePath, fileData);
        });

        btnKembali.addActionListener(e -> {
            dashboard.setVisible(true);
            dispose();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r != -1) {
                tfId.setText(model.getValueAt(r, 0).toString());
                tfNama.setText(model.getValueAt(r, 1).toString());
                tfEmail.setText(model.getValueAt(r, 2).toString());
            }
        });

        loadData();
        setVisible(true);
    }

    private JTextField field() {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(200, 34));
        return tf;
    }

    private void addField(JPanel p, GridBagConstraints c, int y, String l, JTextField f) {
        c.gridy = y; c.gridx = 0; p.add(new JLabel(l), c);
        c.gridx = 1; c.gridwidth = 3; p.add(f, c);
        c.gridwidth = 1;
    }

    private JButton primary(String t) {
        JButton b = new JButton(t);
        b.setBackground(new Color(37, 99, 235));
        b.setForeground(Color.WHITE);
        return b;
    }

    private JButton danger(String t) {
        JButton b = new JButton(t);
        b.setBackground(new Color(220, 38, 38));
        b.setForeground(Color.WHITE);
        return b;
    }

    private JButton secondary(String t) {
        JButton b = new JButton(t);
        b.setBackground(new Color(203, 213, 225));
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
