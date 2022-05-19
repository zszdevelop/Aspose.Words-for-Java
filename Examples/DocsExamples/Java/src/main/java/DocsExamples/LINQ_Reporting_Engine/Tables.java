package DocsExamples.LINQ_Reporting_Engine;

import DocsExamples.DocsExamplesBase;
import DocsExamples.LINQ_Reporting_Engine.Helpers.Common;
import org.testng.annotations.Test;
import com.aspose.words.Document;
import com.aspose.words.ReportingEngine;

/**
 * 报表-table
 */
@Test
class Tables extends DocsExamplesBase
{
    /**
     * 在表格中替代内容
     * @throws Exception
     */
    @Test
    public void inTableAlternateContent() throws Exception
    {
        //ExStart:InTableAlternateContent
        Document doc = new Document(getMyDir() + "Reporting engine template - Total.docx");

        ReportingEngine engine = new ReportingEngine();
        engine.buildReport(doc, Common.getContracts(), "Contracts");

        doc.save(getArtifactsDir() + "ReportingEngine.InTableAlternateContent.docx");
        //ExEnd:InTableAlternateContent
    }

    @Test
    public void inTableMasterDetail() throws Exception
    {
        //ExStart:InTableMasterDetail
        Document doc = new Document(getMyDir() + "Reporting engine template - Nested data table.docx");

        ReportingEngine engine = new ReportingEngine();
        engine.buildReport(doc, Common.getManagers(), "Managers");

        doc.save(getArtifactsDir() + "ReportingEngine.InTableMasterDetail.docx");
        //ExEnd:InTableMasterDetail
    }

    /**
     * 在表与过滤分组排序
     * @throws Exception
     */
    @Test
    public void inTableWithFilteringGroupingSorting() throws Exception
    {
        //ExStart:InTableWithFilteringGroupingSorting
        Document doc = new Document(getMyDir() + "Reporting engine template - Table with filtering.docx");

        ReportingEngine engine = new ReportingEngine();
        engine.buildReport(doc, Common.getContracts(), "contracts");

        doc.save(getArtifactsDir() + "ReportingEngine.InTableWithFilteringGroupingSorting.docx");
        //ExEnd:InTableWithFilteringGroupingSorting
    }
}
