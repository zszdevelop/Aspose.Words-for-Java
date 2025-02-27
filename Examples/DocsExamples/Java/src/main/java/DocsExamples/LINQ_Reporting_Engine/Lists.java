package DocsExamples.LINQ_Reporting_Engine;

import DocsExamples.DocsExamplesBase;
import DocsExamples.LINQ_Reporting_Engine.Helpers.Common;
import DocsExamples.LINQ_Reporting_Engine.Helpers.Data_Source_Objects.Client;
import DocsExamples.LINQ_Reporting_Engine.Helpers.Data_Source_Objects.Manager;
import org.testng.annotations.Test;
import com.aspose.words.Document;
import com.aspose.words.ReportingEngine;
import com.aspose.words.DocumentBuilder;

/**
 * 报表-列表
 */
@Test
public class Lists extends DocsExamplesBase
{
    /**
     * 创建项目符号列表
     * @throws Exception
     */
    @Test
    public void createBulletedList() throws Exception
    {
        //ExStart:BulletedList
        Document doc = new Document(getMyDir() + "Reporting engine template - Bulleted list.docx");

        ReportingEngine engine = new ReportingEngine();
        engine.getKnownTypes().add(Client.class);
        engine.buildReport(doc, Common.getClients(), "clients");

        doc.save(getArtifactsDir() + "ReportingEngine.CreateBulletedList.docx");
        //ExEnd:BulletedList
    }

    /**
     * 通用列表
     * @throws Exception
     */
    @Test
    public void commonList() throws Exception
    {
        //ExStart:CommonList
        Document doc = new Document(getMyDir() + "Reporting engine template - Common master detail.docx");

        ReportingEngine engine = new ReportingEngine();
        engine.getKnownTypes().add(Manager.class);
        engine.buildReport(doc, Common.getManagers(), "managers");

        doc.save(getArtifactsDir() + "ReportingEngine.CommonList.docx");
        //ExEnd:CommonList
    }

    /**
     * 在段落列表中
     * @throws Exception
     */
    @Test
    public void inParagraphList() throws Exception
    {
        //ExStart:InParagraphList
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        
        builder.write("<<foreach [Client in clients]>><<[indexOf() !=0 ? ”, ”:  ””]>><<[getName()]>><</foreach>>");
        
        ReportingEngine engine = new ReportingEngine();
        engine.getKnownTypes().add(Client.class);

        engine.buildReport(doc, Common.getClients(), "clients");

        doc.save(getArtifactsDir() + "ReportingEngine.InParagraphList.docx");
        //ExEnd:InParagraphList
    }

    /**
     * 表列表中
     * @throws Exception
     */
    @Test
    public void inTableList() throws Exception
    {
        //ExStart:InTableList
        Document doc = new Document(getMyDir() + "Reporting engine template - Contextual object member access.docx");

        ReportingEngine engine = new ReportingEngine();
        engine.getKnownTypes().add(Manager.class);
        engine.buildReport(doc, Common.getManagers(), "Managers");

        doc.save(getArtifactsDir() + "ReportingEngine.InTableList.docx");
        //ExEnd:InTableList
    }

    @Test
    public void multicoloredNumberedList() throws Exception
    {
        //ExStart:MulticoloredNumberedList
        Document doc = new Document(getMyDir() + "Reporting engine template - Multicolored numbered list.docx");

        ReportingEngine engine = new ReportingEngine();
        engine.getKnownTypes().add(Client.class);
        engine.buildReport(doc, Common.getClients(), "clients");

        doc.save(getArtifactsDir() + "ReportingEngine.MulticoloredNumberedList.doc");
        //ExEnd:MulticoloredNumberedList
    }

    @Test
    public void numberedList() throws Exception
    {
        //ExStart:NumberedList
        Document doc = new Document(getMyDir() + "Reporting engine template - Numbered list.docx");

        ReportingEngine engine = new ReportingEngine();
        engine.getKnownTypes().add(Client.class);
        engine.buildReport(doc, Common.getClients(), "clients");

        doc.save(getArtifactsDir() + "ReportingEngine.NumberedList.docx");
        //ExEnd:NumberedList
    }
}
