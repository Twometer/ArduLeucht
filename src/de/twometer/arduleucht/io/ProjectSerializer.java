package de.twometer.arduleucht.io;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.model.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ProjectSerializer {

    private Project project;

    private Document document;

    public ProjectSerializer(Project project) {
        this.project = project;
    }

    public void serialize() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        this.document = documentBuilder.newDocument();

        Element root = document.createElement("Project");
        document.appendChild(root);

        for (Block block : project.getTopLevelBlocks()) {
            root.appendChild(serializeBlock(block, true));
        }


        File targetFile = project.getDataFile();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(targetFile);
        transformer.transform(domSource, streamResult);
    }

    private Element serializeBlock(Block block, boolean topLevel) {
        Element elem;
        if (block instanceof ConstantBlock) {
            elem = document.createElement("ConstantBlock");
            elem.setAttribute("Value", ((ConstantBlock) block).valueToString());
        } else {
            elem = document.createElement("Block");
            for (BlockSocket socket : block.getSockets()) elem.appendChild(serializeSocket(socket));
        }

        if (topLevel) {
            elem.setAttribute("X", String.valueOf(block.getShape().getX()));
            elem.setAttribute("Y", String.valueOf(block.getShape().getY()));
        }
        elem.setAttribute("Class", block.getClass().getName());

        return elem;
    }

    private Element serializeSocket(BlockSocket socket) {
        Element elem = document.createElement("BlockSocket");
        elem.setAttribute("Name", socket.getName());
        for (Block block : socket.values()) elem.appendChild(serializeBlock(block, false));
        return elem;
    }

}
