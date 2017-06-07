package com.ciro.docx4j;

import java.util.HashMap;
import java.util.List;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.finders.ClassFinder;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tr;

/**
 * 先替换表格数据再替换其他数据，否则表格替换不起作用。
 *
 */
public class MyExampleCombinationReplaceTextAndTable {

    private static final  String tale_templetate_docx  = "\\PoiDemo\\data\\combination_replace_table_templetate.docx";

    private static final  String tale_output_docx  = "\\PoiDemo\\data\\combination_replace_table__output.docx";

    public static void main(String[] args) throws Exception {
        //加载模板
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
                .load(new java.io.File(System.getProperty("user.dir")+tale_templetate_docx));

        //准备数据
        HashMap<String, String> mappings = new HashMap<String, String>();
        mappings.put("username", "张三");
        mappings.put("party_date", "2014年10月25日");
        mappings.put("numberCount", "150");
        mappings.put("pay_acount", "99.50");
        mappings.put("now_date", "2014年09月25日");

        //进行数据合并
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        ClassFinder finder = new ClassFinder(Tbl.class); // <----- change this to suit
        new TraversalUtil(documentPart.getContent(), finder);
        //查找模板表格（第i个表格 ）
        int seleTableIndex = 0;
        Tbl table_selected =  (Tbl) finder.results.get(seleTableIndex);
        List trs = table_selected.getContent();
        //模板行，第2行为模板行
        int table_templetate_row_index = 2;
        org.docx4j.wml.Tr templetate_row = (Tr)trs.get(table_templetate_row_index);
        String templetate_row_string = XmlUtils.marshaltoString(templetate_row,true,true);
        //System.out.println(templetate_row_string);
        //替换第二行的数据
        List<Object>tds = templetate_row.getContent();
        HashMap datamap = new HashMap();
        datamap.put("table_index", "1");
        datamap.put("product_name", "唐代陶瓷");


        HashMap datamap2 = new HashMap();
        datamap2.put("table_index", "2");
        datamap2.put("product_name", "明代王冠");

        //合并表格数据1
        Object newTr = XmlUtils.unmarshallFromTemplate(templetate_row_string,datamap);
        table_selected.getContent().add(newTr);
        //合并表格数据2
        newTr = XmlUtils.unmarshallFromTemplate(templetate_row_string,datamap2);
        table_selected.getContent().add(newTr);
        //移除第2行
        table_selected.getContent().remove(table_templetate_row_index);

        //数据替换预处理，调用API包
        //在表格替换后调用这个方法
        VariablePrepare.prepare(wordMLPackage);
        documentPart.variableReplace(mappings);
        //保存文件
        String filename = System.getProperty("user.dir") + tale_output_docx;
        wordMLPackage.save(new java.io.File(filename) );
    }
}

