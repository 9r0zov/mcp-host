package com.grozoww.mcphost.util;

import java.util.regex.Pattern;

public final class InstaTextUtils {

    private static final Pattern CODE_BLOCK = Pattern.compile("```[\\s\\S]*?```");
    private static final Pattern LIST_MARKER = Pattern.compile("(?m)^\\s*[-*•]\\s*");
    private static final Pattern MULTI_WS    = Pattern.compile("\\s{2,}");
    private static final Pattern BLANK_LINES = Pattern.compile("(?m)^[ \t]*\n");

    public static String clean(String raw) {
        String t = raw;

        t = CODE_BLOCK.matcher(t).replaceAll("");
        t = LIST_MARKER.matcher(t).replaceAll("");
        t = t.replaceAll("\\r?\\n+", " ");
        t = MULTI_WS.matcher(t).replaceAll(" ");
        t = BLANK_LINES.matcher(t).replaceAll("");

        return t.trim();
    }
}

