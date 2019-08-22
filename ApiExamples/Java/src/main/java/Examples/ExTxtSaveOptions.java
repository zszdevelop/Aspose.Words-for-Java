package Examples;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

import com.aspose.words.Document;
import com.aspose.words.TxtExportHeadersFootersMode;
import com.aspose.words.TxtSaveOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ExTxtSaveOptions extends ApiExampleBase {
    @Test
    public void pageBreaks() throws Exception {
        //ExStart
        //ExFor:TxtSaveOptionsBase.ForcePageBreaks
        //ExSummary:Shows how to specify whether the page breaks should be preserved during export.
        Document doc = new Document(getMyDir() + "SaveOptions.PageBreaks.docx");

        TxtSaveOptions saveOptions = new TxtSaveOptions();
        saveOptions.setForcePageBreaks(false);

        doc.save(getArtifactsDir() + "SaveOptions.PageBreaks.txt", saveOptions);
        //ExEnd
    }

    @Test
    public void addBidiMarks() throws Exception {
        //ExStart
        //ExFor:TxtSaveOptions.AddBidiMarks
        //ExSummary:Shows how to insert Unicode Character 'RIGHT-TO-LEFT MARK' (U+200F) before each bi-directional Run in text.
        Document doc = new Document(getMyDir() + "Document.docx");
        // In Aspose.Words by default this option is set to true unlike Word
        TxtSaveOptions saveOptions = new TxtSaveOptions();
        saveOptions.setAddBidiMarks(false);

        doc.save(getArtifactsDir() + "AddBidiMarks.txt", saveOptions);
        //ExEnd
    }

    @Test(dataProvider = "exportHeadersFootersDataProvider")
    public void exportHeadersFooters(final int txtExportHeadersFootersMode) throws Exception {
        //ExStart
        //ExFor:TxtSaveOptionsBase.ExportHeadersFootersMode
        //ExFor:TxtExportHeadersFootersMode
        //ExSummary:Shows how to specifies the way headers and footers are exported to plain text format.
        Document doc = new Document(getMyDir() + "HeaderFooter.HeaderFooterOrder.docx");

        // Three values are available in TxtExportHeadersFootersMode enum:
        // "None" - No headers and footers are exported
        // "AllAtEnd" - All headers and footers are placed after all section bodies at the very end of a document
        // "PrimaryOnly" - Only primary headers and footers are exported at the beginning and end of each section (default value)
        TxtSaveOptions saveOptions = new TxtSaveOptions();
        saveOptions.setExportHeadersFootersMode(txtExportHeadersFootersMode);

        doc.save(getArtifactsDir() + "ExportHeadersFooters.txt", saveOptions);
        //ExEnd
    }


    @DataProvider(name = "exportHeadersFootersDataProvider")
    public static Object[][] exportHeadersFootersDataProvider() {
        return new Object[][]
                {
                        {TxtExportHeadersFootersMode.NONE},
                        {TxtExportHeadersFootersMode.ALL_AT_END},
                        {TxtExportHeadersFootersMode.PRIMARY_ONLY},
                };
    }

    @Test
    public void txtListIndentation() throws Exception {
        //ExStart
        //ExFor:TxtListIndentation
        //ExFor:TxtListIndentation.Count
        //ExFor:TxtListIndentation.Character
        //ExSummary:Shows how list levels are displayed when the document is converting to plain text format
        Document doc = new Document(getMyDir() + "TxtSaveOptions.TxtListIndentation.docx");

        TxtSaveOptions txtSaveOptions = new TxtSaveOptions();
        txtSaveOptions.getListIndentation().setCount(3);
        txtSaveOptions.getListIndentation().setCharacter(' ');
        txtSaveOptions.setPreserveTableLayout(true);

        doc.save(getArtifactsDir() + "TxtSaveOptions.TxtListIndentation.txt", txtSaveOptions);
        //ExEnd
    }
}
