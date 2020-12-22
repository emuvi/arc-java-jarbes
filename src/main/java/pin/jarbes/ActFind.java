package pin.jarbes;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import pin.jarbox.Local;
import pin.jarbox.Picture;
import pin.jarbox.Places;
import pin.jarbox.Utils;
import pin.jarbox.Variables;
import pin.jarbox.Zone;

public class ActFind extends Act {

  private static final long serialVersionUID = -1601306343022991701L;

  public Zone lookIn;
  public Picture picture;
  public Double maxDifference;
  public String saveIn;
  public Places saveAs;
  public String goToIfFound;
  public String goToElse;

  private Local findLocal() throws Exception {
    BufferedImage capture = Interact.capture(lookIn);
    int thirdWidth = capture.getWidth() / 3;
    int thirdHeight = capture.getHeight() / 3;
    if (picture.image.getWidth() < thirdWidth / 2
        && picture.image.getHeight() < thirdHeight / 2) {
      var zone1 = new Zone(0, 0, thirdWidth + picture.image.getWidth(),
          thirdHeight + picture.image.getHeight());
      var zone2 = new Zone(thirdWidth, 0, thirdWidth + picture.image.getWidth(),
          thirdHeight + picture.image.getHeight());
      var zone3 = new Zone(thirdWidth * 2, 0, thirdWidth,
          thirdHeight + picture.image.getHeight());
      var zone4 = new Zone(0, thirdHeight, thirdWidth + picture.image.getWidth(),
          thirdHeight + picture.image.getHeight());
      var zone5 = new Zone(thirdWidth, thirdHeight,
          thirdWidth + picture.image.getWidth(),
          thirdHeight + picture.image.getHeight());
      var zone6 = new Zone(thirdWidth * 2, thirdHeight, thirdWidth,
          thirdHeight + picture.image.getHeight());
      var zone7 = new Zone(0, thirdHeight * 2,
          thirdWidth + picture.image.getWidth(), thirdHeight);
      var zone8 = new Zone(thirdWidth, thirdHeight * 2,
          thirdWidth + picture.image.getWidth(), thirdHeight);
      var zone9 =
        new Zone(thirdWidth * 2, thirdHeight * 2, thirdWidth, thirdHeight);
      return Utils.getFirstNonNull(Utils.submit(new LookAt(capture, zone1)),
          Utils.submit(new LookAt(capture, zone2)),
          Utils.submit(new LookAt(capture, zone3)),
          Utils.submit(new LookAt(capture, zone4)),
          Utils.submit(new LookAt(capture, zone5)),
          Utils.submit(new LookAt(capture, zone6)),
          Utils.submit(new LookAt(capture, zone7)),
          Utils.submit(new LookAt(capture, zone8)),
          Utils.submit(new LookAt(capture, zone9)));
    } else {
      int halfWidth = capture.getWidth() / 2;
      int halfHeight = capture.getHeight() / 2;
      if (picture.image.getWidth() < halfWidth / 2
          && picture.image.getHeight() < halfHeight / 2) {
        var zone1 = new Zone(0, 0, halfWidth + picture.image.getWidth(),
            halfHeight + picture.image.getHeight());
        var zone2 = new Zone(halfWidth, 0, halfWidth,
            halfHeight + picture.image.getHeight());
        var zone3 = new Zone(0, halfHeight, halfWidth + picture.image.getWidth(),
            halfHeight);
        var zone4 = new Zone(halfWidth, halfHeight, halfWidth, halfHeight);
        return Utils.getFirstNonNull(Utils.submit(new LookAt(capture, zone1)),
            Utils.submit(new LookAt(capture, zone2)),
            Utils.submit(new LookAt(capture, zone3)),
            Utils.submit(new LookAt(capture, zone4)));
      } else {
        var zone = new Zone(0, 0, picture.image.getWidth(),
            picture.image.getHeight());
        return Utils.submit(new LookAt(capture, zone)).get();
      }
    }
  }

  @Override
  public String execute(Variables variables) throws Exception {
    if (maxDifference == null) {
      maxDifference = 0.0;
    }
    if (maxDifference < 0.0) {
      maxDifference = 0.0;
    }
    if (maxDifference > 100.0) {
      maxDifference = 100.0;
    }
    Local found = findLocal();
    if (found != null && saveAs != null) {
      switch (saveAs) {
        case TopLeft:
          break;
        case TopCenter:
          found.x = found.x + (picture.image.getWidth() / 2);
          break;
        case TopRight:
          found.x = found.x + picture.image.getWidth();
          break;
        case MiddleLeft:
          found.y = found.y + (picture.image.getHeight() / 2);
          break;
        case Center:
          found.y = found.y + (picture.image.getHeight() / 2);
          found.x = found.x + (picture.image.getWidth() / 2);
          break;
        case MiddleRight:
          found.y = found.y + (picture.image.getHeight() / 2);
          found.x = found.x + picture.image.getWidth();
          break;
        case BottomLeft:
          found.y = found.y + picture.image.getHeight();
          break;
        case BottomCenter:
          found.y = found.y + picture.image.getHeight();
          found.x = found.x + (picture.image.getWidth() / 2);
          break;
        case BottomRight:
          found.y = found.y + picture.image.getHeight();
          found.x = found.x + picture.image.getWidth();
          break;
      }
    }
    if (saveIn != null && !saveIn.isBlank()) {
      variables.set(saveIn, found);
    }
    if (found != null) {
      if (goToIfFound != null && !goToIfFound.isBlank()) {
        return goToIfFound;
      }
    } else {
      if (goToElse != null && !goToElse.isBlank()) {
        return goToElse;
      }
    }
    return "<next>";
  }

  private class LookAt implements Callable<Local> {

    private final BufferedImage capture;
    private final Zone zone;

    private int differencePotential;
    private double differenceMultiplier;

    public LookAt(BufferedImage capture, Zone zone) {
      this.capture = capture;
      this.zone = zone;
    }

    public boolean lookingAt(int x, int y) {
      int differenceTotal = 0;
      for (int pictureX = 0; pictureX < picture.image.getWidth(); pictureX++) {
        for (int pictureY = 0; pictureY < picture.image.getHeight(); pictureY++) {
          int captureX = pictureX + x;
          int captureY = pictureY + y;
          int pictureColor = picture.image.getRGB(pictureX, pictureY);
          int captureColor = capture.getRGB(captureX, captureY);
          int pictureBlue = (pictureColor) & 0xFF;
          int pictureGreen = (pictureColor >> 8) & 0xFF;
          int pictureRed = (pictureColor >> 16) & 0xFF;
          int captureBlue = (captureColor) & 0xFF;
          int captureGreen = (captureColor >> 8) & 0xFF;
          int captureRed = (captureColor >> 16) & 0xFF;
          int differenceBlue = Math.abs(pictureBlue - captureBlue);
          int differenceGreen = Math.abs(pictureGreen - captureGreen);
          int differenceRed = Math.abs(pictureRed - captureRed);
          int differencePunctual =
            differenceBlue + differenceGreen + differenceRed;
          differenceTotal += differencePunctual;
        }
      }
      double differencePercentual = differenceMultiplier * differenceTotal;
      if (differencePercentual <= maxDifference) {
        return true;
      } else {
        return false;
      }
    }

    @Override
    public Local call() throws Exception {
      differencePotential =
        picture.image.getWidth() * picture.image.getHeight() * 765;
      differenceMultiplier = 100.0 / differencePotential;
      int x = zone.x;
      int untilX = zone.x + (zone.width - picture.image.getWidth());
      while (true) {
        int y = zone.y;
        int untilY = zone.y + (zone.height - picture.image.getHeight());
        while (true) {
          if (lookingAt(x, y)) {
            return new Local(x, y);
          }
          y++;
          if (y >= untilY
              || y + picture.image.getHeight() >= capture.getHeight()) {
            break;
              }
        }
        x++;
        if (x >= untilX || x + picture.image.getWidth() >= capture.getWidth()) {
          break;
        }
      }
      return null;
    }

  }

}
