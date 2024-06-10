package com.dragonsky.teamup.member.repository;

import com.dragonsky.teamup.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    Boolean existsByEmail(String email);
}
