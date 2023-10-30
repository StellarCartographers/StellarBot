package space.tscg.util.text;

import java.util.function.Consumer;

import space.tscg.misc.Predicated;

public class CodeBlock implements Styler
{
    private static CodeBlock staticRef;
    
    public static CodeBlock style(String code)
    {
        var ansi = new CodeBlock(code);
        CodeBlock.staticRef = ansi;
        return ansi;
    }
    
    @SafeVarargs
    public static CodeBlock newBlock(String code, Consumer<Line>... consumers)
    {
        newBlock(code);
        for(Consumer<Line> c : consumers)
        {
            var l = Line.create();
            c.accept(l);
            staticRef.line(l);
        }
        return staticRef;
    }

    final TextBuilder builder;

    private CodeBlock(String code)
    {
        this.builder = new TextBuilder();
        this.builder.appendln("```" + code);
    }

    public CodeBlock line(Line line)
    {
        this.builder.append(line.toString()).ln();
        return this;
    }
    
    public CodeBlock line(String text)
    {
        this.line(Line.create().add(text));
        return this;
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
        
        public <T> Line addIf(Predicated<T> predicated, Object text)
        {
            return (predicated.isTrue()) ? add(text) : this;
        }
        
        public Line add(Object text)
        {
            super.append(text.toString());
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
