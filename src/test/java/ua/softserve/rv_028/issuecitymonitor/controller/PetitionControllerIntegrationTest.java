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
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import ua.softserve.rv_028.issuecitymonitor.TestApplication;
import ua.softserve.rv_028.issuecitymonitor.TestUtils;
import ua.softserve.rv_028.issuecitymonitor.dao.PetitionDao;
import ua.softserve.rv_028.issuecitymonitor.dao.UserDao;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.entity.Petition;
import ua.softserve.rv_028.issuecitymonitor.entity.User;
import ua.softserve.rv_028.issuecitymonitor.service.MapperService;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestApplication.class)
public class PetitionControllerIntegrationTest {

    private static final int LIST_SIZE = 5;
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_OFFSET = 1;

    private Petition petition;
    private List<Petition> petitions;
    private User user;

    @Autowired
    private PetitionDao petitionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MapperService mapperService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup(){
        user = userDao.save(TestUtils.createUser(0));
        petitions = petitionDao.save(TestUtils.createPetitionsList(user, LIST_SIZE));
        petition = petitions.get(0);
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
    public void testUpdatePetitionSuccessfully(){
        String updatedTitle = "testUpdateTitle";
        String updatedDescription = "testUpdateDescription";
        PetitionDto petitionDto = mapperService.fromEntityToDto(petition);
        petitionDto.setTitle(updatedTitle);
        petitionDto.setDescription(updatedDescription);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<PetitionDto> httpEntity = new HttpEntity<>(petitionDto,httpHeaders);
        ResponseEntity<PetitionDto> responseEntity = testRestTemplate.exchange("/api/petitions/" + petitionDto.getId(),
                HttpMethod.PUT, httpEntity, PetitionDto.class);

        System.out.println(httpEntity.toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PetitionDto responseObject = responseEntity.getBody();
        assertNotNull(responseObject);
        assertEquals(updatedTitle, responseObject.getTitle());
        assertEquals(updatedDescription, responseObject.getDescription());
    }

    @Test
    public void testDeletePetitionSuccessfully(){
        long prevCount = petitionDao.count();
        testRestTemplate.delete("/api/petitions/"+petition.getId());
        assertEquals(prevCount-1, petitionDao.count());
    }

    @Test
    public void testPetitionNotFound(){
        ResponseEntity<PetitionDto> responseEntity = testRestTemplate.
                getForEntity("/api/petitions/-1", PetitionDto.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}
