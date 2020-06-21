package com.crm.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class InvoiceGenerator {
	private Context mContext;
    private InvoiceData mData;
    public InvoiceGenerator(Context context, InvoiceData data) {
    	mContext = context;
    	mData = data;
        createPDF(data.getPath());
    }

	public void createPDF (String dest){

		if (new File(dest).exists()) {
			new File(dest).delete();
		}

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(dest));

			InputStream inputStream = mContext.getAssets().open("ic_account_circle_white.png");
			Bitmap bmp = BitmapFactory.decodeStream(inputStream);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			Image image = Image.getInstance(stream.toByteArray());

			image.scaleAbsolute(75f, 75f);//image width,height

			PdfPTable irdTable = new PdfPTable(2);
			irdTable.addCell(getIRDCell("Invoice No"));
			irdTable.addCell(getIRDCell("Invoice Date"));
			Random rnd = new Random();
			int random = 100000 + rnd.nextInt(900000);
			irdTable.addCell(getIRDCell(String.valueOf(random))); // pass invoice number
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			irdTable.addCell(getIRDCell(formatter.format(new Date()))); // pass invoice date


			PdfPTable irhTable = new PdfPTable(3);
			irhTable.setWidthPercentage(100);

			irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
			irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
			irhTable.addCell(getIRHCell("Invoice", PdfPCell.ALIGN_RIGHT));
			irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
			irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
			PdfPCell invoiceTable = new PdfPCell(irdTable);
			invoiceTable.setBorder(0);
			irhTable.addCell(invoiceTable);

			FontSelector fs = new FontSelector();
			Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);
			fs.addFont(font);
			Phrase bill = fs.process("Bill To"); // customer information
            Paragraph contactName = new Paragraph(mData.getContactPerson());
            contactName.setIndentationLeft(20);

			Paragraph companyName = new Paragraph(mData.getCompanyName());
            companyName.setIndentationLeft(20);

          /*  Paragraph contact = new Paragraph(mData.getCompanyName());
            contact.setIndentationLeft(20);*/
			Paragraph address1 = new Paragraph(mData.getAddressFirstLine());
            address1.setIndentationLeft(20);

			Paragraph address2 = new Paragraph(mData.getAddressSecondLine());
			address2.setIndentationLeft(20);

			PdfPTable billTable = new PdfPTable(6); //one page contains 15 records
			billTable.setWidthPercentage(100);
			billTable.setWidths(new float[] { 1, 2,5,2,1,2 });
			billTable.setSpacingBefore(30.0f);
			billTable.addCell(getBillHeaderCell("Index"));
			billTable.addCell(getBillHeaderCell("Item"));
			billTable.addCell(getBillHeaderCell("Description"));
			billTable.addCell(getBillHeaderCell("Unit Price"));
			billTable.addCell(getBillHeaderCell("Qty"));
			billTable.addCell(getBillHeaderCell("Amount"));

			billTable.addCell(getBillRowCell("1"));
			billTable.addCell(getBillRowCell("Mobile"));
			billTable.addCell(getBillRowCell("Nokia Lumia 610 \n IMI:WQ361989213 "));
			billTable.addCell(getBillRowCell("12000.0"));
			billTable.addCell(getBillRowCell("1"));
			billTable.addCell(getBillRowCell("12000.0"));

			billTable.addCell(getBillRowCell("2"));
			billTable.addCell(getBillRowCell("Accessories"));
			billTable.addCell(getBillRowCell("Nokia Lumia 610 Panel \n Serial:TIN3720 "));
			billTable.addCell(getBillRowCell("200.0"));
			billTable.addCell(getBillRowCell("1"));
			billTable.addCell(getBillRowCell("200.0"));


			billTable.addCell(getBillRowCell("3"));
			billTable.addCell(getBillRowCell("Other"));
			billTable.addCell(getBillRowCell("16Gb Memorycard \n Serial:UR8531 "));
			billTable.addCell(getBillRowCell("420.0"));
			billTable.addCell(getBillRowCell("1"));
			billTable.addCell(getBillRowCell("420.0"));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			billTable.addCell(getBillRowCell(" "));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));
			billTable.addCell(getBillRowCell(""));

			PdfPTable validity = new PdfPTable(1);
			validity.setWidthPercentage(100);
			validity.addCell(getValidityCell(" "));
			validity.addCell(getValidityCell("Warranty"));
			validity.addCell(getValidityCell(" * Products purchased comes with 1 year national warranty \n   (if applicable)"));
			validity.addCell(getValidityCell(" * Warranty should be claimed only from the respective manufactures"));		    
			PdfPCell summaryL = new PdfPCell(validity);
			summaryL.setColspan (3);
			summaryL.setPadding (1.0f);	                   
			billTable.addCell(summaryL);

			PdfPTable accounts = new PdfPTable(2);
			accounts.setWidthPercentage(100);
			accounts.addCell(getAccountsCell("Subtotal"));
			accounts.addCell(getAccountsCellR("12620.00"));
			accounts.addCell(getAccountsCell("Discount (10%)"));
			accounts.addCell(getAccountsCellR("1262.00"));
			accounts.addCell(getAccountsCell("Tax(2.5%)"));
			accounts.addCell(getAccountsCellR("315.55"));
			accounts.addCell(getAccountsCell("Total"));
			accounts.addCell(getAccountsCellR("11673.55"));			
			PdfPCell summaryR = new PdfPCell(accounts);
			summaryR.setColspan (3);         
			billTable.addCell(summaryR);  

			PdfPTable describer = new PdfPTable(1);
			describer.setWidthPercentage(100);
			describer.addCell(getdescCell(" "));
			describer.addCell(getdescCell("Goods once sold will not be taken back or exchanged || Subject to product justification || Product damage no one responsible || "
					+ " Service only at concarned authorized service centers"));	

			document.open();//PDF document opened........	

			document.add(image);
			document.add(irhTable);
			document.add(bill);
            document.add(contactName);
			document.add(companyName);
			document.add(address1);
			document.add(address2);
			document.add(billTable);
			document.add(describer);

			document.close();

			Toast.makeText(mContext, "Created... :)", Toast.LENGTH_SHORT).show();

			FileUtils.openFile(mContext, new File(dest));

//			file.close();


			System.out.println("Pdf created successfully..");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setHeader() {

	}


	public static PdfPCell getIRHCell(String text, int alignment) {
		FontSelector fs = new FontSelector();
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
		/*	font.setColor(BaseColor.GRAY);*/
		fs.addFont(font);
		Phrase phrase = fs.process(text);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setPadding(5);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static PdfPCell getIRDCell(String text) {
		PdfPCell cell = new PdfPCell(new Paragraph(text));
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		cell.setPadding (5.0f);
		cell.setBorderColor(BaseColor.LIGHT_GRAY);
		return cell;
	}

	public static PdfPCell getBillHeaderCell(String text) {
		FontSelector fs = new FontSelector();
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
		font.setColor(BaseColor.GRAY);
		fs.addFont(font);
		Phrase phrase = fs.process(text);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		cell.setPadding (5.0f);
		return cell;
	}

	public static PdfPCell getBillRowCell(String text) {
		PdfPCell cell = new PdfPCell(new Paragraph(text));
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		cell.setPadding (5.0f);
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthTop(0);
		return cell;
	}

	public static PdfPCell getBillFooterCell(String text) {
		PdfPCell cell = new PdfPCell(new Paragraph(text));
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		cell.setPadding (5.0f);
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthTop(0);
		return cell;
	}

	public static PdfPCell getValidityCell(String text) {
		FontSelector fs = new FontSelector();
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
		font.setColor(BaseColor.GRAY);
		fs.addFont(font);
		Phrase phrase = fs.process(text);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setBorder(0);
		return cell;
	}

	public static PdfPCell getAccountsCell(String text) {
		FontSelector fs = new FontSelector();
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
		fs.addFont(font);
		Phrase phrase = fs.process(text);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setBorderWidthRight(0);
		cell.setBorderWidthTop(0);
		cell.setPadding (5.0f);
		return cell;
	}
	public static PdfPCell getAccountsCellR(String text) {
		FontSelector fs = new FontSelector();
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
		fs.addFont(font);
		Phrase phrase = fs.process(text);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setBorderWidthLeft(0);
		cell.setBorderWidthTop(0);
		cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
		cell.setPadding (5.0f);
		cell.setPaddingRight(20.0f);
		return cell;
	}

	public static PdfPCell getdescCell(String text) {
		FontSelector fs = new FontSelector();
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
		font.setColor(BaseColor.GRAY);
		fs.addFont(font);
		Phrase phrase = fs.process(text);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		cell.setBorder(0);
		return cell;
	}

}