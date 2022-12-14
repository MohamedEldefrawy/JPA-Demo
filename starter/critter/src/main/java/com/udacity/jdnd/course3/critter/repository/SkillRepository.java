package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findByName(String name);
}
