package pin.jarbes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileNameExtensionFilter;
import pin.jarbes.dsk.Icons;
import pin.jarbox.wzd.WzdDesk;
import pin.jarbox.wzd.WzdLog;


public class HelmEditor {

  private final HelmMain owner;
  private final JFrame window = new JFrame("Actions");
  private final DefaultListModel<Act> actionsModel = new DefaultListModel<>();
  private final JList<Act> actionsList = new JList<>(actionsModel);
  private final JScrollPane actionsScroll = new JScrollPane(actionsList);
  private final HeadEdit editor = new HeadEdit(actionsModel, actionsList);
  private final JPanel upPanel =
    new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
  private final JButton newButton = Icons.buttonNew(e -> menuNew());
  private final JButton openButton = Icons.buttonOpen(e -> menuOpen());
  private final JButton saveButton = Icons.buttonSave(e -> menuSave());
  private final JButton insertButton = Icons.buttonInsert(e -> menuInsert());
  private final JButton editButton = Icons.buttonEdit(e -> menuEdit());
  private final JButton deleteButton = Icons.buttonDelete(e -> menuDelete());
  private final JPanel downPanel =
    new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
  private final JButton findButton = Icons.buttonFind(e -> menuFind());
  private final JButton upButton = Icons.buttonUp(e -> menuUp());
  private final JButton downButton = Icons.buttonDown(e -> menuDown());
  private final JButton undoButton = Icons.buttonUndo(e -> menuUndo());
  private final JButton redoButton = Icons.buttonRedo(e -> menuRedo());
  private final JButton confirmButton = Icons.buttonConfirm(e -> menuConfirm());
  private final JButton cancelButton = Icons.buttonCancel(e -> menuCancel());
  private final JPanel mainPanel = new JPanel(new BorderLayout());

  private static File selected = new File("actions.jbs");
  private final JFileChooser chooser = new JFileChooser();

  private String finding = "";

  public HelmEditor(HelmMain owner) throws Exception {
    this.owner = owner;
    window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    window.setLocation(owner.getWindow().getX(),
        owner.getWindow().getY() +
        owner.getWindow().getHeight());
    window.setIconImage(
        ImageIO.read(HelmEditor.class.getResourceAsStream("jarbes.png")));
    window.addWindowListener(new WindowHandler());
    window.setContentPane(mainPanel);
    actionsScroll.setPreferredSize(new Dimension(360, 450));
    upPanel.add(newButton);
    upPanel.add(openButton);
    upPanel.add(saveButton);
    upPanel.add(new JSeparator(JSeparator.VERTICAL));
    upPanel.add(insertButton);
    upPanel.add(editButton);
    upPanel.add(deleteButton);
    newButton.setToolTipText("New (control N)");
    openButton.setToolTipText("Open (control O)");
    saveButton.setToolTipText("Save (control S)");
    insertButton.setToolTipText("Insert (I | control (I | Insert))");
    editButton.setToolTipText("Edit (E | Space | control (E | Space))");
    deleteButton.setToolTipText("Delete (D | control (D | Delete))");
    downPanel.add(findButton);
    downPanel.add(new JSeparator(JSeparator.VERTICAL));
    downPanel.add(upButton);
    downPanel.add(downButton);
    downPanel.add(new JSeparator(JSeparator.VERTICAL));
    downPanel.add(undoButton);
    downPanel.add(redoButton);
    downPanel.add(new JSeparator(JSeparator.VERTICAL));
    downPanel.add(confirmButton);
    downPanel.add(cancelButton);
    findButton.setToolTipText("Find (F | control F)");
    upButton.setToolTipText("Move Up (U | control alt UP)");
    downButton.setToolTipText("Move Down (O | control alt DOWN)");
    undoButton.setToolTipText("Undo (Z | control Z)");
    redoButton.setToolTipText("Redo (R | control R)");
    mainPanel.add(upPanel, BorderLayout.NORTH);
    mainPanel.add(actionsScroll, BorderLayout.CENTER);
    mainPanel.add(downPanel, BorderLayout.SOUTH);
    actionsList.setDragEnabled(true);
    actionsList.setTransferHandler(new ActionsTransferHandler());
    window.getRootPane().setDefaultButton(confirmButton);
    window.pack();
    chooser.setFileFilter(new FileNameExtensionFilter("Jarbes (*.jbs)", "jbs"));
    chooser.setSelectedFile(selected);
    initKeyShortcuts();
    initMouseHandler();
  }

  private void initKeyShortcuts() {
    WzdDesk.putShortCut(mainPanel, "MenuNew", "control N", () -> menuNew());
    WzdDesk.putShortCut(mainPanel, "MenuOpen", "control O", () -> menuOpen());
    WzdDesk.putShortCut(mainPanel, "MenuSave", "control S", () -> menuSave());
    WzdDesk.putShortCut(mainPanel, "MenuCopyName", "control alt N",
        () -> copyName());
    WzdDesk.putShortCut(mainPanel, "MenuCopyIndex", "control alt I",
        () -> copyIndex());
    WzdDesk.putShortCut(mainPanel, "MenuInsertChar", "I", () -> menuInsert());
    WzdDesk.putShortCut(mainPanel, "MenuInsertControlChar", "control I",
        () -> menuInsert());
    WzdDesk.putShortCut(mainPanel, "MenuInsert", "control INSERT",
        () -> menuInsert());
    WzdDesk.putShortCut(mainPanel, "MenuEditChar", "E", () -> menuEdit());
    WzdDesk.putShortCut(mainPanel, "MenuEditControlChar", "control E",
        () -> menuEdit());
    WzdDesk.putShortCut(mainPanel, "MenuEditSpace", "SPACE", () -> menuEdit());
    WzdDesk.putShortCut(mainPanel, "MenuEdit", "control SPACE", () -> menuEdit());
    WzdDesk.putShortCut(mainPanel, "MenuDeleteChar", "D", () -> menuDelete());
    WzdDesk.putShortCut(mainPanel, "MenuDeleteControlChar", "control D",
        () -> menuDelete());
    WzdDesk.putShortCut(mainPanel, "MenuDelete", "control DELETE",
        () -> menuDelete());
    WzdDesk.putShortCut(mainPanel, "MenuListChar", "L",
        () -> actionsList.requestFocus());
    WzdDesk.putShortCut(mainPanel, "MenuList", "control L",
        () -> actionsList.requestFocus());
    WzdDesk.putShortCut(mainPanel, "MenuFindChar", "F", () -> menuFind());
    WzdDesk.putShortCut(mainPanel, "MenuFind", "control F", () -> menuFind());
    WzdDesk.putShortCut(mainPanel, "MenuFindNextChar", "G", () -> findNext());
    WzdDesk.putShortCut(mainPanel, "MenuFindNextControlChar", "control G",
        () -> findNext());
    WzdDesk.putShortCut(mainPanel, "MenuFindNext", "F3", () -> findNext());
    WzdDesk.putShortCut(mainPanel, "MenuUpChar", "U", () -> menuUp());
    WzdDesk.putShortCut(mainPanel, "MenuUp", "control alt UP", () -> menuUp());
    WzdDesk.putShortCut(mainPanel, "MenuDownChar", "O", () -> menuDown());
    WzdDesk.putShortCut(mainPanel, "MenuDown", "control alt DOWN",
        () -> menuDown());
    WzdDesk.putShortCut(mainPanel, "MenuUndoChar", "Z", () -> menuUndo());
    WzdDesk.putShortCut(mainPanel, "MenuUndo", "control Z", () -> menuUndo());
    WzdDesk.putShortCut(mainPanel, "MenuRedoChar", "R", () -> menuRedo());
    WzdDesk.putShortCut(mainPanel, "MenuRedo", "control R", () -> menuRedo());
    WzdDesk.putShortCut(mainPanel, "Close", "ESCAPE", () -> close());
  }

  private void initMouseHandler() {
    actionsList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() >= 2) {
          if (e.isAltDown()) {
            Act selected = actionsList.getSelectedValue();
            if (selected != null) {
              WzdDesk.copyToClipboard(selected.name);
            }
          } else {
            menuEdit();
          }
        }
      }
    });
  }

  public void loadAndShow(HeadActs actions) {
    for (int i = 0; i < actions.size(); i++) {
      actionsModel.addElement(actions.get(i));
    }
    window.setVisible(true);
  }

  public void close() {
    if (!editor.hasChanges()) {
      window.setVisible(false);
      window.dispose();
    } else if (JOptionPane.showConfirmDialog(
          window, "Editor has changes. Do you really wanna close?",
          "Jarbes",
          JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
      window.setVisible(false);
      window.dispose();
          }
  }

  public JFrame getWindow() { return window; }

  public void insert(Act act) throws Exception { editor.insert(act); }

  public void edit(Act act) throws Exception { editor.edit(act); }

  private void menuNew() {
    if (WzdDesk.question("Do you really wanna clear the actions?")) {
      editor.clear();
    }
  }

  private void menuOpen() {
    if (editor.hasChanges() &&
        !WzdDesk.question("The editor has changes, do you wanna continue?")) {
      return;
        }
    if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
      load(chooser.getSelectedFile());
    }
  }

  private void load(File file) {
    try {
      ListActs actions = HeadActs.bring(file);
      editor.clear();
      actionsModel.addAll(actions);
      selected = file;
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void menuSave() {
    if (chooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
      save(chooser.getSelectedFile());
    }
  }

  private void save(File file) {
    try {
      if (!file.getName().toLowerCase().endsWith(".jbs")) {
        file = new File(file.getParentFile(), file.getName() + ".jbs");
      }
      ListActs actions = new ListActs();
      for (int i = 0; i < actionsModel.size(); i++) {
        actions.add(actionsModel.get(i));
      }
      selected = file;
      HeadActs.carry(actions, selected);
      chooser.setSelectedFile(selected);
      editor.savedChanges();
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void copyIndex() {
    try {
      Integer index = actionsList.getSelectedIndex();
      if (index < 0) {
        throw new Exception("There's no item selected to copy the index.");
      }
      WzdDesk.copyToClipboard(index.toString());
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void copyName() {
    try {
      Act selected = actionsList.getSelectedValue();
      if (selected == null) {
        throw new Exception("There's no item selected to copy the name.");
      }
      WzdDesk.copyToClipboard(selected.name);
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void menuInsert() {
    try {
      new HelmInsert(this).show();
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void menuEdit() {
    try {
      Act selected = actionsList.getSelectedValue();
      if (selected != null) {
        Class<? extends HelmAct<?>> helmClass =
          TypesAct.getHelmClass(selected.getClass());
        helmClass.getConstructor(this.getClass(), selected.getClass())
          .newInstance(this, selected)
          .show();
      }
    } catch (Exception e) {
      WzdLog.treat(e);
    }
  }

  private void menuDelete() { editor.delete(); }

  private void menuFind() {
    var aux =
      JOptionPane.showInputDialog(WzdDesk.getActiveWindow(), "Find:", finding);
    if (aux != null && !aux.isBlank()) {
      finding = aux.trim().toLowerCase();
      findNext();
    }
  }

  private void findNext() {
    int actual = actionsList.getSelectedIndex();
    var found = false;
    for (int i = actual + 1; i < actionsModel.size(); i++) {
      var value = actionsModel.get(i).toString().toLowerCase();
      if (value.contains(finding)) {
        actionsList.setSelectedIndex(i);
        found = true;
        break;
      }
    }
    if (!found) {
      for (int i = 0; i < actual; i++) {
        var value = actionsModel.get(i).toString().toLowerCase();
        if (value.contains(finding)) {
          actionsList.setSelectedIndex(i);
          break;
        }
      }
    }
  }

  private void menuUp() { editor.moveUp(); }

  private void menuDown() { editor.moveDown(); }

  private void menuUndo() { editor.undo(); }

  private void menuRedo() { editor.redo(); }

  private void menuConfirm() {
    ListActs actions = new ListActs();
    for (int i = 0; i < actionsModel.size(); i++) {
      actions.add(actionsModel.get(i));
    }
    owner.set(actions);
    window.setVisible(false);
  }

  private void menuCancel() {
    if (editor.hasChanges() &&
        !WzdDesk.question("The editor has changes, do you wanna continue?")) {
      return;
        }
    window.setVisible(false);
  }

  private class ActionsTransferHandler extends TransferHandler {

    private static final long serialVersionUID = -3844326672544136707L;

    @Override
    public int getSourceActions(JComponent c) {
      return TransferHandler.COPY;
    }

    @Override
    protected Transferable createTransferable(JComponent source) {
      Act value = actionsList.getSelectedValue();
      Transferable result =
        new StringSelection(value != null ? value.name : "null");
      return result;
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
      return false;
    }
  }

  private class WindowHandler extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
      close();
    }
  }
}
