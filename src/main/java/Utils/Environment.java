package Utils;

public enum Environment {
    LOCALHOST("http://localhost:4200", "target"),
    DOCKER(null, "/home/app/target"),
    REMOTE(null, "target");

    private String hostAddress;
    private String ouputPath;

    Environment(String hostAddress, String ouputPath) {
        this.hostAddress = hostAddress;
        this.ouputPath = ouputPath;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getHostAddress() {
        if (hostAddress == null) {
            throw new RuntimeException("You have to set host address by using '--host' in program args");
        }
        return hostAddress;
    }

    public String getOuputPath() {
        return ouputPath;
    }
}
