package com.ciro.docx4j;

import org.docx4j.XmlUtils;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.util.HashMap;

/**
 * Created by MEV on 2017/4/28.
 */
public class docx4jUtil {
    private static final  String tale_templetate_docx  = "\\PoiDemo\\data\\4.27 济宁市空气质量日报 - 模板 (2).docx";

    private static final  String tale_output_docx  = "\\PoiDemo\\data\\write.docx";

    public static void main(String[] args) throws Exception {
        //加载模板
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
                .load(new java.io.File(System.getProperty("user.dir")+tale_templetate_docx));
        //数据替换预处理，调用API包
        VariablePrepare.prepare(wordMLPackage);
        //准备数据
        HashMap<String, String> mappings = new HashMap<String, String>();
        mappings.put("curr_date_ymd", "2014-02-28");
        mappings.put("city_names", "济宁市");
        mappings.put("pre_date_md", "4月27日");
        mappings.put("pm25_d", "33");
        mappings.put("pm10_d", "86");
        mappings.put("o3_d", "77");
        mappings.put("no2_d", "36");
        mappings.put("so2_d", "24");
        mappings.put("co_d", "0.8");
        mappings.put("aqi_d", "68");
        mappings.put("lev_d","良");
        mappings.put("pri_emision","so2");
//        月-表格
        mappings.put("pm25_m", "33");
        mappings.put("pm10_m", "86");
        mappings.put("o3_m", "77");
        mappings.put("no2_m", "36");
        mappings.put("so2_m", "24");
        mappings.put("co_m", "0.8");
        mappings.put("pm25_m_per", "34");
        mappings.put("pm10_m_per", "86");
        mappings.put("o3_m_per", "77");
        mappings.put("no2_m_per", "36");
        mappings.put("so2_m_per", "24");
        mappings.put("co_m_per", "0.8");
        mappings.put("pm25_m_rank", "33");
        mappings.put("pm10_m_rank", "86");
        mappings.put("o3_m_rank", "77");
        mappings.put("no2_m_rank", "36");
        mappings.put("so2_m_rank", "24");
        mappings.put("co_m_rank", "0.8");
        mappings.put("pm25_m_rank_dym", "33");
        mappings.put("pm10_m_rank_dym", "86");
        mappings.put("o3_m_rank_dym", "77");
        mappings.put("no2_m_rank_dym", "36");
        mappings.put("so2_m_rank_dym", "24");
        mappings.put("co_m_rank_dym", "0.8");
//        月-段落
        mappings.put("month","4月");
        mappings.put("aqi_m", "11");
        mappings.put("aqi_m_rank", "2");
        mappings.put("aqi_m_per_dyms","增长了10%");
        mappings.put("aqi_m_rank_dyms","前进了5名");
        mappings.put("total_m","44");
        mappings.put("total_m_per_dym","同比增长了11%");
        mappings.put("city_type","2+26");
        mappings.put("total_m_rank","3");
//        年-表格
        mappings.put("pm25_y", "33");
        mappings.put("pm10_y", "86");
        mappings.put("o3_y", "77");
        mappings.put("no2_y", "36");
        mappings.put("so2_y", "24");
        mappings.put("co_y", "0.8");
        mappings.put("pm25_y_per", "34");
        mappings.put("pm10_y_per", "84");
        mappings.put("o3_y_per", "74");
        mappings.put("no2_y_per", "34");
        mappings.put("so2_y_per", "24");
        mappings.put("co_y_per", "0.4");
        mappings.put("pm25_y_rank", "35");
        mappings.put("pm10_y_rank", "85");
        mappings.put("o3_y_rank", "75");
        mappings.put("no2_y_rank", "35");
        mappings.put("so2_y_rank", "25");
        mappings.put("co_y_rank", "0.5");
        mappings.put("pm25_y_rank_dym", "1");
        mappings.put("pm10_y_rank_dym", "2");
        mappings.put("o3_y_rank_dym", "3");
        mappings.put("no2_y_rank_dym", "4");
        mappings.put("so2_y_rank_dym", "5");
        mappings.put("co_y_rank_dym", "6");
//        年-段落
        mappings.put("years","2017");
        mappings.put("aqi_year","20");
        mappings.put("aqi_y_per_dym","增长17%");
        mappings.put("aqi_y_rank","2");
        mappings.put("aqi_y_rank_dym","前进了1名");
        mappings.put("pm25_predict","20");

        mappings.put("total_y","11");
        mappings.put("total_y_per_dym","增长20%");
        mappings.put("total_year_rank","1");
        //进行数据合并
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        documentPart.variableReplace(mappings);

        //保存文件
        System.out.println(XmlUtils.marshaltoString(documentPart.getJaxbElement()));
        String filename = System.getProperty("user.dir") + tale_output_docx;
        wordMLPackage.save(new java.io.File(filename) );
    }
}
