package ua.softserve.rv_028.issuecitymonitor;

import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static List<Event> createEventsList(int size) {
        return IntStream.range(0, size).mapToObj(TestUtils::createEvent).collect(Collectors.toList());
    }

    public static List<Issue> createIssuesList(int size) {
        return IntStream.range(0, size).mapToObj(TestUtils::createIssue).collect(Collectors.toList());
    }

    public static List<User> createUsersList(int size) {
        return IntStream.range(0, size).mapToObj(TestUtils::createUser).collect(Collectors.toList());
    }
}
