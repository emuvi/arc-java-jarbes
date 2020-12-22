package pin;

import pin.jarbes.HelmMain;
import pin.jarbox.Console;
import pin.jarbox.Utils;

public class Jarbes {

  public static void main(String[] args) throws Exception {
    Console.start(args);
    Utils.startSystemLook();
    java.awt.EventQueue.invokeLater(() -> {
      try {
        new HelmMain().show();
      } catch (Exception e) {
        Utils.treat(e);
      }
    });
  }
}
