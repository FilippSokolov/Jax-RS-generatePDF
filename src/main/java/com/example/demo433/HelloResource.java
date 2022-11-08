package com.example.demo433;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    @GET
    @Path("/generateReport/{name}")
    @Produces("application/pdf")
    public Response generateReport(@PathParam("name") String name) {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        PDFont courierBoldFont = PDType1Font.COURIER_BOLD;
        int fontSize = 12;

        PDPageContentStream contentStream;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(courierBoldFont, fontSize);
            contentStream.newLineAtOffset(150, 750);
            contentStream.showText("Hello " + name);
            contentStream.endText();
            contentStream.close();
            document.save(output);
           // document.save("C:\\Users\\Filipp.Sokolov\\IdeaProjects\\" + name + ".pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response.ResponseBuilder responseBuilder = Response.ok(output.toByteArray());
        responseBuilder.type("application/pdf;charset=utf-8");
        responseBuilder.header("Content-Disposition", "filename=test.pdf");
        return responseBuilder.build();
    }
}