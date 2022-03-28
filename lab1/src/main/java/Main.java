import fileoperations.FileOpener;
import fileoperations.FileParser;
import lombok.extern.slf4j.Slf4j;
import utils.ArgumentsParser;

@Slf4j
public class Main {

    public static void main(String[] args) {
        if (!ArgumentsParser.isArgumentsOk(args)) {
            log.info("WRONG INPUT PARAMETERS");
            return;
        }
        FileOpener.openFile(args[0]).ifPresent(fileInputStream -> {
            FileParser csvParser = new FileParser();
            csvParser.parse(fileInputStream);
        });
    }
}
