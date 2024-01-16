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
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "parking_spot")
@EntityListeners(AuditingEntityListener.class)
public class ParkingSpot implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", nullable = false, unique = true,length = 4)
    private String code;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusParkingSpot status;
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

    public enum StatusParkingSpot {
        FREE, BUSY
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
