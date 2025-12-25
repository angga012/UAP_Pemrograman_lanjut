package org.example.view;

import org.example.model.Contact;
import org.example.util.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportFrame extends JFrame {
    private JButton btnKembali;
    private JTextArea reportArea;

    public ReportFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        generateReport();
    }

    private void initializeComponents() {
        setTitle("Laporan - Aplikasi Manajemen Kontak");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(true); // Bisa di-resize dan maximize

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setBackground(Color.WHITE);

        btnKembali = new JButton("Kembali");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));


        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Laporan Ringkasan Kontak");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));


        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Ringkasan Data"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnKembali);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {

        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DashboardFrame().setVisible(true);
            }
        });
    }


    private void generateReport() {
        ArrayList<Contact> contacts = FileManager.loadContacts();
        
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════\n");
        report.append("          LAPORAN RINGKASAN KONTAK\n");
        report.append("═══════════════════════════════════════════════════\n\n");


        int totalKontak = contacts.size();
        report.append("📊 TOTAL JUMLAH KONTAK: ").append(totalKontak).append("\n\n");

        if (totalKontak == 0) {
            report.append("Belum ada data kontak yang tersimpan.\n");
            report.append("Silakan tambah kontak terlebih dahulu.\n");
        } else {

            report.append("═══════════════════════════════════════════════════\n");
            report.append("RINGKASAN BERDASARKAN KATEGORI:\n");
            report.append("═══════════════════════════════════════════════════\n\n");


            Map<String, Integer> kategoriCount = new HashMap<>();
            for (Contact contact : contacts) {
                String kategori = contact.getKategori();
                kategoriCount.put(kategori, kategoriCount.getOrDefault(kategori, 0) + 1);
            }


            for (Map.Entry<String, Integer> entry : kategoriCount.entrySet()) {
                String kategori = entry.getKey();
                int jumlah = entry.getValue();
                double persentase = (jumlah * 100.0) / totalKontak;
                
                report.append(String.format("  • %-15s : %d kontak (%.1f%%)\n", 
                    kategori, jumlah, persentase));
            }

            report.append("\n");
            report.append("═══════════════════════════════════════════════════\n");
            report.append("INFORMASI TAMBAHAN:\n");
            report.append("═══════════════════════════════════════════════════\n\n");


            if (!contacts.isEmpty()) {
                Contact kontakTerbaru = contacts.get(0);
                for (Contact contact : contacts) {
                    if (contact.getTanggal().isAfter(kontakTerbaru.getTanggal())) {
                        kontakTerbaru = contact;
                    }
                }
                report.append("  • Kontak Terbaru: ").append(kontakTerbaru.getNama())
                      .append(" (").append(kontakTerbaru.getTanggalFormatted()).append(")\n");
            }


            if (!contacts.isEmpty()) {
                int totalPanjangNama = 0;
                for (Contact contact : contacts) {
                    totalPanjangNama += contact.getNama().length();
                }
                double rataRataPanjangNama = (double) totalPanjangNama / contacts.size();
                report.append(String.format("  • Rata-rata Panjang Nama: %.1f karakter\n", 
                    rataRataPanjangNama));
            }
        }

        report.append("\n");
        report.append("═══════════════════════════════════════════════════\n");
        report.append("Laporan dibuat pada: ").append(java.time.LocalDate.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        report.append("═══════════════════════════════════════════════════\n");

        reportArea.setText(report.toString());
        reportArea.setCaretPosition(0); // Scroll to top
    }
}
