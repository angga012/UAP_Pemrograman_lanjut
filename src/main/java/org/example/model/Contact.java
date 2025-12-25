package org.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Contact {
    private String nama;
    private String nomorTelepon;
    private String kategori;
    private LocalDate tanggal;

    public Contact(String nama, String nomorTelepon, String kategori, LocalDate tanggal) {
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.kategori = kategori;
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggalFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return tanggal.format(formatter);
    }

    public String toCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return nama + "," + nomorTelepon + "," + kategori + "," + tanggal.format(formatter);
    }

    public static Contact fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length == 4) {
            String nama = parts[0].trim();
            String nomorTelepon = parts[1].trim();
            String kategori = parts[2].trim();
            LocalDate tanggal = LocalDate.parse(parts[3].trim());
            return new Contact(nama, nomorTelepon, kategori, tanggal);
        }
        return null;
    }
}


