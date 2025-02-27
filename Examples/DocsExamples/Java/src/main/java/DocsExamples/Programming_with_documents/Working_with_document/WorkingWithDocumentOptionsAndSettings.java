package DocsExamples.Programming_with_documents.Working_with_document;

import DocsExamples.DocsExamplesBase;
import com.aspose.words.*;
import org.testng.annotations.Test;

import java.text.MessageFormat;

/**
 * 使用文档选项和设置
 */
@Test
public class WorkingWithDocumentOptionsAndSettings extends DocsExamplesBase
{
    /**
     * 优化Ms Word
     * @throws Exception
     */
    @Test
    public void optimizeForMsWord() throws Exception
    {
        //ExStart:OptimizeForMsWord
        Document doc = new Document(getMyDir() + "Document.docx");

        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2016);

        doc.save(getArtifactsDir() + "WorkingWithDocumentOptionsAndSettings.OptimizeForMsWord.docx");
        //ExEnd:OptimizeForMsWord
    }

    /**
     * 显示语法和拼写错误
     * @throws Exception
     */
    @Test
    public void showGrammaticalAndSpellingErrors() throws Exception
    {
        //ExStart:ShowGrammaticalAndSpellingErrors
        Document doc = new Document(getMyDir() + "Document.docx");

        doc.setShowGrammaticalErrors(true);
        doc.setShowSpellingErrors(true);

        doc.save(getArtifactsDir() + "WorkingWithDocumentOptionsAndSettings.ShowGrammaticalAndSpellingErrors.docx");
        //ExEnd:ShowGrammaticalAndSpellingErrors
    }

    /**
     * 清理未使用的样式和列表
     * @throws Exception
     */
    @Test
    public void cleanupUnusedStylesAndLists() throws Exception
    {
        //ExStart:CleanupUnusedStylesandLists
        Document doc = new Document(getMyDir() + "Unused styles.docx");

        // Combined with the built-in styles, the document now has eight styles.
        // A custom style is marked as "used" while there is any text within the document
        // formatted in that style. This means that the 4 styles we added are currently unused.
        System.out.println(MessageFormat.format("Count of styles before Cleanup: {0}\n", doc.getStyles().getCount()) +
                              MessageFormat.format("Count of lists before Cleanup: {0}", doc.getLists().getCount()));

        // Cleans unused styles and lists from the document depending on given CleanupOptions. 
        CleanupOptions cleanupOptions = new CleanupOptions(); { cleanupOptions.setUnusedLists(false); cleanupOptions.setUnusedStyles(true); }
        doc.cleanup(cleanupOptions);

        System.out.println(MessageFormat.format("Count of styles after Cleanup was decreased: {0}\n", doc.getStyles().getCount()) +
                              MessageFormat.format("Count of lists after Cleanup is the same: {0}", doc.getLists().getCount()));

        doc.save(getArtifactsDir() + "WorkingWithDocumentOptionsAndSettings.CleanupUnusedStylesAndLists.docx");
        //ExEnd:CleanupUnusedStylesandLists
    }

    /**
     * 清理重复样式
     * @throws Exception
     */
    @Test
    public void cleanupDuplicateStyle() throws Exception
    {
        //ExStart:CleanupDuplicateStyle
        Document doc = new Document(getMyDir() + "Document.docx");

        // Count of styles before Cleanup.
        System.out.println(doc.getStyles().getCount());

        // Cleans duplicate styles from the document.
        CleanupOptions options = new CleanupOptions(); { options.setDuplicateStyle(true); }
        doc.cleanup(options);

        // Count of styles after Cleanup was decreased.
        System.out.println(doc.getStyles().getCount());

        doc.save(getArtifactsDir() + "WorkingWithDocumentOptionsAndSettings.CleanupDuplicateStyle.docx");
        //ExEnd:CleanupDuplicateStyle
    }

    /**
     * 查看选项
     * @throws Exception
     */
    @Test
    public void viewOptions() throws Exception
    {
        //ExStart:SetViewOption
        Document doc = new Document(getMyDir() + "Document.docx");
        
        doc.getViewOptions().setViewType(ViewType.PAGE_LAYOUT);
        doc.getViewOptions().setZoomPercent(50);

        doc.save(getArtifactsDir() + "WorkingWithDocumentOptionsAndSettings.ViewOptions.docx");
        //ExEnd:SetViewOption
    }

    /**
     * 文档页面设置
     * @throws Exception
     */
    @Test
    public void documentPageSetup() throws Exception
    {
        //ExStart:DocumentPageSetup
        Document doc = new Document(getMyDir() + "Document.docx");

        // Set the layout mode for a section allowing to define the document grid behavior.
        // Note that the Document Grid tab becomes visible in the Page Setup dialog of MS Word
        // if any Asian language is defined as editing language.
        doc.getFirstSection().getPageSetup().setLayoutMode(SectionLayoutMode.GRID);
        doc.getFirstSection().getPageSetup().setCharactersPerLine(30);
        doc.getFirstSection().getPageSetup().setLinesPerPage(10);

        doc.save(getArtifactsDir() + "WorkingWithDocumentOptionsAndSettings.DocumentPageSetup.docx");
        //ExEnd:DocumentPageSetup
    }

    /**
     * 添加日语作为编辑语言
     * @throws Exception
     */
    @Test
    public void addJapaneseAsEditingLanguages() throws Exception
    {
        //ExStart:AddJapaneseAsEditinglanguages
        LoadOptions loadOptions = new LoadOptions();
        
        // Set language preferences that will be used when document is loading.
        loadOptions.getLanguagePreferences().addEditingLanguage(EditingLanguage.JAPANESE);
        //ExEnd:AddJapaneseAsEditinglanguages

        Document doc = new Document(getMyDir() + "No default editing language.docx", loadOptions);

        int localeIdFarEast = doc.getStyles().getDefaultFont().getLocaleIdFarEast();
        System.out.println(localeIdFarEast == (int) EditingLanguage.JAPANESE
                    ? "The document either has no any FarEast language set in defaults or it was set to Japanese originally."
                    : "The document default FarEast language was set to another than Japanese language originally, so it is not overridden.");
    }

    /**
     * 设置俄语为默认编辑语言
     * @throws Exception
     */
    @Test
    public void setRussianAsDefaultEditingLanguage() throws Exception
    {
        //ExStart:SetRussianAsDefaultEditingLanguage
        LoadOptions loadOptions = new LoadOptions();
        loadOptions.getLanguagePreferences().setDefaultEditingLanguage(EditingLanguage.RUSSIAN);

        Document doc = new Document(getMyDir() + "No default editing language.docx", loadOptions);

        int localeId = doc.getStyles().getDefaultFont().getLocaleId();
        System.out.println(localeId == (int) EditingLanguage.RUSSIAN
                    ? "The document either has no any language set in defaults or it was set to Russian originally."
                    : "The document default language was set to another than Russian language originally, so it is not overridden.");
        //ExEnd:SetRussianAsDefaultEditingLanguage
    }

    /**
     * 设置页面设置和部分格式
     * @throws Exception
     */
    @Test
    public void setPageSetupAndSectionFormatting() throws Exception
    {
        //ExStart:DocumentBuilderSetPageSetupAndSectionFormatting
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.getPageSetup().setOrientation(Orientation.LANDSCAPE);
        builder.getPageSetup().setLeftMargin(50.0);
        builder.getPageSetup().setPaperSize(PaperSize.PAPER_10_X_14);

        doc.save(getArtifactsDir() + "WorkingWithDocumentOptionsAndSettings.SetPageSetupAndSectionFormatting.docx");
        //ExEnd:DocumentBuilderSetPageSetupAndSectionFormatting
    }
}
