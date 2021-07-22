/*
 * $Revision$
 * $Date$
 *
 */
package org.opoo.apps.dv.util;

import com.lowagie.text.pdf.PdfReader;
import org.apache.commons.io.IOUtils;
import org.opoo.apps.dv.ConversionException;
import org.opoo.apps.util.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.*;

public class FileFormats
{
    public static final String PRESENTATION_FILTER = "impress_pdf_Export";
    public static final String SPREADSHEET_FILTER = "calc_pdf_Export";
    public static final String DOCUMENT_FILTER = "writer_pdf_Export";
    //TODO ??
    public static final String TEXT_FILTER = "Text (encoded)";
    //TODO ??
    public static final String TEXT_FILTER_OPTIONS = "DONTKNOW";

    /* TODO add all mime types ??
    "application/vnd.ms-word.document.macroEnabled.12");
        add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        add("application/vnd.ms-word.template.macroEnabled.12");
        add("application/vnd.openxmlformats-officedocument.wordprocessingml.template");
        add("application/vnd.ms-powerpoint.template.macroEnabled.12");
        add("application/vnd.openxmlformats-officedocument.presentationml.template");
        add("application/vnd.ms-powerpoint.addin.macroEnabled.12");
        add("application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
        add("application/vnd.openxmlformats-officedocument.presentationml.slideshow");
        add("application/vnd.ms-powerpoint.presentation.macroEnabled.12");
        add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
        add("application/vnd.ms-excel.addin.macroEnabled.12");
        add("application/vnd.ms-excel.sheet.binary.macroEnabled.12");
        add("application/vnd.ms-excel.sheet.macroEnabled.12");
        add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        add("application/vnd.ms-excel.template.macroEnabled.12");
        add("application/vnd.openxmlformats-officedocument.spreadsheetml.template");
     */


	public enum DocumentType {
		PRESENTATION
			("slide",
			 "slides",
			 PRESENTATION_FILTER,
			 new String[] { "ppt", "pot", "pps" },
			 new String[] { "pptm", "pptx", "ppsm", "ppsx", "potx", "potm" },
			 new String[] {},
			 "odp",
             new String[] {"application/vnd.ms-powerpoint", "application/vnd.ms-office"},
             new String[] {"application/vnd.openxmlformats-officedocument.presentationml.presentation"} ),

		SPREADSHEET
			("worksheet",
			 "worksheets",
			 SPREADSHEET_FILTER,
			 new String[] { "xls" },
			 new String[] { "xlsx", "xlsm" },
			 new String[] {},
			 "ods",
             new String[] {"application/vnd.ms-excel", "application/vnd.ms-office"},
             new String[] {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}),

		DOCUMENT
			("page",
			 "pages",
			 DOCUMENT_FILTER,
			 new String[] { "doc" },
			 new String[] { "docx", "docm" },
			 new String[] {},
			 "odt",
             new String[] {"application/vnd.ms-word", "application/vnd.ms-office"},
             new String[] {"application/vnd.openxmlformats-officedocument.wordprocessingml.document"}),

		DOCUMENT_SIMPLE
			("page",
			 "pages",
			 DOCUMENT_FILTER,
			 new String[] {},
			 new String[] {},
			 new String[] {"txt", "rtf"},
			 "odt",
             new String[] {},
             new String[] {}),

        DOCUMENT_PDF
			("page",
			 "pages",
			 DOCUMENT_FILTER,
			 new String[] {},
			 new String[] {},
			 new String[] {"pdf"},
			 "",
             new String[] {"application/pdf"},
             new String[] {"application/pdf"});


		private DocumentType(
				String singularDescriptor,
				String pluralDescriptor,
				String converterFilterName,
				String[] office2003Extensions,
				String[] office2007Extensions,
				String[] otherExtensions,
				String openDocumentExtension,
                String[] office2003MimeTypes,
                String[] office2007MimeTypes) {
			this.sectionDescriptorSingular = singularDescriptor;
			this.sectionDescriptorPlural = pluralDescriptor;
			this.converterFilterName = converterFilterName;

			this.office2003Extensions = office2003Extensions;
			this.office2007Extensions = office2007Extensions;
			this.otherExtensions = otherExtensions;

			this.openDocumentExtension = openDocumentExtension;

			this.fullExtensionArray = new String[this.office2003Extensions.length +
			                                     this.office2007Extensions.length +
			                                     this.otherExtensions.length];
			System.arraycopy(this.office2003Extensions, 0, this.fullExtensionArray, 0,
					this.office2003Extensions.length);
			System.arraycopy(this.office2007Extensions, 0, this.fullExtensionArray,
					this.office2003Extensions.length, this.office2007Extensions.length);
			System.arraycopy(this.otherExtensions, 0, this.fullExtensionArray,
					this.office2003Extensions.length + this.office2007Extensions.length, this.otherExtensions.length);

            this.office2003MimeTypes = office2003MimeTypes;
			this.office2007MimeTypes = office2007MimeTypes;
            this.fullMimeTypeArray = new String[this.office2003MimeTypes.length +
			                                     this.office2007MimeTypes.length];
            System.arraycopy(this.office2003MimeTypes, 0, this.fullMimeTypeArray, 0,
					this.office2003MimeTypes.length);
			System.arraycopy(this.office2007MimeTypes, 0, this.fullMimeTypeArray,
					this.office2003MimeTypes.length, this.office2007MimeTypes.length);


		}

		private String sectionDescriptorSingular = null;
		private String sectionDescriptorPlural = null;

		private String[] office2003Extensions = null;
		private String[] office2007Extensions = null;
		private String[] otherExtensions = null;
		private String[] fullExtensionArray = null;
		private String converterFilterName = null;

		private String openDocumentExtension = null;
        private String[] office2003MimeTypes = null;
        private String[] office2007MimeTypes = null;
		private String[] fullMimeTypeArray = null;


		public String getSectionDescriptorSingular() { return this.sectionDescriptorSingular; }
		public String getSectionDescriptorPlural() { return this.sectionDescriptorPlural; }
		public String[] get2007Extensions() { return this.office2007Extensions; }
		public String[] get2003Extensions() { return this.office2003Extensions; }
		public String[] getOtherExtensions() { return this.otherExtensions; }
		public String[] getExtensions() { return this.fullExtensionArray; }
		public String getConverterFilterName() { return this.converterFilterName; }
		public String getOpenDocumentExtension() { return this.openDocumentExtension; }

		public static Set<String> getAllDocumentExtensions() {

			Set<String> allExtensions = new HashSet<String>();
			for (DocumentType type : DocumentType.values())
				allExtensions.addAll(Arrays.asList(type.getExtensions()));

			return allExtensions;
		}

		public static DocumentType parse(String filename) {

			String extension = getExtension(filename);
			return parseExtension(extension);
		}

		public static DocumentType parseExtension(String extension) {

			for (DocumentType type : DocumentType.values()) {

				for (String ext : type.getExtensions())
					if (ext.equalsIgnoreCase(extension))
						return type;
			}

			return null;
		}

        public static DocumentType parseMimeType(String mimeType) {

			for (DocumentType type : DocumentType.values()) {

				for (String ext : type.getFullMimeTypeArray())
					if (ext.equalsIgnoreCase(mimeType))
						return type;
			}

			return null;
		}

		public static boolean isOffice2003Extension(String extension) {
			for (DocumentType type : DocumentType.values()) {
				for (String ext : type.get2003Extensions())
					if (ext.equalsIgnoreCase(extension))
						return true;
			}

			return false;
		}

		public static boolean isOffice2003(String filename) {
			String extension = getExtension(filename);
			return isOffice2003Extension(extension);
		}

		public static boolean isOffice2007Extension(String extension) {
			for (DocumentType type : DocumentType.values()) {
				for (String ext : type.get2007Extensions())
					if (ext.equalsIgnoreCase(extension))
						return true;
			}

			return false;
		}

		public static boolean isOffice2007(String filename) {
			String extension = getExtension(filename);
			return isOffice2007Extension(extension);
		}


        public static boolean isOffice2007MimeType(String mimeType) {
			for (DocumentType type : DocumentType.values()) {
				for (String ext : type.getOffice2007MimeTypes())
					if (ext.equalsIgnoreCase(mimeType))
						return true;
			}

			return false;
		}

        public static boolean isOffice2003MimeType(String mimeType) {
			for (DocumentType type : DocumentType.values()) {
				for (String ext : type.getOffice2003MimeTypes())
					if (ext.equalsIgnoreCase(mimeType))
						return true;
			}

			return false;
		}

        public static boolean isPDFMimeType(String mimeType) {
            for(String type : DocumentType.DOCUMENT_PDF.getFullMimeTypeArray()) {
                if (type.equalsIgnoreCase(mimeType)) {
						return true;
                }
            }
            return false;
        }


        public static boolean isTextFileType(String filename) {
           return filename != null && filename.toLowerCase().endsWith(".txt");
        }

        public static boolean isPDFFileType(String filename) {
           return filename != null && filename.toLowerCase().endsWith(".pdf");
        }

		public static boolean isOtherExtension(String extension) {
			for (DocumentType type : DocumentType.values()) {
				for (String ext : type.getOtherExtensions())
					if (ext.equalsIgnoreCase(extension))
						return true;
			}

			return false;
		}

		public static boolean isOther(String filename) {
			String extension = getExtension(filename);
			return isOtherExtension(extension);
		}

        /**
         * Returns the extension for the given filename
         * @param filename
         * @return
         */
        public static String getExtension(String filename) {
            if (filename == null){
                return null;
            }
            int dot = filename.lastIndexOf(".");
            if (dot > 0) {
                return filename.substring(dot + 1).toLowerCase();
            } else {
                return null;
            }
        }

        public String[] getOffice2003MimeTypes() {
            return office2003MimeTypes;
        }

        public String[] getOffice2007MimeTypes() {
            return office2007MimeTypes;
        }

        public String[] getFullMimeTypeArray() {
            return fullMimeTypeArray;
        }
    }

    /**
     * Determines the number of pages in the given PDF file
     *
     * @param pdfContent
     * @return
     * @throws Exception
     */
    public static int getNumberOfPages(File pdfContent) throws Exception {
        InputStream fis = FileUtils.newFileInputStream(pdfContent);
        try {
            PdfReader reader = new PdfReader(fis);
            int numPages = reader.getNumberOfPages();
            if (numPages == 0) {
                throw new ConversionException(
                        String.format("Could not parse PDF File:%s", pdfContent.getName()));
            }
            return numPages;
        }
        finally {
            IOUtils.closeQuietly(fis);
        }        
    }
}