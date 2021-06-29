package DocsExamples.Programming_with_Documents.Protect_or_Encrypt_Document;

// ********* THIS FILE IS AUTO PORTED *********

import DocsExamples.DocsExamplesBase;
import org.testng.annotations.Test;
import com.aspose.words.Document;
import com.aspose.words.ProtectionType;


class DocumentProtection extends DocsExamplesBase
{
    @Test
    public void protect() throws Exception
    {
        //ExStart:ProtectDocument
        Document doc = new Document(getMyDir() + "Document.docx");
        doc.protect(ProtectionType.ALLOW_ONLY_FORM_FIELDS, "password");
        //ExEnd:ProtectDocument
    }

    @Test
    public void unprotect() throws Exception
    {
        //ExStart:UnprotectDocument
        Document doc = new Document(getMyDir() + "Document.docx");
        doc.unprotect();
        //ExEnd:UnprotectDocument
    }

    @Test
    public void getProtectionType() throws Exception
    {
        //ExStart:GetProtectionType
        Document doc = new Document(getMyDir() + "Document.docx");
        /*ProtectionType*/int protectionType = doc.getProtectionType();
        //ExEnd:GetProtectionType
    }
}
