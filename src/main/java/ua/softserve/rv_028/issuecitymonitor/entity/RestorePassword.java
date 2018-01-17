package ua.softserve.rv_028.issuecitymonitor.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ua.softserve.rv_028.issuecitymonitor.entity.converter.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "restore_password")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestorePassword {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "token")
    String token;

    @Column(name = "expire_date")
    @Convert(converter = LocalDateTimeConverter.class)
    LocalDateTime expireDate;

    public RestorePassword(User user, String token, LocalDateTime expireDate) {
        this.user = user;
        this.token = token;
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "RestorePassword{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", token='" + token + '\'' +
                ", expireDate=" + expireDate +
                '}';
    }
}
