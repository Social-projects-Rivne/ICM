package ua.softserve.rv_028.issuecitymonitor.exception;
import ua.softserve.rv_028.issuecitymonitor.Constants;

public class GeneratePdfException extends RuntimeException{

    public GeneratePdfException() {
        super(Constants.GENERATE_PDF_FAIL);
    }

}
