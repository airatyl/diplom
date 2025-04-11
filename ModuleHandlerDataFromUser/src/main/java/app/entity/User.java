package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "user_sch")
public class User {
    @Id
    @ColumnDefault("nextval('user_sch.users_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "firstname", nullable = false, length = 30)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 30)
    private String lastname;

    @Column(name = "login", nullable = false, length = 30)
    private String login;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department", nullable = false)
    private Department department;

}