package br.com.ero.demoparkapi.config.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@EntityListeners(AuditingEntityListener.class)
@Table(name = "clients")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;
    @OneToOne
    @JoinColumn(name = "id_user",nullable = false)
    private User user;
    @CreatedDate
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @LastModifiedDate
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;
    @CreatedBy
    @Column(name = "create_by")
    private String createBy;
    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
