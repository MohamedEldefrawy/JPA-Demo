package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet createPet(PetDTO petDTO) {
        return this.petRepository.save(petDTO.toPet());
    }

    public Pet getPet(Long id) {
        Optional<Pet> petOptional = this.petRepository.findById(id);
        return petOptional.orElse(null);
    }
}
