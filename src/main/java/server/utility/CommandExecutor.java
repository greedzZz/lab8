package server.utility;

import common.Reply;
import common.commands.Command;
import common.commands.Show;
import javafx.util.Pair;
import server.CollectionManager;
import server.Server;

import java.sql.SQLException;

public class CommandExecutor implements Runnable {
    private final Command command;
    private final Server server;
    private final CollectionManager collectionManager;
    private final UserValidator userValidator;

    public CommandExecutor(Command command, Server server, CollectionManager collectionManager, UserValidator userValidator) {
        this.command = command;
        this.server = server;
        this.collectionManager = collectionManager;
        this.userValidator = userValidator;
    }

    @Override
    public void run() {
        try {
            if (userValidator.validate(command.getUser())) {
                if (command instanceof Show) {
                    server.sendAnswer(new Reply(collectionManager.getTreeMap(), true, "Authorization was successful."));
                } else {
                    Pair<String, Boolean> answer = server.executeCommand(command, collectionManager);
                    new Thread(() -> server.sendAnswer(new Reply(collectionManager.getTreeMap(), answer.getValue(), answer.getKey()))).start();
                }
            } else if (command.getUser().isNewbie()) {
                server.sendAnswer(new Reply(null, false, "A user with this login is already registered."));
            } else {
                server.sendAnswer(new Reply(null, false, "Sorry, the login/password is incorrect."));
            }
        } catch (SQLException s) {
            server.sendAnswer(new Reply(null, false, "A database access error has occurred or connection has closed."));
        }
    }
}
