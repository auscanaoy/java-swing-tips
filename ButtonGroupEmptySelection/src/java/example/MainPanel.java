// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import javax.swing.*;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout());

    ButtonGroup bg = new ToggleButtonGroup();
    JPanel p = new JPanel();
    Stream.of("A", "B", "C").map(JToggleButton::new).forEach(r -> {
      r.setActionCommand(r.getText());
      p.add(r);
      bg.add(r);
    });

    JLabel label = new JLabel();
    JButton button = new JButton("check");
    button.addActionListener(e -> {
      String txt = Optional.ofNullable(bg.getSelection())
          .map(b -> String.format("\"%s\" isSelected.", b.getActionCommand()))
          .orElse("Please select one of the option above.");
      label.setText(txt);
      // ButtonModel bm = bg.getSelection();
      // if (bm != null) {
      //   label.setText(String.format("\"%s\" isSelected.", bm.getActionCommand()));
      // } else {
      //   label.setText("Please select one of the option above.");
      // }
    });

    Box box = Box.createHorizontalBox();
    box.add(label);
    box.add(Box.createHorizontalGlue());
    box.add(button, BorderLayout.WEST);
    box.add(Box.createHorizontalStrut(5));

    add(p);
    add(box, BorderLayout.SOUTH);
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setPreferredSize(new Dimension(320, 240));
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(MainPanel::createAndShowGui);
  }

  private static void createAndShowGui() {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (UnsupportedLookAndFeelException ignored) {
      Toolkit.getDefaultToolkit().beep();
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
      ex.printStackTrace();
      return;
    }
    JFrame frame = new JFrame("@title@");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new MainPanel());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}

class ToggleButtonGroup extends ButtonGroup {
  private ButtonModel prevModel;
  private final AtomicBoolean isAdjusting = new AtomicBoolean();

  @Override
  public void setSelected(ButtonModel m, boolean b) {
    if (isAdjusting.get()) {
      return;
    }
    if (m.equals(prevModel)) {
      isAdjusting.set(true);
      clearSelection();
      isAdjusting.set(false);
    } else {
      super.setSelected(m, b);
    }
    prevModel = getSelection();
  }
}
