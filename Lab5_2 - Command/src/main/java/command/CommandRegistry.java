package command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandRegistry {

	private ObservableList<Command> commandStack = FXCollections
			.observableArrayList();


	private ObservableList<Command> undoStack = FXCollections
			.observableArrayList();

	public void executeCommand(Command command) {
		command.execute();
		commandStack.add(command);
		undoStack.clear();
	}

	public void redo() {
		if(!undoStack.isEmpty()){
			Command command = undoStack.getLast();
			command.redo();
			undoStack.remove(command);
		}
	}

	public void undo() {
		if(!commandStack.isEmpty()) {
			Command command = commandStack.getLast();
			command.undo();
			undoStack.add(command);
		}

	}

	public ObservableList<Command> getCommandStack() {
		return commandStack;
	}
}
