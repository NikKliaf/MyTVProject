package gui;
import api.Episode;
import api.Season;
import api.Series;
import api.User;



import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class ADDSEASONWINDOW extends ADDSERIESWINDOW{
    public JDialog dialog;
    private ADDSERIESWINDOW previousSeriesWindow;

    public ADDSEASONWINDOW(User user, ADDSERIESWINDOW SeriesWindow,List<Season> seasons, DefaultListModel<String> myList,JList<String> seriesList,Series existingSeries,boolean exists,Season existingSeason) {
        super(user,myList,seriesList,existingSeries,seasons);
        JPanel panel = new JPanel();
        dialog = new JDialog(SeriesWindow.frame,"Add Season", true);

        //frame operations
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(350, 350));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.pack();


        panel.setLayout(null);
        panel.setBackground(Color.GRAY);


        //season number operations
        JLabel seasonNumberLabel = new JLabel("Season Number:");
        seasonNumberLabel.setBounds(50, 50, 100, 30);
        JTextField seasonNumberField = new JTextField();
        seasonNumberField.setBounds(150, 50, 100, 30);

        //season release year operations
        JLabel releaseYearLabel = new JLabel("Release Year:");
        releaseYearLabel.setBounds(50, 100, 100, 30);
        JTextField releaseYearField = new JTextField();
        releaseYearField.setBounds(150, 100, 100, 30);

        //Episodes combo box operations
        JComboBox<String> episodesComboBox = new JComboBox<>(new String[]{"Episodes: "});
        episodesComboBox.setBounds(100, 200, 150, 30);
        final int[] episodeCounter = {1};
        if(exists) {
            if(existingSeason != null) {
                seasonNumberField.setText(Integer.toString(existingSeason.getSeasonNumber()));
                releaseYearField.setText(Integer.toString(existingSeason.getReleaseYear()));
                List<Episode> episodes = existingSeason.getEpisodes();
                for (Episode episode : episodes) {
                    addEpisodeDuration(Integer.toString(episode.getDuration()), episodesComboBox, new JDialog(), episodeCounter, episodes, true);
                }
            } else {
                seasonNumberField.setText(seasonsBox.getSelectedItem().toString());
                releaseYearField.setText(String.valueOf(seasons.get(seasonsBox.getSelectedIndex()).getReleaseYear()));
                List<Episode> episodes = seasons.get(seasonsBox.getSelectedIndex()).getEpisodes();
                for(Episode episode : episodes) {
                    addEpisodeDuration(Integer.toString(episode.getDuration()), episodesComboBox, new JDialog(), episodeCounter, episodes, true);
                }
            }
        }

        episodesComboBox.addActionListener(e -> {
            String selectedItem = (String) episodesComboBox.getSelectedItem();
            if (selectedItem != null && !selectedItem.equals("Episodes: ")) {
                String[] parts = selectedItem.split(", Duration: ");
                String episodeNumber = parts[0].replace("Episode ", "");

                List<Episode> episodes = existingSeason.getEpisodes();

                // Create a new JDialog for editing duration and deleting episode
                JDialog editEpisodeDialog = new JDialog(dialog, "Edit Episode " + episodeNumber, true);
                editEpisodeDialog.setPreferredSize(new Dimension(300, 150));
                editEpisodeDialog.setResizable(false);
                editEpisodeDialog.setLocationRelativeTo(null);
                editEpisodeDialog.setLayout(new BorderLayout());

                JPanel editPanel = new JPanel();
                editPanel.setLayout(new GridLayout(2, 1));

                JButton editDurationButton = new JButton("Edit Duration");
                JButton deleteEpisodeButton = new JButton("Delete Episode");

                editDurationButton.addActionListener(editEvent -> {
                    String newDuration = JOptionPane.showInputDialog(editEpisodeDialog, "Enter new duration for Episode " + episodeNumber + ":");
                    try {
                        int duration = Integer.parseInt(newDuration);
                        for (Episode episode : episodes) {
                            if (episode.getNumber() == Integer.parseInt(episodeNumber)) {
                                episode.setDuration(duration);
                                break;
                            }
                        }
                        updateComboBox(episodesComboBox, episodes);
                        editEpisodeDialog.dispose();
                        editPanel.revalidate();
                        editPanel.repaint();
                    } catch (NumberFormatException | NullPointerException ex) {
                        JOptionPane.showMessageDialog(editEpisodeDialog, "Please enter a valid duration!");
                    }
                });

                deleteEpisodeButton.addActionListener(deleteEvent -> {
                    int confirmDelete = JOptionPane.showConfirmDialog(editEpisodeDialog, "Are you sure you want to delete Episode " + episodeNumber + "?", "Delete Episode", JOptionPane.YES_NO_OPTION);
                    if (confirmDelete == JOptionPane.YES_OPTION) {
                        int episodeToDelete = Integer.parseInt(episodeNumber);
                        for (Episode episode : episodes) {
                            if (episode.getNumber() == episodeToDelete) {
                                episodes.remove(episode);
                                break;
                            }
                        }
                        // Update episode numbers and update the ComboBox
                        updateEpisodeNumbers(episodes, episodeToDelete);
                        updateComboBox(episodesComboBox, episodes);
                        editEpisodeDialog.dispose();
                    }
                });

                editPanel.add(editDurationButton);
                editPanel.add(deleteEpisodeButton);

                editEpisodeDialog.add(editPanel, BorderLayout.CENTER);
                editEpisodeDialog.pack();
                editEpisodeDialog.setVisible(true);
            }
        });



        //Add episode operations
        List<Episode> episodes;
        if(existingSeason != null) {
            episodes = existingSeason.getEpisodes();
        } else {
            episodes = new ArrayList<>();
        }

        JButton episodesButton = new JButton();
        episodesButton.setBounds(100, 150, 150, 30);
        episodesButton.setText("Add Episode");
        episodesButton.addActionListener(e ->{
            //episode duration dialog
            final JDialog epDurationBox = new JDialog(dialog, "Add Episode Duration: ", true);
            epDurationBox.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            epDurationBox.setLocationRelativeTo(null);
            epDurationBox.setResizable(false);
            epDurationBox.setPreferredSize(new Dimension(200, 100));
            epDurationBox.pack();
            JPanel epPanel = new JPanel();
            epPanel.setLayout(null);
            epPanel.setBackground(Color.GRAY);
            epDurationBox.add(epPanel);

            JTextField epTF = new JTextField();
            epTF.setBounds(50, 0, 100, 50);
            epTF.addActionListener(e1 -> {
                addEpisodeDuration(epTF.getText(), episodesComboBox, epDurationBox, episodeCounter, episodes,false);
            });
            epPanel.add(epTF);
            epDurationBox.add(epPanel, BorderLayout.CENTER);
            epDurationBox.setVisible(true);
        });

        //add season button operations
        JButton addSeasonButton = new JButton();
        addSeasonButton.setBounds(100, 250, 150, 30);
        addSeasonButton.setText("Add Season");
        addSeasonButton.addActionListener(e -> {
            try{
                int num = Integer.parseInt(seasonNumberField.getText());
                int year = Integer.parseInt(releaseYearField.getText());
                if(seasonNumberField.getText().isEmpty() || releaseYearField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,"Please Enter Valid Season Number and Release Year");
                } else {
                    if(!episodes.isEmpty()) {
                        if(existingSeason == null) {
                            seasonsStr.add("Season: " + num);
                            seasons.add(new Season(num, year, episodes));
                        } else {
                            existingSeason.setSeasonNumber(num);
                            existingSeason.setReleaseYear(year);
                            existingSeason.setEpisodes(episodes);
                        }
                        dialog.dispose();
                        frame.dispose();
                        new ADDSERIESWINDOW(user, myList, seriesList, existingSeries,seasons);
                        resetFields(seasonNumberField, releaseYearField, episodesComboBox);
                    } else {
                        JOptionPane.showMessageDialog(dialog,"Please Add at least one Episode");
                    }
                }
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(dialog,"Please Enter Valid Season Number and Release Year");
            }
        });


        panel.add(episodesButton);
        panel.add(episodesComboBox);
        panel.add(seasonNumberLabel);
        panel.add(seasonNumberField);
        panel.add(releaseYearLabel);
        panel.add(releaseYearField);
        panel.add(addSeasonButton);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    public void addEpisodeDuration(String epTF, JComboBox<String> episodesComboBox, JDialog epDurationBox, int[] episodeCounter, List<Episode> episodes,boolean exists){
        try{
            int num = Integer.parseInt(epTF);
            if(epTF.isEmpty()) {
                JOptionPane.showMessageDialog(epDurationBox,"Please Enter Valid Episode Duration");
            } else {
                if(!exists) {
                    episodes.add(new Episode(episodeCounter[0], num));
                }
                episodesComboBox.addItem("Episode " + episodeCounter[0] + ", Duration: " + num);
                episodeCounter[0]++;
                epDurationBox.dispose();
            }
        } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(epDurationBox,"Please Enter Valid Episode Duration");
        }}

    // Method to reset fields to default values
    private void resetFields(JTextField seasonNumberField, JTextField releaseYearField, JComboBox<String> episodes) {
        // Reset seasonNumberField and releaseYearField to default values
        seasonNumberField.setText("");
        releaseYearField.setText("");

        // Clear the episodes JComboBox
        episodes.setModel(new DefaultComboBoxModel<>(new String[]{"Episodes: "}));
    }

    private void updateEpisodeNumbers(List<Episode> episodes, int deletedEpisode) {
        for (Episode episode : episodes) {
            if (episode.getNumber() > deletedEpisode) {
                episode.setNumber(episode.getNumber() - 1);
            }
        }
    }

    private void updateComboBox(JComboBox<String> episodesComboBox, List<Episode> episodes) {
        episodesComboBox.removeAllItems();
        episodesComboBox.addItem("Episodes: ");
        for (Episode episode : episodes) {
            episodesComboBox.addItem("Episode " + episode.getNumber() + ", Duration: " + episode.getDuration());
        }
    }

}

