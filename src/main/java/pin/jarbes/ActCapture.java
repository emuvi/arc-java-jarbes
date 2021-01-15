package pin.jarbes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import pin.jarbox.Utils;
import pin.jarbox.Variables;
import pin.jarbox.Zone;


public class ActCapture extends Act {

  private static final long serialVersionUID = -6028513373684052619L;

  public Zone zone;
  public Integer dpi;

  @Override
  public String execute(Variables variables) throws Exception {
    BufferedImage captured = Interact.capture(zone);
    Integer capturedCounter = 0;
    Object result = variables.get("<CapturedCounter>");
    if (result instanceof Integer) {
      capturedCounter = (Integer) result;
    }
    File saveIn = new File("captured");
    Files.createDirectories(saveIn.toPath());
    String name = Utils.fill(capturedCounter.toString(), '0', 4, true);
    File destiny = new File(saveIn, name + ".png");
    destiny.delete();
    saveImage(captured, destiny, dpi != null ? dpi : 300);
    variables.set("<CapturedCounter>", ++capturedCounter);
    return "<next>";
  }

  private void saveImage(BufferedImage image, File output, int dpi) throws IOException {
    String formatName = "png";
    Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName(formatName);
    iterator.hasNext();
    ImageWriter writer = iterator.next();
    ImageWriteParam writeParam = writer.getDefaultWriteParam();
    ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier
      .createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
    IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
    setDPI(metadata, dpi);
    final ImageOutputStream stream = ImageIO.createImageOutputStream(output);
    try {
      writer.setOutput(stream);
      writer.write(metadata, new IIOImage(image, null, metadata), writeParam);
    } finally {
      writer.dispose();
      stream.flush();
      stream.close();
    }
  }

  private void setDPI(IIOMetadata metadata, int dpi) throws IIOInvalidTreeException {
    double dotsPerMilli = 1.0 * dpi / 10 / 2.54f;
    IIOMetadataNode horizontal = new IIOMetadataNode("HorizontalPixelSize");
    horizontal.setAttribute("value", Double.toString(dotsPerMilli));
    IIOMetadataNode vertical = new IIOMetadataNode("VerticalPixelSize");
    vertical.setAttribute("value", Double.toString(dotsPerMilli));
    IIOMetadataNode dimension = new IIOMetadataNode("Dimension");
    dimension.appendChild(horizontal);
    dimension.appendChild(vertical);
    IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
    root.appendChild(dimension);
    metadata.mergeTree("javax_imageio_1.0", root);
  }

}
