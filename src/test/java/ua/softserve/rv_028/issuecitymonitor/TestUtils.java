package ua.softserve.rv_028.issuecitymonitor;

import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TestUtils {

    public static Event createEvent(User owner, int i) {
        return new Event(owner, "Title" + i, "description"+i, new Date().toString(),
                0.0, 0.0, new Date().toString(), EventCategory.CAT1);
    }

    public static Issue createIssue(User owner, int i) {
        return new Issue(owner, "Title" + i, "description" + i, new Date().toString(),
                0.0, 0.0, IssueCategory.CAT1);
    }

    public static Petition createPetition(User owner, int i) {
        return new Petition(owner, "Title" + i, "description" + i, new Date().toString(),
                PetitionCategory.CAT1);
    }

    public static User createAdmin(int i) {
        return new User("User"+i, "Jerry"+i,"000",
                "mail"+i,"+380997755331",true,
                UserStatus.ACTIVE,UserRole.ADMIN,"url");
    }

    public static User createUser(int i) {
        return new User("User"+i, "Jerry"+i,"000",
                "mail"+i,"+380997755331",true,
                UserStatus.ACTIVE,UserRole.USER,"url");
    }

    public static List<Event> createEventsList(User owner, int size) {
        return IntStream.range(0, size).mapToObj(i -> createEvent(owner, i)).collect(Collectors.toList());
    }

    public static List<Issue> createIssuesList(User owner, int size) {
        return IntStream.range(0, size).mapToObj(i -> createIssue(owner, i)).collect(Collectors.toList());
    }

    public static List<Petition> createPetitionsList(User owner, int size) {
        return IntStream.range(0, size).mapToObj(i -> createPetition(owner, i)).collect(Collectors.toList());
    }

    public static List<User> createUsersList(int size) {
        return IntStream.range(0, size).mapToObj(TestUtils::createUser).collect(Collectors.toList());
    }
}