package org.example.view;

import org.example.model.Contact;
import org.example.util.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

public class ContactListFrame extends JFrame {
    private JTable contactTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton btnCari;
    private JButton btnSortNama;
    private JButton btnEdit;
    private JButton btnHapus;
    private JButton btnKembali;
    
    private ArrayList<Contact> contacts;
    private ArrayList<Contact> originalContacts;

    public ContactListFrame() {
        contacts = FileManager.loadContacts();
        originalContacts = new ArrayList<>(contacts);
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadTableData();
    }

    private void initializeComponents() {
        setTitle("Data Kontak - Aplikasi Manajemen Kontak");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true); // Bisa di-resize dan maximize

        String[] columnNames = {"Nama", "Nomor Telepon", "Kategori", "Tanggal"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Non-editable table
            }
        };
        
        contactTable = new JTable(tableModel);
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactTable.setRowHeight(25);

        contactTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        contactTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        contactTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        contactTable.getColumnModel().getColumn(3).setPreferredWidth(150);

        searchField = new JTextField(20);
        btnCari = new JButton("Cari");
        btnSortNama = new JButton("Sort Nama");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnKembali = new JButton("Kembali");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createTitledBorder("Pencarian"));
        topPanel.add(new JLabel("Cari:"));
        topPanel.add(searchField);
        topPanel.add(btnCari);

        JScrollPane scrollPane = new JScrollPane(contactTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Kontak"));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(btnSortNama);
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnHapus);
        bottomPanel.add(btnKembali);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        btnSortNama.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortByName();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedContact();
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedContact();
            }
        });

        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DashboardFrame().setVisible(true);
            }
        });
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Contact contact : contacts) {
            Object[] row = {
                contact.getNama(),
                contact.getNomorTelepon(),
                contact.getKategori(),
                contact.getTanggalFormatted()
            };
            tableModel.addRow(row);
        }
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        
        if (searchTerm.isEmpty()) {
            contacts = new ArrayList<>(originalContacts);
        } else {
            contacts = new ArrayList<>();
            for (Contact contact : originalContacts) {
                if (contact.getNama().toLowerCase().contains(searchTerm) ||
                    contact.getNomorTelepon().contains(searchTerm) ||
                    contact.getKategori().toLowerCase().contains(searchTerm)) {
                    contacts.add(contact);
                }
            }
        }
        
        loadTableData();
        
        if (contacts.isEmpty() && !searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Tidak ada kontak yang ditemukan dengan kata kunci: " + searchTerm,
                "Hasil Pencarian",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void sortByName() {
        // Menggunakan Comparator untuk sorting berdasarkan nama
        contacts.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getNama().compareToIgnoreCase(c2.getNama());
            }
        });
        
        loadTableData();
        JOptionPane.showMessageDialog(this,
            "Data telah diurutkan berdasarkan nama",
            "Sorting",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void editSelectedContact() {
        int selectedRow = contactTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Pilih kontak yang ingin diedit terlebih dahulu!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Contact contactToEdit = contacts.get(selectedRow);
            dispose();
            new InputContactFrame(contactToEdit).setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error membuka form edit: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedContact() {
        int selectedRow = contactTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Pilih kontak yang ingin dihapus terlebih dahulu!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin menghapus kontak ini?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Contact contactToDelete = contacts.get(selectedRow);
                contacts.remove(selectedRow);

                originalContacts.remove(contactToDelete);

                FileManager.saveContacts(originalContacts);

                loadTableData();
                
                JOptionPane.showMessageDialog(this,
                    "Kontak berhasil dihapus!",
                    "Berhasil",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error menghapus kontak: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


