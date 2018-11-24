package sharycar.authentication.api;

public class ProjectInfoClass {
    public String[] clani;
    public String opis_projekta;
    public String[] mikrostoritve;
    public String[] github;
    public String[] travis;
    public String[] dockerhub;


    public ProjectInfoClass() {
        this.clani = new String[2];
        this.mikrostoritve = new String[2];
        this.github = new String[2];
        this.travis = new String[2];
        this.dockerhub = new String[2];

        this.opis_projekta = "SharyCar - Mobile app for car sharing/renting build on Kumuluzee";
        this.clani[0] = "jk8279";
        // @ TODO add Janez jšXXXX; ??

        // First microservice -authentication
        this.mikrostoritve[0] = "http://35.189.96.118:8081/v1/orders"; //@TODO replace, add second one
        this.github[0] = "https://github.com/sharycar/sharycar-authentication";
        this.travis[0] = "https://travis-ci.org/sharycar/sharycar-authentication";
        this.dockerhub[0] = "https://hub.docker.com/r/jkrajnc11/sharycar-microservice-authentication";

    }
}
