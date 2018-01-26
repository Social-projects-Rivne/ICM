package ua.softserve.rv_028.issuecitymonitor.controller.PDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.rv_028.issuecitymonitor.entity.enums.PdfTypes;
import ua.softserve.rv_028.issuecitymonitor.service.EventService;
import ua.softserve.rv_028.issuecitymonitor.service.IssueService;
import ua.softserve.rv_028.issuecitymonitor.service.PetitionService;
import ua.softserve.rv_028.issuecitymonitor.service.UserService;
import static ua.softserve.rv_028.issuecitymonitor.entity.enums.PdfTypes.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/apis/pdf")
public class Client {


    private final IssueService issueService;
    private final EventService eventService;
    private final PetitionService petitionService;
    private final UserService userService;

    @Autowired
    Client(IssueService issueService, EventService eventService, PetitionService petitionService, UserService userService) {
        this.issueService = issueService;
        this.eventService = eventService;
        this.petitionService = petitionService;
        this.userService = userService;
    }


    @GetMapping(path = "/issues")
    private void createPdfIssues() {
        createPdf(ISSUES);
    }


    @GetMapping(path = "/petitions")
    private void createPdfPetitions() {
        createPdf(PETITIONS);
    }


    @GetMapping(path = "/events")
    private void createPdfEvents() {
        createPdf(EVENTS);
    }


    @GetMapping(path = "/users")
    private void createPdfUsers() {
        createPdf(USERS);
    }


    @Value("${path.for.download}")
    private String downloadPath;
    private static final String PDF_EXTENSION = ".pdf";

    private void createPdf(PdfTypes pdfName) {

        List<DataObject> dataObjList = getDataObjectList(pdfName);
        writePdf(pdfName.getName(), dataObjList);
    }

    private void writePdf(String pdfName, List<DataObject> dataObjList) {
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
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FileNotFoundException occurs.." + e.getMessage());
        } finally {
            if (null != document) {
                document.close();
            }
        }
    }

    public List<DataObject> getDataObjectList(PdfTypes pdfName) {

        List<PdfWritable> dataList = new ArrayList<>();
        switch (pdfName) {
            case ISSUES:
                dataList = issueService.findAllForPDF();
                break;
            case EVENTS:
                dataList = eventService.findAllForPDF();
                break;
            case PETITIONS:
                dataList = petitionService.findAllForPDF();
                break;
            case USERS:
                dataList = userService.findAllForPDF();
                break;
        }

        return dataList.stream().map(DataObject::new).collect(Collectors.toList());

    }




}
