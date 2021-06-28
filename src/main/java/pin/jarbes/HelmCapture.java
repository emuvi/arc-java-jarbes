package pin.jarbes;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import pin.jarbox.bin.Variables;
import pin.jarbox.wzd.WzdDesk;
import pin.jarbox.wzd.WzdLog;

public class HelmCapture {

  private final JFrame window = new JFrame("Capture");
  private final JPanel mainPanel = new JPanel(new BorderLayout(4, 4));
  private final JButton captureButton = new JButton("Capture");
  private final JButton minimizeButton = new JButton("-");
  private final JButton closeButton = new JButton("x");
  private final JLabel copyLabel = new JLabel("Copy:");
  private final JButton localButton = new JButton("Local");
  private final JButton zoneButton = new JButton("Zone");
  private final JButton pictureButton = new JButton("Picture");
  private final JLabel selectedLabel = new JLabel("Selected:");
  private final JPanel toolsPanel =
    new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
  private final Editor captureEditor = new Editor();
  private final JScrollPane captureScroll = new JScrollPane(captureEditor);

  private volatile BufferedImage captured;

  private Point controlPin = null;
  private Point altPin = null;

  private Point startDot = null;
  private Point endDot = null;

  private int selectedX = -1;
  private int selectedY = -1;
  private int selectedWidth = -1;
  private int selectedHeight = -1;

  public HelmCapture() throws Exception {
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    window.setIconImage(
        ImageIO.read(HelmEditor.class.getResourceAsStream("jarbes.png")));
    window.setContentPane(mainPanel);
    mainPanel.add(toolsPanel, BorderLayout.NORTH);
    mainPanel.add(captureScroll, BorderLayout.CENTER);
    toolsPanel.add(captureButton);
    toolsPanel.add(minimizeButton);
    toolsPanel.add(closeButton);
    toolsPanel.add(Box.createHorizontalStrut(9));
    toolsPanel.add(copyLabel);
    toolsPanel.add(localButton);
    toolsPanel.add(zoneButton);
    toolsPanel.add(pictureButton);
    toolsPanel.add(Box.createHorizontalStrut(9));
    toolsPanel.add(selectedLabel);
    captureButton.setMnemonic('C');
    localButton.setMnemonic('L');
    zoneButton.setMnemonic('Z');
    pictureButton.setMnemonic('P');
    captureButton.addActionListener(event -> capture());
    minimizeButton.addActionListener(event -> minimize());
    minimizeButton.addActionListener(event -> close());
    localButton.addActionListener(event -> copyLocal());
    zoneButton.addActionListener(event -> copyZone());
    pictureButton.addActionListener(event -> copyPicture());
    updateSelected();
    WzdDesk.putShortCut(mainPanel, "CaptureChar", "C", () -> capture());
    WzdDesk.putShortCut(mainPanel, "Capture", "control C", () -> capture());
    WzdDesk.putShortCut(mainPanel, "Minimize", "M", () -> minimize());
    WzdDesk.putShortCut(mainPanel, "Copy Local", "L", () -> copyLocal());
    WzdDesk.putShortCut(mainPanel, "Copy Zone", "Z", () -> copyZone());
    WzdDesk.putShortCut(mainPanel, "Copy Picture", "P", () -> copyPicture());
  }

  private void updateSelected() {
    selectedLabel.setText(
        String.format("Selected: X = %d Y = %d Width = %d Height = %d",
          selectedX, selectedY, selectedWidth, selectedHeight));
  }

  private void copyLocal() {
    WzdDesk.copyToClipboard(String.format("%d,%d", selectedX, selectedY));
  }

  private void copyZone() {
    WzdDesk.copyToClipboard(String.format("%d,%d,%d,%d", selectedX, selectedY,
          selectedWidth, selectedHeight));
  }

  private void copyPicture() {
    BufferedImage result = new BufferedImage(selectedWidth, selectedHeight,
        BufferedImage.TYPE_INT_RGB);
    result.createGraphics().drawImage(captured.getSubimage(selectedX, selectedY,
          selectedWidth,
          selectedHeight),
        0, 0, Color.WHITE, null);
    TransferableImage trans = new TransferableImage(result);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(trans, null);
  }

  public void show() {
    window.pack();
    window.setVisible(true);
    SwingUtilities.invokeLater(
        () -> window.setExtendedState(JFrame.MAXIMIZED_BOTH));
    ;
  }

  public void minimize() { window.setExtendedState(JFrame.ICONIFIED); }

  public void close() { window.setVisible(true); }

  private void refreshCaptured() {
    captureEditor.repaint();
    captureScroll.revalidate();
  }

  public void capture() {
    new Thread("HelmCapture") {
      @Override
      public void run() {
        try {
          Variables variables = new Variables();
          new ActHide().execute(variables);
          Thread.sleep(500);
          BufferedImage newCapture = Interact.capture(null);
          Thread.sleep(500);
          new ActShow().execute(variables);
          Thread.sleep(300);
          SwingUtilities.invokeLater(() -> {
            try {
              captured = newCapture;
              refreshCaptured();
            } catch (Exception e) {
              WzdLog.treat(e);
            }
          });
        } catch (Exception e) {
          WzdLog.treat(e);
        }
      };
    }.start();
  }

  private class Editor extends JComponent {

    private static final long serialVersionUID = 8967518851029633195L;

    private MouseAdapter adapter = new MouseAdapter() {
      private void getSelectedFromPins() {
        if (controlPin != null && altPin != null) {
          selectedX = Math.min(controlPin.x, altPin.x);
          selectedY = Math.min(controlPin.y, altPin.y);
          selectedWidth = Math.abs(altPin.x - controlPin.x);
          selectedHeight = Math.abs(altPin.y - controlPin.y);
        }
        repaint();
      }

      private void getSelectedFromDots() {
        if (startDot != null && endDot != null) {
          selectedX = Math.min(startDot.x, endDot.x);
          selectedY = Math.min(startDot.y, endDot.y);
          selectedWidth = Math.abs(endDot.x - startDot.x);
          selectedHeight = Math.abs(endDot.y - startDot.y);
          updateSelected();
        }
        repaint();
      }

      @Override
      public void mousePressed(MouseEvent e) {
        if (e.isControlDown() && e.isShiftDown()) {
          getSelectedFromPins();
        } else if (e.isControlDown()) {
          controlPin = e.getPoint();
          getSelectedFromPins();
        } else if (e.isShiftDown()) {
          altPin = e.getPoint();
          getSelectedFromPins();
        } else {
          startDot = e.getPoint();
          getSelectedFromDots();
        }
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        if (e.isControlDown() && e.isShiftDown()) {
          getSelectedFromPins();
        } else if (e.isControlDown()) {
          controlPin = e.getPoint();
          getSelectedFromPins();
        } else if (e.isShiftDown()) {
          altPin = e.getPoint();
          getSelectedFromPins();
        } else {
          endDot = e.getPoint();
          getSelectedFromDots();
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if (e.isControlDown() && e.isShiftDown()) {
          getSelectedFromPins();
        } else if (e.isControlDown()) {
          controlPin = e.getPoint();
          getSelectedFromPins();
        } else if (e.isShiftDown()) {
          altPin = e.getPoint();
          getSelectedFromPins();
        } else {
          endDot = e.getPoint();
          getSelectedFromDots();
        }
      }

      public void mouseClicked(MouseEvent e) {
        if (e.isControlDown() && e.isShiftDown()) {
          getSelectedFromPins();
        } else if (e.isControlDown()) {
          controlPin = e.getPoint();
          getSelectedFromPins();
        } else if (e.isShiftDown()) {
          altPin = e.getPoint();
          getSelectedFromPins();
        } else {
          endDot = e.getPoint();
          getSelectedFromDots();
        }
      };
    };

    public Editor() {
      addMouseListener(adapter);
      addMouseMotionListener(adapter);
    }

    @Override
    public Dimension getMinimumSize() {
      return new Dimension(800, 600);
    }

    @Override
    public Dimension getPreferredSize() {
      if (captured != null) {
        return new Dimension(captured.getWidth(), captured.getHeight());
      } else {
        return getMinimumSize();
      }
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D gr = (Graphics2D)g;
      if (captured != null) {
        gr.drawImage(captured, 0, 0, null);
      }
      if (controlPin != null) {
        gr.setColor(Color.RED);
        gr.fillOval(controlPin.x - 5, controlPin.y - 5, 10, 10);
        gr.setColor(Color.BLACK);
        Stroke dashedBlack =
          new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
              new float[] {5}, 0);
        gr.setStroke(dashedBlack);
        gr.drawOval(controlPin.x - 5, controlPin.y - 5, 10, 10);
        gr.setColor(Color.WHITE);
        Stroke dashedWhite =
          new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
              new float[] {5}, 5);
        gr.setStroke(dashedWhite);
        gr.drawOval(controlPin.x - 5, controlPin.y - 5, 10, 10);
      }
      if (altPin != null) {
        gr.setColor(Color.RED);
        gr.fillOval(altPin.x - 5, altPin.y - 5, 10, 10);
        gr.setColor(Color.BLACK);
        Stroke dashedBlack =
          new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
              new float[] {5}, 0);
        gr.setStroke(dashedBlack);
        gr.drawOval(altPin.x - 5, altPin.y - 5, 10, 10);
        gr.setColor(Color.WHITE);
        Stroke dashedWhite =
          new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
              new float[] {5}, 5);
        gr.setStroke(dashedWhite);
        gr.drawOval(altPin.x - 5, altPin.y - 5, 10, 10);
      }
      if (selectedX > -1 && selectedY > -1 && selectedWidth > -1 &&
          selectedHeight > -1) {
        gr.setColor(Color.BLACK);
        Stroke dashedBlack =
          new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
              new float[] {5}, 0);
        gr.setStroke(dashedBlack);
        gr.drawRect(selectedX, selectedY, selectedWidth, selectedHeight);
        gr.setColor(Color.WHITE);
        Stroke dashedWhite =
          new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
              new float[] {5}, 5);
        gr.setStroke(dashedWhite);
        gr.drawRect(selectedX, selectedY, selectedWidth, selectedHeight);
          }
    }
  }

  private static class TransferableImage implements Transferable {

    private final Image image;

    public TransferableImage(Image i) { this.image = i; }

    public Object getTransferData(DataFlavor flavor)
        throws UnsupportedFlavorException, IOException {
        if (flavor.equals(DataFlavor.imageFlavor) && image != null) {
          return image;
        } else {
          throw new UnsupportedFlavorException(flavor);
        }
    }

    public DataFlavor[] getTransferDataFlavors() {
      DataFlavor[] flavors = new DataFlavor[1];
      flavors[0] = DataFlavor.imageFlavor;
      return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
      DataFlavor[] flavors = getTransferDataFlavors();
      for (int i = 0; i < flavors.length; i++) {
        if (flavor.equals(flavors[i])) {
          return true;
        }
      }
      return false;
    }
  }
}
