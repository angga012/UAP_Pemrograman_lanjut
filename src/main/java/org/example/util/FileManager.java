package org.example.util;

import org.example.model.Contact;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager {
    private static final String FILE_NAME = "data_kontak.csv";
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + FILE_NAME;

    public static ArrayList<Contact> loadContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Path path = Paths.get(FILE_PATH);
        
        if (!Files.exists(path)) {
            return contacts;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Contact contact = Contact.fromCSV(line);
                    if (contact != null) {
                        contacts.add(contact);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading contacts: " + e.getMessage());
        }
        
        return contacts;
    }

    public static void saveContacts(ArrayList<Contact> contacts) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
            for (Contact contact : contacts) {
                writer.write(contact.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving contacts: " + e.getMessage());
            throw new RuntimeException("Gagal menyimpan data kontak", e);
        }
    }
}


