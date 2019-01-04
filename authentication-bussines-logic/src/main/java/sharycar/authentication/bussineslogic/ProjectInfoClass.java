package sharycar.authentication.bussineslogic;

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

        this.opis_projekta = "SharyCar - Mobile App for car sharing/renting - built on Kumuluzee";
        this.clani[0] = "jk8279";
        this.clani[1] = "js0730";

        // EndPoints
        this.mikrostoritve[0] = "http://35.188.156.176:8080/users";
        this.mikrostoritve[1] = "http://104.198.218.72:8080/comments";
        // Github links
        this.github[0] = "https://github.com/sharycar/sharycar-authentication";
        this.github[1] = "https://github.com/sharycar/sharycar-feedback";
        // Travis links
        this.travis[0] = "https://travis-ci.org/sharycar/sharycar-authentication";
        this.travis[1] = "https://travis-ci.org/sharycar/sharycar-feedback";
        // Dockerhub links
        this.dockerhub[0] = "https://hub.docker.com/r/jkrajnc11/sharycar-microservice-authentication";
        this.dockerhub[1] = "https://hub.docker.com/r/jkrajnc11/sharycar-microservice-feedback";

    }
}
