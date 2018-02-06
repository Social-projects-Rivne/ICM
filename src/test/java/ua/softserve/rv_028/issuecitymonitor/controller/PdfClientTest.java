package ua.softserve.rv_028.issuecitymonitor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.softserve.rv_028.issuecitymonitor.controller.PDF.Client;
import ua.softserve.rv_028.issuecitymonitor.controller.PDF.DataObject;
import ua.softserve.rv_028.issuecitymonitor.controller.PDF.PdfWritable;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.PdfTypes;
import ua.softserve.rv_028.issuecitymonitor.service.EventService;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;
import ua.softserve.rv_028.issuecitymonitor.service.PetitionService;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.softserve.rv_028.issuecitymonitor.entity.enums.PdfTypes.ISSUES;

@RunWith(MockitoJUnitRunner.class)
public class PdfClientTest {

    private final static PdfTypes pdfTypes = ISSUES;

    @Mock
    private IssueService issueService;

    @InjectMocks
    private Client client;

    @Test
    public void getDataObjectList(){

        List<PdfWritable> dataList = new ArrayList<>();
        when(issueService.findAllForPDF()).thenReturn(dataList);

        List<DataObject> dataObjects = dataList.stream().map(DataObject::new).collect(Collectors.toList());
        List<DataObject> dataObjectsResult = client.getDataObjectList(pdfTypes);

        verify(issueService).findAllForPDF();
        assertEquals(dataObjects, dataObjectsResult);
    }


}
