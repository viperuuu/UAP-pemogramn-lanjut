package view;

import javax.swing.*;
import java.awt.*;

public class TentangFrame extends JFrame {

    DashboardFrame dashboard;

    public TentangFrame(DashboardFrame dashboard) {
        this.dashboard = dashboard;

        setTitle("Tentang Aplikasi");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("TENTANG APLIKASI", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JTextArea info = getJTextArea();

        add(info, BorderLayout.CENTER);

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

    private static JTextArea getJTextArea() {
        JTextArea info = new JTextArea(
                "Sistem Manajemen Event\n\n" +
                        "Fitur Aplikasi:\n" +
                        "- Kelola Event\n" +
                        "- Kelola Peserta\n" +
                        "- Kehadiran Peserta\n" +
                        "- Laporan Event\n\n" +
                        "Dibangun menggunakan Java Swing"
        );
        info.setEditable(false);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        info.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return info;
    }
}
