package naranja.custodia_360.models;

public class TestimonyContext {
    private final String testimonyText;

    private String crimesAnalysis; // Delitos
    private String suspectsAnalysis; // Sospechas
    private String liesAnalysis; // Mentiras

    public TestimonyContext(String testimonyText) {
        this.testimonyText = testimonyText;
    }

    public String getTestimonyText() {
        return testimonyText;
    }

    public String getCrimesAnalysis() {
        return crimesAnalysis;
    }

    public void setCrimesAnalysis(String crimesAnalysis) {
        this.crimesAnalysis = crimesAnalysis;
    }

    public String getSuspectsAnalysis() {
        return suspectsAnalysis;
    }

    public void setSuspectsAnalysis(String suspectsAnalysis) {
        this.suspectsAnalysis = suspectsAnalysis;
    }

    public String getLiesAnalysis() {
        return liesAnalysis;
    }

    public void setLiesAnalysis(String liesAnalysis) {
        this.liesAnalysis = liesAnalysis;
    }

}
