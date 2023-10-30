/**
 * Copyright (C) 2020 ROMVoid95
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package space.tscg.util.text;

public class TextBuilder
{
    private transient StringBuffer buffer;

    private TextBuilder(StringBuffer TextBuilder)
    {
        this.buffer = TextBuilder;
    }

    public TextBuilder()
    {
        this(new StringBuffer(16));
    }

    public TextBuilder(int capacity)
    {
        this(new StringBuffer(capacity));
    }

    public TextBuilder(String str)
    {
        this(new StringBuffer(str));
    }

    public TextBuilder(CharSequence seq)
    {
        this(new StringBuffer(seq));
    }

    public synchronized int compareTo(TextBuilder another)
    {
        return this.buffer.compareTo(another.buffer);
    }

    public synchronized int length()
    {
        return this.buffer.length();
    }

    public synchronized int capacity()
    {
        return this.buffer.capacity();
    }

    public synchronized void ensureCapacity(int minimumCapacity)
    {
        this.buffer.ensureCapacity(minimumCapacity);
    }

    public synchronized void trimToSize()
    {
        this.buffer.trimToSize();
    }

    public synchronized void setLength(int newLength)
    {
        this.buffer.setLength(newLength);
    }

    public synchronized char charAt(int index)
    {
        return this.buffer.charAt(index);
    }

    public synchronized int codePointAt(int index)
    {
        return this.buffer.codePointAt(index);
    }

    public synchronized int codePointBefore(int index)
    {
        return this.buffer.codePointBefore(index);
    }

    public synchronized int codePointCount(int beginIndex, int endIndex)
    {
        return this.buffer.codePointCount(beginIndex, endIndex);
    }

    public synchronized int offsetByCodePoints(int index, int codePointOffset)
    {
        return this.buffer.offsetByCodePoints(index, codePointOffset);
    }

    public synchronized void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
    {
        this.buffer.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public synchronized void setCharAt(int index, char ch)
    {
        this.buffer.setCharAt(index, ch);
    }

    public synchronized TextBuilder append(Object obj)
    {
        this.buffer.append(String.valueOf(obj));
        return this;
    }

    public synchronized TextBuilder append(String str)
    {
        this.buffer.append(str);
        return this;
    }

    public synchronized TextBuilder append(TextBuilder sb)
    {
        this.buffer.append(sb);
        return this;
    }

    public synchronized TextBuilder append(CharSequence s)
    {
        this.buffer.append(s);
        return this;
    }

    public synchronized TextBuilder append(CharSequence s, int start, int end)
    {
        this.buffer.append(s, start, end);
        return this;
    }

    public synchronized TextBuilder append(char[] str)
    {
        this.buffer.append(str);
        return this;
    }

    public synchronized TextBuilder append(char[] str, int offset, int len)
    {
        this.buffer.append(str, offset, len);
        return this;
    }

    public synchronized TextBuilder append(boolean b)
    {
        this.buffer.append(b);
        return this;
    }

    public synchronized TextBuilder append(char c)
    {
        this.buffer.append(c);
        return this;
    }

    public synchronized TextBuilder append(int i)
    {
        this.buffer.append(i);
        return this;
    }

    public synchronized TextBuilder appendCodePoint(int codePoint)
    {
        this.buffer.appendCodePoint(codePoint);
        return this;
    }

    public synchronized TextBuilder append(long lng)
    {
        this.buffer.append(lng);
        return this;
    }

    public synchronized TextBuilder append(float f)
    {
        this.buffer.append(f);
        return this;
    }

    public synchronized TextBuilder append(double d)
    {
        this.buffer.append(d);
        return this;
    }

    public synchronized TextBuilder appendln(String str)
    {
        append(str).ln();
        return this;
    }
    
    public synchronized TextBuilder ln(int i)
    {
        for (int j = 0; j < i; j++) {
            ln();
        }
        return this;
    }
    
    public synchronized TextBuilder ln()
    {
        this.buffer.append("\n");
        return this;
    }
    
    public synchronized TextBuilder nextLine(String str)
    {
        ln().append(str);
        return this;
    }

    public synchronized TextBuilder delete(int start, int end)
    {
        this.buffer.delete(start, end);
        return this;
    }

    public synchronized TextBuilder deleteCharAt(int index)
    {
        this.buffer.deleteCharAt(index);
        return this;
    }

    public synchronized TextBuilder replace(int start, int end, String str)
    {
        this.buffer.replace(start, end, str);
        return this;
    }

    public synchronized String substring(int start)
    {
        return substring(start, this.buffer.length());
    }

    public synchronized CharSequence subSequence(int start, int end)
    {
        return this.buffer.substring(start, end);
    }

    public synchronized String substring(int start, int end)
    {
        return this.buffer.substring(start, end);
    }

    public synchronized TextBuilder insert(int index, char[] str, int offset, int len)
    {
        this.buffer.insert(index, str, offset, len);
        return this;
    }

    public synchronized TextBuilder insert(int offset, Object obj)
    {
        this.buffer.insert(offset, String.valueOf(obj));
        return this;
    }

    public synchronized TextBuilder insert(int offset, String str)
    {
        this.buffer.insert(offset, str);
        return this;
    }

    public synchronized TextBuilder insert(int offset, char[] str)
    {
        this.buffer.insert(offset, str);
        return this;
    }

    public synchronized TextBuilder insert(int dstOffset, CharSequence s, int start, int end)
    {
        this.buffer.insert(dstOffset, s, start, end);
        return this;
    }

    public synchronized TextBuilder insert(int offset, char c)
    {
        this.buffer.insert(offset, c);
        return this;
    }

    public synchronized int indexOf(String str, int fromIndex)
    {
        return this.buffer.indexOf(str, fromIndex);
    }

    public synchronized int lastIndexOf(String str, int fromIndex)
    {
        return this.buffer.lastIndexOf(str, fromIndex);
    }

    public synchronized TextBuilder reverse()
    {
        this.buffer.reverse();
        return this;
    }

    public synchronized String toString()
    {
        return this.buffer.toString();
    }
}
