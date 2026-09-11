import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class typing implements NativeKeyListener, NativeMouseListener {
    boolean togg=true;
    boolean triger=false;
    static int big_delay = 500;
    static int small_delay= 72;
    static boolean loop =false;
    Robot robot;
    static JLabel togglelable = new JLabel("(Midel Click) to toggle: off", SwingConstants.CENTER);
    
        public typing() throws Exception {
            robot = new Robot();
        }

        public static List<Integer> translate_list(String a){
            List<Integer> clist = new ArrayList<>();
            for (char i:a.toCharArray()){
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(i);
                if (keyCode != KeyEvent.VK_UNDEFINED) clist.add(keyCode);
            }
            return clist;
        }
    
        @Override
        public void nativeKeyPressed(NativeKeyEvent e) {}
    
        @Override public void nativeMousePressed(NativeMouseEvent e) {}
    
        @Override public void nativeKeyReleased(NativeKeyEvent e) {}
        @Override public void nativeKeyTyped(NativeKeyEvent e) {}
        @Override public void nativeMouseReleased(NativeMouseEvent e) {
            if (e.getButton() == NativeMouseEvent.BUTTON3&&FileAnal.works) {
                togg=!togg;
                if (togg){
                    togglelable.setText("(Midel Click) to toggle: off");
                    togglelable.setForeground(Color.RED);
                }else {
                    togglelable.setText("(Midel Click) to toggle: on");
                    togglelable.setForeground(Color.green);
                }
            }
            if (e.getButton() != NativeMouseEvent.BUTTON1||togg||triger) return;
            triger=true;
            new Thread(() -> clicker()).start();
        }
        @Override public void nativeMouseClicked(NativeMouseEvent e) {}
    
        
        
        public static void main(String[] args) {
            typing self=null;
            try {
                self =new typing();
                GlobalScreen.registerNativeHook();
                GlobalScreen.addNativeKeyListener(self);
                GlobalScreen.addNativeMouseListener(self);
            } catch (Exception e) {
                e.printStackTrace();
            }
            new FileAnal();
            //System.out.println(InputEvent.BUTTON1_DOWN_MASK);//////////////////////////////////////////////////////
    
            // --- Create the Swing window ---t
            JFrame frame = new JFrame("TBot");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLocationRelativeTo(null);
    
            // Main label
            JLabel label = new JLabel("Press (Left click) to start", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 18));
            togglelable.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel label3 = new JLabel("commands: Unavailable", SwingConstants.CENTER);
        label3.setFont(new Font("Arial", Font.BOLD, 16));
        label3.setForeground(Color.RED);
        togglelable.setForeground(Color.RED);
        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        centerPanel.add(label);
        centerPanel.add(togglelable);
        centerPanel.add(label3);
        if (FileAnal.works) {
            label3.setText("commands: Available");
            label3.setForeground(Color.green);
        }

        // Input controls
        JLabel promptLabel = new JLabel("big delay (1 to 99): ");
        JTextField inputField = new JTextField(2);
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(promptLabel);
        inputPanel.add(inputField);

        // Input controls2
        JLabel promptLabel2 = new JLabel("small delay (1 to 99): ");
        JTextField inputField2 = new JTextField(2);
        JButton applyButton = new JButton("Apply");
        inputPanel.add(promptLabel2);
        inputPanel.add(inputField2);
        

        // Input controls2
        JLabel promptLabel3 = new JLabel("loop: ");
        JCheckBox checkBox = new JCheckBox();
        inputPanel.add(promptLabel3);
        inputPanel.add(checkBox);
        inputPanel.add(applyButton);

        // Main frame: label at top, input panel at center
        frame.setLayout(new BorderLayout());
        frame.add(centerPanel, BorderLayout.NORTH);
        frame.add(inputPanel);
        inputField.setText("2");
        inputField2.setText("6");

        // --- Button action ---
        applyButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String big = inputField.getText().trim();
                String small = inputField2.getText().trim();
                try {
                    int bigInput = Integer.parseInt(big);
                    int smallInput = Integer.parseInt(small);
                    if(bigInput>0&&bigInput<100&&smallInput>0&&smallInput<100) {
                        big_delay=bigInput*250;
                        small_delay=smallInput*12;
                        loop=checkBox.isSelected();
                    }
                } catch (NumberFormatException ex) {}
            }
        });

        // Optional: Unregister hook before the JVM exits (cleanup)
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void press_keybord(int a) {
        robot.keyPress(a);
        robot.keyRelease(a);
    }
    public void press_mouse(int a) {
        robot.mousePress(a);
        robot.mouseRelease(a);
    }
    public void delay(int a){
        try {
            Thread.sleep(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void clicker(){
        delay(small_delay);
        for (List<Integer> i : FileAnal.order) {
            if (i.get(0)==1){
                if (i.get(1)==1){
                    robot.mousePress(i.get(2));
                }else if (i.get(1)==2){
                    robot.mouseRelease(i.get(2));
                }else if (i.get(1)==3){
                    press_mouse(i.get(2));
                }else if (i.get(1)==4){
                    robot.mouseMove(i.get(2), i.get(3));
                }
            }else if (i.get(0)==2){
                if (i.get(1)==1){
                    robot.keyPress(i.get(2));
                }else if (i.get(1)==2){
                    robot.keyRelease(i.get(2));
                }else if (i.get(1)==3){
                    press_keybord(i.get(2));
                }else if (i.get(1)==4){
                    for (int j:i.subList(2, i.size()))press_keybord(j);
                }
            }else if (i.get(0)==3){
                if (i.get(1)==-1) delay(big_delay);
                else delay(i.get(1));
            }
            delay(small_delay);
        }
        if (loop&&!togg){
            clicker();
        }else triger=false;
    }
}