package space.tscg.util.text;

import java.util.function.Consumer;

import space.tscg.misc.Predicated;

public class Ansi implements Styler
{
    private static Ansi staticRef;
    
    public static Ansi newBlock()
    {
        var ansi = new Ansi();
        Ansi.staticRef = ansi;
        return ansi;
    }
    
    @SafeVarargs
    public static Ansi newBlock(Consumer<Line>... consumers)
    {
        newBlock();
        for(Consumer<Line> c : consumers)
        {
            var l = Line.create();
            c.accept(l);
            staticRef.line(l);
        }
        return staticRef;
    }

    public enum Color
    {
        GRAY(30),
        RED(31),
        GREEN(32),
        YELLOW(33),
        BLUE(34),
        PINK(35),
        CYAN(36),
        WHITE(37);

        int code;

        Color(int code)
        {
            this.code = code;
        }
    }

    public enum Background
    {
        DARK_BLUE(40),
        ORANGE(41),
        MARBLE_BLUE(42),
        TURQUOISE_GRAY(43),
        GRAY(44),
        INDIGO(45),
        LIGHT_GRAY(46),
        WHITE(47);

        int code;

        Background(int code)
        {
            this.code = code;
        }
    }

    public enum Style
    {
        BOLD(1),
        UNDERLINE(4);

        int code;

        Style(int code)
        {
            this.code = code;
        }
    }

    final TextBuilder builder;
    final static String      RST = "[0;0m";
    final static String      S1 = "[0;%dm";
    final static String      S2 = "[%d;%dm";
    final static String      S3 = "[%d;%d;%dm";

    String reset()
    {
        return S1.formatted(0);
    }

    private Ansi()
    {
        this.builder = new TextBuilder();
        this.builder.appendln("```ansi");
    }

    public void line(Line line)
    {
        this.builder.append(line.toString()).ln();
    }

    @Override
    public String string()
    {
        return toString();
    }
    
    @Override
    public String toString()
    {
        var string = this.builder.toString();
        if(!string.endsWith("\n"))
            string += "\n```";
        else
            string += "```";
        return string;
    }
    
    public static class Line extends TextBuilder {
        
        public static Line create()
        {
            return new Line();
        }
        
        public Line blankLine()
        {
            super.ln();
            return this;
        }
        
        public <T> Line addIf(Predicated<T> predicated, Color color, Object text)
        {
            return (predicated.isTrue()) ? add(color, text) : this;
        }
        
        public Line add(Color color, Object text)
        {
            String toAppend = S1.formatted(color.code);
            toAppend += text.toString();
            super.append(toAppend).append(RST);
            return this;
        }

        public <T> Line addIf(Predicated<T> predicated, Style style, Color color, Object text)
        {
            return (predicated.isTrue()) ? add(style, color, text) : this;
        }
        
        public Line add(Style style, Color color, Object text)
        {
            String toAppend = S2.formatted(style.code, color.code);
            toAppend += text.toString();
            super.append(toAppend).append(RST);
            return this;
        }

        public <T> Line addIf(Predicated<T> predicated, Style style, Background background, Color color, Object text)
        {
            return (predicated.isTrue()) ? add(style, background, color, text) : this;
        }
        
        public Line add(Style style, Background background, Color color, Object text)
        {
            String toAppend = S3.formatted(style.code, background.code, color.code);
            toAppend += text.toString();
            super.append(toAppend).append(RST);
            return this;
        }

        public Line space(int i)
        {
            for (int j = 0; j < i; j++) {
                super.append(" ");
            }
            return this;
        }
        
        @Override
        public synchronized String toString()
        {
            return super.toString();
        }
    }
}
