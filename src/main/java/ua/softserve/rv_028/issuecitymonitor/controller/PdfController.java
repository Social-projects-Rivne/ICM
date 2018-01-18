package ua.softserve.rv_028.issuecitymonitor.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/apis/pdf")
public class PdfController {


    public static void main(String...args){
    }


    private static final Logger LOGGER = Logger.getLogger(PetitionController.class.getName());

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
    private void createPdf(String pdfName){


        Document document = new Document();
        try{
            PdfWriter.getInstance(document, new FileOutputStream(downloadPath + pdfName + ".pdf"));
            document.open();
            Paragraph p1 = new Paragraph(pdfName);
            Paragraph p2 = new Paragraph("Examsmyantra.com is committed to provide you valuable information and tutorials on various technologies.");
            document.add(p1);
            document.add(p2);
        }
        catch(Exception e){
            System.out.println(e);
        }
        document.close();


        LOGGER.info(pdfName + " request successful! my TEST");
    }
}