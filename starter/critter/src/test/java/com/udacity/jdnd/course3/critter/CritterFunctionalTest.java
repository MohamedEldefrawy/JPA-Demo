package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.controller.PetController;
import com.udacity.jdnd.course3.critter.controller.ScheduleController;
import com.udacity.jdnd.course3.critter.controller.UserController;
import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.pet.PetType;
import com.udacity.jdnd.course3.critter.entity.schedule.Day;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import com.udacity.jdnd.course3.critter.entity.user.Employee;
import com.udacity.jdnd.course3.critter.repository.DayRepository;
import com.udacity.jdnd.course3.critter.repository.SkillRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is a set of functional tests to validate the basic capabilities desired for this application.
 * Students will need to configure the application to run these tests by adding application.properties file
 * to the test/resources directory that specifies the datasource. It can run using an in-memory H2 instance
 * and should not try to re-use the same datasource used by the rest of the app.
 * These tests should all pass once the project is complete.
 */
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = CritterApplication.class)
public class CritterFunctionalTest {

    @Autowired
    private UserController userController;

    @Autowired
    private PetController petController;

    @Autowired
    private ScheduleController scheduleController;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private SkillRepository skillRepository;

    private List<Skill> skills;
    private List<Day> days;

    @BeforeAll
    public void initializeSkillsAndDays() {
        this.skills = this.skillRepository.findAll();
        this.days = this.dayRepository.findAll();
    }

    @Test
    public void testCreateCustomer() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO).getBody();
        CustomerDTO retrievedCustomer = userController.getAllCustomers().getBody().get(0);
        Assertions.assertEquals(newCustomer.getName(), customerDTO.getName());
        Assertions.assertEquals(newCustomer.getId(), retrievedCustomer.getId());
        Assertions.assertTrue(retrievedCustomer.getId() > 0);
    }

    @Test
    public void testCreateEmployee() {
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO newEmployee = userController.saveEmployee(employeeDTO).getBody();
        EmployeeDTO retrievedEmployee = userController.getEmployee(newEmployee.getId()).getBody();
        Assertions.assertTrue(employeeDTO.getSkills().containsAll(newEmployee.getSkills()));
        Assertions.assertEquals(newEmployee.getId(), retrievedEmployee.getId());
        Assertions.assertTrue(retrievedEmployee.getId() > 0);
    }

    @Test
    public void testAddPetsToCustomer() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO).getBody();

        PetDTO petDTO = createPetDTO();
        petDTO.setCustomer(newCustomer.toCustomer());
        PetDTO newPet = petController.savePet(petDTO).getBody();

        //make sure pet contains customer id
        PetDTO retrievedPet = petController.getPet(newPet.getId()).getBody();
        Assertions.assertEquals(retrievedPet.getId(), newPet.getId());
        Assertions.assertEquals(retrievedPet.getCustomer().getId(), newCustomer.getId());

        //make sure you can retrieve pets by owner
        List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId()).getBody();
        Assertions.assertEquals(newPet.getId(), pets.get(0).getId());
        Assertions.assertEquals(newPet.getName(), pets.get(0).getName());

        //check to make sure customer now also contains pet
        CustomerDTO retrievedCustomer = userController.getAllCustomers().getBody().get(0);
        Assertions.assertTrue(retrievedCustomer.getPets() != null && retrievedCustomer.getPets().size() > 0);
        Assertions.assertEquals(retrievedCustomer.getPets().get(0).getId(), retrievedPet.getId());
    }

    @Test
    public void testFindPetsByOwner() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO).getBody();

        PetDTO petDTO = createPetDTO();
        petDTO.setCustomer(newCustomer.toCustomer());
        PetDTO newPet = petController.savePet(petDTO).getBody();
        petDTO.setType(PetType.DOG);
        petDTO.setName("DogName");
        PetDTO newPet2 = petController.savePet(petDTO).getBody();

        List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId()).getBody();
        Assertions.assertEquals(pets.size(), 2);
        Assertions.assertEquals(pets.get(0).getCustomer().getId(), newCustomer.getId());
        Assertions.assertEquals(pets.get(0).getId(), newPet.getId());
    }

    @Test
    public void testFindOwnerByPet() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO).getBody();

        PetDTO petDTO = createPetDTO();
        petDTO.setCustomer(newCustomer.toCustomer());
        PetDTO newPet = petController.savePet(petDTO).getBody();

        CustomerDTO owner = userController.getOwnerByPet(newPet.getId()).getBody();
        List<PetDTO> ownerPets = petController.getPetsByOwner(owner.getId()).getBody();
        Assertions.assertEquals(owner.getId(), newCustomer.getId());
        Assertions.assertEquals(ownerPets.get(0).getId(), newPet.getId());
    }

    @Test
    public void testChangeEmployeeAvailability() {
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO emp1 = userController.saveEmployee(employeeDTO).getBody();
        Assertions.assertNull(emp1.getDays());

        List<Day> days = this.dayRepository.findAll();
        List<Day> availability = Lists.newArrayList(days.get(2), days.get(3), days.get(4));
        userController.setAvailability(availability, emp1.getId());

        EmployeeDTO emp2 = userController.getEmployee(emp1.getId()).getBody();
        Assertions.assertEquals(availability, emp2.getDays());
    }

    @Test
    public void testFindEmployeesByServiceAndTime() {
        EmployeeDTO emp1 = createEmployeeDTO();
        EmployeeDTO emp2 = createEmployeeDTO();
        EmployeeDTO emp3 = createEmployeeDTO();

        emp1.setDays(Lists.newArrayList(this.days.get(0), this.days.get(1), this.days.get(2)));
        emp2.setDays(Lists.newArrayList(this.days.get(2), this.days.get(3), this.days.get(4)));
        emp3.setDays(Lists.newArrayList(this.days.get(4), this.days.get(5), this.days.get(6)));

        emp1.setSkills(Lists.newArrayList(this.skills.get(0), this.skills.get(1)));
        emp2.setSkills(Lists.newArrayList(this.skills.get(1), this.skills.get(2)));
        emp3.setSkills(Lists.newArrayList(this.skills.get(3)));

        EmployeeDTO emp1n = userController.saveEmployee(emp1).getBody();
        EmployeeDTO emp2n = userController.saveEmployee(emp2).getBody();
        EmployeeDTO emp3n = userController.saveEmployee(emp3).getBody();

        //make a request that matches employee 1 or 2
        EmployeeRequestDTO er1 = new EmployeeRequestDTO();
        er1.setDate(LocalDate.of(2019, 12, 25)); //wednesday
        er1.setSkills(Lists.newArrayList(this.skills.get(0), this.skills.get(1)));

        Set<Long> eIds1 = userController.findEmployeesForService(er1).getBody().stream().map(EmployeeDTO::getId).collect(Collectors.toSet());
        Set<Long> eIds1expected = Sets.newHashSet(emp1n.getId(), emp2n.getId());
        Assertions.assertEquals(eIds1, eIds1expected);

        //make a request that matches only employee 3
        EmployeeRequestDTO er2 = new EmployeeRequestDTO();
        er2.setDate(LocalDate.of(2019, 12, 27)); //friday
        er2.setSkills(Lists.newArrayList(this.skills.get(3)));

        Set<Long> eIds2 = userController.findEmployeesForService(er2).getBody().stream().map(EmployeeDTO::getId).collect(Collectors.toSet());
        Set<Long> eIds2expected = Sets.newHashSet(emp3n.getId());
        Assertions.assertEquals(eIds2, eIds2expected);
    }

    @Test
    public void testSchedulePetsForServiceWithEmployee() {
        EmployeeDTO employeeTemp = createEmployeeDTO();
        employeeTemp.setDays(Lists.newArrayList(this.days.get(0), this.days.get(1), this.days.get(2)));
        ;
        EmployeeDTO employeeDTO = userController.saveEmployee(employeeTemp).getBody();
        CustomerDTO customerDTO = userController.saveCustomer(createCustomerDTO()).getBody();
        PetDTO petTemp = createPetDTO();
        petTemp.setCustomer(customerDTO.toCustomer());
        PetDTO petDTO = petController.savePet(petTemp).getBody();

        LocalDate date = LocalDate.of(2019, 12, 25);
        List<Pet> petList = Lists.newArrayList(petDTO.toPet());
        List<Employee> employeeList = Lists.newArrayList(employeeDTO.toEmployee());
        List<Skill> skillSet = Lists.newArrayList(this.skills.get(0));

        scheduleController.createSchedule(createScheduleDTO(petList, employeeList, date, skillSet));
        ScheduleDTO scheduleDTO = scheduleController.getAllSchedules().getBody().get(0);

        Assertions.assertEquals(scheduleDTO.getActivities(), skillSet);
        Assertions.assertEquals(scheduleDTO.getDate(), date);
        Assertions.assertEquals(scheduleDTO.getEmployees(), employeeList);
        Assertions.assertEquals(scheduleDTO.getPets(), petList);
    }

//    @Test
//    public void testFindScheduleByEntities() {
//        ScheduleDTO sched1 = populateSchedule(1, 2, LocalDate.of(2019, 12, 25), Lists.newArrayList(this.skills.get(0), this.skills.get(2)));
//        ScheduleDTO sched2 = populateSchedule(3, 1, LocalDate.of(2019, 12, 26), Lists.newArrayList(this.skills.get(3)));
//
//        //add a third schedule that shares some employees and pets with the other schedules
//        ScheduleDTO sched3 = new ScheduleDTO();
//        sched3.setEmployeeIds(sched1.getEmployeeIds());
//        sched3.setPetIds(sched2.getPetIds());
//        sched3.setActivities(Sets.newHashSet(EmployeeSkill.SHAVING, EmployeeSkill.PETTING));
//        sched3.setDate(LocalDate.of(2020, 3, 23));
//        scheduleController.createSchedule(sched3);
//
//        /*
//            We now have 3 schedule entries. The third schedule entry has the same employees as the 1st schedule
//            and the same pets/owners as the second schedule. So if we look up schedule entries for the employee from
//            schedule 1, we should get both the first and third schedule as our result.
//         */
//
//        //Employee 1 in is both schedule 1 and 3
//        List<ScheduleDTO> scheds1e = scheduleController.getScheduleForEmployee(sched1.getEmployeeIds().get(0));
//        compareSchedules(sched1, scheds1e.get(0));
//        compareSchedules(sched3, scheds1e.get(1));
//
//        //Employee 2 is only in schedule 2
//        List<ScheduleDTO> scheds2e = scheduleController.getScheduleForEmployee(sched2.getEmployeeIds().get(0));
//        compareSchedules(sched2, scheds2e.get(0));
//
//        //Pet 1 is only in schedule 1
//        List<ScheduleDTO> scheds1p = scheduleController.getScheduleForPet(sched1.getPetIds().get(0));
//        compareSchedules(sched1, scheds1p.get(0));
//
//        //Pet from schedule 2 is in both schedules 2 and 3
//        List<ScheduleDTO> scheds2p = scheduleController.getScheduleForPet(sched2.getPetIds().get(0));
//        compareSchedules(sched2, scheds2p.get(0));
//        compareSchedules(sched3, scheds2p.get(1));
//
//        //Owner of the first pet will only be in schedule 1
//        List<ScheduleDTO> scheds1c = scheduleController.getScheduleForCustomer(userController.getOwnerByPet(sched1.getPetIds().get(0)).getBody().getId());
//        compareSchedules(sched1, scheds1c.get(0));
//
//        //Owner of pet from schedule 2 will be in both schedules 2 and 3
//        List<ScheduleDTO> scheds2c = scheduleController.getScheduleForCustomer(userController.getOwnerByPet(sched2.getPetIds().get(0)).getBody().getId());
//        compareSchedules(sched2, scheds2c.get(0));
//        compareSchedules(sched3, scheds2c.get(1));
//    }


    private EmployeeDTO createEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("TestEmployee");

        employeeDTO.setSkills(Lists.newArrayList(this.skills.get(0), this.skills.get(1)));
        return employeeDTO;
    }

    private static CustomerDTO createCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("TestEmployee");
        customerDTO.setPhoneNumber("123-456-789");
        return customerDTO;
    }

    private static PetDTO createPetDTO() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("TestPet");
        petDTO.setType(PetType.CAT);
        return petDTO;
    }

    private EmployeeRequestDTO createEmployeeRequestDTO() {
        EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO();
        employeeRequestDTO.setDate(LocalDate.of(2019, 12, 25));
        employeeRequestDTO.setSkills(Lists.newArrayList(this.skills.get(0), this.skills.get(1)));
        return employeeRequestDTO;
    }

    private static ScheduleDTO createScheduleDTO(List<Pet> pets, List<Employee> employees, LocalDate date, List<Skill> activities) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setPets(pets);
        scheduleDTO.setEmployees(employees);
        scheduleDTO.setDate(date);
        scheduleDTO.setActivities(activities);
        return scheduleDTO;
    }

    private ScheduleDTO populateSchedule(int numEmployees, int numPets, LocalDate date, List<Skill> activities) {
        List<Employee> employees = IntStream.range(0, numEmployees)
                .mapToObj(i -> createEmployeeDTO())
                .map(e -> {
                    e.setSkills(activities);
                    e.setDays(Lists.newArrayList(this.days.get(date.getDayOfWeek().getValue())));
                    return userController.saveEmployee(e).getBody().toEmployee();
                }).collect(Collectors.toList());

        CustomerDTO customerDTO = userController.saveCustomer(createCustomerDTO()).getBody();
        List<Pet> pets = IntStream.range(0, numPets)
                .mapToObj(i -> createPetDTO())
                .map(p -> {
                    assert customerDTO != null;
                    p.setCustomer(customerDTO.toCustomer());
                    return Objects.requireNonNull(petController.savePet(p).getBody()).toPet();
                }).collect(Collectors.toList());
        return scheduleController.createSchedule(createScheduleDTO(pets, employees, date, activities)).getBody();
    }

    private static void compareSchedules(ScheduleDTO sched1, ScheduleDTO sched2) {
        Assertions.assertEquals(sched1.getPets(), sched2.getPets());
        Assertions.assertEquals(sched1.getActivities(), sched2.getActivities());
        Assertions.assertEquals(sched1.getEmployees(), sched2.getEmployees());
        Assertions.assertEquals(sched1.getDate(), sched2.getDate());
    }

}
