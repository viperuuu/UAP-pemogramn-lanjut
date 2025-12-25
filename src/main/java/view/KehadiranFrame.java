package view;

import util.FileUtil;
import util.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KehadiranFrame extends JFrame {

    DefaultTableModel model;
    List<String> fileData = new ArrayList<>();
    String filePath = "data/kehadiran.csv";
    DashboardFrame dashboard;

    public KehadiranFrame(DashboardFrame dashboard) {
        this.dashboard = dashboard;

        setTitle("Kehadiran Peserta");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG);

        JLabel title = new JLabel("Data Kehadiran Peserta");
        title.setFont(Theme.TITLE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID Peserta", "Nama", "Event", "Status"}, 0
        );
        JTable table = new JTable(model);
        table.setRowHeight(28);

        JScrollPane tablePane = new JScrollPane(table);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfId = field();
        JTextField tfNama = field();
        JTextField tfEvent = field();
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Hadir", "Tidak Hadir"});

        addField(card, c, 0, "ID Peserta", tfId);
        addField(card, c, 1, "Nama Peserta", tfNama);
        addField(card, c, 2, "Nama Event", tfEvent);

        c.gridy = 3; c.gridx = 0;
        card.add(new JLabel("Status"), c);
        c.gridx = 1; c.gridwidth = 2;
        card.add(cbStatus, c);
        c.gridwidth = 1;

        JButton btnTambah = primary();
        JButton btnKembali = secondary();

        c.gridy = 4; c.gridx = 0;
        card.add(btnTambah, c);
        c.gridx = 1;
        card.add(btnKembali, c);

        JPanel center = new JPanel(new BorderLayout(20, 20));
        center.setBackground(Theme.BG);
        center.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        center.add(tablePane, BorderLayout.CENTER);
        center.add(card, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        btnTambah.addActionListener(e -> {
            String row = tfId.getText() + "," +
                    tfNama.getText() + "," +
                    tfEvent.getText() + "," +
                    cbStatus.getSelectedItem();

            model.addRow(row.split(","));
            fileData.add(row);
            FileUtil.writeCSV(filePath, fileData);

            tfId.setText("");
            tfNama.setText("");
            tfEvent.setText("");
        });

        btnKembali.addActionListener(e -> {
            dashboard.setVisible(true);
            dispose();
        });

        loadData();
        setVisible(true);
    }

    private JTextField field() {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(200, 36));
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
        JButton b = new JButton("Simpan");
        b.setBackground(new Color(37, 99, 235));
        b.setForeground(Color.WHITE);
        return b;
    }

    private JButton secondary() {
        JButton b = new JButton("Kembali");
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
