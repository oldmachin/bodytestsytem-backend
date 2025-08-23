package org.example.model.user;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String workId;

    private String name;

    @Column(name = "user_id", unique = true)
    private Long userId;

    public Teacher(String workId, String name, Long userId) {
        this.workId = workId;
        this.name = name;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getWorkId() {
        return workId;
    }

    public String getName() {
        return name;
    }

    public Long getUserid() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserid(Long userId) {
        this.userId = userId;
    }
}
