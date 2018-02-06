package ua.softserve.rv_028.issuecitymonitor.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.UserRole;
import ua.softserve.rv_028.issuecitymonitor.service.mappers.PetitionMapper;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createPetitionsList;
import static ua.softserve.rv_028.issuecitymonitor.TestUtils.createUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetitionControllerITest {

    private static final int LIST_SIZE = 5;
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_OFFSET = 1;

    private Petition petition;
    private List<Petition> petitions;
    private User user;

    @Autowired
    private PetitionDao petitionDao;

    @Autowired
    private PetitionMapper petitionMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PetitionController controller;

    private static User USER = TestUtils.createAdmin(0);
    private static final String USERNAME = "mock-test@mail.com";

    @Before
    public void setup(){
        user = userDao.save(createUser(0));
        petitions = petitionDao.save(createPetitionsList(user, LIST_SIZE));
        petition = petitions.get(0);

        USER.setUsername(USERNAME);
        User user = userDao.findUserByUsername(USERNAME);
        if (user == null)
            userDao.save(USER);
        else
            USER = user;
        USER.setUserRole(UserRole.ADMIN);
        userDao.save(USER);
    }

    @After
    public void tearDown() {
        petitionDao.delete(petitions);
        userDao.delete(user);
    }

    @Test
    public void testGetPetitionSuccessfully(){
        ResponseEntity<PetitionDto> responseEntity = testRestTemplate.
                getForEntity("/api/petitions/"+petition.getId(), PetitionDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        PetitionDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(petition.getTitle(), responseObject.getTitle());
        assertEquals(petition.getDescription(), responseObject.getDescription());
    }

    @Test
    public void testGetAllByPage(){
        ResponseEntity<String> responseEntity = testRestTemplate.
                getForEntity("/api/petitions?size=" + PAGE_SIZE + "&page=" + PAGE_OFFSET, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode content = objectMapper.readTree(responseEntity.getBody()).path("content");
            assertEquals(PAGE_SIZE, content.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void testUpdatePetitionSuccessfully(){
        String updatedTitle = "testUpdateTitle";
        String updatedDescription = "testUpdateDescription";
        PetitionDto petitionDto = petitionMapper.toDto(petition);
        petitionDto.setTitle(updatedTitle);
        petitionDto.setDescription(updatedDescription);

        PetitionDto updatedPetition = controller.update(petitionDto.getId(), petitionDto);

        assertEquals(updatedTitle, updatedPetition.getTitle());
        assertEquals(updatedDescription, updatedPetition.getDescription());
    }

    @Test
    @WithMockUser(username = "mock-test@mail.com")
    public void testDeletePetitionSuccessfully(){
        long prevCount = petitionDao.count();
        controller.delete(petition.getId());
        assertEquals(prevCount-1, petitionDao.count());
    }

    @Test
    public void testPetitionNotFound(){
        ResponseEntity<PetitionDto> responseEntity = testRestTemplate.
                getForEntity("/api/petitions/-1", PetitionDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}
