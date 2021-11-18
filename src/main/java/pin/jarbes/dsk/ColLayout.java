package pin.jarbes.dsk;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;

public class ColLayout implements LayoutManager, Serializable {

  private static final long serialVersionUID = 5047248871663289220L;

  int _halign;
  int _valign;
  int _hgap;
  int _vgap;

  public final static int TOP = 0;

  public final static int CENTER = 1;

  public final static int BOTTOM = 2;

  public final static int LEFT = 3;

  public final static int RIGHT = 4;


  public ColLayout() {
    this(CENTER, CENTER, 5, 5);
  }


  public ColLayout(int horizontalAlignment, int verticalAlignment) {
    this(horizontalAlignment, verticalAlignment, 5, 5);
  }


  public ColLayout(int horizontalAlignment, int verticalAlignment,
      int horizontalGap, int verticalGap) {
    _hgap = horizontalGap;
    _vgap = verticalGap;
    setAlignment(horizontalAlignment, verticalAlignment);
  }

  public void setAlignment(int horizontal, int vertical) {
    _halign = horizontal;
    _valign = vertical;
  }

  public void setHgap(int hgap) {
    _hgap = hgap;
  }

  public void setVgap(int vgap) {
    _vgap = vgap;
  }


  public int getHorizontalAlignment() {
    return _halign;
  }

  public int getVerticalAlignment() {
    return _valign;
  }


  public int getHgap() {
    return _hgap;
  }


  public int getVgap() {
    return _vgap;
  }


  public void addLayoutComponent(String name, Component comp) {
  }


  public void removeLayoutComponent(Component comp) {
  }


  public Dimension preferredLayoutSize(Container target) {
    synchronized (target.getTreeLock()) {
      Dimension dim = new Dimension(0, 0);
      int numberMembers = target.getComponentCount();
      boolean firstVisibleComponent = true;
      for (int ii = 0; ii < numberMembers; ii++) {
        Component m = target.getComponent(ii);
        if (m.isVisible()) {
          Dimension d = m.getPreferredSize();
          dim.width = Math.max(dim.width, d.width);
          if (firstVisibleComponent) {
            firstVisibleComponent = false;
          } else {
            dim.height += _vgap;
          }
          dim.height += d.height;
        }
      }
      Insets insets = target.getInsets();
      dim.width += insets.left + insets.right + _hgap * 2;
      dim.height += insets.top + insets.bottom + _vgap * 2;
      return dim;
    }
  }

  public Dimension minimumLayoutSize(Container target) {
    synchronized (target.getTreeLock()) {
      Dimension dim = new Dimension(0, 0);
      int numberMembers = target.getComponentCount();
      boolean firstVisibleComponent = true;
      for (int ii = 0; ii < numberMembers; ii++) {
        Component m = target.getComponent(ii);
        if (m.isVisible()) {
          Dimension d = m.getPreferredSize();
          dim.width = Math.max(dim.width, d.width);
          if (firstVisibleComponent) {
            firstVisibleComponent = false;
          } else {
            dim.height += _vgap;
          }
          dim.height += d.height;
        }
      }
      Insets insets = target.getInsets();
      dim.width += insets.left + insets.right + _hgap * 2;
      dim.height += insets.top + insets.bottom + _vgap * 2;
      return dim;
    }
  }

  public void layoutContainer(Container target) {
    synchronized (target.getTreeLock()) {
      Insets insets = target.getInsets();
      int maxHeight = target.getHeight() - (insets.top + insets.bottom + _vgap * 2);
      int numberMembers = target.getComponentCount();
      int y = 0;
      Dimension preferredSize = preferredLayoutSize(target);
      Dimension targetSize = target.getSize();
      switch (_valign) {
        case TOP:
          y = insets.top;
          break;
        case CENTER:
          y = (targetSize.height - preferredSize.height) / 2;
          break;
        case BOTTOM:
          y = targetSize.height - preferredSize.height - insets.bottom;
          break;
      }
      for (int i = 0; i < numberMembers; i++) {
        Component m = target.getComponent(i);
        if (m.isVisible()) {
          Dimension d = m.getPreferredSize();
          m.setSize(d.width, d.height);
          if ((y + d.height) <= maxHeight) {
            if (y > 0) {
              y += _vgap;
            }
            int x = 0;
            switch (_halign) {
              case LEFT:
                x = insets.left;
                break;
              case CENTER:
                x = (targetSize.width - d.width) / 2;
                break;
              case RIGHT:
                x = targetSize.width - d.width - insets.right;
                break;
            }
            m.setLocation(x, y);
            y += d.getHeight();
          } else {
            break;
          }
        }
      }
    }
  }
}
