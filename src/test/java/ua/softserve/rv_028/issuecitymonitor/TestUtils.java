package ua.softserve.rv_028.issuecitymonitor;

import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Event;
import ua.softserve.rv_028.issuecitymonitor.entity.Issue;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TestUtils {
    public static final String TITLE = "Title";
    public static final String DESCRIPTION = "Description";
    public static final String USER_FNAME = "User";
    public static final String USER_LNAME = "Jerry";
    public static final String USER_EMAIL = "mail@mail.ua";

    public static Event createEvent(User owner, int i) {
        return new Event(owner, TITLE + i, DESCRIPTION + i, LocalDateTime.now(),
                0.0, 0.0, LocalDateTime.now(), EventCategory.CAT1);
    }

    public static Issue createIssue(User owner, int i) {
        return new Issue(owner, TITLE + i, DESCRIPTION + i, LocalDateTime.now(),
                0.0, 0.0, IssueCategory.CAT1, "first_img");
    }

    public static Petition createPetition(User owner, int i) {
        return new Petition(owner, TITLE + i, DESCRIPTION + i, LocalDateTime.now(),
                PetitionCategory.CAT1);
    }

    public static User createUser(int i) {
        return new User(USER_FNAME + i, USER_LNAME + i,"000",
                UUID.randomUUID() + USER_EMAIL + i,"+380997755331", UserStatus.ACTIVE, UserRole.USER, "url");
    }

    public static UserDto createUserDto(int i) {
        return new UserDto(USER_FNAME + i, USER_LNAME + i, USER_EMAIL + i, "000");
    }

    public static User createAdmin(int i) {
        return new User(USER_FNAME + i, USER_LNAME + i,"000",
                UUID.randomUUID() + USER_EMAIL + i,"+380997755331", UserStatus.ACTIVE, UserRole.ADMIN, "url");
    }

    public static UserDto createAdminDto(int i) {
        return new UserDto(USER_FNAME + i, USER_LNAME + i, USER_EMAIL + i, "000");
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
