package com.ciro.docx4j;

/**
 * Created by MEV on 2017/5/3.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        String inPath = "C:\\Users\\MEV\\Desktop\\4.27 济宁市空气质量日报 - 模板 (2).docx";
        String outPath="C:\\Users\\MEV\\Desktop\\Hello2.docx";
        Docx4jNew docx4j = new Docx4jNew();
        docx4j.start(inPath,outPath);
    }
}
