package pin.jarbes.dsk;

import javax.swing.JCheckBox;

public class EditBoolean extends Edit<Boolean> {


  private static final long serialVersionUID = -5164457947859196089L;

  private final JCheckBox field = new JCheckBox();

  public EditBoolean() {
    super();
    addMain(field);
  }

  @Override
  public Boolean getValue() {
    return field.isSelected();
  }

  @Override
  public void setValue(Boolean value) {
    field.setSelected(value != null ? value : false);
  }

}
