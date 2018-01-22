package ua.softserve.rv_028.issuecitymonitor.controller.PDF;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import ua.softserve.rv_028.issuecitymonitor.dto.EventDto;
import ua.softserve.rv_028.issuecitymonitor.dto.IssueDto;
import ua.softserve.rv_028.issuecitymonitor.dto.PetitionDto;
import ua.softserve.rv_028.issuecitymonitor.service.EventService;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;
import ua.softserve.rv_028.issuecitymonitor.service.PetitionService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/apis/pdf")
public class Client {


    private final IssueService issueService;
    private final EventService eventService;
    private final PetitionService petitionService;

    @Autowired
    Client(IssueService issueService, EventService eventService, PetitionService petitionService){
        this.issueService = issueService;
        this.eventService = eventService;
        this.petitionService = petitionService;
    }


    @GetMapping(path = "/issues")
    private void createPdfIssues() {
        createPdf("issues");
    }


    @GetMapping(path = "/petitions")
    private void createPdfPetitions() {
        createPdf("petitions");
    }


    @GetMapping(path = "/events")
    private void createPdfEvents() {
        createPdf("events");
    }


    @GetMapping(path = "/users")
    private void createPdfUsers() {
        createPdf("users");
    }


    @Value("${path.for.download}")
    private String downloadPath;
    private static final String PDF_EXTENSION = ".pdf";

    private void createPdf(String pdfName){

        List<DataObject> dataObjList = getDataObjectList(pdfName);
        Document document = null;
        try {
            //Document is not auto-closable hence need to close it separately
            document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(downloadPath + pdfName + PDF_EXTENSION));
            HeaderFooter event = new HeaderFooter();
            event.setHeader(pdfName);
            writer.setPageEvent(event);
            document.open();
            PDFCreator.addMetaData(document, pdfName);
            PDFCreator.addTitlePage(document, pdfName);
            PDFCreator.addContent(document, dataObjList);
        }
        catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FileNotFoundException occurs.." + e.getMessage());
        }

        finally{
            if(null != document){
                document.close();
            }
        }
    }

    public List<DataObject> getDataObjectList(String pdfName){

        System.out.println(pdfName);

        List<DataObject> dataObjList = new ArrayList<DataObject>();

        DataObject[] d = new DataObject[100];

        for(int i = 0; i < 100; i++){
            d[i] = new DataObject();
            d[i].setNoteID("1");
            d[i].setTitle("Petition name 1");
            d[i].setDesc("desc 1");
            d[i].setCat("cat 1");
            d[i].setUserID("1");
            d[i].setDate("2017");

            dataObjList.add(d[i]);
        }

        if (pdfName.equals("issues")){

            List<IssueDto> test = issueService.findAllForPDF();
            for(IssueDto item : test){
                System.out.println(item);
            }

        }

        else if (pdfName.equals("events")){

            List<EventDto> test = eventService.findAllForPDF();
            for(EventDto item : test){
                System.out.println(item);
            }

        }


        else if (pdfName.equals("petitions")){

            List<PetitionDto> test = petitionService.findAllForPDF();
            for(PetitionDto item : test){
                System.out.println(item);
            }

        }


        return dataObjList;
    }

}
