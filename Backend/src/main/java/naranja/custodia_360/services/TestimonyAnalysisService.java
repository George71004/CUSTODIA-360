package naranja.custodia_360.services;

import naranja.custodia_360.handlers.CrimesAnalysisHandler;
import naranja.custodia_360.handlers.LiesAnalysisHandler;
import naranja.custodia_360.handlers.SuspectsAnalysisHandler;
import naranja.custodia_360.models.TestimonyContext;
import org.springframework.stereotype.Service;

@Service
public class TestimonyAnalysisService {
    private final CrimesAnalysisHandler crimesAnalysisHandler;
    private final SuspectsAnalysisHandler suspectsAnalysisHandler;
    private final LiesAnalysisHandler liesAnalysisHandler;

    public TestimonyAnalysisService(CrimesAnalysisHandler crimesAnalysisHandler,
                                   SuspectsAnalysisHandler suspectsAnalysisHandler,
                                   LiesAnalysisHandler liesAnalysisHandler) {
        this.crimesAnalysisHandler = crimesAnalysisHandler;
        this.suspectsAnalysisHandler = suspectsAnalysisHandler;
        this.liesAnalysisHandler = liesAnalysisHandler;

        this.crimesAnalysisHandler.setNext(this.suspectsAnalysisHandler);
        this.suspectsAnalysisHandler.setNext(this.liesAnalysisHandler);
    }

    public TestimonyContext executeFullAnalysis(String testimonyText) {
        TestimonyContext context = new TestimonyContext(testimonyText);

        // Se dispara el flujo comenzando por el primer eslabón (Crimes)
        crimesAnalysisHandler.process(context);

        return context;
    }

}
