package run;

import server.Server;

public class Run {
    public static void main(String[] args) {
        if (System.getenv("PSQL_URL") != null && System.getenv("SCRIPT_PATH") != null) {
            String port = "9876";
            Server server = new Server(port);
            server.run();
        } else {
            if (System.getenv("PSQL_URL") == null) {
                System.out.println("Please configure the psql url");
            }
            if (System.getenv("SCRIPT_PATH") == null) {
                System.out.println("Please configure the script folder path");
            }
            System.exit(0);
        }
    }
}