package com.lizo.demo.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.station.Station;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class FileWrite implements Station {
    private String filePath;
    private List<String> writeDataFormContext;

    @Override
    public void doBusiness(Bus bus) {
        StringBuilder sb = new StringBuilder();
        if (writeDataFormContext != null) {
            for (String s : writeDataFormContext) {
                sb.append(bus.getContest(s) + "\n");
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            bufferedWriter.write(sb.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            bus.occurException(e);
        }

    }

    @Override
    public String getName() {
        return null;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getWriteDataFormContext() {
        return writeDataFormContext;
    }

    public void setWriteDataFormContext(List<String> writeDataFormContext) {
        this.writeDataFormContext = writeDataFormContext;
    }
}
