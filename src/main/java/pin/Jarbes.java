package pin;

import pin.jarbes.HelmMain;
import pin.jarbox.bin.Console;
import pin.jarbox.wzd.WzdDesk;
import pin.jarbox.wzd.WzdLog;

public class Jarbes {

  public static void main(String[] args) throws Exception {
    Console.start("Jarbes", "0.1.0", args,
        "A small robot to automate repetitive routines on the desktop.");
    WzdDesk.startSystemLook();
    java.awt.EventQueue.invokeLater(() -> {
      try {
        new HelmMain().show();
      } catch (Exception e) {
        WzdLog.treat(e);
      }
    });
  }
}
