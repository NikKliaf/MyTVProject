package gui;

import api.Movie;
import api.Series;
import api.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static gui.INITIALIZE.movieData;
import static gui.INITIALIZE.seriesData;

public class ALLSERIESWINDOW extends JFrame{
    private SERIESWINDOW currentSeriesWindow;
    public JFrame frame = new JFrame();
    private DefaultListModel<String> myList;
    public Set<String> selectedCriteria;

    public ALLSERIESWINDOW(User user) {
        frame.setIconImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel("Series");

        if(myList == null) {
            myList = new DefaultListModel<>();
            refreshSeriesList();
        }
        //JList creation
        JList<String> seriesList = new JList<>(myList);
        seriesList.setFont(new Font("Arial", Font.BOLD, 20));
        seriesList.setBackground(Color.GRAY);
        seriesList.setSelectionBackground(Color.GRAY.brighter());
        seriesList.setCellRenderer(getRenderer());
        seriesList.setBorder(BorderFactory.createMatteBorder(0,1,1,1,Color.BLACK));
        seriesList.setVisibleRowCount(6);
        seriesList.setFixedCellHeight(60);

        JScrollPane scrollPane = new JScrollPane(seriesList);
        scrollPane.setBounds(80, 80, 600, 350);
        scrollPane.setBackground(Color.GRAY);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        seriesList.addListSelectionListener(e -> {
            String selectedValue = seriesList.getSelectedValue();
            boolean favorites = false;
            if(selectedValue != null){
                if(currentSeriesWindow!= null) {
                    currentSeriesWindow.updateTitle(seriesList.getSelectedValue());
                }
                currentSeriesWindow = new SERIESWINDOW(user, seriesData.searchByTitle(selectedValue),myList,seriesList, favorites);
                frame.dispose();
                refreshSeriesList();
            }
        });

        //user label creation and admin checker
        JLabel userLabel = new JLabel("<html>Logged in as: <br>"+ user.getFirstName() +"</body></html>");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setBounds(750,25,100,35);

        //create AddSeries Button if user is admin
        if(user.isAdmin()){
            JButton addSeriesButton = new JButton("Add Series");
            addSeriesButton.setBounds(550,500,100,30);
            panel.add(addSeriesButton);
            addSeriesButton.addActionListener(e -> {
                frame.dispose();
                new ADDSERIESWINDOW(user,myList,seriesList,null,null);
            });
        }

        JButton backButton = new JButton("Back");
        JButton logoutButton = new JButton("Logout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Create a Search button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(400, 450, 100, 30);
        panel.add(searchButton);

        JTextField searchField = new JTextField(" Search Criteria (separate each criteria by comma)");
        searchField.setBounds(80, 450, 300, 30);
        searchField.setForeground(Color.GRAY);
        textFieldFocusGained(searchField , " Search Criteria (separate each criteria by comma)");
        panel.add(searchField);

        //Create a search by combobox
        JComboBox<String> searchBox = new JComboBox<>(new String[]{"Search By", "Title","Actors" ,"For minors","Category","Minimum Rating"});
        searchBox.setBounds(520,450, 200,30);
        panel.add(searchBox);

        JTextArea searchByLabel = new JTextArea("");
        searchByLabel.setBounds(730,450,250,50);
        searchByLabel.setFont(new Font("Arial", Font.BOLD,14));
        searchByLabel.setLineWrap(true);
        searchByLabel.setWrapStyleWord(true);
        searchByLabel.setEditable(false);
        searchByLabel.setBackground(Color.GRAY);
        panel.add(searchByLabel);

        selectedCriteria = new HashSet<>();

        searchBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) e.getItem();
                if(!selectedItem.equals("Search By")) {
                    if (selectedCriteria.contains(selectedItem)) {
                        selectedCriteria.remove(selectedItem);
                    } else {
                        selectedCriteria.add(selectedItem);
                    }
                    updateSearchLabel(searchByLabel);
                }
            }
        });
        // ActionListener for the Search button
        searchButton.addActionListener(e -> {
            searchMovie(searchField.getText(), searchByLabel.getText());
        });

        //frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("MyTv");
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);

        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        //title label operations
        titleLabel.setBounds(80, 25, 100, 30);

        //logout button operations
        logoutButton.setBounds(850,25,100,25);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LOGINWINDOW();
        });

        //back button operations
        backButton.setBounds(80,500,100,30);
        backButton.addActionListener(e -> {
            frame.dispose();
            new MAINWINDOW(user);
        });

        //panel operations
        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(backButton);
        panel.add(logoutButton);
        panel.add(scrollPane);


        frame.add(panel, BorderLayout.CENTER);

    }

    private void searchMovie(String searchTerm, String searchBy) {
        if (myList != null) {
            myList.clear();

            if (searchTerm.isEmpty()) {
                List<Series> series = seriesData.getSeriesList();
                for (Series serie : series) {
                    myList.addElement(serie.getTitle());
                }
            } else {
                List<Series> series = seriesData.getSeriesList();
                for (Series serie : series) {
                    if (seriesContainsSearchTerm(serie, searchTerm, searchBy)) {
                        myList.addElement(serie.getTitle());
                    }
                }
            }
        }
    }

    private boolean seriesContainsSearchTerm(Series series, String searchTerm, String searchBy) {
        String[] searchTerms = searchTerm.split(",");
        boolean match = true;

        for (String term : searchTerms) {
            String searchTermLower = term.trim().toLowerCase();
            String[] selectedFields = searchBy.split(", ");

            boolean currentTermMatch = false;

            for (String selectedField : selectedFields) {
                selectedField = selectedField.trim().toLowerCase();

                switch (selectedField) {
                    case "title":
                        currentTermMatch = series.getTitle().toLowerCase().contains(searchTermLower);
                        break;
                    case "actors":
                        currentTermMatch = series.getProtagonists().toLowerCase().contains(searchTermLower);
                        break;
                    case "for minors":
                        if (searchTermLower.equals("yes")) {
                            return series.isSuitableForMinors();
                        } else if (searchTermLower.equals("no")) {
                            return !series.isSuitableForMinors();
                        }
                        break;
                    case "category":
                        currentTermMatch = series.getCategory().toLowerCase().contains(searchTermLower);
                        break;
                    case "minimum rating":
                        try {
                            currentTermMatch = series.getAverageRating() >= Double.parseDouble(searchTermLower);
                        } catch (NumberFormatException e) {

                        }
                        break;
                    default:
                        break;
                }
                if (currentTermMatch) {
                    break;
                }
            }
            match = currentTermMatch;
            if (!match) {
                break;
            }
        }
        return match;
    }
    public void refreshSeriesList() {
        myList.clear();
        List<Series> allseries = seriesData.getSeriesList();
        for (Series series : allseries) {
            myList.addElement(series.getTitle());
        }
    }


    private ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super
                        .getListCellRendererComponent(list, value, index, isSelected,
                                cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(1,
                        0, 1, 0, Color.BLACK));
                return listCellRendererComponent;
            }
        };
    }

    private void updateSearchLabel(JTextArea searchByLabel) {
        StringBuilder labelText = new StringBuilder();
        for (String criterion : selectedCriteria) {
            labelText.append(criterion).append(", ");
        }
        if (!selectedCriteria.isEmpty()) {
            labelText.delete(labelText.length() - 2, labelText.length());
        }
        searchByLabel.setText(labelText.toString());
    }

    private void textFieldFocusGained(JTextField TF , String msg) {
        TF.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(TF.getText().equals(msg)){
                    TF.setText("");
                    TF.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if(TF.getText().isEmpty()) {
                    TF.setForeground(Color.GRAY);
                    TF.setText(msg);
                }
            }
        });
    }
}


