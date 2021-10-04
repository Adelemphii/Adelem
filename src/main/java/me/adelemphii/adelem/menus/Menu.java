package me.adelemphii.adelem.menus;

import me.adelemphii.adelem.Core;
import me.adelemphii.adelem.commands.CommandLockDown;
import org.jetbrains.annotations.NotNull;
import org.pushingpixels.substance.api.SubstanceCortex;
import org.pushingpixels.substance.api.skin.GraphiteChalkSkin;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteChalkLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Menu extends JFrame {

    private final Core core;
    private final CommandLockDown lockDown;
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

    public Menu(@NotNull Core core, @NotNull CommandLockDown lockDown) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.core = core;
        this.lockDown = lockDown;
        configureSettings();
        configureMenus();
        addActionListeners();
    }

    private void configureMenus() {
        consoleTextArea.setEditable(false);
        configText.setText("EXTREMELY WIP, SORRY!");
        configText.setEditable(false);
        core.getConfig().getChannels().forEach(channelBox::addItem);
    }

    private void addActionListeners() {
        channelBox.addActionListener(e -> {
            JComboBox box = (JComboBox) e.getSource();
            core.setChannelChosen(box.getSelectedItem().toString());
        });
        chatInput.addActionListener(e -> {
            final String user = core.getConfig().getBot().get("name");
            this.sendMessageToTwitch(core.getChannelChosen(), user, chatInput.getText());
            chatInput.setText("");
        });
    }

    private void configureSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("birbpog-twitch.png")));
        if (icon.getImage() != null) setIconImage(icon.getImage());

        // TODO: Make this work with JTextPane instead of JTextArea (for customization w/ text)
        PrintStream con = new PrintStream(new TextAreaOutputStream(consoleTextArea));
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

    public void sendMessageToTwitch(@NotNull String channel, String user, @NotNull String text) {
        Date date = Calendar.getInstance().getTime();
        String payload = "[%s] %s: %s \n\t-Channel: %s\n".formatted(date, user, text, channel.toUpperCase());
        if (text.contains("!lockdown")) {
            lockDown.runCommand(channel, user, text);
            consoleTextArea.insert(payload, 0);
            return;
        }
        consoleTextArea.insert(payload, 0);
        core.getTwitchBot().getClient().getChat().sendMessage(core.getChannelChosen(), chatInput.getText());
    }

    public void sendMessageToConsole(@NotNull String channel, String user, String text) {
        Date date = Calendar.getInstance().getTime();
        String payload = "[%s] %s: %s \n\t-Channel: %s\n".formatted(date, user, text, channel.toUpperCase());
        consoleTextArea.insert(payload, 0);
    }
}
