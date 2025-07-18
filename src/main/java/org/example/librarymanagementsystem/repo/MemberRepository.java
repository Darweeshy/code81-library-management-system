package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
