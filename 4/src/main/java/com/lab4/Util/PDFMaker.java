package com.lab4.Util;

import com.lab4.University.Group;
import com.lab4.University.Lectures;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PDFMaker {
    private static final int tableCellHeight = 15;
    private static final int tableCellWidth = 20;
    private static final int firstTableCellWidth = 200;
    private static final int pagePadding = 50;
    private static final String fontFilepath = "/usr/share/fonts/truetype/ubuntu/Ubuntu-C.ttf";

    public static void groupToPDF(Group group) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            File file = new File(fontFilepath);
            PDFont font = PDTrueTypeFont.loadTTF(document, file);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setStrokingColor(Color.BLACK);
            int x = pagePadding;
            int y = (int)page.getTrimBox().getHeight() - pagePadding;
            int rowCount = group.getStudentCount();
            int colCount = Lectures.COUNT + 1;

            for (int i = 0; i < colCount; i++) {
                String text = "";
                if (i == 0) {
                    contentStream.addRect(x, y, firstTableCellWidth, -tableCellHeight);
                    x += firstTableCellWidth;
                    text = "Student";
                    contentStream.beginText();
                    contentStream.newLineAtOffset(x - firstTableCellWidth + 10, y - tableCellHeight + 4);
                } else {
                    contentStream.addRect(x, y, tableCellWidth, -tableCellHeight);
                    x += tableCellWidth;
                    text = "L" + i;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(x - 18, y - tableCellHeight + 4);
                }
                contentStream.setFont(font, 12);
                contentStream.showText(text);
                contentStream.endText();
            }

            x = pagePadding;
            y -= tableCellHeight;

            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    String text = "";
                    if (j == 0) {
                        contentStream.addRect(x, y, firstTableCellWidth, -tableCellHeight);
                        x += firstTableCellWidth;
                        text = group.getStudents().get(i).getFullName();
                        contentStream.beginText();
                        contentStream.newLineAtOffset(x - firstTableCellWidth + 10, y - tableCellHeight + 4);
                    } else {
                        contentStream.addRect(x, y, tableCellWidth, -tableCellHeight);
                        x += tableCellWidth;
                        if (!group.getStudents().get(i).getAttendance()[j - 1].isSelected())
                            text = "n";
                        contentStream.beginText();
                        contentStream.newLineAtOffset(x - 12, y - tableCellHeight + 4);
                    }
                    contentStream.setFont(font, 12);
                    contentStream.showText(text);
                    contentStream.endText();
                }
                x = pagePadding;
                y -= tableCellHeight;
            }
            contentStream.stroke();
            contentStream.close();

            document.save( "Group_" + group.getNumber() + "_attendance.pdf");
            document.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
