package ua.softserve.rv_028.issuecitymonitor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.softserve.rv_028.issuecitymonitor.entity.*;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.*;

import javax.persistence.EntityManagerFactory;
import java.security.Principal;
import java.util.Date;
import java.util.Random;

import static ua.softserve.rv_028.issuecitymonitor.Constants.DATE_FORMAT;

@Component
public class DBSeeder {

    private SessionFactory sessionFactory;

    private static final Logger LOGGER = LogManager.getLogger(DBSeeder.class.getName());

    @Autowired
    public DBSeeder(EntityManagerFactory factory) {

        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        sessionFactory = factory.unwrap(SessionFactory.class);
        try {
            LOGGER.info("Seeding database...");
            fillDatabase();
            LOGGER.info("Seeding finished");
        } catch (RuntimeException e) {
            LOGGER.error("Seeding has been done already. Skipping...");
        }
    }

    private void fillDatabase() throws RuntimeException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        for (int i = 0; i < 10; i++) {

            User user = new User("Tom" + i, "Jerry" + i, i + "" + i + "" + i,
                    "tom" + i + "@mail.rv.ua", "+380997755331", r.nextBoolean(),
                    randomEnum(UserStatus.class), randomEnum(UserRole.class), "http://url.com" + i);
            session.save(user);

            for (int a = 0; a < 3; a++) {
                Event event = new Event(user, "Title" + a + "" + i, "description" + a + "" + i, date(),
                        r.nextDouble(), r.nextDouble(), date(), randomEnum(EventCategory.class));
                session.save(event);

                Petition petition = new Petition(user, "Title" + a + "" + i, "descript" + a + "" + i, date(),
                        randomEnum(PetitionCategory.class));
                session.save(petition);

                Issue issue = new Issue(user, "title" + a + "" + i, "desc" + a + "" + i, date(),
                        r.nextDouble(), r.nextDouble(), randomEnum(IssueCategory.class));
                session.save(issue);

                for (int j = 0; j < 5; j++) {
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

    private String date() {
        return DATE_FORMAT.format(new Date());
    }

    private <T extends Enum<?>> T randomEnum(Class<T> classname) {
        int x = r.nextInt(classname.getEnumConstants().length);
        return classname.getEnumConstants()[x];
    }
}