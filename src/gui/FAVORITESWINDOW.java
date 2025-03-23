package gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static gui.INITIALIZE.movieData;
import static gui.INITIALIZE.seriesData;

public class FAVORITESWINDOW extends JFrame{
    public JFrame frame = new JFrame();
    private MOVIEWINDOW currentMovieWindow;
    private SERIESWINDOW currentSeriesWindow;
    private DefaultListModel<String> myList;
    private Subscriber sub;
    public FAVORITESWINDOW(Subscriber sub) {
        frame.setIconImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        this.sub = sub;
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel("Favorites");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        if(myList == null) {
            myList = new DefaultListModel<>();
            refreshFavoritesList();
        }

        //JList creation
        JList<String> favoritesList = new JList<>(myList);
        favoritesList.setFont(new Font("Arial", Font.BOLD, 20));
        favoritesList.setBackground(Color.GRAY);
        favoritesList.setSelectionBackground(Color.GRAY.brighter());
        favoritesList.setCellRenderer(getRenderer());
        favoritesList.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
        favoritesList.setVisibleRowCount(6);
        favoritesList.setFixedCellHeight(60);

        JScrollPane scrollPane = new JScrollPane(favoritesList);
        scrollPane.setBounds(80, 80, 600, 350);
        scrollPane.setBackground(Color.GRAY);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        favoritesList.addListSelectionListener(e -> {
            String selectedValue = favoritesList.getSelectedValue();
            VideoContent video = null;
            try {
                video = movieData.searchByTitle(selectedValue);
            } catch (NullPointerException e1) {

            }
            try {
                video = seriesData.searchByTitle(selectedValue);
            } catch (NullPointerException e1){

            }
            boolean favorites = true;


            if(video instanceof Movie){
                if(selectedValue!= null) {
                    if (currentMovieWindow != null) {
                        currentMovieWindow.updateTitle(selectedValue);
                    }
                    currentMovieWindow = new MOVIEWINDOW(sub, movieData.searchByTitle(selectedValue),myList,favoritesList,favorites);
                    frame.dispose();
                    refreshFavoritesList();
                }
            } else if (video != null){
                if(currentSeriesWindow!= null) {
                    currentSeriesWindow.updateTitle(selectedValue);
                }
                currentSeriesWindow = new SERIESWINDOW(sub, seriesData.searchByTitle(selectedValue),myList,favoritesList,favorites);
                frame.dispose();
                refreshFavoritesList();
            }

        });

        //user label creation and admin checker
        JLabel userLabel = new JLabel("<html>Logged in as: <br>"+ sub.getFirstName() +"</body></html>");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setBounds(750,25,100,35);

        JButton backButton = new JButton("Back");
        JButton logoutButton = new JButton("Logout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

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

        //user label operations
        userLabel.setBounds(750,25,100,35);

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
            new MAINWINDOW(sub);
        });

        //panel operations
        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(backButton);
        panel.add(logoutButton);
        panel.add(scrollPane);


        frame.add(panel, BorderLayout.CENTER);

    }

    private void refreshFavoritesList() {
        myList.clear();
        List<VideoContent> favorites = sub.getFavorites();
        for (VideoContent video : favorites) {
            myList.addElement(video.getTitle());
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
}
