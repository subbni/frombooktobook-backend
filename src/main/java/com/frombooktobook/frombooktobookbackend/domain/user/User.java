package com.frombooktobook.frombooktobookbackend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frombooktobook.frombooktobookbackend.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    private String password;

    private String name;

    @Column(nullable = true)
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="provider_type")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    // private String providerId;

    @Builder
    public User(String email, String name, String password, String imgUrl, Role role, ProviderType providerType) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.imgUrl = imgUrl;
        this.role= role;
        this.providerType = providerType;
    }

    public User setName(String name) {
        this.name=name;
        return this;
    }

    public User setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public User update(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
