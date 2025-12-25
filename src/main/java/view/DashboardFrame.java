package view;

import util.Theme;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("Event App");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG);

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel appName = new JLabel("Event Management System");
        appName.setFont(Theme.TITLE);
        appName.setForeground(Theme.TEXT);

        JLabel subtitle = new JLabel("Dashboard");
        subtitle.setFont(Theme.NORMAL);
        subtitle.setForeground(Theme.MUTED);

        JPanel titleBox = new JPanel(new GridLayout(2, 1));
        titleBox.setBackground(Color.WHITE);
        titleBox.add(appName);
        titleBox.add(subtitle);

        header.add(titleBox, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // ===== CONTENT =====
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Theme.BG);
        content.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));

        JLabel welcome = new JLabel("Selamat Datang 👋");
        welcome.setFont(Theme.SUBTITLE);
        welcome.setForeground(Theme.TEXT);

        JLabel desc = new JLabel(
                "<html>Kelola data event, peserta, kehadiran, dan laporan<br>" +
                        "melalui menu di bawah ini.</html>"
        );
        desc.setFont(Theme.NORMAL);
        desc.setForeground(Theme.MUTED);

        JPanel intro = new JPanel(new GridLayout(2, 1, 0, 5));
        intro.setBackground(Theme.BG);
        intro.add(welcome);
        intro.add(desc);

        content.add(intro, BorderLayout.NORTH);

        // ===== MENU GRID (PROPORSIONAL) =====
        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setBackground(Theme.BG);
        grid.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        grid.add(menuCard("📅", "Event", "Kelola data event",
                () -> open(new EventFrame(this))));
        grid.add(menuCard("👥", "Peserta", "Kelola peserta event",
                () -> open(new PesertaFrame(this))));
        grid.add(menuCard("✅", "Kehadiran", "Catat kehadiran peserta",
                () -> open(new KehadiranFrame(this))));
        grid.add(menuCard("📊", "Laporan", "Laporan event & kehadiran",
                () -> open(new LaporanFrame(this))));
        grid.add(menuCard("ℹ️", "Tentang", "Informasi aplikasi",
                () -> open(new TentangFrame(this))));

        content.add(grid, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        setVisible(true);
    }

    // ===== CARD MENU =====
    private JPanel menuCard(String icon, String title,
                            String desc, Runnable action) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel iconLabel = new JLabel(icon, JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(Theme.SUBTITLE);
        titleLabel.setForeground(Theme.TEXT);

        JLabel descLabel = new JLabel(desc, JLabel.CENTER);
        descLabel.setFont(Theme.NORMAL);
        descLabel.setForeground(Theme.MUTED);

        JPanel textBox = new JPanel(new GridLayout(2, 1));
        textBox.setBackground(Theme.CARD);
        textBox.add(titleLabel);
        textBox.add(descLabel);

        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textBox, BorderLayout.CENTER);

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // hover
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(new Color(248, 250, 252));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBackground(Theme.CARD);
            }

            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.run();
            }
        });

        return card;
    }

    private void open(JFrame frame) {
        setVisible(false);
    }
}
