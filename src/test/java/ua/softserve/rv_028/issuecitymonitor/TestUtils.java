package ua.softserve.rv_028.issuecitymonitor;

import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.EventCategory;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.IssueCategory;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TestUtils {

    public static Event createEvent(User owner, int i) {
        return new Event(owner, "Title" + i, "description"+i, LocalDateTime.now(),
                0.0, 0.0, LocalDateTime.now(), EventCategory.CAT1);
    }

    public static Issue createIssue(User owner, int i) {
        return new Issue(owner, "Title" + i, "description" + i, LocalDateTime.now(),
                0.0, 0.0, IssueCategory.CAT1);
    }

    public static User createUser(int i) {
        return new User("User"+i, "Jerry"+i,"000",
                "mail","+380997755331",true,
                UserStatus.ACTIVE,UserRole.USER,"url");
    }

    public static List<Event> createEventsList(User owner, int size) {
        return IntStream.range(0, size).mapToObj(i -> createEvent(owner, size)).collect(Collectors.toList());
    }

    public static List<Issue> createIssuesList(User owner, int size) {
        return IntStream.range(0, size).mapToObj(i -> createIssue(owner, size)).collect(Collectors.toList());
    }

    public static List<User> createUsersList(User owner, int size) {
        return IntStream.range(0, size).mapToObj(i -> createUser(size)).collect(Collectors.toList());
    }
}
