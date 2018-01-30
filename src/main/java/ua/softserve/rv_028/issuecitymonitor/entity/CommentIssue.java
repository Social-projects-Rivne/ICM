package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ua.softserve.rv_028.issuecitymonitor.entity.converter.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    Issue issue;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "body")
    String body;

    @Column(name = "initial_date")
    @Convert(converter = LocalDateTimeConverter.class)
    LocalDateTime initialDate;

    public Comment(Issue issue, User user, String body, LocalDateTime initialDate){
        this.issue = issue;
        this.user = user;
        this.body = body;
        this.initialDate = initialDate;
    }
}
