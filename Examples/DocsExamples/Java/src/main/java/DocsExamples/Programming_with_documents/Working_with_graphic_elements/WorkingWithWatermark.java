package DocsExamples.Programming_with_documents.Working_with_graphic_elements;

import DocsExamples.DocsExamplesBase;
import com.aspose.words.Shape;
import com.aspose.words.*;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * 添加水印
 */
@Test
public class WorkingWithWatermark extends DocsExamplesBase
{
    /**
     * 添加带有特定选项的文本水印
     * @throws Exception
     */
    @Test
    public void addTextWatermarkWithSpecificOptions() throws Exception
    {
        //ExStart:AddTextWatermarkWithSpecificOptions
//        Document doc = new Document(getMyDir() + "Document.docx");
        Document doc = new Document(getMyDir() + "test.docx");

        TextWatermarkOptions options = new TextWatermarkOptions();
        {
            options.setFontFamily("Arial");
            options.setFontSize(36f);
            options.setColor(Color.BLACK);
            options.setLayout(WatermarkLayout.HORIZONTAL);
            options.isSemitrasparent(false);
        }

        doc.getWatermark().setText("Test", options);

        doc.save(getArtifactsDir() + "WorkWithWatermark.AddTextWatermarkWithSpecificOptions.docx");
        //ExEnd:AddTextWatermarkWithSpecificOptions
    }

    /**
     * 添加带有特定选项的图片水印
     * @throws Exception
     */
    @Test
    public void imageWatermark() throws Exception
    {
        //ExStart:AddTextWatermarkWithSpecificOptions
//        Document doc = new Document(getMyDir() + "Document.docx");
        Document doc = new Document(getMyDir() + "test.docx");

        // Modify the image watermark's appearance with an ImageWatermarkOptions object,
        // then pass it while creating a watermark from an image file.
        ImageWatermarkOptions imageWatermarkOptions = new ImageWatermarkOptions();
        imageWatermarkOptions.setScale(0.2);
        imageWatermarkOptions.isWashout(false);


        doc.getWatermark().setImage(ImageIO.read(new File(getImagesDir() + "Logo.jpg")), imageWatermarkOptions);


        doc.save(getArtifactsDir() + "imageWatermark.docx");
        //ExEnd:AddTextWatermarkWithSpecificOptions
    }


    //ExStart:AddWatermark
    @Test
    public void addAndRemoveWatermark() throws Exception
    {
        Document doc = new Document(getMyDir() + "Document.docx");

        insertWatermarkText(doc, "CONFIDENTIAL");
        doc.save(getArtifactsDir() + "TestFile.Watermark.docx");

        removeWatermarkText(doc);
        doc.save(getArtifactsDir() + "WorkWithWatermark.RemoveWatermark.docx");
    }

    /// <summary>
    /// Inserts a watermark into a document.
    /// </summary>
    /// <param name="doc">The input document.</param>
    /// <param name="watermarkText">Text of the watermark.</param>
    private void insertWatermarkText(Document doc, String watermarkText) throws Exception
    {
        // Create a watermark shape, this will be a WordArt shape.
        Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT); { watermark.setName("Watermark"); }

        watermark.getTextPath().setText(watermarkText);
        watermark.getTextPath().setFontFamily("Arial");
        watermark.setWidth(500.0);
        watermark.setHeight(100.0);

        // Text will be directed from the bottom-left to the top-right corner.
        watermark.setRotation(-40);

        // Remove the following two lines if you need a solid black text.
        watermark.setFillColor(Color.GRAY);
        watermark.setStrokeColor(Color.GRAY);

        // Place the watermark in the page center.
        watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        watermark.setWrapType(WrapType.NONE);
        watermark.setVerticalAlignment(VerticalAlignment.CENTER);
        watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // Create a new paragraph and append the watermark to this paragraph.
        Paragraph watermarkPara = new Paragraph(doc);
        watermarkPara.appendChild(watermark);

        // Insert the watermark into all headers of each document section.
        for (Section sect : (Iterable<Section>) doc.getSections())
        {
            // There could be up to three different headers in each section.
            // Since we want the watermark to appear on all pages, insert it into all headers.
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
    }

    private void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect,
        /*HeaderFooterType*/int headerType)
    {
        HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);

        if (header == null)
        {
            // There is no header of the specified type in the current section, so we need to create it.
            header = new HeaderFooter(sect.getDocument(), headerType);
            sect.getHeadersFooters().add(header);
        }

        // Insert a clone of the watermark into the header.
        header.appendChild(watermarkPara.deepClone(true));
    }
    //ExEnd:AddWatermark
    
    //ExStart:RemoveWatermark
    private void removeWatermarkText(Document doc)
    {
        for (HeaderFooter hf : (Iterable<HeaderFooter>) doc.getChildNodes(NodeType.HEADER_FOOTER, true))
        {
            for (Shape shape : (Iterable<Shape>) hf.getChildNodes(NodeType.SHAPE, true))
            {
                if (shape.getName().contains("WaterMark"))
                {
                    shape.remove();
                }
            }
        }
    }
    //ExEnd:RemoveWatermark
}

