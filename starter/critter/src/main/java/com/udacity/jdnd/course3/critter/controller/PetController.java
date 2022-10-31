package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetDTO> savePet(@RequestBody PetDTO petDTO) {
        Pet createdPet = this.petService.createPet(petDTO);
        if (createdPet == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(createdPet.toPetDto(), HttpStatus.CREATED);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable long petId) {

        Pet selectedPet = this.petService.getPet(petId);
        if (selectedPet == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(selectedPet.toPetDto(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getPets() {
        List<PetDTO> pets = this.petService.getPets();
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDTO>> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> pets = this.petService.getPetsByOwner(ownerId);
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }
}
