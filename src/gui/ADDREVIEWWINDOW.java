package gui;

import api.*;

import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static gui.INITIALIZE.movieData;
import static gui.INITIALIZE.seriesData;

public class ADDREVIEWWINDOW extends JFrame {
    public JFrame frame = new JFrame();
    private static JLabel ratingLabel = new JLabel();

    public ADDREVIEWWINDOW(User user, VideoContent video, DefaultListModel<String> myList, JList<String> reviewsList, Review finalIndex) {
        frame.setIconImage(new ImageIcon("src/resources/tv.png").getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel(video.getTitle() + " Review");

        //frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("MyTv");
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(80, 50, 550, 30);

        JLabel userLabel = new JLabel("<html>Logged in as: <br>" + user.getFirstName() + "</body></html>");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setBounds(750, 25, 100, 35);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(850, 25, 100, 25);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LOGINWINDOW();
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(80, 500, 100, 30);
        backButton.addActionListener(e -> {
            frame.dispose();
            new REVIEWSWINDOW(user, video, myList, reviewsList);
        });

        boolean reviewed = false;
        Review reviewIndex = null;
        JTextArea reviewArea = new JTextArea();
        ratingLabel = new JLabel();

        for (Review review : video.getReviews()) {
            if (review.getName().equals("User: " + user.getFirstName() + " " + user.getLastName())) {
                reviewed = true;
                reviewIndex = review;
            }
        }

        if (reviewed) {
            reviewArea.setText(reviewIndex.getReview().replaceFirst("Review: ", ""));
            reviewArea.setForeground(Color.BLACK);
            ratingLabel.setText(reviewIndex.getRating().replaceAll("Rating: ", ""));
        } else {
            reviewArea.setText(" Add Your Review (Up to 500 characters)");
            reviewArea.setForeground(Color.GRAY);
            ratingLabel.setText("0");
        }
        reviewArea.setBounds(80, 100, 350, 200);
        textFieldFocusGained(reviewArea, " Add Your Review (Up to 500 characters)");
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);

        JLabel rL = new JLabel("Rating: ");
        rL.setFont(new Font("Arial", Font.BOLD, 14));
        rL.setBounds(80, 400, 100, 30);

        ratingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ratingLabel.setBounds(400, 400, 100, 30);

        JPanel ratingPanel = new JPanel(new GridLayout(1, 6, 0, 0));
        ButtonGroup ratingGroup = new ButtonGroup();

        String blankStarPath = "src/resources/star_blank.png";
        ImageIcon blankStar = new ImageIcon(blankStarPath);
        Image img1 = blankStar.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        blankStar.setImage(img1);
        String fullStarPath = "src/resources/star_full.png";
        ImageIcon fullStar = new ImageIcon(fullStarPath);
        Image img2 = fullStar.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        fullStar.setImage(img2);

        for (int i = 1; i <= 5; i++) {
            JButton button = new JButton(String.valueOf(i), blankStar);
            if (i <= Integer.parseInt(ratingLabel.getText().replaceAll("[^0-9]", ""))) {
                button.setIcon(fullStar);
                button.setForeground(Color.YELLOW);
            } else {
                button.setIcon(blankStar);
                button.setForeground(Color.GRAY);
            }
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.CENTER);
            button.setVerticalAlignment(SwingConstants.CENTER);
            button.setBackground(Color.GRAY);
            button.addActionListener(new RatingButtonListener(i));
            button.setFocusPainted(false);
            ratingGroup.add(button);
            ratingPanel.add(button);
        }
        ratingPanel.setBounds(140, 400, 250, 30);
        ratingPanel.setFont(new Font("Arial", Font.BOLD, 10));
        ratingPanel.setBackground(Color.GRAY);

        JButton addReviewButton = new JButton("Add Review");
        addReviewButton.setBounds(800, 500, 150, 30);
        addReviewButton.addActionListener(e -> {
            boolean successReviewed = false;
            ReviewAddHandler handler = new ReviewAddHandler(reviewArea, ratingLabel, user, video,movieData, seriesData);
            String message = handler.handleAdd();
            switch(message){
                case "fill":
                    JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case "added":
                    JOptionPane.showMessageDialog(null, "Review Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                    successReviewed = true;
                    break;
                case "exists":
                    JOptionPane.showMessageDialog(null, "Review already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case "failed":
                    JOptionPane.showMessageDialog(null, "Please add a valid review", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
            if (successReviewed) {
                List<Review> revs = video.getReviews();
                if (revs.contains(finalIndex)) {
                    if (video instanceof Movie) {
                        movieData.removeReview(finalIndex, (Movie) video);
                    } else {
                        seriesData.removeReview(finalIndex, (Series) video);
                    }
                    revs.remove(finalIndex);
                    video.setReviews(revs);
                }
                myList.clear();
                for (Review review : video.getReviews()) {
                    myList.addElement(" User: " + review.getName() + "\n\n Review:" + review.getReview() + "\n\n Rating: " + review.getRating());
                }
                reviewsList.setModel(myList);
                frame.dispose();
                new REVIEWSWINDOW(user, video, myList, reviewsList);
            }
        });

        panel.add(ratingPanel);
        panel.add(rL);
        panel.add(ratingLabel);
        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(logoutButton);
        panel.add(backButton);
        panel.add(reviewArea);
        panel.add(addReviewButton);

        frame.add(panel, BorderLayout.CENTER);
    }


    private void textFieldFocusGained(JTextArea TF, String msg) {
        TF.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (TF.getText().equals(msg)) {
                    TF.setText("");
                    TF.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (TF.getText().isEmpty()) {
                    TF.setForeground(Color.GRAY);
                    TF.setText(msg);
                }
            }
        });
        TF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkCharacters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkCharacters();
            }

            private void checkCharacters() {
                if (TF.getText().length() > 500) {
                    JOptionPane.showMessageDialog(null, "Review cannot be more than 500 characters");
                    String text = TF.getText().substring(0, 499);
                    TF.setText(text);
                }
            }
        });
    }


    static class RatingButtonListener implements ActionListener {
        private int ratingValue;
        private int previousValue = -1;

        RatingButtonListener(int value) {
            ratingValue = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            String blankStarPath = "src/resources/star_blank.png";
            ImageIcon blankStar = new ImageIcon(blankStarPath);
            Image img1 = blankStar.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
            blankStar.setImage(img1);
            String fullStarPath = "src/resources/star_full.png";
            ImageIcon fullStar = new ImageIcon(fullStarPath);
            Image img2 = fullStar.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
            fullStar.setImage(img2);

            int clickedValue = Integer.parseInt(clickedButton.getText());

            if (clickedValue == previousValue) {
                ratingValue = 0;
                previousValue = -1;
            } else {
                ratingValue = clickedValue;
                previousValue = clickedValue;
            }

            for (Component comp : clickedButton.getParent().getComponents()) {
                if (comp instanceof JButton) {
                    JButton button = (JButton) comp;

                    int buttonValue = Integer.parseInt(button.getText());

                    if (buttonValue <= ratingValue) {
                        button.setForeground(Color.YELLOW);
                        button.setIcon(fullStar);
                    } else {
                        button.setIcon(blankStar);
                        button.setForeground(Color.GRAY);
                    }
                }
            }
            ratingLabel.setText(String.valueOf(ratingValue));
        }
    }
}
