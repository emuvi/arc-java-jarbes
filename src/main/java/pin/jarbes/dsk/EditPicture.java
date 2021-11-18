package pin.jarbes.dsk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import pin.jarbox.val.Picture;
import pin.jarbox.wzd.WzdDesk;
import pin.jarbox.wzd.WzdFile;
import pin.jarbox.wzd.WzdLog;

public class EditPicture extends Edit<Picture> {

  private static final long serialVersionUID = -1295428529700687329L;

  private final JLabel view = new JLabel();
  private final JScrollPane scroll = new JScrollPane(view);
  private final JButton pasteButton = new JButton();
  private final JButton loadButton = new JButton();
  private final JButton saveButton = new JButton();
  private final JPanel buttons = new JPanel();
  private final JPanel field = new JPanel(new BorderLayout(0, 0));

  private final JFileChooser chooser = new JFileChooser();
  private static File selected = new File("picture.bmp");

  private BufferedImage loaded = null;

  public EditPicture() {
    super();
    Dimension size = new Dimension(120, 100);
    scroll.setMinimumSize(size);
    scroll.setPreferredSize(size);
    field.add(scroll, BorderLayout.CENTER);
    pasteButton.setIcon(new ImageIcon(Icons.getPaste()));
    loadButton.setIcon(new ImageIcon(Icons.getFolder()));
    saveButton.setIcon(new ImageIcon(Icons.getSave()));
    pasteButton.addActionListener(event -> paste());
    loadButton.addActionListener(event -> load());
    saveButton.addActionListener(event -> save());
    buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
    buttons.add(pasteButton);
    buttons.add(loadButton);
    buttons.add(saveButton);
    buttons.add(Box.createVerticalGlue());
    field.add(buttons, BorderLayout.EAST);
    addMain(field);
    chooser.setFileFilter(new FileNameExtensionFilter(
        "Images (*.bmp, *.png, *.jpg, *.jpeg)", "bmp", "png", "jpg", "jpeg"));
    chooser.setSelectedFile(selected);
  }

  private void paste() {
    try {
      BufferedImage pasted = (BufferedImage) WzdDesk.getImageFromClipboard();
      if (pasted != null) {
        loaded = pasted;
        view.setIcon(new ImageIcon(loaded));
      }
    } catch (Exception e) {
    }
  }

  private void load() {
    try {
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        loaded = ImageIO.read(chooser.getSelectedFile());
        view.setIcon(new ImageIcon(loaded));
        selected = chooser.getSelectedFile();
      }
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void save() {
    try {
      if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        try (FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile())) {
          ImageIO.write(loaded, WzdFile.getExtension(chooser.getSelectedFile().getName()),
              fos);
          selected = chooser.getSelectedFile();
        }
      }
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  @Override
  public Picture getValue() {
    if (loaded != null) {
      return new Picture(loaded);
    } else {
      return null;
    }
  }

  @Override
  public void setValue(Picture value) {
    if (value == null || value.image == null) {
      loaded = null;
      view.setIcon(null);
    } else {
      loaded = value.image;
      view.setIcon(new ImageIcon(loaded));
    }
  }

}
