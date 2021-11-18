package pin.jarbes.dsk;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Box;

public class RowPanel extends Panel {

  private final GridBagConstraints constraints;

  public RowPanel() {
    this(false);
  }

  public RowPanel(boolean scrollable) {
    super(scrollable, new GridBagLayout());
    constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.NORTHWEST;
    constraints.insets = new Insets(0, 0, 0, 2);
    constraints.gridx = 0;
    constraints.gridy = 0;
  }

  public RowPanel(Component... components) {
    this(false, components);
  }

  public RowPanel(boolean scrollable, Component... components) {
    this(scrollable);
    for (Component component : components) {
      add(component);
    }
    close();
  }

  @Override
  public RowPanel add(Component component) {
    constraints.gridx++;
    constraints.fill = GridBagConstraints.NONE;
    constraints.weightx = 0;
    constraints.weighty = 0;
    panel.add(component, constraints);
    return this;
  }

  @Override
  public RowPanel addGrow(Component component, int weight) {
    constraints.gridx++;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.weightx = weight;
    constraints.weighty = 0;
    panel.add(component, constraints);
    return this;
  }

  @Override
  public RowPanel addGrow(Component component) {
    return addGrow(component, 1);
  }

  @Override
  public RowPanel addCross(Component component, int weight) {
    constraints.gridx++;
    constraints.fill = GridBagConstraints.VERTICAL;
    constraints.weightx = 0;
    constraints.weighty = weight;
    panel.add(component, constraints);
    return this;
  }

  @Override
  public RowPanel addCross(Component component) {
    return addCross(component, 1);
  }

  @Override
  public RowPanel addMax(Component component, int weight) {
    constraints.gridx++;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = weight;
    constraints.weighty = 1;
    panel.add(component, constraints);
    return this;
  }

  @Override
  public RowPanel addMax(Component component) {
    return addMax(component, 1);
  }

  @Override
  public RowPanel close() {
    return addMax(Box.createHorizontalGlue());
  }
}
