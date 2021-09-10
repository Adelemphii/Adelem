package me.adelemphii.adelem.menus;

import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.commands.CommandLockDown;
import org.pushingpixels.substance.api.SubstanceCortex;
import org.pushingpixels.substance.api.skin.GraphiteChalkSkin;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteChalkLookAndFeel;

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

    private void configureSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PrintStream con=new PrintStream(new TextAreaOutputStream(consoleTextArea));
        System.setOut(con);
        System.setErr(con);

        add(consolePanel);
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setDefaultLookAndFeelDecorated(true);

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new SubstanceGraphiteChalkLookAndFeel());
                SubstanceCortex.GlobalScope.setSkin(new GraphiteChalkSkin());
            } catch(Exception e) {
                System.out.println("Substance Graphite Chalk L&F failed to initialize!");
            }
        });


        setSize(620, 830);
        setMinimumSize(new Dimension(820, 255));
        setTitle("Bot Console");

        // Please help, I can't make GUIs..
    }

    public void sendMessageToTwitch(String channel, String user, String text) {
        Date date = Calendar.getInstance().getTime();
        String payload = "[" + date + "] " + user + ": " + text + " \n\t-Channel: " + channel.toUpperCase() + "\n";
        if(text.contains("!lockdown")) {
            CommandLockDown.runCommand(channel, user, text);
            consoleTextArea.append(payload);
            consoleTextArea.append("Lockdown Enabled/Disabled!\n");
            return;
        }

        consoleTextArea.append(payload);
        Core.twitchBot.getClient().getChat().sendMessage(Core.getChannelChosen(), chatInput.getText());
    }
}
