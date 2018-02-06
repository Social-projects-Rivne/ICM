package ua.softserve.rv_028.issuecitymonitor;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ua.softserve.rv_028.issuecitymonitor.entity.*;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.*;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Random;

@Component
@Log4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DBSeeder {

    SessionFactory sessionFactory;
    final BCryptPasswordEncoder encoder;

    @Autowired
    public DBSeeder(EntityManagerFactory factory, BCryptPasswordEncoder encoder) {
        this.encoder = encoder;

        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        sessionFactory = factory.unwrap(SessionFactory.class);
        try {
            log.info("Seeding database...");
            fillDatabase();
            log.info("Seeding finished");
        } catch (RuntimeException e) {
            log.info("Seeding has been done already. Skipping...");
        }
    }

    private void fillDatabase() throws RuntimeException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        User defaultAdmin = new User("Root", "Admin", encoder.encode("1234"), "admin@mail.com", "+380997755331",
                UserStatus.ACTIVE, UserRole.ADMIN, "http://url.com");

        session.save(defaultAdmin);

        for(int i=0; i < 10 ; i++){
            User user = new User("Tom"+i, "Jerry"+i,encoder.encode(i+""+i+""+i),
                    "tom"+i+"@mail.rv.ua","+380997755331", randomEnum(UserStatus.class),
                    randomEnum(UserRole.class),"http://url.com"+i);

            session.save(user);

            for (int a = 0; a < 3; a++) {
                Event event = new Event(user, "Title" + a + "" + i, "description" + a + "" + i, date(),
                        r.nextDouble(), r.nextDouble(), date(), randomEnum(EventCategory.class));
                session.save(event);

                Petition petition = new Petition(user, "Title" + a + "" + i, "descript" + a + "" + i, date(),
                        randomEnum(PetitionCategory.class));
                session.save(petition);

                Issue issue = new Issue(user, "title" + a + "" + i, "desc" + a + "" + i, date(),
                        gen(), gen2(), randomEnum(IssueCategory.class),
                        "first_img.jpg");
                session.save(issue);

                for (int j = 0; j < 5; j++) {

                    CommentIssue comment = new CommentIssue(issue, user, "body" + j + "" + a + "" + i, date());
                    session.save(comment);

                    EventAttachment eventAttachment = new EventAttachment(event, user, "url" + j + "" + a + "" + i);
                    session.save(eventAttachment);

                    EventChangeRecord eventChangeRecord = new EventChangeRecord(event,
                            randomEnum(ChangeRecordStatus.class), user, "msg" + j + "" + a + "" + i);
                    session.save(eventChangeRecord);

                    IssueAttachment issueAttachment = new IssueAttachment(issue, user, "url" + j + "" + a + "" + i);
                    session.save(issueAttachment);

                    IssueChangeRecord issueChangeRecord = new IssueChangeRecord(issue,
                            randomEnum(ChangeRecordStatus.class), user, "msg" + j + "" + a + "" + i);
                    session.save(issueChangeRecord);

                    PetitionAttachment petitionAttachment = new PetitionAttachment(petition, user,
                            "url" + j + "" + a + "" + i);
                    session.save(petitionAttachment);

                    PetitionChangeRecord petitionChangeRecord = new PetitionChangeRecord(petition,
                            randomEnum(ChangeRecordStatus.class), user, "msg" + j + "" + a + "" + i);
                    session.save(petitionChangeRecord);

                    UserVote userVote = new UserVote(user, petition);
                    session.save(userVote);
                }
            }
        }

        transaction.commit();
    }

    private static final Random r = new Random();

    private static LocalDateTime date() {
        return LocalDateTime.now();
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> classname) {
        int x = r.nextInt(classname.getEnumConstants().length);
        return classname.getEnumConstants()[x];
    }

    // coordinates of Rivne City (Up - Down)
    public double gen() {
        double leftLimit = 50.609D;
        double rightLimit = 50.633D;
        double generatedDouble = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);

        return generatedDouble;
    }

    // coordinates of Rivne City (Left - Right)
    public double gen2() {
        double leftLimit = 26.239D;
        double rightLimit = 26.272972D;
        double generatedDouble = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);

        return generatedDouble;
    }
}