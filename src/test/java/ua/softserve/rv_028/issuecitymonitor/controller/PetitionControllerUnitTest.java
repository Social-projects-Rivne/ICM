package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.service.PetitionService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PetitionControllerUnitTest {

    private final static String TEST_TITLE = "test";
    private final static String TEST_DESCRIPTION = "testDescription";
    private final static IllegalStateException EXCEPTION_NOT_FOUND = new IllegalStateException("petition not found");

    @InjectMocks
    private PetitionController petitionController;

    @Mock
    private PetitionService petitionService;

    @Test
    public void testGetPetitionSuccessfully(){
        PetitionDto petition = new PetitionDto();
        petition.setTitle(TEST_TITLE);
        petition.setDescription(TEST_DESCRIPTION);

        when(petitionService.findById(1)).thenReturn(petition);

        PetitionDto dto = petitionController.getOne(1);

        assertEquals(TEST_TITLE,dto.getTitle());
        assertEquals(TEST_DESCRIPTION,dto.getDescription());
    }

    @Test
    public void testGetPetitionNotFound() {
        when(petitionService.findById(-1)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            petitionController.getOne(-1);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testUpdatePetitionSuccessfully(){
        PetitionDto petitionDto = new PetitionDto();
        petitionDto.setId(1);
        petitionDto.setTitle(TEST_TITLE);
        petitionDto.setDescription(TEST_DESCRIPTION);
        when(petitionService.update(petitionDto)).thenReturn(petitionDto);

        PetitionDto success = petitionController.update(petitionDto.getId(),petitionDto);

        assertEquals(TEST_TITLE,success.getTitle());
        assertEquals(TEST_DESCRIPTION,success.getDescription());
    }

    @Test
    public void testUpdatePetitionNotFound(){
        PetitionDto petitionDto = new PetitionDto();
        petitionDto.setId(1);
        when(petitionService.update(petitionDto)).thenThrow(EXCEPTION_NOT_FOUND);

        try {
            petitionController.update(2,petitionDto);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }

    @Test
    public void testDeletePetitionSuccessfully(){
        doNothing().when(petitionService).deleteById(1); //This is obvious
        petitionController.delete(1);
    }

    @Test
    public void testDeletePetitionNotFound(){
        doThrow(EXCEPTION_NOT_FOUND).when(petitionService).deleteById(1);

        try {
            petitionController.delete(1);
            fail("expected exception was not thrown");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is(EXCEPTION_NOT_FOUND.getMessage()));
        }
    }
}
