package me.adelemphii.adelem.menus;

import me.adelemphii.adelem.Core;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

public class Menu extends JFrame {
    private JPanel consolePanel;
    private JTabbedPane tabs;
    private JPanel consoleTab;
    private JTextArea consoleTextArea;
    private JPanel configTab;
    private JTextPane configText;
    private JButton saveButton;
    private JComboBox channelBox;
    private JTextField chatInput;
    private JScrollPane scrollyArea;

    public Menu() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        configureSettings();

        consoleTextArea.setEditable(false);

        configText.setText("EXTREMELY WIP, SORRY!");
        configText.setEditable(false);

        for(String channel : Core.config.getChannels()) {
            channelBox.addItem(channel);
        }

        channelBox.addActionListener(e -> {
            JComboBox box = (JComboBox) e.getSource();

            Core.setChannelChosen(box.getSelectedItem().toString());

        });
        chatInput.addActionListener(e -> {

            this.sendMessageToTwitch(Core.getChannelChosen(), "phiiBee", chatInput.getText());
            chatInput.setText("");
        });
    }

    private void configureSettings() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PrintStream con=new PrintStream(new TextAreaOutputStream(consoleTextArea));
        System.setOut(con);
        System.setErr(con);

        add(consolePanel);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        setSize(620, 830);
        setMinimumSize(new Dimension(820, 255));
        setTitle("Bot Console");

        // Please help, I can't make GUIs..
        consolePanel.setBackground(Color.BLACK);
        consoleTab.setBackground(Color.BLACK);
        configTab.setBackground(Color.BLACK);

        tabs.setBackground(Color.GRAY);
        channelBox.setBackground(Color.GRAY);
        configText.setBackground(Color.GRAY);
        configText.setForeground(Color.WHITE);

        saveButton.setBackground(Color.GRAY);
    }

    public void sendMessageToTwitch(String channel, String user, String text) {
        Date date = Calendar.getInstance().getTime();

        consoleTextArea.append("[" + date + "] " + user + ": " + text + " \n\t-Sent in: " + channel.toUpperCase() + "\n");
        Core.twitchBot.getClient().getChat().sendMessage(Core.getChannelChosen(), chatInput.getText());
    }
}
