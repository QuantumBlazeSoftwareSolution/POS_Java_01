package com.qb.app.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class SVGIconGroup extends Group {

    public SVGIconGroup(String svgFileLocation) {
        super();
        loadSVGFromFile(svgFileLocation);
    }

    private void loadSVGFromFile(String svgFileLocation) {
        try (InputStream inputStream = getClass().getResourceAsStream(svgFileLocation)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("SVG file not found: " + svgFileLocation);
            }

            // Parse the SVG content into a Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);

            // Get all <path> elements
            NodeList pathElements = doc.getElementsByTagName("path");

            // Create an SVGPath for each <path> element
            for (int i = 0; i < pathElements.getLength(); i++) {
                Node pathNode = pathElements.item(i);
                if (pathNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element pathElement = (Element) pathNode;

                    // Create an SVGPath object
                    SVGPath svgPath = new SVGPath();

                    // Set the path data (d attribute)
                    String dAttribute = pathElement.getAttribute("d");
                    svgPath.setContent(dAttribute);

                    // Set stroke color (if specified)
                    String strokeColor = pathElement.getAttribute("stroke");
                    if (!strokeColor.isEmpty()) {
                        svgPath.setStroke(Color.web(strokeColor));
                    } else {
                        svgPath.setStroke(Color.TRANSPARENT); // Default stroke color
                    }

                    // Set stroke width (if specified)
                    String strokeWidth = pathElement.getAttribute("stroke-width");
                    if (!strokeWidth.isEmpty()) {
                        svgPath.setStrokeWidth(Double.parseDouble(strokeWidth));
                    } else {
                        svgPath.setStrokeWidth(1.0); // Default stroke width
                    }

                    // Set fill color (if specified)
                    String fillColor = pathElement.getAttribute("fill");
                    if (!fillColor.isEmpty()) {
                        svgPath.setFill(Color.web(fillColor));
                    } else {
                        svgPath.setFill(Color.TRANSPARENT); // Default fill color
                    }

                    defaultOutlineIcon(this);
                    // Add the SVGPath to the Group
                    this.getChildren().add(svgPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the stroke and fill colors of all SVGPath objects in the Group.
     *
     * @param strokeColor The new stroke color.
     * @param fillColor The new fill color.
     */
    public void setIconColor(Color strokeColor, Color fillColor) {
        for (var node : this.getChildren()) {
            if (node instanceof SVGPath) {
                SVGPath svgPath = (SVGPath) node;
                svgPath.setStroke(strokeColor);
                svgPath.setFill(fillColor);
            }
        }
    }

    private void defaultOutlineIcon(Group iconGroup) {
        // Iterate through all children of the Group
        for (var node : iconGroup.getChildren()) {
            if (node instanceof SVGPath) {
                SVGPath svgPath = (SVGPath) node;
                // Set stroke to white
                svgPath.setStroke(Color.WHITE);
                // Set fill to transparent
                svgPath.setFill(Color.TRANSPARENT);
            }
        }
    }
}
