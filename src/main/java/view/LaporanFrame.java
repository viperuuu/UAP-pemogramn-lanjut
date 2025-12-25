package view;

import util.FileUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LaporanFrame extends JFrame {

    DashboardFrame dashboard;

    public LaporanFrame(DashboardFrame dashboard) {
        this.dashboard = dashboard;

        setTitle("Laporan Event");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("LAPORAN KEHADIRAN EVENT", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Event", "Peserta", "Status"}, 0
        );
        JTable table = new JTable(model);
        table.setRowHeight(25);

        List<String[]> rows = FileUtil.readCSV("data/kehadiran.csv");
        for (String[] r : rows) {
            model.addRow(r);
        }

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> {
            dashboard.setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(btnKembali);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}
