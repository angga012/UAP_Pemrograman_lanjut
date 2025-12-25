package org.example.view;

import org.example.model.Contact;
import org.example.util.FileManager;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class InputContactFrame extends JFrame {
    private JTextField namaField;
    private JTextField nomorTeleponField;
    private JComboBox<String> kategoriComboBox;
    private JButton btnSimpan;
    private JButton btnKembali;
    
    private Contact contactToEdit;

    public InputContactFrame(Contact contactToEdit) {
        this.contactToEdit = contactToEdit;
        initializeComponents();
        setupLayout();
        setupEventHandlers();

        if (contactToEdit != null) {
            loadContactData();
        }
    }

    private void initializeComponents() {
        if (contactToEdit == null) {
            setTitle("Tambah Kontak - Aplikasi Manajemen Kontak");
        } else {
            setTitle("Edit Kontak - Aplikasi Manajemen Kontak");
        }
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(true); // Bisa di-resize dan maximize

        namaField = new JTextField(25);
        nomorTeleponField = new JTextField(25);

        ((AbstractDocument) nomorTeleponField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                if (string == null) {
                    return;
                }

                String digitsOnly = string.replaceAll("[^0-9]", "");

                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                if ((currentText.length() + digitsOnly.length()) <= 12) {
                    super.insertString(fb, offset, digitsOnly, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                if (text == null) {
                    return;
                }

                String digitsOnly = text.replaceAll("[^0-9]", "");
                // Cek apakah setelah replace masih <= 12 digit
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                int newLength = currentText.length() - length + digitsOnly.length();
                if (newLength <= 12) {
                    super.replace(fb, offset, length, digitsOnly, attrs);
                }
            }
        });
        

        String[] kategoriOptions = {"Keluarga", "Teman", "Kerja", "Lainnya"};
        kategoriComboBox = new JComboBox<>(kategoriOptions);
        
        btnSimpan = new JButton("Simpan");
        btnKembali = new JButton("Kembali");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                contactToEdit == null ? "Form Tambah Kontak" : "Form Edit Kontak"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;


        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nama:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(namaField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Nomor Telepon:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(nomorTeleponField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Kategori:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(kategoriComboBox, gbc);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnKembali);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {

        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveContact();
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

    private void loadContactData() {
        namaField.setText(contactToEdit.getNama());
        nomorTeleponField.setText(contactToEdit.getNomorTelepon());
        kategoriComboBox.setSelectedItem(contactToEdit.getKategori());
    }


    private void saveContact() {
        try {

            String nama = namaField.getText().trim();
            if (nama.isEmpty()) {
                throw new IllegalArgumentException("Nama tidak boleh kosong!");
            }


            String nomorTelepon = nomorTeleponField.getText().trim();
            if (nomorTelepon.isEmpty()) {
                throw new IllegalArgumentException("Nomor telepon tidak boleh kosong!");
            }


            if (!nomorTelepon.matches("\\d+")) {
                throw new IllegalArgumentException("Nomor telepon harus berupa angka!");
            }


            if (nomorTelepon.length() != 12) {
                throw new IllegalArgumentException("Nomor telepon harus tepat 12 digit!");
            }


            String kategori = (String) kategoriComboBox.getSelectedItem();
            if (kategori == null || kategori.isEmpty()) {
                throw new IllegalArgumentException("Kategori harus dipilih!");
            }

             LocalDate tanggal = LocalDate.now();


            ArrayList<Contact> contacts = FileManager.loadContacts();

            if (contactToEdit == null) {

                Contact newContact = new Contact(nama, nomorTelepon, kategori, tanggal);
                contacts.add(newContact);
                
                JOptionPane.showMessageDialog(this,
                    "Kontak berhasil ditambahkan!",
                    "Berhasil",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {

                int index = -1;
                for (int i = 0; i < contacts.size(); i++) {
                    Contact c = contacts.get(i);
                    if (c.getNama().equals(contactToEdit.getNama()) &&
                        c.getNomorTelepon().equals(contactToEdit.getNomorTelepon()) &&
                        c.getKategori().equals(contactToEdit.getKategori()) &&
                        c.getTanggal().equals(contactToEdit.getTanggal())) {
                        index = i;
                        break;
                    }
                }
                
                if (index != -1) {

                    Contact updatedContact = new Contact(
                        nama, 
                        nomorTelepon, 
                        kategori, 
                        contactToEdit.getTanggal()
                    );
                    contacts.set(index, updatedContact);
                    
                    JOptionPane.showMessageDialog(this,
                        "Kontak berhasil diupdate!",
                        "Berhasil",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    throw new Exception("Kontak tidak ditemukan untuk diupdate!");
                }
            }


            FileManager.saveContacts(contacts);


            dispose();
            new DashboardFrame().setVisible(true);

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this,
                ex.getMessage(),
                "Error Validasi",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                "Error menyimpan kontak: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}


