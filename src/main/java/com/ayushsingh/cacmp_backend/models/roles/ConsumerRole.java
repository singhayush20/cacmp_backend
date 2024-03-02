package com.ayushsingh.cacmp_backend.models.roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

import com.ayushsingh.cacmp_backend.models.entities.Consumer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "consumer_role")
public class ConsumerRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role", nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<Consumer> consumers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumerRole that = (ConsumerRole) o;
        return Objects.equals(roleId, that.roleId) && Objects.equals(roleName, that.roleName) && Objects.equals(consumers, that.consumers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }
}
