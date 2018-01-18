package ua.softserve.rv_028.issuecitymonitor.controller.PDF;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import ua.softserve.rv_028.issuecitymonitor.controller.IssueController;
import ua.softserve.rv_028.issuecitymonitor.controller.PetitionController;
import ua.softserve.rv_028.issuecitymonitor.controller.EventController;
import ua.softserve.rv_028.issuecitymonitor.controller.UserController;


@RestController
@RequestMapping("/apis/pdf")
public class Client {


    public static void main(String...args){
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

        List<DataObject> dataObjList = getDataObjectList();
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

    public static List<DataObject> getDataObjectList(){
        List<DataObject> dataObjList = new ArrayList<DataObject>();
        DataObject d1 = new DataObject();
        d1.setTitile("Petition 1");
        d1.setIncome("20000");
        d1.setYear("2017");
        d1.setYear("1");
        d1.setYear("2");
        DataObject d2 = new DataObject();
        d2.setTitile("Petition 2");
        d2.setIncome("30000");
        d2.setYear("2017");
        d2.setYear("1");
        d2.setYear("2");
        dataObjList.add(d1);
        dataObjList.add(d2);
        return dataObjList;
    }

}
