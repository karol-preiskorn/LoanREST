package pl.ultimasolution.loan.controller;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/")
public class IndexController {
    public static String readFileAsString(String fileName)throws Exception
    {
        String data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
    @GetMapping
    public String sayHello() throws Exception {
        Parser parser = Parser.builder().build();
        String data = readFileAsString("README.md");
        Node document = parser.parse(data);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
