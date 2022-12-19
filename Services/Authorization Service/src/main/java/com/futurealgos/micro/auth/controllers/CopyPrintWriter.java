/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.controllers;

import java.io.PrintWriter;
import java.io.Writer;

public class CopyPrintWriter extends PrintWriter {

    private StringBuilder copy = new StringBuilder();

    public CopyPrintWriter(Writer writer) {
        super(writer);
    }

    @Override
    public void write(int c) {
        copy.append((char) c); // It is actually a char, not an int.
        super.write(c);
    }

    @Override
    public void write(char[] chars, int offset, int length) {
        copy.append(chars, offset, length);
        super.write(chars, offset, length);
    }

    @Override
    public void write(String string, int offset, int length) {
        copy.append(string, offset, length);
        super.write(string, offset, length);
    }

    public String getCopy() {
        return copy.toString();
    }

}