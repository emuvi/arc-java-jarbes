package pin.jarbes;

import java.util.Deque;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import pin.jarbox.Utils;

public class HeadEdit {

  private final DefaultListModel<Act> model;
  private final JList<Act> list;

  private volatile boolean hasChanges;
  private final Deque<Object> forUndo = new LinkedList<>();
  private final Deque<Object> forRedo = new LinkedList<>();

  public HeadEdit(DefaultListModel<Act> model, JList<Act> list) {
    this.model = model;
    this.list = list;
  }

  public synchronized void insert(Act act) throws Exception {
    if (act.name != null && !act.name.isBlank()) {
      for (int i = 0; i < model.size(); i++) {
        Act old = model.get(i);
        if (old.name != null && !old.name.isBlank()) {
          if (act.name.equals(old.name)) {
            throw new Exception("This name already exists.");
          }
        }
      }
    }
    insert(list.getSelectedIndex() + 1, act);
  }

  private void insert(int index, Act act) {
    model.add(index, act);
    list.setSelectedIndex(index);
    hasChanges = true;
    forUndo.addFirst(new InsertedUndo(index));
  }

  public synchronized void edit(Act act) throws Exception {
    int selected = list.getSelectedIndex();
    if (selected < 0) {
      throw new Exception("You must select an item do edit.");
    }
    if (act.name != null && !act.name.isBlank()) {
      for (int i = 0; i < model.size(); i++) {
        if (i != selected) {
          Act old = model.get(i);
          if (old.name != null && !old.name.isBlank()) {
            if (act.name.equals(old.name)) {
              throw new Exception("This name already exists.");
            }
          }
        }
      }
    }
    Act value = model.get(selected);
    model.set(selected, act);
    hasChanges = true;
    forUndo.addFirst(new EditedUndo(selected, value));
  }

  public synchronized void delete() {
    int[] selected = list.getSelectedIndices();
    if (selected.length > 0) {
      Act[] values = new Act[selected.length];
      for (int i = selected.length - 1; i >= 0; i--) {
        values[i] = model.get(selected[i]);
        model.remove(selected[i]);
      }
      list.setSelectedIndex(selected[0] - 1);
      hasChanges = true;
      forUndo.addFirst(new DeletedUndo(selected, values));
    }
  }

  public synchronized void moveUp() {
    int[] selected = list.getSelectedIndices();
    if (selected[0] > 0) {
      for (int index : selected) {
        moveUp(index);
      }
      list.getSelectionModel().clearSelection();
      for (int index : selected) {
        list.getSelectionModel().addSelectionInterval(index - 1, index - 1);
      }
      hasChanges = true;
      forUndo.addFirst(new MovedUpUndo(selected));
    }
  }

  private void moveUp(int index) {
    Act aux = model.get(index - 1);
    model.set(index - 1, model.get(index));
    model.set(index, aux);
  }

  public synchronized void moveDown() {
    int[] selected = list.getSelectedIndices();
    if (selected[selected.length - 1] < model.size() - 1) {
      for (int i = selected.length - 1; i >= 0; i--) {
        moveDown(selected[i]);
      }
      list.getSelectionModel().clearSelection();
      for (int index : selected) {
        list.getSelectionModel().addSelectionInterval(index + 1, index + 1);
      }
      hasChanges = true;
      forUndo.addFirst(new MovedDownUndo(selected));
    }
  }

  private void moveDown(int index) {
    Act aux = model.get(index + 1);
    model.set(index + 1, model.get(index));
    model.set(index, aux);
  }

  public synchronized void undo() {
    Object event = forUndo.pollFirst();
    if (event == null) {
      return;
    }
    if (event instanceof InsertedUndo) {
      var inserted = (InsertedUndo) event;
      Act aux = model.get(inserted.index);
      model.remove(inserted.index);
      forRedo.addFirst(new InsertedRedo(inserted.index, aux));
    } else if (event instanceof EditedUndo) {
      var edited = (EditedUndo) event;
      Act aux = model.get(edited.index);
      model.set(edited.index, edited.value);
      forRedo.addFirst(new EditedRedo(edited.index, aux));
    } else if (event instanceof DeletedUndo) {
      var deleted = (DeletedUndo) event;
      for (int i = 0; i < deleted.indices.length; i++) {
        model.add(deleted.indices[i], deleted.values[i]);
      }
      list.setSelectedIndices(deleted.indices);
      forRedo.addFirst(new DeletedRedo(deleted.indices));
    } else if (event instanceof MovedUpUndo) {
      var movedUp = (MovedUpUndo) event;
      for (int i = movedUp.indices.length - 1; i >= 0; i--) {
        moveDown(movedUp.indices[i] - 1);
      }
      list.setSelectedIndices(movedUp.indices);
      forRedo.addFirst(new MovedUpRedo(movedUp.indices));
    } else if (event instanceof MovedDownUndo) {
      var movedDown = (MovedDownUndo) event;
      for (int i = 0; i < movedDown.indices.length; i++) {
        moveUp(movedDown.indices[i] + 1);
      }
      list.setSelectedIndices(movedDown.indices);
      forRedo.addFirst(new MovedDownRedo(movedDown.indices));
    }
  }

  public synchronized void redo() {
    Object event = forRedo.pollFirst();
    if (event == null) {
      return;
    }
    if (event instanceof InsertedRedo) {
      var inserted = (InsertedRedo) event;
      insert(inserted.index, inserted.value);
    } else if (event instanceof EditedRedo) {
      var edited = (EditedRedo) event;
      list.setSelectedIndex(edited.index);
      try {
        edit(edited.value);
      } catch (Exception e) {
        Utils.treat(e);
      }
    } else if (event instanceof DeletedRedo) {
      var deleted = (DeletedRedo) event;
      list.setSelectedIndices(deleted.indices);
      delete();
    } else if (event instanceof MovedUpRedo) {
      var movedUp = (MovedUpRedo) event;
      list.setSelectedIndices(movedUp.indices);
      moveUp();
    } else if (event instanceof MovedDownRedo) {
      var movedDown = (MovedDownRedo) event;
      list.setSelectedIndices(movedDown.indices);
      moveDown();
    }
  }

  public synchronized boolean hasChanges() {
    return hasChanges;
  }

  public synchronized void savedChanges() {
    hasChanges = false;
  }

  public synchronized void clearHistory() {
    forUndo.clear();
    forRedo.clear();
  }

  public synchronized void clear() {
    model.clear();
    hasChanges = false;
    forUndo.clear();
    forRedo.clear();
  }

  private class InsertedUndo {
    public int index;
    
    public InsertedUndo(int index) {
      this.index = index;
    }
  }

  private class InsertedRedo {
    public int index;
    public Act value;
    
    public InsertedRedo(int index, Act value) {
      this.index = index;
      this.value = value;
    }
  }

  private class EditedUndo {
    public int index;
    public Act value;
    
    public EditedUndo(int index, Act value) {
      this.index = index;
      this.value = value;
    }
  }

  private class EditedRedo {
    public int index;
    public Act value;
    
    public EditedRedo(int index, Act value) {
      this.index = index;
      this.value = value;
    }
  }

  private class DeletedUndo {
    public int[] indices;
    public Act[] values;
    
    public DeletedUndo(int[] indices, Act[] values) {
      this.indices = indices;
      this.values = values;
    }
  }

  private class DeletedRedo {
    public int[] indices;
    
    public DeletedRedo(int[] indices) {
      this.indices = indices;
    }
  }

  private class MovedUpUndo {
    public int[] indices;
    
    public MovedUpUndo(int[] indices) {
      this.indices = indices;
    }
  }

  private class MovedUpRedo {
    public int[] indices;
    
    public MovedUpRedo(int[] indices) {
      this.indices = indices;
    }
  }

  private class MovedDownUndo {
    public int[] indices;
    
    public MovedDownUndo(int[] indices) {
      this.indices = indices;
    }
  }

  private class MovedDownRedo {
    public int[] indices;
    
    public MovedDownRedo(int[] indices) {
      this.indices = indices;
    }
  }

}
