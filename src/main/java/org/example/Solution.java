
package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;

class Solution{

    public String path;

    public Solution(String path){
        this.path = path;
    }

    public void build(){
        try {
            File inputFile = new File(this.path);
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);
            document.getDocumentElement().normalize();

            StringBuilder classContent = new StringBuilder();
            String className = process(classContent, document);

            FileWriter fWriter = new FileWriter(System.getProperty("user.dir") + File.separator +  "src" + File.separator + "main" + File.separator + "java" + File.separator + "org" + File.separator + "example" + File.separator + className + ".java");
            fWriter.write(classContent.toString());

            fWriter.flush();
            fWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private String process(StringBuilder classContent, Document document){
        Element root = document.getDocumentElement();

        String className = root.getAttribute("type");

        classContent.append("public class ").append(className).append("{\n\n");

        NodeList rootChildNodes = root.getChildNodes();
        for (int i = 0; i < rootChildNodes.getLength(); i++) {
            Node currentNode = rootChildNodes.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) currentNode;
                String fieldName = childElement.getTagName();
                String fieldType = getTypeFromValue(childElement.getTextContent());
                classContent.append("  private ").append(fieldType + " ").append(fieldName).append(";\n");
            }
        }

        for (int i = 0; i < rootChildNodes.getLength(); i++) {
            Node node = rootChildNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) node;
                String fieldName = childElement.getTagName();
                String fieldType = getTypeFromValue(childElement.getTextContent());
                classContent.append("\n  public ").append(fieldType + " get").append(fieldName.substring(0, 1).toUpperCase())
                        .append(fieldName.substring(1)).append("() {\n")
                        .append("    return ").append(fieldName).append(";\n  }\n\n")
                        .append("  public void set").append(fieldName.substring(0, 1).toUpperCase())
                        .append(fieldName.substring(1)).append("(" + fieldType + " ").append(fieldName).append(") {\n")
                        .append("    this.").append(fieldName).append(" = ").append(fieldName).append(";\n  }\n");
            }
        }
        classContent.append("}");

        return className;
    }

    private String getTypeFromValue(String input){
        try {
            int intValue = Integer.parseInt(input);
            return "Integer";
        } catch (NumberFormatException e1) {
            try {
                double doubleValue = Double.parseDouble(input);
                return "Double";
            } catch (NumberFormatException e2) {
                return "String";
            }
        }
    }

}