package pin.jarbes.dsk;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import pin.jarbox.wzd.WzdLog;


public class Icons {

  private static final BufferedImage error = new BufferedImage(16, 16,
      BufferedImage.TYPE_INT_RGB);

  static {
    Graphics2D g = error.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, 16, 16);
    g.setColor(Color.RED);
    g.setStroke(new BasicStroke(1));
    g.drawRect(0, 0, 15, 15);
    g.drawLine(0, 0, 15, 15);
    g.drawLine(0, 15, 15, 0);
    g.dispose();
  }

  public static BufferedImage get(Class<?> clazz, String name) {
    try {
      return ImageIO.read(clazz.getResourceAsStream(name));
    } catch (Exception e) {
      WzdLog.treat(e);
      return error;
    }
  }

  private static BufferedImage get(String name) {
    try {
      return ImageIO.read(Icons.class.getResourceAsStream(name));
    } catch (Exception e) {
      WzdLog.treat(e);
      return error;
    }
  }

  private static final Dimension buttonDimension = new Dimension(26, 26);

  public static void setAsIcon(JButton button) {
    button.setMinimumSize(buttonDimension);
    button.setPreferredSize(buttonDimension);
    button.setMaximumSize(buttonDimension);
    button.setFocusable(false);
  }

  public static BufferedImage getCancel() {
    return get("cancel.png");
  }

  public static void putCancel(JButton button) {
    button.setIcon(new ImageIcon(getCancel()));
    setAsIcon(button);
  }

  public static JButton buttonCancel(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putCancel(result);
    return result;
  }

  public static BufferedImage getCog() {
    return get("cog.png");
  }

  public static void putCog(JButton button) {
    button.setIcon(new ImageIcon(getCog()));
    setAsIcon(button);
  }

  public static JButton buttonCog(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putCog(result);
    return result;
  }

  public static BufferedImage getConfig() {
    return get("config.png");
  }

  public static void putConfig(JButton button) {
    button.setIcon(new ImageIcon(getConfig()));
    setAsIcon(button);
  }

  public static JButton buttonConfig(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putConfig(result);
    return result;
  }

  public static BufferedImage getConfirm() {
    return get("confirm.png");
  }

  public static void putConfirm(JButton button) {
    button.setIcon(new ImageIcon(getConfirm()));
    setAsIcon(button);
  }

  public static JButton buttonConfirm(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putConfirm(result);
    return result;
  }

  public static BufferedImage getConnect() {
    return get("connect.png");
  }

  public static void putConnect(JButton button) {
    button.setIcon(new ImageIcon(getConnect()));
    setAsIcon(button);
  }

  public static JButton buttonConnect(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putConnect(result);
    return result;
  }

  public static BufferedImage getCopy() {
    return get("copy.png");
  }

  public static void putCopy(JButton button) {
    button.setIcon(new ImageIcon(getCopy()));
    setAsIcon(button);
  }

  public static JButton buttonCopy(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putCopy(result);
    return result;
  }

  public static BufferedImage getCut() {
    return get("cut.png");
  }

  public static void putCut(JButton button) {
    button.setIcon(new ImageIcon(getCut()));
    setAsIcon(button);
  }

  public static JButton buttonCut(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putCut(result);
    return result;
  }

  public static BufferedImage getDelete() {
    return get("delete.png");
  }

  public static void putDelete(JButton button) {
    button.setIcon(new ImageIcon(getDelete()));
    setAsIcon(button);
  }

  public static JButton buttonDelete(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putDelete(result);
    return result;
  }

  public static BufferedImage getDown() {
    return get("down.png");
  }

  public static void putDown(JButton button) {
    button.setIcon(new ImageIcon(getDown()));
    setAsIcon(button);
  }

  public static JButton buttonDown(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putDown(result);
    return result;
  }

  public static BufferedImage getEdit() {
    return get("edit.png");
  }

  public static void putEdit(JButton button) {
    button.setIcon(new ImageIcon(getEdit()));
    setAsIcon(button);
  }

  public static JButton buttonEdit(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putEdit(result);
    return result;
  }

  public static BufferedImage getFind() {
    return get("find.png");
  }

  public static void putFind(JButton button) {
    button.setIcon(new ImageIcon(getFind()));
    setAsIcon(button);
  }

  public static JButton buttonFind(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putFind(result);
    return result;
  }

  public static BufferedImage getFolder() {
    return get("folder.png");
  }

  public static void putFolder(JButton button) {
    button.setIcon(new ImageIcon(getFolder()));
    setAsIcon(button);
  }

  public static JButton buttonFolder(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putFolder(result);
    return result;
  }

  public static BufferedImage getFormEdit() {
    return get("form_edit.png");
  }

  public static void putFormEdit(JButton button) {
    button.setIcon(new ImageIcon(getFormEdit()));
    setAsIcon(button);
  }

  public static JButton buttonFormEdit(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putFormEdit(result);
    return result;
  }

  public static BufferedImage getInsert() {
    return get("insert.png");
  }

  public static void putInsert(JButton button) {
    button.setIcon(new ImageIcon(getInsert()));
    setAsIcon(button);
  }

  public static JButton buttonInsert(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putInsert(result);
    return result;
  }

  public static BufferedImage getNew() {
    return get("new.png");
  }

  public static void putNew(JButton button) {
    button.setIcon(new ImageIcon(getNew()));
    setAsIcon(button);
  }

  public static JButton buttonNew(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putNew(result);
    return result;
  }

  public static BufferedImage getOpen() {
    return get("open.png");
  }

  public static void putOpen(JButton button) {
    button.setIcon(new ImageIcon(getOpen()));
    setAsIcon(button);
  }

  public static JButton buttonOpen(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putOpen(result);
    return result;
  }

  public static BufferedImage getPaste() {
    return get("paste.png");
  }

  public static void putPaste(JButton button) {
    button.setIcon(new ImageIcon(getPaste()));
    setAsIcon(button);
  }

  public static JButton buttonPaste(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putPaste(result);
    return result;
  }

  public static BufferedImage getRedo() {
    return get("redo.png");
  }

  public static void putRedo(JButton button) {
    button.setIcon(new ImageIcon(getRedo()));
    setAsIcon(button);
  }

  public static JButton buttonRedo(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putRedo(result);
    return result;
  }

  public static BufferedImage getSave() {
    return get("save.png");
  }

  public static void putSave(JButton button) {
    button.setIcon(new ImageIcon(getSave()));
    setAsIcon(button);
  }

  public static JButton buttonSave(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putSave(result);
    return result;
  }

  public static BufferedImage getTerminal() {
    return get("terminal.png");
  }

  public static void putTerminal(JButton button) {
    button.setIcon(new ImageIcon(getTerminal()));
    setAsIcon(button);
  }

  public static JButton buttonTerminal(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putTerminal(result);
    return result;
  }

  public static BufferedImage getUndo() {
    return get("undo.png");
  }

  public static void putUndo(JButton button) {
    button.setIcon(new ImageIcon(getUndo()));
    setAsIcon(button);
  }

  public static JButton buttonUndo(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putUndo(result);
    return result;
  }

  public static BufferedImage getUp() {
    return get("up.png");
  }

  public static void putUp(JButton button) {
    button.setIcon(new ImageIcon(getUp()));
    setAsIcon(button);
  }

  public static JButton buttonUp(ActionListener action) {
    JButton result = new JButton();
    if (action != null) {
      result.addActionListener(action);
    }
    putUp(result);
    return result;
  }
}
