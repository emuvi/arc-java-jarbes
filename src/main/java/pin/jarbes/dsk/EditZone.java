package pin.jarbes.dsk;

import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import pin.jarbox.val.Zone;
import pin.jarbox.wzd.WzdDesk;
import pin.jarbox.wzd.WzdLog;

public class EditZone extends Edit<Zone> {

  private static final long serialVersionUID = -4369344529851945956L;

  private final EditInteger x = new EditInteger();
  private final EditInteger y = new EditInteger();
  private final EditInteger width = new EditInteger();
  private final EditInteger height = new EditInteger();

  private final JButton pasteButton = new JButton();

  private final JPanel field = new JPanel();

  public EditZone() {
    super();
    JPanel first = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
    first.add(WzdDesk.wrap(x, "X:"));
    first.add(WzdDesk.wrap(y, "Y:"));
    JPanel second = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
    second.add(WzdDesk.wrap(width, "Width:"));
    second.add(WzdDesk.wrap(height, "Height:"));
    pasteButton.setIcon(new ImageIcon(Icons.getPaste()));
    pasteButton.addActionListener(event -> paste());
    second.add(WzdDesk.wrap(pasteButton, " "));
    field.setLayout(new BoxLayout(field, BoxLayout.Y_AXIS));
    field.add(first);
    field.add(second);
    addMain(field);
  }

  public void paste() {
    try {
      String pasted = WzdDesk.getStringFromClipboard();
      if (pasted != null) {
        String[] parts = pasted.split("\\,");
        x.setValue(Integer.parseInt(parts[0]));
        y.setValue(Integer.parseInt(parts[1]));
        width.setValue(Integer.parseInt(parts[2]));
        height.setValue(Integer.parseInt(parts[3]));
      }
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  @Override
  public Zone getValue() {
    Integer mX = x.getValue();
    Integer mY = y.getValue();
    Integer mWidth = width.getValue();
    Integer mHeight = height.getValue();
    if (mX == null && mY == null && mWidth == null && mHeight == null) {
      return null;
    } else {
      return new Zone(mX, mY, mWidth, mHeight);
    }
  }

  @Override
  public void setValue(Zone value) {
    if (value == null) {
      x.setValue(null);
      y.setValue(null);
      width.setValue(null);
      height.setValue(null);
    } else {
      x.setValue(value.x);
      y.setValue(value.y);
      width.setValue(value.width);
      height.setValue(value.height);
    }
  }

}
