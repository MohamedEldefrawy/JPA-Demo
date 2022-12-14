package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.controller.PetController;
import com.udacity.jdnd.course3.critter.controller.ScheduleController;
import com.udacity.jdnd.course3.critter.controller.UserController;
import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.dto.schedule.DayDto;
import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.pet.Pet;
import com.udacity.jdnd.course3.critter.entity.pet.PetType;
import com.udacity.jdnd.course3.critter.entity.schedule.Day;
import com.udacity.jdnd.course3.critter.entity.skill.Skill;
import com.udacity.jdnd.course3.critter.entity.user.Customer;
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
import java.util.HashSet;
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
        CustomerDTO retrievedCustomer = Objects.requireNonNull(userController.getAllCustomers().getBody()).get(0);
        assert newCustomer != null;
        Assertions.assertEquals(newCustomer.getName(), customerDTO.getName());
        Assertions.assertEquals(newCustomer.getId(), retrievedCustomer.getId());
        Assertions.assertTrue(retrievedCustomer.getId() > 0);
    }

    @Test
    public void testCreateEmployee() {
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO newEmployee = userController.saveEmployee(employeeDTO).getBody();
        assert newEmployee != null;
        EmployeeDTO retrievedEmployee = userController.getEmployee(newEmployee.getId()).getBody();
        Assertions.assertTrue(employeeDTO.getSkills().containsAll(newEmployee.getSkills()));
        assert retrievedEmployee != null;
        Assertions.assertEquals(newEmployee.getId(), retrievedEmployee.getId());
        Assertions.assertTrue(retrievedEmployee.getId() > 0);
    }

    @Test
    public void testAddPetsToCustomer() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO).getBody();

        PetDTO petDTO = createPetDTO();
        assert newCustomer != null;
        petDTO.setCustomer(newCustomer.toCustomer());
        PetDTO newPet = petController.savePet(petDTO).getBody();

        //make sure pet contains customer id
        assert newPet != null;
        PetDTO retrievedPet = petController.getPet(newPet.getId()).getBody();
        assert retrievedPet != null;
        Assertions.assertEquals(retrievedPet.getId(), newPet.getId());
        Assertions.assertEquals(retrievedPet.getCustomer().getId(), newCustomer.getId());

        //make sure you can retrieve pets by owner
        List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId()).getBody();
        assert pets != null;
        Assertions.assertEquals(newPet.getId(), pets.get(0).getId());
        Assertions.assertEquals(newPet.getName(), pets.get(0).getName());

        //check to make sure customer now also contains pet
        CustomerDTO retrievedCustomer = Objects.requireNonNull(userController.getAllCustomers().getBody()).get(0);
        Assertions.assertTrue(retrievedCustomer.getPets() != null && retrievedCustomer.getPets().size() > 0);
        Assertions.assertEquals(retrievedCustomer.getPets().get(0).getId(), retrievedPet.getId());
    }

    @Test
    public void testFindPetsByOwner() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO).getBody();

        PetDTO petDTO = createPetDTO();
        assert newCustomer != null;
        petDTO.setCustomer(newCustomer.toCustomer());
        PetDTO newPet = petController.savePet(petDTO).getBody();
        petDTO.setType(PetType.DOG);
        petDTO.setName("DogName");
        petController.savePet(petDTO).getBody();

        List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId()).getBody();
        assert pets != null;
        Assertions.assertEquals(pets.size(), 2);
        Assertions.assertEquals(pets.get(0).getCustomer().getId(), newCustomer.getId());
        assert newPet != null;
        Assertions.assertEquals(pets.get(0).getId(), newPet.getId());
    }

    @Test
    public void testFindOwnerByPet() {
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerDTO newCustomer = userController.saveCustomer(customerDTO).getBody();

        PetDTO petDTO = createPetDTO();
        assert newCustomer != null;
        petDTO.setCustomer(newCustomer.toCustomer());
        PetDTO newPet = petController.savePet(petDTO).getBody();

        assert newPet != null;
        CustomerDTO owner = userController.getOwnerByPet(newPet.getId()).getBody();
        assert owner != null;
        List<PetDTO> ownerPets = petController.getPetsByOwner(owner.getId()).getBody();
        Assertions.assertEquals(owner.getId(), newCustomer.getId());
        assert ownerPets != null;
        Assertions.assertEquals(ownerPets.get(0).getId(), newPet.getId());
    }

    @Test
    public void testChangeEmployeeAvailability() {
        EmployeeDTO employeeDTO = createEmployeeDTO();
        EmployeeDTO emp1 = userController.saveEmployee(employeeDTO).getBody();
        assert emp1 != null;
        Assertions.assertNull(emp1.getDays());

        List<Day> days = this.dayRepository.findAll();
        Set<Day> availability = Sets.newHashSet(days.get(2), days.get(3), days.get(4));
        DayDto dayDto = new DayDto();
        dayDto.setDays(availability);
        userController.setAvailability(dayDto, emp1.getId());

        EmployeeDTO emp2 = userController.getEmployee(emp1.getId()).getBody();
        assert emp2 != null;
        Assertions.assertEquals(availability, emp2.getDays());
    }

    @Test
    public void testFindEmployeesByServiceAndTime() {
        EmployeeDTO emp1 = createEmployeeDTO();
        EmployeeDTO emp2 = createEmployeeDTO();
        EmployeeDTO emp3 = createEmployeeDTO();

        emp1.setDays(Sets.newHashSet(this.days.get(0), this.days.get(1), this.days.get(2)));
        emp2.setDays(Sets.newHashSet(this.days.get(2), this.days.get(3), this.days.get(4)));
        emp3.setDays(Sets.newHashSet(this.days.get(4), this.days.get(5), this.days.get(6)));

        emp1.setSkills(Sets.newHashSet(this.skills.get(0), this.skills.get(1)));
        emp2.setSkills(Sets.newHashSet(this.skills.get(1), this.skills.get(2)));
        emp3.setSkills(Sets.newHashSet(this.skills.get(3)));

        EmployeeDTO emp1n = userController.saveEmployee(emp1).getBody();
        EmployeeDTO emp2n = userController.saveEmployee(emp2).getBody();
        EmployeeDTO emp3n = userController.saveEmployee(emp3).getBody();

        //make a request that matches employee 1 or 2
        EmployeeRequestDTO er1 = new EmployeeRequestDTO();
        er1.setDate(LocalDate.of(2019, 12, 25)); //wednesday
        er1.setSkills(Lists.newArrayList(this.skills.get(0), this.skills.get(1)));

        Set<Long> eIds1 = Objects.requireNonNull(userController.findEmployeesForService(er1).getBody()).stream().map(EmployeeDTO::getId).collect(Collectors.toSet());
        assert emp1n != null;
        assert emp2n != null;
        Set<Long> eIds1expected = Sets.newHashSet(emp1n.getId(), emp2n.getId());
        Assertions.assertEquals(eIds1, eIds1expected);

        //make a request that matches only employee 3
        EmployeeRequestDTO er2 = new EmployeeRequestDTO();
        er2.setDate(LocalDate.of(2019, 12, 27)); //friday
        er2.setSkills(Lists.newArrayList(this.skills.get(3)));

        Set<Long> eIds2 = Objects.requireNonNull(userController.findEmployeesForService(er2).getBody()).stream().map(EmployeeDTO::getId).collect(Collectors.toSet());
        assert emp3n != null;
        Set<Long> eIds2expected = Sets.newHashSet(emp3n.getId());
        Assertions.assertEquals(eIds2, eIds2expected);
    }

    @Test
    public void testSchedulePetsForServiceWithEmployee() {
        EmployeeDTO employeeTemp = createEmployeeDTO();
        employeeTemp.setDays(Sets.newHashSet(this.days.get(0), this.days.get(1), this.days.get(2)));
        EmployeeDTO employeeDTO = userController.saveEmployee(employeeTemp).getBody();
        CustomerDTO customerDTO = userController.saveCustomer(createCustomerDTO()).getBody();
        PetDTO petTemp = createPetDTO();
        assert customerDTO != null;
        petTemp.setCustomer(customerDTO.toCustomer());
        PetDTO petDTO = petController.savePet(petTemp).getBody();

        LocalDate date = LocalDate.of(2019, 12, 25);
        assert petDTO != null;
        assert employeeDTO != null;
        Set<Employee> employeeList = Sets.newHashSet(employeeDTO.toEmployee());
        Set<Customer> customers = new HashSet<>();
        customers.add(customerDTO.toCustomer());


        scheduleController.createSchedule(createScheduleDTO(employeeList, customers, date));
        ScheduleDTO scheduleDTO = Objects.requireNonNull(scheduleController.getAllSchedules().getBody()).get(0);

        Assertions.assertEquals(scheduleDTO.getDate(), date);
        Assertions.assertEquals(scheduleDTO.getEmployees(), employeeList);
        Assertions.assertEquals(scheduleDTO.getCustomers(), customers);
    }

    @Test
    public void testFindScheduleByEntities() {
        ScheduleDTO sched1 = populateSchedule(1, 2, LocalDate.of(2019, 12, 25), Sets.newHashSet(this.skills.get(0), this.skills.get(2)));
        ScheduleDTO sched2 = populateSchedule(3, 1, LocalDate.of(2019, 12, 26), Sets.newHashSet(this.skills.get(3)));

        //add a third schedule that shares some employees and pets with the other schedules
        ScheduleDTO sched3 = new ScheduleDTO();
        sched3.setEmployees(sched1.getEmployees());
        sched3.setCustomers(sched2.getCustomers());
        sched3.setDate(LocalDate.of(2020, 3, 23));
        scheduleController.createSchedule(sched3);

        /*
            We now have 3 schedule entries. The third schedule entry has the same employees as the 1st schedule
            and the same pets/owners as the second schedule. So if we look up schedule entries for the employee from
            schedule 1, we should get both the first and third schedule as our result.
         */

        //Employee 1 in is both schedule 1 and 3
        List<ScheduleDTO> scheds1e = scheduleController.getScheduleForEmployee(sched1.getEmployees().toArray(new Employee[0])[0].getId()).getBody();
        compareSchedules(sched1, scheds1e.get(0));
        compareSchedules(sched3, scheds1e.get(1));

        //Employee 2 is only in schedule 2
        List<ScheduleDTO> scheds2e = scheduleController.getScheduleForEmployee(sched2.getEmployees().toArray(new Employee[0])[0].getId()).getBody();
        compareSchedules(sched2, scheds2e.get(0));

        //Pet 1 is only in schedule 1
        List<ScheduleDTO> scheds1p = scheduleController.getScheduleForPet(sched1.getCustomers().stream().iterator().next().getPets().get(0).getId()).getBody();
        compareSchedules(sched1, scheds1p.get(0));

        //Pet from schedule 2 is in both schedules 2 and 3
        List<ScheduleDTO> scheds2p = scheduleController.getScheduleForPet(sched2.getCustomers().stream().iterator().next().getPets().get(0).getId()).getBody();
        compareSchedules(sched2, scheds2p.get(0));
        compareSchedules(sched3, scheds2p.get(1));

        //Owner of the first pet will only be in schedule 1
        List<ScheduleDTO> scheds1c = scheduleController.getScheduleForCustomer(Objects.requireNonNull(userController.getOwnerByPet(sched1.getCustomers().stream().iterator().next().getPets().get(0).getId()).getBody()).getId()).getBody();
        compareSchedules(sched1, scheds1c.get(0));

        //Owner of pet from schedule 2 will be in both schedules 2 and 3
        List<ScheduleDTO> scheds2c = scheduleController.getScheduleForCustomer(Objects.requireNonNull(userController.getOwnerByPet(sched2.getCustomers().stream().iterator().next().getPets().get(0).getId()).getBody()).getId()).getBody();
        compareSchedules(sched2, scheds2c.get(0));
        compareSchedules(sched3, scheds2c.get(1));
    }


    private EmployeeDTO createEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("TestEmployee");

        employeeDTO.setSkills(Sets.newHashSet(this.skills.get(0), this.skills.get(1)));
        return employeeDTO;
    }

    private static CustomerDTO createCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("TestCustomer");
        customerDTO.setPhoneNumber("123-456-789");
        customerDTO.setNotes("TestCustomerNotes");
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

    private static ScheduleDTO createScheduleDTO(Set<Employee> employees, Set<Customer> customers, LocalDate date) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setCustomers(customers);
        scheduleDTO.setEmployees(employees);
        scheduleDTO.setDate(date);
        return scheduleDTO;
    }

    private ScheduleDTO populateSchedule(int numEmployees, int numPets, LocalDate date, Set<Skill> activities) {
        Set<Employee> employees = IntStream.range(0, numEmployees)
                .mapToObj(i -> createEmployeeDTO())
                .map(e -> {
                    e.setSkills(activities);
                    e.setDays(Sets.newHashSet(this.days.get(date.getDayOfWeek().getValue())));
                    return Objects.requireNonNull(userController.saveEmployee(e).getBody()).toEmployee();
                }).collect(Collectors.toSet());

        CustomerDTO customerDTO = userController.saveCustomer(createCustomerDTO()).getBody();
        Set<Pet> pets = IntStream.range(0, numPets)
                .mapToObj(i -> createPetDTO())
                .map(p -> {
                    assert customerDTO != null;
                    p.setCustomer(customerDTO.toCustomer());
                    return Objects.requireNonNull(petController.savePet(p).getBody()).toPet();
                }).collect(Collectors.toSet());
        Set<Customer> customers = new HashSet<>();
        customerDTO.setPets(Lists.newArrayList(pets));
        customers.add(customerDTO.toCustomer());
        return scheduleController.createSchedule(createScheduleDTO(employees, customers, date)).getBody();
    }

    private static void compareSchedules(ScheduleDTO sched1, ScheduleDTO sched2) {
        Assertions.assertEquals(sched1.getCustomers(), sched2.getCustomers());
        Assertions.assertArrayEquals(sched1.getEmployees().toArray(new Employee[0]), sched2.getEmployees().toArray(new Employee[0]));
        Assertions.assertEquals(sched1.getDate(), sched2.getDate());
    }
}
