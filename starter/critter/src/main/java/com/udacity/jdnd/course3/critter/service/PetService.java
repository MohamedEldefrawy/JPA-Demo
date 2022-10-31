package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet createPet(PetDTO petDTO) {
        return this.petRepository.save(petDTO.toPet());
    }

    public Pet getPet(Long id) {
        Optional<Pet> petOptional = this.petRepository.findById(id);
        return petOptional.orElse(null);
    }

    public List<PetDTO> getPets() {
        List<PetDTO> pets = new ArrayList<>();
        this.petRepository.findAll().forEach(pet -> pets.add(pet.toPetDto()));
        return pets;
    }

    public List<PetDTO> getPetsByOwner(Long ownerId) {
        List<PetDTO> pets = new ArrayList<>();
        this.petRepository.findPetsByUser(this.customerRepository.findById(ownerId).get()).forEach(pet -> pets.add(pet.toPetDto()));
        return pets;
    }
}
