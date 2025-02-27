package DocsExamples.File_formats_and_conversions.Load_options;

import DocsExamples.DocsExamplesBase;
import org.testng.annotations.Test;
import com.aspose.words.LoadOptions;
import com.aspose.words.Document;
import com.aspose.words.OdtSaveOptions;
import com.aspose.words.SaveFormat;
import com.aspose.words.MsWordVersion;
import com.aspose.words.IWarningCallback;
import com.aspose.words.WarningInfo;
import com.aspose.words.PdfLoadOptions;

import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 * 加载选项的设置
 */
@Test
public class WorkingWithLoadOptions extends DocsExamplesBase {
    @Test
    public void updateDirtyFields() throws Exception {
        //ExStart:UpdateDirtyFields
        LoadOptions loadOptions = new LoadOptions();
        {
            loadOptions.setUpdateDirtyFields(true);
        }

        Document doc = new Document(getMyDir() + "Dirty field.docx", loadOptions);

        doc.save(getArtifactsDir() + "WorkingWithLoadOptions.UpdateDirtyFields.docx");
        //ExEnd:UpdateDirtyFields
    }

    /**
     * 加载加密文档
     * @throws Exception
     */
    @Test
    public void loadEncryptedDocument() throws Exception {
        //ExStart:LoadSaveEncryptedDoc
        //ExStart:OpenEncryptedDocument
        Document doc = new Document(getMyDir() + "Encrypted.docx", new LoadOptions("docPassword"));
        //ExEnd:OpenEncryptedDocument

        doc.save(getArtifactsDir() + "WorkingWithLoadOptions.LoadAndSaveEncryptedOdt.odt", new OdtSaveOptions("newPassword"));
        //ExEnd:LoadSaveEncryptedDoc
    }

    @Test
    public void convertShapeToOfficeMath() throws Exception {
        //ExStart:ConvertShapeToOfficeMath
        LoadOptions loadOptions = new LoadOptions();
        {
            loadOptions.setConvertShapeToOfficeMath(true);
        }

        Document doc = new Document(getMyDir() + "Office math.docx", loadOptions);

        doc.save(getArtifactsDir() + "WorkingWithLoadOptions.ConvertShapeToOfficeMath.docx", SaveFormat.DOCX);
        //ExEnd:ConvertShapeToOfficeMath
    }

    /**
     * 设置word版本
     * @throws Exception
     */
    @Test
    public void setMsWordVersion() throws Exception {
        //ExStart:SetMSWordVersion
        // Create a new LoadOptions object, which will load documents according to MS Word 2019 specification by default
        // and change the loading version to Microsoft Word 2010.
        LoadOptions loadOptions = new LoadOptions();
        {
            loadOptions.setMswVersion(MsWordVersion.WORD_2010);
        }

        Document doc = new Document(getMyDir() + "Document.docx", loadOptions);

        doc.save(getArtifactsDir() + "WorkingWithLoadOptions.SetMsWordVersion.docx");
        //ExEnd:SetMSWordVersion
    }

    /**
     * 使用临时文件夹
     * @throws Exception
     */
    @Test
    public void useTempFolder() throws Exception {
        //ExStart:UseTempFolder  
        LoadOptions loadOptions = new LoadOptions();
        {
            loadOptions.setTempFolder(getArtifactsDir());
        }

        Document doc = new Document(getMyDir() + "Document.docx", loadOptions);
        //ExEnd:UseTempFolder  
    }

    /**
     * 文档加载警告监听
     * @throws Exception
     */
    @Test
    public void warningCallback() throws Exception {
        //ExStart:WarningCallback
        LoadOptions loadOptions = new LoadOptions();
        {
            loadOptions.setWarningCallback(new DocumentLoadingWarningCallback());
        }

        Document doc = new Document(getMyDir() + "Document.docx", loadOptions);
        //ExEnd:WarningCallback
    }

    //ExStart:DocumentLoadingWarningCallback
    public static class DocumentLoadingWarningCallback implements IWarningCallback {
        public void warning(WarningInfo info) {
            // Prints warnings and their details as they arise during document loading.
            System.out.println(MessageFormat.format("WARNING: {0}, source: {1}", info.getWarningType(), info.getSource()));
            System.out.println(MessageFormat.format("\tDescription: {0}", info.getDescription()));
        }
    }
    //ExEnd:DocumentLoadingWarningCallback

    @Test
    public void convertMetafilesToPng() throws Exception {
        //ExStart:ConvertMetafilesToPng
        LoadOptions loadOptions = new LoadOptions();
        {
            loadOptions.setConvertMetafilesToPng(true);
        }

        Document doc = new Document(getMyDir() + "WMF with image.docx", loadOptions);
        //ExEnd:ConvertMetafilesToPng
    }

    @Test
    public void loadChm() throws Exception {
        //ExStart:LoadCHM
        LoadOptions loadOptions = new LoadOptions();
        {
            loadOptions.setEncoding(Charset.forName("windows-1251"));
        }

        Document doc = new Document(getMyDir() + "HTML help.chm", loadOptions);
        //ExEnd:LoadCHM
    }
}
