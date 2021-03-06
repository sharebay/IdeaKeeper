package lv.javaguru.java3.core.domain;

import javax.persistence.*;

@Deprecated
@Entity
@Table(name="clients")
public class Client {

//    @Id
//    @GeneratedValue(generator = "clients_seq", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "clients_seq", sequenceName = "clients_seq", allocationSize = 1)
//    @Column(name="id", nullable = false)

    @Id
    @Column(name="id", columnDefinition = "int(11)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="login", columnDefinition = "CHAR(50)", nullable = false)
    private String login;

    @Column(name="password", columnDefinition = "CHAR(50)", nullable = false)
    private String password;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
