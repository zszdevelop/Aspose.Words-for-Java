package DocsExamples.Programming_with_documents.Working_with_tables;

import DocsExamples.DocsExamplesBase;
import com.aspose.words.*;
import com.aspose.words.net.System.Data.DataColumn;
import com.aspose.words.net.System.Data.DataRow;
import com.aspose.words.net.System.Data.DataSet;
import com.aspose.words.net.System.Data.DataTable;
import com.sun.deploy.xml.XMLNode;
import jdk.nashorn.internal.runtime.Undefined;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;
import org.w3c.dom.Attr;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 处理表格
 */
@Test
public class WorkingWithTables extends DocsExamplesBase
{
    /**
     * 移除列
     * @throws Exception
     */
    @Test
    public void removeColumn() throws Exception
    {
        //ExStart:RemoveColumn
        Document doc = new Document(getMyDir() + "Tables.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 1, true);

        Column column = Column.fromIndex(table, 2);
        column.remove();
        //ExEnd:RemoveColumn
        doc.save(getArtifactsDir() + "WorkingWithTables.removeColumn.docx");
    }

    /**
     * 添加列
     * @throws Exception
     */
    @Test
    public void insertBlankColumn() throws Exception
    {
        //ExStart:InsertBlankColumn
        Document doc = new Document(getMyDir() + "Tables.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);

        //ExStart:GetPlainText
        Column column = Column.fromIndex(table, 0);
        // Print the plain text of the column to the screen.
        System.out.println(column.toTxt());
        //ExEnd:GetPlainText
        
        // Create a new column to the left of this column.
        // This is the same as using the "Insert Column Before" command in Microsoft Word.
        Column newColumn = column.insertColumnBefore();

        for (Cell cell : newColumn.getColumnCells())
            cell.getFirstParagraph().appendChild(new Run(doc, "Column Text " + newColumn.indexOf(cell)));
        //ExEnd:InsertBlankColumn
        doc.save(getArtifactsDir() + "WorkingWithTables.insertBlankColumn.docx");
    }

    //ExStart:ColumnClass
    /// <summary>
    /// Represents a facade object for a column of a table in a Microsoft Word document.
    /// </summary>
    static class Column
    {
        private Column(Table table, int columnIndex) {
            if (table != null) {
                mTable = table;
            } else {
                throw new IllegalArgumentException("table");
            }

            mColumnIndex = columnIndex;
        }

        /// <summary>
        /// Returns a new column facade from the table and supplied zero-based index.
        /// </summary>
        public static Column fromIndex(Table table, int columnIndex)
        {
            return new Column(table, columnIndex);
        }

        private ArrayList<Cell> getCells() {
            return getColumnCells();
        }

        /// <summary>
        /// Returns the index of the given cell in the column.
        /// </summary>
        public int indexOf(Cell cell)
        {
            return getColumnCells().indexOf(cell);
        }

        /// <summary>
        /// Inserts a brand new column before this column into the table.
        /// </summary>
        public Column insertColumnBefore()
        {
            ArrayList<Cell> columnCells = getCells();

            if (columnCells.size() == 0)
                throw new IllegalArgumentException("Column must not be empty");

            // Create a clone of this column.
            for (Cell cell : columnCells)
                cell.getParentRow().insertBefore(cell.deepClone(false), cell);

            // This is the new column.
            Column column = new Column(columnCells.get(0).getParentRow().getParentTable(), mColumnIndex);

            // We want to make sure that the cells are all valid to work with (have at least one paragraph).
            for (Cell cell : column.getCells())
                cell.ensureMinimum();

            // Increase the index which this column represents since there is now one extra column in front.
            mColumnIndex++;

            return column;
        }

        /// <summary>
        /// Removes the column from the table.
        /// </summary>
        public void remove()
        {
            for (Cell cell : getCells())
                cell.remove();
        }

        /// <summary>
        /// Returns the text of the column. 
        /// </summary>
        public String toTxt() throws Exception
        {
            StringBuilder builder = new StringBuilder();

            for (Cell cell : getCells())
                builder.append(cell.toString(SaveFormat.TEXT));

            return builder.toString();
        }

        /// <summary>
        /// Provides an up-to-date collection of cells which make up the column represented by this facade.
        /// </summary>
        private ArrayList<Cell> getColumnCells()
        {
            ArrayList<Cell> columnCells = new ArrayList<Cell>();

            for (Row row : mTable.getRows())
            {
                Cell cell = row.getCells().get(mColumnIndex);
                if (cell != null)
                    columnCells.add(cell);
            }

            return columnCells;
        }

        private int mColumnIndex;
        private Table mTable;
    }
    //ExEnd:ColumnClass

    @Test
    public void autoFitTableToContents() throws Exception
    {
        //ExStart:AutoFitTableToContents
        Document doc = new Document(getMyDir() + "Tables.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);
        table.autoFit(AutoFitBehavior.AUTO_FIT_TO_CONTENTS);

        doc.save(getArtifactsDir() + "WorkingWithTables.AutoFitTableToContents.docx");
        //ExEnd:AutoFitTableToContents
    }

    @Test
    public void autoFitTableToFixedColumnWidths() throws Exception
    {
        //ExStart:AutoFitTableToFixedColumnWidths
        Document doc = new Document(getMyDir() + "Tables.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);
        // Disable autofitting on this table.
        table.autoFit(AutoFitBehavior.FIXED_COLUMN_WIDTHS);

        doc.save(getArtifactsDir() + "WorkingWithTables.AutoFitTableToFixedColumnWidths.docx");
        //ExEnd:AutoFitTableToFixedColumnWidths
    }

    @Test
    public void autoFitTableToPageWidth() throws Exception
    {
        //ExStart:AutoFitTableToPageWidth
        Document doc = new Document(getMyDir() + "Tables.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);
        // Autofit the first table to the page width.
        table.autoFit(AutoFitBehavior.AUTO_FIT_TO_WINDOW);

        doc.save(getArtifactsDir() + "WorkingWithTables.AutoFitTableToWindow.docx");
        //ExEnd:AutoFitTableToPageWidth
    }

    @Test
    public void buildTableFromDataTable() throws Exception
    {
        //ExStart:BuildTableFromDataTable
        Document doc = new Document();
        // We can position where we want the table to be inserted and specify any extra formatting to the table.
        DocumentBuilder builder = new DocumentBuilder(doc);

        // We want to rotate the page landscape as we expect a wide table.
        doc.getFirstSection().getPageSetup().setOrientation(Orientation.LANDSCAPE);

        DataSet ds = new DataSet();
        ds.readXml(getMyDir() + "List of people.xml");
        // Retrieve the data from our data source, which is stored as a DataTable.
        DataTable dataTable = ds.getTables().get(0);

        // Build a table in the document from the data contained in the DataTable.
        Table table = importTableFromDataTable(builder, dataTable, true);

        // We can apply a table style as a very quick way to apply formatting to the entire table.
        table.setStyleIdentifier(StyleIdentifier.MEDIUM_LIST_2_ACCENT_1);
        table.setStyleOptions(TableStyleOptions.FIRST_ROW | TableStyleOptions.ROW_BANDS | TableStyleOptions.LAST_COLUMN);

        // For our table, we want to remove the heading for the image column.
        table.getFirstRow().getLastCell().removeAllChildren();

        doc.save(getArtifactsDir() + "WorkingWithTables.BuildTableFromDataTable.docx");
        //ExEnd:BuildTableFromDataTable
    }

    //ExStart:ImportTableFromDataTable
    /// <summary>
    /// Imports the content from the specified DataTable into a new Aspose.Words Table object.
    /// The table is inserted at the document builder's current position and using the current builder's formatting if any is defined.
    /// </summary>
    public Table importTableFromDataTable(DocumentBuilder builder, DataTable dataTable,
        boolean importColumnHeadings)
    {
        Table table = builder.startTable();

        // Check if the columns' names from the data source are to be included in a header row.
        if (importColumnHeadings)
        {
            // Store the original values of these properties before changing them.
            boolean boldValue = builder.getFont().getBold();
            int paragraphAlignmentValue = builder.getParagraphFormat().getAlignment();

            // Format the heading row with the appropriate properties.
            builder.getFont().setBold(true);
            builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

            // Create a new row and insert the name of each column into the first row of the table.
            for (DataColumn column : dataTable.getColumns())
            {
                builder.insertCell();
                builder.writeln(column.getColumnName());
            }

            builder.endRow();

            // Restore the original formatting.
            builder.getFont().setBold(boldValue);
            builder.getParagraphFormat().setAlignment(paragraphAlignmentValue);
        }

        for (DataRow dataRow : (Iterable<DataRow>) dataTable.getRows())
        {
            for (Object item : dataRow.getItemArray())
            {
                // Insert a new cell for each object.
                builder.insertCell();

                switch (item.getClass().getName())
                {
                    case "DateTime":
                        // Define a custom format for dates and times.
                        Date dateTime = (Date) item;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy");
                        builder.write(simpleDateFormat.format(dateTime));
                        break;
                    default:
                        // By default any other item will be inserted as text.
                        builder.write(item.toString());
                        break;
                }
            }

            // After we insert all the data from the current record, we can end the table row.
            builder.endRow();
        }

        // We have finished inserting all the data from the DataTable, we can end the table.
        builder.endTable();

        return table;
    }
    //ExEnd:ImportTableFromDataTable

    @Test
    public void cloneCompleteTable() throws Exception
    {
        //ExStart:CloneCompleteTable
        Document doc = new Document(getMyDir() + "Tables.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);

        // Clone the table and insert it into the document after the original.
        Table tableClone = (Table) table.deepClone(true);
        table.getParentNode().insertAfter(tableClone, table);

        // Insert an empty paragraph between the two tables,
        // or else they will be combined into one upon saving this has to do with document validation.
        table.getParentNode().insertAfter(new Paragraph(doc), table);
        
        doc.save(getArtifactsDir() + "WorkingWithTables.CloneCompleteTable.docx");
        //ExEnd:CloneCompleteTable
    }

    @Test
    public void cloneLastRow() throws Exception
    {
        //ExStart:CloneLastRow
        Document doc = new Document(getMyDir() + "Tables.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);

        Row clonedRow = (Row) table.getLastRow().deepClone(true);
        // Remove all content from the cloned row's cells. This makes the row ready for new content to be inserted into.
        for (Cell cell : (Iterable<Cell>) clonedRow.getCells())
            cell.removeAllChildren();

        table.appendChild(clonedRow);

        doc.save(getArtifactsDir() + "WorkingWithTables.CloneLastRow.docx");
        //ExEnd:CloneLastRow
    }
    
    @Test
    public void findingIndex() throws Exception
    {
        Document doc = new Document(getMyDir() + "Tables.docx");

        //ExStart:RetrieveTableIndex
        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);

        NodeCollection allTables = doc.getChildNodes(NodeType.TABLE, true);
        int tableIndex = allTables.indexOf(table);
        //ExEnd:RetrieveTableIndex
        System.out.println("\nTable index is " + tableIndex);

        //ExStart:RetrieveRowIndex
        int rowIndex = table.indexOf(table.getLastRow());
        //ExEnd:RetrieveRowIndex
        System.out.println("\nRow index is " + rowIndex);

        Row row = table.getLastRow();
        //ExStart:RetrieveCellIndex
        int cellIndex = row.indexOf(row.getCells().get(4));
        //ExEnd:RetrieveCellIndex
        System.out.println("\nCell index is " + cellIndex);
    }

    @Test
    public void insertTableDirectly() throws Exception
    {
        //ExStart:InsertTableDirectly
        Document doc = new Document();
        
        // We start by creating the table object. Note that we must pass the document object
        // to the constructor of each node. This is because every node we create must belong
        // to some document.
        Table table = new Table(doc);
        doc.getFirstSection().getBody().appendChild(table);

        // Here we could call EnsureMinimum to create the rows and cells for us. This method is used
        // to ensure that the specified node is valid. In this case, a valid table should have at least one Row and one cell.

        // Instead, we will handle creating the row and table ourselves.
        // This would be the best way to do this if we were creating a table inside an algorithm.
        Row row = new Row(doc);
        row.getRowFormat().setAllowBreakAcrossPages(true);
        table.appendChild(row);

        // We can now apply any auto fit settings.
        table.autoFit(AutoFitBehavior.FIXED_COLUMN_WIDTHS);

        Cell cell = new Cell(doc);
        cell.getCellFormat().getShading().setBackgroundPatternColor(Color.BLUE);
        cell.getCellFormat().setWidth(80.0);
        cell.appendChild(new Paragraph(doc));
        cell.getFirstParagraph().appendChild(new Run(doc, "Row 1, Cell 1 Text"));

        row.appendChild(cell);

        // We would then repeat the process for the other cells and rows in the table.
        // We can also speed things up by cloning existing cells and rows.
        row.appendChild(cell.deepClone(false));
        row.getLastCell().appendChild(new Paragraph(doc));
        row.getLastCell().getFirstParagraph().appendChild(new Run(doc, "Row 1, Cell 2 Text"));
        
        doc.save(getArtifactsDir() + "WorkingWithTables.InsertTableDirectly.docx");
        //ExEnd:InsertTableDirectly
    }

    /**
     * 插入html表格
     * @throws Exception
     */
    @Test
    public void insertTableFromHtml() throws Exception
    {
        //ExStart:InsertTableFromHtml
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Note that AutoFitSettings does not apply to tables inserted from HTML.
        builder.insertHtml("<table>" +
                           "<tr>" +
                           "<td>Row 1, Cell 1</td>" +
                           "<td>Row 1, Cell 2</td>" +
                           "</tr>" +
                           "<tr>" +
                           "<td>Row 2, Cell 2</td>" +
                           "<td>Row 2, Cell 2</td>" +
                           "</tr>" +
                           "</table>");

        doc.save(getArtifactsDir() + "WorkingWithTables.InsertTableFromHtml.docx");
        //ExEnd:InsertTableFromHtml
    }

    /**
     * 创建简单表格
     * @throws Exception
     */
    @Test
    public void createSimpleTable() throws Exception
    {
        //ExStart:CreateSimpleTable
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        
        // Start building the table.
        builder.startTable();
        builder.insertCell();
        builder.write("Row 1, Cell 1 Content.");
        
        // Build the second cell.
        builder.insertCell();
        builder.write("Row 1, Cell 2 Content.");
        
        // Call the following method to end the row and start a new row.
        builder.endRow();

        // Build the first cell of the second row.
        builder.insertCell();
        builder.write("Row 2, Cell 1 Content");

        // Build the second cell.
        builder.insertCell();
        builder.write("Row 2, Cell 2 Content.");
        builder.endRow();

        // Signal that we have finished building the table.
        builder.endTable();

        doc.save(getArtifactsDir() + "WorkingWithTables.CreateSimpleTable.docx");
        //ExEnd:CreateSimpleTable
    }

    /**
     * 格式化表
     * @throws Exception
     */
    @Test
    public void formattedTable() throws Exception
    {
        //ExStart:FormattedTable
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();
        builder.insertCell();

        // Table wide formatting must be applied after at least one row is present in the table.
        table.setLeftIndent(20.0);

        // Set height and define the height rule for the header row.
        builder.getRowFormat().setHeight(40.0);
        builder.getRowFormat().setHeightRule(HeightRule.AT_LEAST);

        builder.getCellFormat().getShading().setBackgroundPatternColor(new Color((198), (217), (241)));
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        builder.getFont().setSize(16.0);
        builder.getFont().setName("Arial");
        builder.getFont().setBold(true);

        builder.getCellFormat().setWidth(100.0);
        builder.write("Header Row,\n Cell 1");

        // We don't need to specify this cell's width because it's inherited from the previous cell.
        builder.insertCell();
        builder.write("Header Row,\n Cell 2");

        builder.insertCell();
        builder.getCellFormat().setWidth(200.0);
        builder.write("Header Row,\n Cell 3");
        builder.endRow();

        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.WHITE);
        builder.getCellFormat().setWidth(100.0);
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);

        // Reset height and define a different height rule for table body.
        builder.getRowFormat().setHeight(30.0);
        builder.getRowFormat().setHeightRule(HeightRule.AUTO);
        builder.insertCell();
        
        // Reset font formatting.
        builder.getFont().setSize(12.0);
        builder.getFont().setBold(false);

        builder.write("Row 1, Cell 1 Content");
        builder.insertCell();
        builder.write("Row 1, Cell 2 Content");

        builder.insertCell();
        builder.getCellFormat().setWidth(200.0);
        builder.write("Row 1, Cell 3 Content");
        builder.endRow();

        builder.insertCell();
        builder.getCellFormat().setWidth(100.0);
        builder.write("Row 2, Cell 1 Content");

        builder.insertCell();
        builder.write("Row 2, Cell 2 Content");

        builder.insertCell();
        builder.getCellFormat().setWidth(200.0);
        builder.write("Row 2, Cell 3 Content.");
        builder.endRow();
        builder.endTable();

        doc.save(getArtifactsDir() + "WorkingWithTables.FormattedTable.docx");
        //ExEnd:FormattedTable
    }

    /**
     * 嵌套表
     * @throws Exception
     */
    @Test
    public void nestedTable() throws Exception
    {
        //ExStart:NestedTable
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Cell cell = builder.insertCell();
        builder.writeln("Outer Table Cell 1");

        builder.insertCell();
        builder.writeln("Outer Table Cell 2");

        // This call is important to create a nested table within the first table. 
        // Without this call, the cells inserted below will be appended to the outer table.
        builder.endTable();

        // Move to the first cell of the outer table.
        builder.moveTo(cell.getFirstParagraph());

        // Build the inner table.
        builder.insertCell();
        builder.writeln("Inner Table Cell 1");
        builder.insertCell();
        builder.writeln("Inner Table Cell 2");
        builder.endTable();

        doc.save(getArtifactsDir() + "WorkingWithTables.NestedTable.docx");
        //ExEnd:NestedTable
    }

    /**
     * 组合行
     * @throws Exception
     */
    @Test
    public void combineRows() throws Exception
    {
        //ExStart:CombineRows
        Document doc = new Document(getMyDir() + "Tables.docx");

        // The rows from the second table will be appended to the end of the first table.
        Table firstTable = (Table) doc.getChild(NodeType.TABLE, 0, true);
        Table secondTable = (Table) doc.getChild(NodeType.TABLE, 1, true);

        // Append all rows from the current table to the next tables
        // with different cell count and widths can be joined into one table.
        while (secondTable.hasChildNodes())
            firstTable.getRows().add(secondTable.getFirstRow());

        secondTable.remove();

        doc.save(getArtifactsDir() + "WorkingWithTables.CombineRows.docx");
        //ExEnd:CombineRows
    }

    /**
     * 拆分表
     * @throws Exception
     */
    @Test
    public void splitTable() throws Exception
    {
        //ExStart:SplitTable
        Document doc = new Document(getMyDir() + "Tables.docx");

        Table firstTable = (Table) doc.getChild(NodeType.TABLE, 0, true);

        // We will split the table at the third row (inclusive).
        Row row = firstTable.getRows().get(2);

        // Create a new container for the split table.
        Table table = (Table) firstTable.deepClone(false);

        // Insert the container after the original.
        firstTable.getParentNode().insertAfter(table, firstTable);

        // Add a buffer paragraph to ensure the tables stay apart.
        firstTable.getParentNode().insertAfter(new Paragraph(doc), firstTable);

        Row currentRow;

        do
        {
            currentRow = firstTable.getLastRow();
            table.prependChild(currentRow);
        } while (currentRow != row);

        doc.save(getArtifactsDir() + "WorkingWithTables.SplitTable.docx");
        //ExEnd:SplitTable
    }

    /**
     * 行格式禁用跨页断行
     * @throws Exception
     */
    @Test
    public void rowFormatDisableBreakAcrossPages() throws Exception
    {
        //ExStart:RowFormatDisableBreakAcrossPages
        Document doc = new Document(getMyDir() + "Table spanning two pages.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);

        // Disable breaking across pages for all rows in the table.
        for (Row row : (Iterable<Row>) table.getRows())
            row.getRowFormat().setAllowBreakAcrossPages(false);

        doc.save(getArtifactsDir() + "WorkingWithTables.RowFormatDisableBreakAcrossPages.docx");
        //ExEnd:RowFormatDisableBreakAcrossPages
    }

    /**
     * 把表放在一起
     * @throws Exception
     */
    @Test
    public void keepTableTogether() throws Exception
    {
        //ExStart:KeepTableTogether
        Document doc = new Document(getMyDir() + "Table spanning two pages.docx");
        
        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);

        // We need to enable KeepWithNext for every paragraph in the table to keep it from breaking across a page,
        // except for the last paragraphs in the last row of the table.
        for (Cell cell : (Iterable<Cell>) table.getChildNodes(NodeType.CELL, true))
        {
            cell.ensureMinimum();

            for (Paragraph para : (Iterable<Paragraph>) cell.getParagraphs())
                if (!(cell.getParentRow().isLastRow() && para.isEndOfCell()))
                    para.getParagraphFormat().setKeepWithNext(true);
        }

        doc.save(getArtifactsDir() + "WorkingWithTables.KeepTableTogether.docx");
        //ExEnd:KeepTableTogether
    }

    /**
     * 检查合并的单元格
     * @throws Exception
     */
    @Test
    public void checkCellsMerged() throws Exception
    {
        //ExStart:CheckCellsMerged 
        Document doc = new Document(getMyDir() + "Table with merged cells.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);

        for (Row row : (Iterable<Row>) table.getRows())
        {
            for (Cell cell : (Iterable<Cell>) row.getCells())
            {
                System.out.println(printCellMergeType(cell));
            }
        }
        //ExEnd:CheckCellsMerged 
    }

    //ExStart:PrintCellMergeType 
    public String printCellMergeType(Cell cell)
    {
        boolean isHorizontallyMerged = cell.getCellFormat().getHorizontalMerge() != CellMerge.NONE;
        boolean isVerticallyMerged = cell.getCellFormat().getVerticalMerge() != CellMerge.NONE;
        
        String cellLocation =
                MessageFormat.format("R{0}, C{1}", cell.getParentRow().getParentTable().indexOf(cell.getParentRow()) + 1, cell.getParentRow().indexOf(cell) + 1);

        if (isHorizontallyMerged && isVerticallyMerged)
            return MessageFormat.format("The cell at {0} is both horizontally and vertically merged", cellLocation);
        
        if (isHorizontallyMerged)
            return MessageFormat.format("The cell at {0} is horizontally merged.", cellLocation);
        
        if (isVerticallyMerged)
            return MessageFormat.format("The cell at {0} is vertically merged", cellLocation);
        
        return MessageFormat.format("The cell at {0} is not merged", cellLocation);
    }
    //ExEnd:PrintCellMergeType
    
    @Test
    public void verticalMerge() throws Exception
    {
        //ExStart:VerticalMerge           
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.getCellFormat().setVerticalMerge(CellMerge.FIRST);
        builder.write("Text in merged cells.");

        builder.insertCell();
        builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        builder.write("Text in one cell");
        builder.endRow();

        builder.insertCell();
        // This cell is vertically merged to the cell above and should be empty.
        builder.getCellFormat().setVerticalMerge(CellMerge.PREVIOUS);

        builder.insertCell();
        builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
        builder.write("Text in another cell");
        builder.endRow();
        builder.endTable();
        
        doc.save(getArtifactsDir() + "WorkingWithTables.VerticalMerge.docx");
        //ExEnd:VerticalMerge
    }

    @Test
    public void horizontalMerge() throws Exception
    {
        //ExStart:HorizontalMerge         
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertCell();
        builder.getCellFormat().setHorizontalMerge(CellMerge.FIRST);
        builder.write("Text in merged cells.");

        builder.insertCell();
        // This cell is merged to the previous and should be empty.
        builder.getCellFormat().setHorizontalMerge(CellMerge.PREVIOUS);
        builder.endRow();

        builder.insertCell();
        builder.getCellFormat().setHorizontalMerge(CellMerge.NONE);
        builder.write("Text in one cell.");

        builder.insertCell();
        builder.write("Text in another cell.");
        builder.endRow();
        builder.endTable();
        
        doc.save(getArtifactsDir() + "WorkingWithTables.HorizontalMerge.docx");
        //ExEnd:HorizontalMerge
    }

    @Test
    public void mergeCellRange() throws Exception
    {
        //ExStart:MergeCellRange
        Document doc = new Document(getMyDir() + "Table with merged cells.docx");

        Table table = doc.getFirstSection().getBody().getTables().get(0);

        // We want to merge the range of cells found inbetween these two cells.
        Cell cellStartRange = table.getRows().get(0).getCells().get(0);
        Cell cellEndRange = table.getRows().get(1).getCells().get(1);

        // Merge all the cells between the two specified cells into one.
        mergeCells(cellStartRange, cellEndRange);
        
        doc.save(getArtifactsDir() + "WorkingWithTables.MergeCellRange.docx");
        //ExEnd:MergeCellRange
    }

    @Test
    public void printHorizontalAndVerticalMerged() throws Exception
    {
        //ExStart:PrintHorizontalAndVerticalMerged
        Document doc = new Document(getMyDir() + "Table with merged cells.docx");

        SpanVisitor visitor = new SpanVisitor(doc);
        doc.accept(visitor);
        //ExEnd:PrintHorizontalAndVerticalMerged
    }

    @Test
    public void convertToHorizontallyMergedCells() throws Exception
    {
        //ExStart:ConvertToHorizontallyMergedCells         
        Document doc = new Document(getMyDir() + "Table with merged cells.docx");

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        // Now merged cells have appropriate merge flags.
        table.convertToHorizontallyMergedCells();
        //ExEnd:ConvertToHorizontallyMergedCells
    }

    //ExStart:MergeCells
    void mergeCells(Cell startCell, Cell endCell)
    {
        Table parentTable = startCell.getParentRow().getParentTable();

        // Find the row and cell indices for the start and end cell.
        Point startCellPos = new Point(startCell.getParentRow().indexOf(startCell),
            parentTable.indexOf(startCell.getParentRow()));
        Point endCellPos = new Point(endCell.getParentRow().indexOf(endCell), parentTable.indexOf(endCell.getParentRow()));

        // Create a range of cells to be merged based on these indices.
        // Inverse each index if the end cell is before the start cell.
        Rectangle mergeRange = new Rectangle(Math.min(startCellPos.x, endCellPos.y),
            Math.min(startCellPos.y, endCellPos.y),
            Math.abs(endCellPos.x - startCellPos.x) + 1, Math.abs(endCellPos.y - startCellPos.y) + 1);

        for (Row row : parentTable.getRows())
        {
            for (Cell cell : row.getCells())
            {
                Point currentPos = new Point(row.indexOf(cell), parentTable.indexOf(row));

                // Check if the current cell is inside our merge range, then merge it.
                if (mergeRange.contains(currentPos))
                {
                    cell.getCellFormat().setHorizontalMerge(currentPos.x == mergeRange.getX() ? CellMerge.FIRST : CellMerge.PREVIOUS);

                    cell.getCellFormat().setVerticalMerge(currentPos.y == mergeRange.getY() ? CellMerge.FIRST : CellMerge.PREVIOUS);
                }
            }
        }
    }
    //ExEnd:MergeCells
    
    //ExStart:HorizontalAndVerticalMergeHelperClasses
    /// <summary>
    /// Helper class that contains collection of rowinfo for each row.
    /// </summary>
    public static class TableInfo
    {
        public ArrayList<RowInfo> getRows() { return mRows; }

        private ArrayList<RowInfo> mRows = new ArrayList<>();
    }

    /// <summary>
    /// Helper class that contains collection of cellinfo for each cell.
    /// </summary>
    public static class RowInfo
    {
        public ArrayList<CellInfo> getCells() { return mCells; };

        private ArrayList<CellInfo> mCells = new ArrayList<>();
    }

    /// <summary>
    /// Helper class that contains info about cell. currently here is only colspan and rowspan.
    /// </summary>
    public static class CellInfo
    {
        public CellInfo(int colSpan, int rowSpan)
        {
            mColSpan = colSpan;
            mRowSpan = rowSpan;
        }

        public int getColSpan() { return mColSpan; };

        private  int mColSpan;
        public int getRowSpan() { return mRowSpan; };

        private  int mRowSpan;
    }

    public static class SpanVisitor extends DocumentVisitor
    {
        /// <summary>
        /// Creates new SpanVisitor instance.
        /// </summary>
        /// <param name="doc">
        /// Is document which we should parse.
        /// </param>
        public SpanVisitor(Document doc) throws Exception {
            mWordTables = doc.getChildNodes(NodeType.TABLE, true);

            // We will parse HTML to determine the rowspan and colspan of each cell.
            ByteArrayOutputStream htmlStream = new ByteArrayOutputStream();

            HtmlSaveOptions options = new HtmlSaveOptions();
            {
                options.setImagesFolder(System.getProperty("java.io.tmpdir"));
            }

            doc.save(htmlStream, options);

            // Load HTML into the XML document.
            org.jsoup.nodes.Document document = Jsoup.parse(htmlStream.toString());

            // Get collection of tables in the HTML document.
            Elements tables = document.getElementsByTag("table");

            for (Element table : tables) {
                TableInfo tableInf = new TableInfo();

                // Get collection of rows in the table.
                Elements rows = table.getElementsByTag("tr");

                for (Element row : rows) {
                    RowInfo rowInf = new RowInfo();

                    // Get collection of cells.
                    Elements cells = row.getElementsByTag("td");

                    for (Element cell : cells) {
                        // Determine row span and colspan of the current cell.
                        String colSpanAttr = cell.attributes().get("colspan");
                        String rowSpanAttr = cell.attributes().get("rowspan");

                        int colSpan = StringUtils.isNotBlank(colSpanAttr) ? Integer.parseInt(colSpanAttr) : 0;
                        int rowSpan = StringUtils.isNotBlank(rowSpanAttr) ? Integer.parseInt(rowSpanAttr) : 0;

                        CellInfo cellInf = new CellInfo(colSpan, rowSpan);
                        rowInf.getCells().add(cellInf);
                    }

                    tableInf.getRows().add(rowInf);
                }

                mTables.add(tableInf);
            }
        }

        public int visitCellStart(Cell cell)
        {
            int tabIdx = mWordTables.indexOf(cell.getParentRow().getParentTable());
            int rowIdx = cell.getParentRow().getParentTable().indexOf(cell.getParentRow());
            int cellIdx = cell.getParentRow().indexOf(cell);

            int colSpan = 0;
            int rowSpan = 0;
            if (tabIdx < mTables.size() &&
                rowIdx < mTables.get(tabIdx).getRows().size() &&
                cellIdx < mTables.get(tabIdx).getRows().get(rowIdx).getCells().size())
            {
                colSpan = mTables.get(tabIdx).getRows().get(rowIdx).getCells().get(cellIdx).getColSpan();
                rowSpan = mTables.get(tabIdx).getRows().get(rowIdx).getCells().get(cellIdx).getRowSpan();
            }

            System.out.println(MessageFormat.format("{0}.{1}.{2} colspan={3}\t rowspan={4}", tabIdx, rowIdx, cellIdx, colSpan, rowSpan));

            return VisitorAction.CONTINUE;
        }

        private ArrayList<TableInfo> mTables = new ArrayList<>();
        private NodeCollection mWordTables;
    }
    //ExEnd:HorizontalAndVerticalMergeHelperClasses

    /**
     * 在后续页面上重复行
     * @throws Exception
     */
    @Test
    public void repeatRowsOnSubsequentPages() throws Exception
    {
        //ExStart:RepeatRowsOnSubsequentPages
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.startTable();
        builder.getRowFormat().setHeadingFormat(true);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        builder.getCellFormat().setWidth(100.0);
        builder.insertCell();
        builder.writeln("Heading row 1");
        builder.endRow();
        builder.insertCell();
        builder.writeln("Heading row 2");
        builder.endRow();

        builder.getCellFormat().setWidth(50.0);
        builder.getParagraphFormat().clearFormatting();

        for (int i = 0; i < 50; i++)
        {
            builder.insertCell();
            builder.getRowFormat().setHeadingFormat(false);
            builder.write("Column 1 Text");
            builder.insertCell();
            builder.write("Column 2 Text");
            builder.endRow();
        }

        doc.save(getArtifactsDir() + "WorkingWithTables.RepeatRowsOnSubsequentPages.docx");
        //ExEnd:RepeatRowsOnSubsequentPages
    }

    /**
     * 自动适应页面宽度
     * @throws Exception
     */
    @Test
    public void autoFitToPageWidth() throws Exception
    {
        //ExStart:AutoFitToPageWidth
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a table with a width that takes up half the page width.
        Table table = builder.startTable();

        builder.insertCell();
        table.setPreferredWidth(PreferredWidth.fromPercent(50.0));
        builder.writeln("Cell #1");

        builder.insertCell();
        builder.writeln("Cell #2");

        builder.insertCell();
        builder.writeln("Cell #3");

        doc.save(getArtifactsDir() + "WorkingWithTables.AutoFitToPageWidth.docx");
        //ExEnd:AutoFitToPageWidth
    }

    /**
     * 首选宽度设置
     * @throws Exception
     */
    @Test
    public void preferredWidthSettings() throws Exception
    {
        //ExStart:PreferredWidthSettings
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Insert a table row made up of three cells which have different preferred widths.
        builder.startTable();

        // Insert an absolute sized cell.
        builder.insertCell();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(40.0));
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.YELLOW);
        builder.writeln("Cell at 40 points width");

        // Insert a relative (percent) sized cell.
        builder.insertCell();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(20.0));
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.BLUE);
        builder.writeln("Cell at 20% width");

        // Insert a auto sized cell.
        builder.insertCell();
        builder.getCellFormat().setPreferredWidth(PreferredWidth.AUTO);
        builder.getCellFormat().getShading().setBackgroundPatternColor(Color.GREEN);
        builder.writeln(
            "Cell automatically sized. The size of this cell is calculated from the table preferred width.");
        builder.writeln("In this case the cell will fill up the rest of the available space.");

        doc.save(getArtifactsDir() + "WorkingWithTables.PreferredWidthSettings.docx");
        //ExEnd:PreferredWidthSettings
    }

    /**
     * 检索首选宽度类型
     * @throws Exception
     */
    @Test
    public void retrievePreferredWidthType() throws Exception
    {
        //ExStart:RetrievePreferredWidthType
        Document doc = new Document(getMyDir() + "Tables.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);
        //ExStart:AllowAutoFit
        table.setAllowAutoFit(true);
        //ExEnd:AllowAutoFit

        Cell firstCell = table.getFirstRow().getFirstCell();
        /*PreferredWidthType*/int type = firstCell.getCellFormat().getPreferredWidth().getType();
        double value = firstCell.getCellFormat().getPreferredWidth().getValue();
        //ExEnd:RetrievePreferredWidthType
    }

    /**
     * 得到表的位置
     * @throws Exception
     */
    @Test
    public void getTablePosition() throws Exception
    {
        //ExStart:GetTablePosition
        Document doc = new Document(getMyDir() + "Tables.docx");

        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);

        if (table.getTextWrapping() == TextWrapping.AROUND)
        {
            System.out.println(table.getRelativeHorizontalAlignment());
            System.out.println(table.getRelativeVerticalAlignment());
        }
        else
        {
            System.out.println(table.getAlignment());
        }
        //ExEnd:GetTablePosition
    }

    @Test
    public void getFloatingTablePosition() throws Exception
    {
        //ExStart:GetFloatingTablePosition
        Document doc = new Document(getMyDir() + "Table wrapped by text.docx");
        
        for (Table table : doc.getFirstSection().getBody().getTables())
        {
            // If the table is floating type, then print its positioning properties.
            if (table.getTextWrapping() == TextWrapping.AROUND)
            {
                System.out.println(table.getHorizontalAnchor());
                System.out.println(table.getVerticalAnchor());
                System.out.println(table.getAbsoluteHorizontalDistance());
                System.out.println(table.getAbsoluteVerticalDistance());
                System.out.println(table.getAllowOverlap());
                System.out.println(table.getAbsoluteHorizontalDistance());
                System.out.println(table.getRelativeVerticalAlignment());
                System.out.println("..............................");
            }
        }
        //ExEnd:GetFloatingTablePosition
    }

    @Test
    public void floatingTablePosition() throws Exception
    {
        //ExStart:FloatingTablePosition
        Document doc = new Document(getMyDir() + "Table wrapped by text.docx");

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        table.setAbsoluteHorizontalDistance(10.0);
        table.setRelativeVerticalAlignment(VerticalAlignment.CENTER);

        doc.save(getArtifactsDir() + "WorkingWithTables.FloatingTablePosition.docx");
        //ExEnd:FloatingTablePosition
    }

    @Test
    public void setRelativeHorizontalOrVerticalPosition() throws Exception
    {
        //ExStart:SetRelativeHorizontalOrVerticalPosition
        Document doc = new Document(getMyDir() + "Table wrapped by text.docx");

        Table table = doc.getFirstSection().getBody().getTables().get(0);
        table.setHorizontalAnchor(RelativeHorizontalPosition.COLUMN);
        table.setVerticalAnchor(RelativeVerticalPosition.PAGE);

        doc.save(getArtifactsDir() + "WorkingWithTables.SetFloatingTablePosition.docx");
        //ExEnd:SetRelativeHorizontalOrVerticalPosition
    }
}
