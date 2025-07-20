package org.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "publishers")
public class Publisher extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    private Set<Book> books;
}
