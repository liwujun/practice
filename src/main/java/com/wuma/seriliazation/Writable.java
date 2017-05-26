package com.wuma.seriliazation;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by user on 17/5/16.
 */
public interface Writable {
    void write(DataOutput out) throws IOException;
    void readFields(DataInput in) throws IOException;
}
