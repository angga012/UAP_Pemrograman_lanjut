package org.example.view;

import org.example.model.Contact;
import org.example.util.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;

public class DashboardFrame extends JFrame {
    private JButton btnDataKontak;
    private JButton btnTambahKontak;
    private JButton btnLaporan;
    private JButton btnKeluar;
    
    private JPanel recentActivityPanel;
    private JPanel summaryPanel;
    private JLabel recentLabel;
    private JLabel totalContactsValueLabel; // Reference ke value label di circle

    public DashboardFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        addSampleContacts();
        loadOverviewData();
    }

    private void addSampleContacts() {
        ArrayList<Contact> existingContacts = FileManager.loadContacts();

        if (existingContacts.size() >= 10) {
            return;
        }

        String[][] sampleData = {
        };

        ArrayList<String> existingNames = new ArrayList<>();
        for (Contact contact : existingContacts) {
            existingNames.add(contact.getNama());
        }

        int added = 0;
        for (String[] data : sampleData) {
            if (!existingNames.contains(data[0]) && added < 10) {
                try {
                    Contact newContact = new Contact(
                        data[0],
                        data[1],
                        data[2],
                        LocalDate.parse(data[3])
                    );
                    existingContacts.add(newContact);
                    added++;
                } catch (Exception e) {
                    System.err.println("Error adding sample contact: " + e.getMessage());
                }
            }
        }

        if (added > 0) {
            FileManager.saveContacts(existingContacts);
        }
    }

    private void initializeComponents() {
        setTitle("Dashboard - Aplikasi Manajemen Kontak");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(true); // Bisa di-resize dan maximize

        btnDataKontak = new JButton("<html><div style='text-align:center;'><font size='5'>📋</font><br>DATA KONTAK</div></html>");
        btnTambahKontak = new JButton("<html><div style='text-align:center;'><font size='5'>➕</font><br>TAMBAH KONTAK</div></html>");
        btnLaporan = new JButton("<html><div style='text-align:center;'><font size='5'>📊</font><br>LAPORAN</div></html>");
        btnKeluar = new JButton("<html><div style='text-align:center;'><font size='5'>🚪</font><br>KELUAR</div></html>");


        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        
        btnDataKontak.setFont(buttonFont);
        btnDataKontak.setBackground(new Color(135, 206, 250)); // Light blue
        btnDataKontak.setForeground(Color.WHITE);
        btnDataKontak.setFocusPainted(false);
        btnDataKontak.setBorderPainted(false);
        btnDataKontak.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDataKontak.setPreferredSize(new Dimension(200, 80));
        
        btnTambahKontak.setFont(buttonFont);
        btnTambahKontak.setBackground(new Color(144, 238, 144)); // Light green
        btnTambahKontak.setForeground(Color.WHITE);
        btnTambahKontak.setFocusPainted(false);
        btnTambahKontak.setBorderPainted(false);
        btnTambahKontak.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTambahKontak.setPreferredSize(new Dimension(200, 80));
        
        btnLaporan.setFont(buttonFont);
        btnLaporan.setBackground(new Color(255, 165, 0)); // Orange
        btnLaporan.setForeground(Color.WHITE);
        btnLaporan.setFocusPainted(false);
        btnLaporan.setBorderPainted(false);
        btnLaporan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLaporan.setPreferredSize(new Dimension(200, 80));
        
        btnKeluar.setFont(buttonFont);
        btnKeluar.setBackground(new Color(255, 99, 71)); // Red
        btnKeluar.setForeground(Color.WHITE);
        btnKeluar.setFocusPainted(false);
        btnKeluar.setBorderPainted(false);
        btnKeluar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKeluar.setPreferredSize(new Dimension(200, 80));
        

        addHoverEffect(btnDataKontak, new Color(135, 206, 250), new Color(173, 216, 230));
        addHoverEffect(btnTambahKontak, new Color(144, 238, 144), new Color(152, 251, 152));
        addHoverEffect(btnLaporan, new Color(255, 165, 0), new Color(255, 200, 0));
        addHoverEffect(btnKeluar, new Color(255, 99, 71), new Color(255, 127, 80));
    }
    

    private void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel leftPanel = createLeftPanel();

        JPanel rightPanel = createRightPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);
        splitPane.setBorder(null);

        add(splitPane, BorderLayout.CENTER);
    }
    
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(25, 25, 112)); // Dark blue
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JPanel topSection = new JPanel();
        topSection.setOpaque(false);
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));

        JLabel iconLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int size = Math.min(getWidth(), getHeight());
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                

                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(x + 2, y + 2, size - 4, size - 4);
                

                g2d.setColor(Color.BLACK);

                int headSize = (int) (size * 0.45);
                int headX = x + (size - headSize) / 2;
                int headY = y + (int) (size * 0.2);
                g2d.fillOval(headX, headY, headSize, headSize);

                int shoulderWidth = (int) (size * 0.65);
                int shoulderX = x + (size - shoulderWidth) / 2;
                int shoulderY = y + (int) (size * 0.6);
                int shoulderHeight = (int) (size * 0.3);

                g2d.fillArc(shoulderX, shoulderY, shoulderWidth, shoulderHeight * 2, 0, 180);
                
                g2d.dispose();
            }
        };
        iconLabel.setPreferredSize(new Dimension(70, 70));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("DATA KONTAK");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        topSection.add(iconLabel);
        topSection.add(Box.createVerticalStrut(10));
        topSection.add(titleLabel);
        topSection.add(Box.createVerticalStrut(30));

        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        menuPanel.add(btnDataKontak);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(btnTambahKontak);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(btnLaporan);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(btnKeluar);
        
        leftPanel.add(topSection, BorderLayout.NORTH);
        leftPanel.add(menuPanel, BorderLayout.CENTER);
        
        return leftPanel;
    }
    
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(240, 248, 255)); // Light blue-white gradient

        JPanel titleSection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titleSection.setOpaque(false);
        titleSection.setBorder(BorderFactory.createEmptyBorder(40, 40, 30, 40));
        
        JLabel overviewTitle = new JLabel("Dashboard");
        overviewTitle.setFont(new Font("Arial", Font.BOLD, 24));
        overviewTitle.setForeground(new Color(25, 25, 112));
        titleSection.add(overviewTitle);

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));

        summaryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        summaryPanel.setOpaque(false);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        JPanel totalCircle = createSummaryCircle("Total Kontak", "0", new Color(70, 130, 180));
        summaryPanel.add(totalCircle);

        JPanel recentActivityHeaderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        recentActivityHeaderPanel.setOpaque(false);
        recentLabel = new JLabel("Aktivitas Terbaru");
        recentLabel.setFont(new Font("Arial", Font.BOLD, 18));
        recentLabel.setForeground(new Color(25, 25, 112));
        recentActivityHeaderPanel.add(recentLabel);
        
        recentActivityPanel = new JPanel();
        recentActivityPanel.setOpaque(false);
        recentActivityPanel.setLayout(new BoxLayout(recentActivityPanel, BoxLayout.Y_AXIS));
        recentActivityPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        contentPanel.add(summaryPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(recentActivityHeaderPanel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(recentActivityPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        
        rightPanel.add(titleSection, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        return rightPanel;
    }
    
    private JPanel createSummaryCircle(String title, String value, Color color) {
        JPanel circlePanel = new JPanel();
        circlePanel.setOpaque(false);
        circlePanel.setLayout(new BoxLayout(circlePanel, BoxLayout.Y_AXIS));
        circlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel circle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawOval(10, 10, getWidth() - 20, getHeight() - 20);
                g2d.dispose();
            }
        };
        circle.setPreferredSize(new Dimension(180, 180));
        circle.setOpaque(false);
        circle.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(color);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        circle.add(valueLabel, gbc);

        JLabel titleLabel = new JLabel("<html><div style='text-align:center;'>" + title + "</div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setForeground(new Color(25, 25, 112));
        gbc.gridy = 1;
        gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, 20, 0); // Margin bawah
        circle.add(titleLabel, gbc);

        if (title.equals("Total Kontak")) {
            totalContactsValueLabel = valueLabel;
        }
        
        circlePanel.add(circle);
        
        return circlePanel;
    }
    
    private void loadOverviewData() {
        ArrayList<Contact> contacts = FileManager.loadContacts();

        if (totalContactsValueLabel != null) {
            totalContactsValueLabel.setText(String.valueOf(contacts.size()));
        }

        loadRecentActivity(contacts);
    }
    
    private void loadRecentActivity(ArrayList<Contact> contacts) {
        recentActivityPanel.removeAll();
        
        if (contacts.isEmpty()) {

            recentActivityPanel.setVisible(false);
            recentLabel.setVisible(false);
        } else {
            recentActivityPanel.setVisible(true);
            recentLabel.setVisible(true);

            for (int i = 0; i < contacts.size(); i++) {
                Contact contact = contacts.get(i);
                JPanel activityCard = createActivityCard(contact);
                recentActivityPanel.add(activityCard);
                if (i < contacts.size() - 1) {
                    recentActivityPanel.add(Box.createVerticalStrut(10));
                }
            }
        }
        
        recentActivityPanel.revalidate();
        recentActivityPanel.repaint();
    }
    
    private JPanel createActivityCard(Contact contact) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        

        JLabel avatar = new JLabel("👤", SwingConstants.CENTER);
        avatar.setFont(new Font("Arial", Font.PLAIN, 30));
        avatar.setPreferredSize(new Dimension(50, 50));
        

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel nameLabel = new JLabel(contact.getNama());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(new Color(50, 50, 50));
        
        JLabel actionLabel = new JLabel("Kontak ditambahkan");
        actionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        actionLabel.setForeground(new Color(150, 150, 150));
        
        infoPanel.add(nameLabel);
        infoPanel.add(actionLabel);
        

        JLabel timeLabel = new JLabel(contact.getTanggalFormatted());
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        timeLabel.setForeground(new Color(150, 150, 150));
        
        card.add(avatar, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(timeLabel, BorderLayout.EAST);
        
        return card;
    }

    private void setupEventHandlers() {

        btnDataKontak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ContactListFrame().setVisible(true);
            }
        });

        btnTambahKontak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new InputContactFrame(null).setVisible(true);
            }
        });

        btnLaporan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ReportFrame().setVisible(true);
            }
        });

        btnKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    DashboardFrame.this,
                    "Apakah Anda yakin ingin keluar?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
}

