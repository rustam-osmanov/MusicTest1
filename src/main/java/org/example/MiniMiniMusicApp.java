package org.example;

import javax.accessibility.Accessible;
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniMiniMusicApp {

    static JFrame frame = new JFrame("Мой музыкальный синтезатор");
    static MyDrawPanel panel;


    public static void main(String[] args) {
        MiniMiniMusicApp mini = new MiniMiniMusicApp();
        mini.go();
    }

    public void setUpGui(){
        panel = new MyDrawPanel();
        frame.setContentPane(panel);
        frame.setBounds(30,30,300,300);
        frame.setVisible(true);
    }

    public void go() {
        setUpGui();
        play();

    }

    public void play() {
        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            int[] eventsIWant = {127};
            player.addControllerEventListener(panel,eventsIWant);
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();
            int r = 0;
            for (int i = 0; i < 61; i++) {
                r = (int) ((Math.random() * 50) + 1);
                //Создаем ноту
                track.add(makeEvent(144, 1, r + 0, 100, i + 0));

                //Событие
                track.add(makeEvent(176, 1, 127, 0, i + 0));

                //Закрываем ноту
                track.add(makeEvent(128, 1, r + 0, 100, i + 2));
            }
            player.setSequence(seq);
            player.start();
            player.setTempoInBPM(120);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return event;
    }



    class ActionListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            play();
        }
    }

    class ColorListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            return;
        }
    }

    class MyDrawPanel extends JPanel implements ControllerEventListener {
        boolean msg = false;

        @Override
        public void controlChange(ShortMessage event) {
            msg = true;
            repaint();
        }

        public void paintComponent(Graphics g) {
            if (msg) {
                Graphics2D g2 = (Graphics2D) g;

                //g.setColor(Color.white);

                //g.fillRect(0, 0, this.getWidth(), this.getHeight());

                int red = (int) (Math.random() * 250);
                int green = (int) (Math.random() * 250);
                int blue = (int) (Math.random() * 250);

                Color randomColor = new Color(red, green, blue);
                g.setColor(randomColor);

                int height = (int) ((Math.random() * 120) + 10);
                int width = (int) ((Math.random() * 120) + 10);
                int x = (int) ((Math.random() * 40) + 10);
                int y = (int) ((Math.random() * 40) + 10);

                g.fillRect(x+0, y+0, width+0, height+0);
                msg = false;
            }
        }


    }

}



