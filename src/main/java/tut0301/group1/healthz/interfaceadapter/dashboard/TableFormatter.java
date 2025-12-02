package tut0301.group1.healthz.interfaceadapter.dashboard;

import java.util.Map;

public final class TableFormatter {
    private TableFormatter() {}

    public static String twoColumn(String title, String leftHeader, String rightHeader, Map<String, String> rows) {
        int leftW = leftHeader.length();
        int rightW = rightHeader.length();

        for (var e : rows.entrySet()) {
            leftW  = Math.max(leftW, e.getKey().length());
            rightW = Math.max(rightW, val(e.getValue()).length());
        }

        String sep = "+" + "-".repeat(leftW + 2) + "+" + "-".repeat(rightW + 2) + "+";
        StringBuilder sb = new StringBuilder();
        if (title != null && !title.isBlank()) {
            sb.append("== ").append(title).append(" ==\n");
        }
        sb.append(sep).append("\n");
        sb.append("| ").append(pad(leftHeader, leftW)).append(" | ").append(pad(rightHeader, rightW)).append(" |\n");
        sb.append(sep).append("\n");

        for (var e : rows.entrySet()) {
            sb.append("| ").append(pad(e.getKey(), leftW))
                    .append(" | ").append(pad(val(e.getValue()), rightW)).append(" |\n");
        }
        sb.append(sep);
        return sb.toString();
    }

    private static String pad(String s, int w) {
        if (s == null) s = "";
        if (s.length() >= w) return s;
        return s + " ".repeat(w - s.length());
    }
    private static String val(String v) { return (v == null || v.isBlank()) ? "-" : v; }
}
