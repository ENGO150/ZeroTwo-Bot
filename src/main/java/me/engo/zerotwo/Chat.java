package me.engo.zerotwo;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Chat {

    public static String message = "";
    public static String id = "";

    public Chat(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JTextField t = new JTextField(50);
        JTextField t2 = new JTextField(18);
        JButton b = new JButton("submit");

        panel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        panel.add(t);
        panel.add(t2);
        panel.add(b);

        b.addActionListener(e -> {
            message = t.getText();
            id = t2.getText();

            Objects.requireNonNull(Bot.jda.getTextChannelById(id)).sendMessage(message).queue();
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("Chat");
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
