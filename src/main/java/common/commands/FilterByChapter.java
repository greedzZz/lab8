package common.commands;

import common.Holder;
import common.User;
import common.content.Chapter;
import javafx.util.Pair;

public class FilterByChapter extends Command {
    public FilterByChapter(User user, Chapter chapArg) {
        super(user);
        this.chapArg = chapArg;
    }

    @Override
    public Pair<String, Boolean> execute(Holder cm) {
        return cm.filterByChapter(chapArg);
    }
}
