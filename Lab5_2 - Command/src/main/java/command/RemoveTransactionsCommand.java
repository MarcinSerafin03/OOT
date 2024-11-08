package command;

import model.Account;
import model.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveTransactionsCommand implements Command {
    private List<Transaction> transactionsToRemove;
    private Account account;

    public RemoveTransactionsCommand(List<Transaction> transactionsToRemove, Account account) {
        this.transactionsToRemove = transactionsToRemove;
        this.account = account;
    }

    @Override
    public void execute() {
        for(Transaction transaction : transactionsToRemove) {
            account.removeTransaction(transaction);
        }
    }

    @Override
    public String getName() {
        return "Remove transaction: " + transactionsToRemove.toString();
    }

    @Override
    public void undo() {
        for(Transaction transaction : transactionsToRemove) {
            account.addTransaction(transaction);
        }
    }

    @Override
    public void redo() {
        for(Transaction transaction : transactionsToRemove) {
            account.removeTransaction(transaction);
        }
    }
}
