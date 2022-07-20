package com.mmelo.financial.persistence.entity;

import com.mmelo.financial.persistence.enumerator.ProfileType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idt_profile")
    private Long id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileType name;

    @OneToOne(mappedBy = "profile")
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "profile")
    private List<ProfileRole> profileRoles;
}