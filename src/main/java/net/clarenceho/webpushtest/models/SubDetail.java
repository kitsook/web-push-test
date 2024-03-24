package net.clarenceho.webpushtest.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sub_details")
public class SubDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "endpoint", unique=true, length = 8000)
    private String endpoint;

    @Column(name = "p256h")
    private String p256h;

    @Column(name = "auth")
    private String auth;

    public SubDetail() {
        // empty
    }

    public SubDetail(@Nullable Long id, String endpoint, String p256h, String auth) {
        this.id = id;
        this.endpoint = endpoint;
        this.p256h = p256h;
        this.auth = auth;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public String getAuth() {
        return this.auth;
    }

    public String getP256h() {
        return this.p256h;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setP256h(String p256h) {
        this.p256h = p256h;
    }
}
