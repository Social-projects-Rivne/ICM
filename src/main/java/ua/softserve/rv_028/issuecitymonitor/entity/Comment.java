package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.jni.Local;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    @Column(name = "id", unique = true)
    Long id;

    @Column
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "body")
    String body;

    @Column(name = "initial_date")
    LocalDateTime initialDate;
}
