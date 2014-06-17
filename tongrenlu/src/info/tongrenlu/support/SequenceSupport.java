package info.tongrenlu.support;

import info.tongrenlu.persistence.SequenceMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class SequenceSupport {

    @Autowired
    private SequenceMapper sequenceMapper = null;

    public String getNextId() {
        final String id = DateFormatUtils.format(System.currentTimeMillis(),
                                                 "yyyyMMdd")
                          + StringUtils.leftPad(this.sequenceMapper.getNextValue(),
                                                7,
                                                "0");
        return id;
    }
}
