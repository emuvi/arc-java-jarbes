package pin.jarbes;

import java.awt.FlowLayout;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pin.jarbox.Utils;

public class HelmInsert {

  private final HelmEditor owner;
  private final JFrame window = new JFrame("Insert");
  private final JPanel mainPanel = new JPanel();
  private final JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
  private final JLabel typeLabel = new JLabel("Type:");
  private final DefaultComboBoxModel<TypesAct> typeModel =
    new DefaultComboBoxModel<>();
  private final JComboBox<TypesAct> typeField = new JComboBox<>(typeModel);
  private final JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
  private final JLabel nameLabel = new JLabel("Name:");
  private final JTextField nameField = new JTextField(12);
  private final JPanel buttonsPanel =
    new JPanel(new FlowLayout(FlowLayout.LEFT));
  private final JButton confirmButton = new JButton("Confirm");
  private final JButton cancelButton = new JButton("Cancel");

  public HelmInsert(HelmEditor owner) throws Exception {
    this.owner = owner;
    window.setIconImage(
        ImageIO.read(HelmEditor.class.getResourceAsStream("jarbes.png")));
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    Utils.setNextLocationFor(window);
    window.setResizable(false);
    window.setContentPane(mainPanel);
    mainPanel.add(typePanel);
    mainPanel.add(namePanel);
    mainPanel.add(buttonsPanel);
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    typePanel.add(typeLabel);
    typePanel.add(typeField);
    namePanel.add(nameLabel);
    namePanel.add(nameField);
    Utils.setWidthMinAsPreferredMax(nameLabel, typeLabel);
    for (TypesAct actType : TypesAct.values()) {
      typeModel.addElement(actType);
    }
    buttonsPanel.add(confirmButton);
    buttonsPanel.add(cancelButton);
    confirmButton.addActionListener(event -> confirm());
    cancelButton.addActionListener(event -> cancel());
    window.getRootPane().setDefaultButton(confirmButton);
    window.pack();
    Utils.putShortCut(mainPanel, "Close", "ESCAPE", () -> cancel());
  }

  public void show() { window.setVisible(true); }

  private void confirm() {
    try {
      TypesAct typeAct = (TypesAct)typeField.getSelectedItem();
      Act act = typeAct.getType().getConstructor().newInstance();
      act.name = nameField.getText();
      owner.insert(act);
      window.setVisible(false);
    } catch (Exception e) {
      Utils.treat(e);
    }
  }

  private void cancel() { window.setVisible(false); }
}
