package com.frombooktobook.frombooktobookbackend.domain.user;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.frombooktobook.frombooktobookbackend.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role;

    @Builder
    public User(String email, String name, String picture, Role role) {
        this.email = email;
        this.name=name;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name= name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
