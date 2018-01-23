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
import ua.softserve.rv_028.issuecitymonitor.dto.UserDto;
import ua.softserve.rv_028.issuecitymonitor.service.EventService;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;
import ua.softserve.rv_028.issuecitymonitor.service.PetitionService;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;

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
    private final UserService userService;

    @Autowired
    Client(IssueService issueService, EventService eventService, PetitionService petitionService, UserService userService){
        this.issueService = issueService;
        this.eventService = eventService;
        this.petitionService = petitionService;
        this.userService = userService;
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
            PDFCreator.addContent(document, dataObjList, pdfName);
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


        List<DataObject> dataObjList = new ArrayList<DataObject>();


        if (pdfName.equals("issues")){

            List<IssueDto> dataList = issueService.findAllForPDF();
            DataObject[] d = new DataObject[dataList.size()];

            for (int i = 0; i < dataList.size(); i++)
            {
                int j = dataList.size() - i - 1;

                d[j] = new DataObject();
                d[j].setNoteID(Long.toString(dataList.get(j).getId()));
                d[j].setTitle(dataList.get(j).getTitle());
                d[j].setDesc(dataList.get(j).getDescription());
                d[j].setCat(dataList.get(j).getCategory().toString());
                d[j].setUserID(Long.toString(dataList.get(j).getUserDto().getId()));
                d[j].setDate(dataList.get(j).getInitialDate());

                dataObjList.add(d[j]);
            }

        }

        else if (pdfName.equals("events")){

            List<EventDto> dataList = eventService.findAllForPDF();
            DataObject[] d = new DataObject[dataList.size()];

            for (int i = 0; i < dataList.size(); i++)
            {
                int j = dataList.size() - i - 1;

                d[j] = new DataObject();
                d[j].setNoteID(Long.toString(dataList.get(j).getId()));
                d[j].setTitle(dataList.get(j).getTitle());
                d[j].setDesc(dataList.get(j).getDescription());
                d[j].setCat(dataList.get(j).getCategory().toString());
                d[j].setUserID(Long.toString(dataList.get(j).getUserDto().getId()));
                d[j].setDate(dataList.get(j).getInitialDate());

                dataObjList.add(d[j]);
            }

        }


        else if (pdfName.equals("petitions")){

            List<PetitionDto> dataList = petitionService.findAllForPDF();
            DataObject[] d = new DataObject[dataList.size()];

            for (int i = 0; i < dataList.size(); i++)
            {
                int j = dataList.size() - i - 1;

                d[j] = new DataObject();
                d[j].setNoteID(Long.toString(dataList.get(j).getId()));
                d[j].setTitle(dataList.get(j).getTitle());
                d[j].setDesc(dataList.get(j).getDescription());
                d[j].setCat(dataList.get(j).getCategory().toString());
                d[j].setUserID(Long.toString(dataList.get(j).getUserDto().getId()));
                d[j].setDate(dataList.get(j).getInitialDate());

                dataObjList.add(d[j]);
            }

        }


        else if (pdfName.equals("users")){

            List<UserDto> dataList = userService.findAllForPDF();
            DataObject[] d = new DataObject[dataList.size()];

            for (int i = 0; i < dataList.size(); i++)
            {
                int j = dataList.size() - i - 1;

                d[j] = new DataObject();
                d[j].setNoteID(Long.toString(dataList.get(j).getId()));
                d[j].setTitle(dataList.get(j).getFirstName());
                d[j].setDesc(dataList.get(j).getLastName());
                d[j].setCat(dataList.get(j).getPhone());
                d[j].setEmail(dataList.get(j).getEmail());
                d[j].setUserID(dataList.get(j).getUserRole().toString());
                d[j].setDate(dataList.get(j).getRegistrationDate());

                dataObjList.add(d[j]);
            }

        }


        return dataObjList;
    }

}
