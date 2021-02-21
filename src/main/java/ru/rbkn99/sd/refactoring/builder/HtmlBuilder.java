package ru.rbkn99.sd.refactoring.builder;

public class HtmlBuilder {
    private StringBuilder builder = new StringBuilder();
    private String openTags = "<html><body>\n";
    private String closeTags = "</body></html>\n";

    public void appendText(String str) {
        builder.append(str).append("\n");
    }

    public void appendH1Tag(String str) {
        builder.append("<h1>").append(str).append("</h1>\n");
    }

    public void clear() {
        openTags = "";
        closeTags = "";
        builder = new StringBuilder();
    }

    @Override
    public String toString() {
        return openTags + builder.toString() + closeTags;
    }
}
