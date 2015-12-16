package org.tongrenlu.batch;

import java.io.File;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileItemReader implements InitializingBean, ItemReader<File> {

    @Value("${tmlc.path}")
    private String searchPath;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public File read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        return null;
    }

}
