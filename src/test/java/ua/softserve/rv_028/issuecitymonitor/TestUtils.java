package ua.softserve.rv_028.issuecitymonitor;

import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

public final class TestUtils {

    public static Event createEvent(int i) {
        Event event = new Event();
        event.setTitle("event"+i);
        return event;
    }

    public static Issue createIssue(int i) {
        Issue issue = new Issue();
        issue.setTitle("issue"+i);
        return issue;
    }

    public static User createUser(int i) {
        User user = new User();
        user.setFirstName("user"+i);
        return user;
    }
}
