package org.tongrenlu;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class FileItemReader implements ItemReader<File> {

    private Iterator<File> files = null;

    public FileItemReader(Collection<File> args) {
        this.files = args.iterator();
    }

    @Override
    public File read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return this.files.hasNext() ? this.files.next() : null;
    }

}
