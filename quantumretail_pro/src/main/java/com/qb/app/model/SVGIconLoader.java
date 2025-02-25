package com.qb.app.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.shape.SVGPath;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;

public class SVGIconLoader {

    public static void loadSVGIcon(SVGPath svgPath, String svgFileLocation) {
        try {
            // Read the SVG file content from the resource folder
            String svgContent;
            try {
                svgContent = new String(Files.readAllBytes(Paths.get(SVGIconLoader.class.getResource(svgFileLocation).toURI())));

                // Extract only the SVG content by removing everything outside of the <svg> tag
                svgContent = extractSVGContent(svgContent);

                // Extract path data
                String pathData = extractPathData(svgContent);

                // Set the extracted path data to the SVGPath
                svgPath.setContent(pathData);

            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error if the file can't be read
        }
    }

    private static String extractSVGContent(String svgContent) {
        // Remove everything outside the <svg> tag, including DOCTYPE and metadata
        String svgTagContent = svgContent.replaceAll("(?s)^.*?<svg", "<svg");
        svgTagContent = svgTagContent.replaceAll("(?s)<\\/svg>.*$", "</svg>");
        return svgTagContent;
    }

    private static String extractPathData(String svgContent) {
        // Extract the 'd' attributes from all <path> elements
        StringBuilder pathData = new StringBuilder();
        try {
            // Parse the SVG content into a DOM document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new java.io.StringReader(svgContent));
            Document doc = builder.parse(is);

            // Get all path elements
            NodeList pathElements = doc.getElementsByTagName("path");

            // Loop through all path elements and append their 'd' attribute values
            for (int i = 0; i < pathElements.getLength(); i++) {
                Node pathNode = pathElements.item(i);
                String dAttribute = pathNode.getAttributes().getNamedItem("d").getTextContent();
                pathData.append(dAttribute).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pathData.toString().trim();
    }
}
