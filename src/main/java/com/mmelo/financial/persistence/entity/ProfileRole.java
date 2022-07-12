package com.mmelo.financial.persistence.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "profile_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"idt_profile", "idt_role"})})
public class ProfileRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idt_profile_role")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idt_profile")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "idt_role")
    private Role role;

    @Override
    public String getAuthority() {
        return role.getName();
    }
}