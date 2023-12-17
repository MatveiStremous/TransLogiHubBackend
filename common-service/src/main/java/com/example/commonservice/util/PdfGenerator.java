package com.example.commonservice.util;

import com.example.commonservice.dto.TransportationInfoResponse;
import com.example.commonservice.exception.BusinessException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGenerator {
    private static Font font;
    private static Font boldFont;

    @SneakyThrows
    @PostConstruct
    public void init() {
        BaseFont baseFont = BaseFont.createFont("fonts/TimesNewRomanRegular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        font = new Font(baseFont, 14, Font.NORMAL, BaseColor.BLACK);
        boldFont = new Font(baseFont, 18, Font.BOLD, BaseColor.BLACK);
    }

    public static ByteArrayResource generatePdfByTransportation(TransportationInfoResponse transportation) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            addHeaderToDocument(document, transportation.getId());
            document.add(Chunk.NEWLINE);
            PdfPTable table = createTable(transportation);
            document.add(table);
            document.add(Chunk.NEWLINE);
            addDataToDocument(document);

            document.close();
            byte[] bytes = outputStream.toByteArray();
            return new ByteArrayResource(bytes);
        } catch (DocumentException | IOException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "PDF-1");
        }
    }

    private static void addHeaderToDocument(Document document, Integer id) throws DocumentException {
        Paragraph header = new Paragraph("Сведения о грузоперевозке №" + id, boldFont);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
    }

    private static void addDataToDocument(Document document) throws DocumentException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        Paragraph dateTime = new Paragraph(formattedDateTime, font);
        dateTime.setAlignment(Element.ALIGN_LEFT);
        document.add(dateTime);
    }

    private static PdfPTable createTable(TransportationInfoResponse transportation) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        addTableCell(table, "Номер заказа", transportation.getOrder().getId().toString());
        addTableCell(table, "Наименование груза", transportation.getOrder().getCargoName());
        addTableCell(table, "Масса", transportation.getWeight() + " кг.");
        addTableCell(table, "Загрузка", transportation.getOrder().getLoadingDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + "\n" + transportation.getOrder().getArrivalAddress());
        addTableCell(table, "Разгрузка", transportation.getOrder().getUnloadingDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + "\n" + transportation.getOrder().getDepartureAddress());
        addTableCell(table, "Примечание", transportation.getNote());
        addTableCell(table, "Грузовик", transportation.getTruck() == null ? "Не назначен" :
                transportation.getTruck().getBrand() + " " + transportation.getTruck().getModel() + "\n" + transportation.getTruck().getStateNumber());
        addTableCell(table, "Прицеп", transportation.getTrailer() == null ? "Не назначен" :
                transportation.getTrailer().getBrand() + " " + transportation.getTrailer().getModel() + "\n" + transportation.getTrailer().getStateNumber());
        addTableCell(table, "Водитель", transportation.getDriver() == null ? "Не назначен" :
                transportation.getDriver().getLogin() + "\n" + transportation.getDriver().getPhone() + "\n" + transportation.getDriver().getLastName() + " " + transportation.getDriver().getFirstName());
        addTableCell(table, "Колонна", transportation.getConvoy() == null ? "Не назначена" :
                transportation.getConvoy().getName());

        return table;
    }

    private static void addTableCell(PdfPTable table, String key, String value) {
        table.addCell(new PdfPCell(new Phrase(key, font)));
        table.addCell(new PdfPCell(new Phrase(value, font)));
    }
}
