import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FlappyBirdForm extends JFrame {
    private JButton buttonStart;
    private JButton buttonExit;
    private JLabel label;
    private JPanel panel1;
    private Logic logic;
    private View view;

    public FlappyBirdForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 640);
        setLocationRelativeTo(null);
        setResizable(false);

        panel1 = new JPanel();
        panel1.setLayout(new OverlayLayout(panel1));
        panel1.setBackground(Color.cyan);

        logic = new Logic();
        view = new View(logic);
        logic.setView(view);
        view.setPreferredSize(new Dimension(360, 640));

        // Label "Press to Start"
        label = new JLabel("PRESS TO START");
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // START Button
        buttonStart = new JButton("START") {
            Color normalColor = new Color(116, 160, 80);
            Color hoverColor = new Color(152, 228, 255);
            Color borderColor = Color.BLACK;
            int arcRadius = 8;
            boolean hovered = false;

            {
                setFont(new Font("Impact", Font.PLAIN, 18));
                setForeground(Color.BLACK);
                setFocusPainted(false);
                setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
                setContentAreaFilled(false);
                setOpaque(false);

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hovered = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        hovered = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (hovered) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(normalColor);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcRadius, arcRadius);
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, arcRadius, arcRadius);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        buttonStart.setPreferredSize(new Dimension(140, 50));
        buttonStart.addActionListener(e -> {
            logic.startGame();
            buttonStart.setVisible(false);
            buttonExit.setVisible(false);
            label.setVisible(false); // Label hilang saat game dimulai
            view.requestFocus();
        });

        // EXIT Button
        buttonExit = new JButton("EXIT") {
            Color normalColor = new Color(255, 82, 82);
            Color hoverColor = new Color(152, 228, 255);
            Color borderColor = Color.BLACK;
            int arcRadius = 8;
            boolean hovered = false;

            {
                setFont(new Font("Impact", Font.PLAIN, 18));
                setForeground(Color.BLACK);
                setFocusPainted(false);
                setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
                setContentAreaFilled(false);
                setOpaque(false);

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hovered = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        hovered = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (hovered) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(normalColor);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcRadius, arcRadius);
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, arcRadius, arcRadius);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        buttonExit.setPreferredSize(new Dimension(140, 50));
        buttonExit.addActionListener(e -> System.exit(0));

        // Button Layout with label
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(0.5f);
        buttonPanel.setAlignmentY(0.55f);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonExit.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components with spacing
        buttonPanel.add(label);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(buttonStart);
        buttonPanel.add(Box.createVerticalStrut(12));
        buttonPanel.add(buttonExit);

        panel1.add(buttonPanel);
        panel1.add(view);

        add(panel1);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FlappyBirdForm();
    }
}
