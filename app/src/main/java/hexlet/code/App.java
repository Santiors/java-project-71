package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
        name = "gendiff",
        description = "Compares two configuration files and shows a difference."
)
public class App implements Runnable {

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Parameters(description = "path to first file")
    private String filepath1;

    @Parameters(description = "path to second file")
    private String filepath2;

    @Option(names = {"-h", "--help"}, description = "Show this help message and exit.", help = true)
    private boolean helpRequested;

    @Option(names = {"-V", "--version"}, description = "Print version information and exit.")
    private boolean versionRequested;

    @Override
    public void run() {
        if (helpRequested) {
            CommandLine.usage(this, System.out);
        } else if (versionRequested) {
            System.out.println("Version 1.0");
        } else {
//            System.out.println("Comparing configuration files...");
//            System.out.println("Format: " + format);
//            System.out.println("Filepath 1: " + filepath1);
//            System.out.println("Filepath 2: " + filepath2);
        }
    }

    public static void main(String[] args) {
        CommandLine.run(new App(), args);
    }
}
