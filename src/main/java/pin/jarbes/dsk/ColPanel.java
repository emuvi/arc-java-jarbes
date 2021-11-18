package pin.jarbes.dsk;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Box;

public class ColPanel extends Panel {

  private final GridBagConstraints constraints;

  public ColPanel() {
    this(false);
  }

  public ColPanel(boolean scrollable) {
    super(scrollable, new GridBagLayout());
    constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.NORTHWEST;
    constraints.insets = new Insets(0, 0, 2, 0);
    constraints.gridx = 0;
    constraints.gridy = 0;
  }

  public ColPanel(Component... components) {
    this(false, components);
  }

  public ColPanel(boolean scrollable, Component... components) {
    this(scrollable);
    for (Component component : components) {
      add(component);
    }
    close();
  }

  @Override
  public ColPanel add(Component component) {
    constraints.gridy++;
    constraints.fill = GridBagConstraints.NONE;
    constraints.weightx = 0;
    constraints.weighty = 0;
    panel.add(component, constraints);
    return this;
  }

  @Override
  public ColPanel addGrow(Component component, int weight) {
    constraints.gridy++;
    constraints.fill = GridBagConstraints.VERTICAL;
    constraints.weightx = 0;
    constraints.weighty = weight;
    panel.add(component, constraints);
    return this;
  }

  @Override
  public ColPanel addGrow(Component component) {
    return addGrow(component, 1);
  }

  @Override
  public ColPanel addCross(Component component, int weight) {
    constraints.gridy++;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.weightx = weight;
    constraints.weighty = 0;
    panel.add(component, constraints);
    return this;
  }

  @Override
  public ColPanel addCross(Component component) {
    return addCross(component, 1);
  }

  @Override
  public ColPanel addMax(Component component, int weight) {
    constraints.gridy++;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1;
    constraints.weighty = weight;
    panel.add(component, constraints);
    return this;
  }

  @Override
  public ColPanel addMax(Component component) {
    return addMax(component, 1);
  }

  @Override
  public ColPanel close() {
    return addMax(Box.createVerticalGlue());
  }
}
