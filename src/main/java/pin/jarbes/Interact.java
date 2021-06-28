package pin.jarbes;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import pin.jarbox.val.Local;

import pin.jarbox.val.Zone;
import pin.jarbox.wzd.WzdDesk;
import pin.jarbox.wzd.WzdLog;

public class Interact {

  private static Robot robot;

  static {
    try {
      robot = new Robot();
      robot.setAutoDelay(180);
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  public static void keyDown(int keyCode) {
    robot.keyPress(keyCode);
  }

  public static void keyUp(int keyCode) {
    robot.keyRelease(keyCode);
  }

  public static void mouseMove(Local local) {
    robot.mouseMove(local.x, local.y);
  }

  public static void mouseDown(Buttons button) {
    robot.mousePress(button.getCode());
  }

  public static void mouseUp(Buttons button) {
    robot.mouseRelease(button.getCode());
  }

  public static BufferedImage capture(Zone zone) {
    BufferedImage result;
    if (zone == null || zone.x == null || zone.y == null || zone.width == null
        || zone.height == null) {
      result = robot.createScreenCapture(
          new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    } else {
      result = robot.createScreenCapture(
          new Rectangle(zone.x, zone.y, zone.width, zone.height));
    }
    result = WzdDesk.convertToRGB(result);
    if (WzdLog.DEBUG) {
      try {
        ImageIO.write(result, "bmp", new File("capture.bmp"));
      } catch (Exception e) {
        WzdLog.treat(e, true);
      }
    }
    return result;
  }

}
