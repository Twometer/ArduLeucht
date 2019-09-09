package de.twometer.arduleucht.io;

import de.twometer.arduleucht.blocks.base.Block;
import de.twometer.arduleucht.blocks.base.ConstantBlock;
import de.twometer.arduleucht.blocks.model.BlockException;
import de.twometer.arduleucht.blocks.model.BlockSocket;
import de.twometer.arduleucht.model.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ProjectDeserializer {

    private File projectFolder;

    public ProjectDeserializer(File projectFolder) {
        this.projectFolder = projectFolder;
    }

    public Project deserialize() throws ParserConfigurationException, IOException, SAXException, IllegalAccessException, InstantiationException, ClassNotFoundException, BlockException {
        File dataFile = Project.getDataFile(projectFolder);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(dataFile);

        Element rootElement = document.getDocumentElement();
        Project project = new Project(projectFolder);

        for (int i = 0; i < rootElement.getChildNodes().getLength(); i++) {
            Element elem = (Element) rootElement.getChildNodes().item(i);
            if (elem.getTagName().equals("Block") || elem.getTagName().equals("ConstantBlock"))
                project.getTopLevelBlocks().add(deserializeBlock(elem));
        }
        return project;
    }

    private Block deserializeBlock(Element blockElement) throws ClassNotFoundException, IllegalAccessException, InstantiationException, BlockException {
        String className = blockElement.getAttribute("Class");
        Block block = (Block) Class.forName(className).newInstance();

        if (blockElement.hasAttribute("X") && blockElement.hasAttribute("Y"))
            block.getShape().setPosition(Integer.parseInt(blockElement.getAttribute("X")), Integer.parseInt(blockElement.getAttribute("Y")));

        if (blockElement.hasAttribute("Value") && block instanceof ConstantBlock)
            ((ConstantBlock) block).valueFromString(blockElement.getAttribute("Value"));


        for (int i = 0; i < blockElement.getChildNodes().getLength(); i++) {
            Element elem = (Element) blockElement.getChildNodes().item(i);
            if (elem.getTagName().equals("BlockSocket")) {
                BlockSocket socket = block.getSocket(elem.getAttribute("Name"));
                socket.values().clear();
                for (int j = 0; j < blockElement.getChildNodes().getLength(); j++) {
                    Element blockElem = (Element) elem.getChildNodes().item(j);
                    if (blockElem == null) continue;
                    socket.addValue(deserializeBlock(blockElem));
                }
            }
        }

        return block;
    }

}
