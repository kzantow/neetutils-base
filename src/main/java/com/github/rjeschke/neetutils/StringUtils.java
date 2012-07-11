/*
 * Copyright (C) 2012 René Jeschke <rene_jeschke@yahoo.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rjeschke.neetutils;

/**
 * String utility methods.
 * 
 * @author René Jeschke (rene_jeschke@yahoo.de)
 */
public class StringUtils
{
    /**
     * Produces a in Java usable string representation of the given String,
     * wrapping it in quotes and escaping all relevant characters.
     * 
     * @param str
     *            The String to escape.
     * @return Escape String or &quote;null&quote; if str is <code>null</code>
     */
    public static String escapeString(String str)
    {
        if(str == null)
            return "null";

        final StringBuilder sb = new StringBuilder();

        sb.append('"');
        for(int i = 0; i < str.length(); i++)
        {
            final char c = str.charAt(i);
            switch(c)
            {
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '"':
                sb.append("\\\"");
                break;
            default:
                sb.append(c);
                break;
            }
        }
        sb.append('"');

        return sb.toString();
    }
}