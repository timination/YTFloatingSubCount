/*
	Author: Tim Talbot.
	Date  : 20 March 2017
	
	Feel free to use, modify or otherwise make use of this code in any way you wish,
	I just ask that you keep this notice here and pull-request any cool changes back
	to me. Thanks!

*/
package YTFloatingSubCount;

import java.util.ArrayList;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JColorChooser;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.*;
import javax.swing.UIManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

class YTFloatingSubCountSettings {
    YTFloatingSubCount clock;

    String screenName = "";

    final String configFileName = "YTFloatingSubCount.config";

    final int MAX_OPACITY = 100, MIN_OPACITY = 5, MAX_SIZE = 40, MIN_SIZE = 12;

    int opacityValue = 70, distanceValue = 0, sizeValue = 15;

    boolean isDraggable = false;

    String fontName = "", apiKeyStr = "", tickRateStr = "", channelIdStr = "";

    JFrame frame;
    ButtonGroup deviceGroup = new ButtonGroup();
    ArrayList<JRadioButton> deviceRadios = new ArrayList<JRadioButton>();
    JDialog fgDialog, bgDialog;
    JCheckBox isDraggableCheckbox;
    GridLayout layout;
    JPanel pane;
    JTextField distanceFromTop, channelId, apiKey, tickRate;
    JFontChooser fontChooser;
    JButton saveButton, exitButton, changeFontButton;
    JSlider opacitySlider, sizeSlider;
    Color backgroundColor, foregroundColor;
    JLabel titleLabel, backgroundColorLabel, foregroundColorLabel,
            backgroundColorExample, foregroundColorExample,
            distanceFromTopLabel, opacityLabel, sizeLabel, isDraggableLabel, fontLabel, channelIdLabel, apiKeyLabel, tickRateLabel;


    JColorChooser bgChooser, fgChooser;

    public YTFloatingSubCountSettings() {
        loadSettings();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                init();
            }
        });
    }

    public void init() {

        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("Label.font", UIManager.getFont("Label.font").deriveFont(20.0f));
            UIManager.put("Label.foreground", Color.gray);
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        }

        ActionListener fgOkActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                foregroundColor = fgChooser.getColor();
                foregroundColorExample.setBackground(foregroundColor);
            }
        };

        ActionListener bgOkActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                backgroundColor = bgChooser.getColor();
                backgroundColorExample.setBackground(backgroundColor);
            }
        };


        ActionListener bgCancelActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

            }
        };

        ActionListener fgCancelActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

            }
        };


        Border border = new LineBorder(Color.darkGray, 10);

        layout = new GridLayout(0, 2);
        frame = new JFrame("YTFloatingSubCount Settings");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth()/2;
        int height = (int) (screenSize.getHeight() - (screenSize.getHeight()*0.05));
        frame.setUndecorated(true);
        frame.setSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new YTFloatingSubCount();

            }
        });

        pane = new JPanel();
        pane.setBorder(border);
        pane.setBackground(Color.darkGray);
        pane.setLayout(layout);

        distanceFromTop = new JTextField(Integer.toString(distanceValue));
        apiKey = new JTextField();
        channelId = new JTextField();
        tickRate = new JTextField();

        bgChooser = new JColorChooser();
        fontChooser = new JFontChooser();

        bgDialog = JColorChooser.createDialog(null, "Change Background", true, bgChooser, bgOkActionListener, bgCancelActionListener);


        fgChooser = new JColorChooser();
        fgDialog = JColorChooser.createDialog(null, "Change Foreground", true, fgChooser, fgOkActionListener, fgCancelActionListener);

			/* fgChooser.getSelectionModel().addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e)
				{
					// update the backgroundColorExample color
					foregroundColorExample.setBackground(fgChooser.getColor());
				}
			});
			*/

        opacitySlider = new JSlider(JSlider.HORIZONTAL, MIN_OPACITY, MAX_OPACITY, opacityValue);
        opacitySlider.setBackground(Color.darkGray);
        opacitySlider.setForeground(Color.gray);

        sizeSlider = new JSlider(JSlider.HORIZONTAL, MIN_SIZE, MAX_SIZE, sizeValue);
        sizeSlider.setBackground(Color.darkGray);
        sizeSlider.setForeground(Color.gray);

        //opacitySlider.setMajorTickSpacing(10);
        //opacitySlider.setPaintTicks(true); //display tick lines
        //opacitySlider.setPaintLabels(true); // displays tick labels

        backgroundColorLabel = new JLabel("Background Color: ");
        foregroundColorLabel = new JLabel("Foreground Color: ");

        isDraggableLabel = new JLabel("Is Draggable: ");

        isDraggableCheckbox = new JCheckBox();
        isDraggableCheckbox.setBackground(Color.darkGray);
        isDraggableCheckbox.setSelected(isDraggable);

        backgroundColorExample = new JLabel();
        backgroundColorExample.setOpaque(true);
        backgroundColorExample.setBorder(border);
        backgroundColorExample.setBackground(backgroundColor);

        backgroundColorExample.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                bgDialog.setVisible(true);
            } // end mouseClicked
        }); // end addMouseListener

        foregroundColorExample = new JLabel();
        foregroundColorExample.setOpaque(true);
        foregroundColorExample.setBorder(border);
        foregroundColorExample.setBackground(foregroundColor);

        foregroundColorExample.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                fgDialog.setVisible(true);
            } // end mouseClicked
        });

        titleLabel = new JLabel("YTFloatingSubCount Settings");
        distanceFromTopLabel = new JLabel("Distance from top (px): ");
        opacityLabel = new JLabel("Opacity: ");
        sizeLabel = new JLabel("Font Size: ");

        saveButton = new JButton("Save");
        saveButton.setBackground(Color.gray);
        saveButton.setForeground(Color.WHITE);

        saveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                saveSettings();
                new YTFloatingSubCount();
                frame.dispose();
            }
        });


        fontLabel = new JLabel("Change Font");
        fontLabel.setFont(new Font(fontName, Font.PLAIN, 12));

        changeFontButton = new JButton("Change Font");
        changeFontButton.setBackground(Color.gray);
        changeFontButton.setForeground(Color.WHITE);

        changeFontButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int result = fontChooser.showDialog(frame);
                if (result == JFontChooser.OK_OPTION) {
                    Font font = fontChooser.getSelectedFont();
                    fontLabel.setFont(font);
                    fontName = font.getFontName();
                }
            }
        });

        exitButton = new JButton("Close");


        exitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new YTFloatingSubCount();
                frame.dispose();
            }
        });

        apiKeyLabel = new JLabel("API KEY: ");
        channelIdLabel = new JLabel("Channel ID: ");
        tickRateLabel = new JLabel("Tick Rate: ");

        apiKey.setText(apiKeyStr);
        channelId.setText(channelIdStr);
        tickRate.setText(tickRateStr);

        frame.add(titleLabel);
        layout.setHgap(10);
        pane.add(apiKeyLabel);
        pane.add(apiKey);
        pane.add(channelIdLabel);
        pane.add(channelId);
        pane.add(tickRateLabel);
        pane.add(tickRate);
        pane.add(fontLabel);
        pane.add(changeFontButton);
        pane.add(foregroundColorLabel);
        pane.add(foregroundColorExample);
        pane.add(backgroundColorLabel);
        pane.add(backgroundColorExample);
        pane.add(distanceFromTopLabel);
        pane.add(distanceFromTop);
        pane.add(opacityLabel);
        pane.add(opacitySlider);
        pane.add(sizeLabel);
        pane.add(sizeSlider);
        pane.add(isDraggableLabel);
        pane.add(isDraggableCheckbox);

        GraphicsEnvironment ge = GraphicsEnvironment.
                getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        for (int j = 0; j < gs.length; j++) {
            GraphicsDevice gd = gs[j];
            deviceRadios.add(new JRadioButton(gd.getIDstring()));

        }

        for (JRadioButton rb : deviceRadios) {
            deviceGroup.add(rb);
            pane.add(new JLabel(rb.getText()));
            if (rb.getText().equals(screenName)) rb.setSelected(true);
            rb.setBackground(Color.darkGray);
            rb.setForeground(Color.gray);
            pane.add(rb);
        }

        layout.setVgap(40);
        pane.add(saveButton);
        pane.add(exitButton);

        frame.add(pane);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void saveSettings() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(configFileName);

            String distanceVal = "0";

            try {
                int temp = Integer.parseInt(distanceFromTop.getText());
                distanceVal = distanceFromTop.getText();
            } catch (NumberFormatException e) {
                distanceVal = "0";
            }
            // set the properties value

            prop.setProperty("Foreground", Integer.toString(foregroundColor.getRGB()));
            prop.setProperty("Background", Integer.toString(backgroundColor.getRGB()));
            prop.setProperty("Distance", distanceVal);
            prop.setProperty("Opacity", Integer.toString(opacitySlider.getValue()));
            prop.setProperty("FontSize", Integer.toString(sizeSlider.getValue()));
            prop.setProperty("isDraggable", Boolean.toString(isDraggableCheckbox.isSelected()));
            prop.setProperty("fontName", fontChooser.getSelectedFontFamily());
            prop.setProperty("apiKey", apiKey.getText());
            prop.setProperty("channelId", channelId.getText());
            prop.setProperty("tickRate", tickRate.getText());

            // identify selected screen
            for (JRadioButton rb : deviceRadios) {
                if (rb.isSelected()) {
                    screenName = rb.getText();
                }
            }

            prop.setProperty("ScreenName", screenName);
            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }
    }

    private void loadSettings() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(configFileName);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            try {
                apiKeyStr = prop.getProperty("apiKey");
            } catch (Exception e) {
                apiKeyStr = "";
            }
            try {
                channelIdStr = prop.getProperty("channelId");
            } catch (Exception e) {
                channelIdStr = "";
            }
            try {
                tickRateStr = prop.getProperty("tickRate");
            } catch (Exception e) {
                tickRateStr = "15000";
            }
            try {
                fontName = prop.getProperty("fontName");
            } catch (Exception e) {
                fontName = foregroundColorLabel.getFont().getFontName();
            }
            try {
                backgroundColor = new Color(Integer.parseInt(prop.getProperty("Background")), true);
            } catch (NumberFormatException e) {
                System.out.println(e.toString());
                backgroundColor = Color.BLACK;
            }

            try {
                foregroundColor = new Color(Integer.parseInt(prop.getProperty("Foreground")), true);
            } catch (NumberFormatException e) {
                System.out.println(e.toString());
                foregroundColor = Color.WHITE;
            }

            try {
                distanceValue = Integer.parseInt(prop.getProperty("Distance"));
            } catch (NumberFormatException e) {
                System.out.println(e.toString());
                distanceValue = 0;
            }

            try {
                opacityValue = Integer.parseInt(prop.getProperty("Opacity"));
            } catch (NumberFormatException e) {
                System.out.println(e.toString());
                opacityValue = 70;
            }

            try {
                sizeValue = Integer.parseInt(prop.getProperty("FontSize"));
            } catch (NumberFormatException e) {
                System.out.println(e.toString());
                sizeValue = 15;
            }

            try {
                isDraggable = Boolean.parseBoolean(prop.getProperty("isDraggable"));
            } catch (Exception e) {
                System.out.println(e.toString());
                isDraggable = false;
            }

            screenName = prop.getProperty("ScreenName");
            //System.out.println("S: "+sizeValue); System.out.println("O: " + opacityValue); System.out.println("D: "+distanceValue);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }
    }
}