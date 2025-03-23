package gui;
import api.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static gui.INITIALIZE.movieData;
import static gui.INITIALIZE.seriesData;

public class REVIEWSWINDOW extends JFrame {
    public JFrame frame = new JFrame();
    public boolean admin = false;
    private DefaultListModel<String> listModel;
    private VideoContent video;
    public REVIEWSWINDOW(User user, VideoContent videoContent, DefaultListModel<String> myList, JList<String> movieList) {
        frame.setIconImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        video = videoContent;
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel("Reviews");

        if(listModel == null) {
            listModel = new DefaultListModel<>();
            refreshReviewsList();
        }

        JList<String> reviewsList = new JList<>(listModel);
        reviewsList.setCellRenderer(new MultiLineCellRenderer());
        reviewsList.setBackground(Color.GRAY);
        reviewsList.setSelectionBackground(Color.GRAY.brighter());
        reviewsList.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
        reviewsList.setFixedCellHeight(200);

        JScrollPane scrollPane = new JScrollPane(reviewsList);
        scrollPane.setBounds(80, 80, 600, 350);
        scrollPane.setBackground(Color.GRAY);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel userLabel = new JLabel("<html>Logged in as: <br>"+ user.getFirstName() +"</body></html>");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setBounds(750,25,100,35);

        JButton backButton = new JButton("Back");
        JButton logoutButton = new JButton("Logout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

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
            boolean favorites = false;
            frame.dispose();
            if (videoContent instanceof Movie){
                new MOVIEWINDOW(user, (Movie) videoContent, myList, movieList, favorites);
            } else if (videoContent instanceof Series){
                new SERIESWINDOW(user, (Series) videoContent, myList, movieList, favorites);
            }
        });

        List<Review> reviews = video.getReviews();
        Review index = null;
        boolean reviewed = false;
        for(Review review : reviews) {
            if(review.getName().equals("User: "+ user.getFirstName() + " " + user.getLastName())) {
                reviewed = true;
                index = review;
            }
        }

        JButton reviewButton = new JButton();
        reviewButton.setBounds(750,500,150,30);
        Review finalIndex;
        if(reviewed) {
            JButton deleteReviewButton = new JButton("Delete Review");
            deleteReviewButton.setBounds(750,450,150,30);
            finalIndex = index;
            deleteReviewButton.addActionListener(e -> {
                List<Review> revs = video.getReviews();
                if(revs.contains(finalIndex)) {
                    if(video instanceof Movie) {
                        movieData.removeReview(finalIndex, (Movie) video);
                    } else {
                        seriesData.removeReview(finalIndex, (Series) video);
                    }
                    revs.remove(finalIndex);
                    video.setReviews(revs);
                    refreshReviewsList();
                    panel.remove(deleteReviewButton);
                    reviewButton.setText("Add Review");
                    panel.revalidate();
                    panel.repaint();
                }
            });
            panel.add(deleteReviewButton);
            reviewButton.setText("Edit Review");
        } else {
            reviewButton.setText("Add Review");
            finalIndex = null;
        }
        reviewButton.addActionListener(e -> {
            frame.dispose();
            new ADDREVIEWWINDOW(user, videoContent, listModel, reviewsList, finalIndex);
            refreshReviewsList();
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
        //panel operations
        panel.add(reviewButton);
        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(backButton);
        panel.add(logoutButton);
        panel.add(scrollPane);

        frame.add(panel, BorderLayout.CENTER);

    }
    public void refreshReviewsList() {
        listModel.clear();
        List<Review> reviews = video.getReviews();
        for (Review review : reviews) {
            listModel.addElement(review.getName() + "\n\n" + review.getReview() + "\n\n" + review.getRating());
        }
    }
    static class MultiLineCellRenderer extends JTextArea implements ListCellRenderer<Object> {
        MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setFont(new Font("Arial", Font.BOLD, 14));
            setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText((String) value);
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
            setPreferredSize(new Dimension(500, getPreferredSize().height));
            return this;
        }
    }
}


