package com.ciro.docx4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;

/**
 * http://stackoverflow.com/questions/20484722/docx4j-how-to-replace-placeholder-with-value
 */
public class Docx4j {

    public static void main(String[] args) throws Docx4JException, IOException, JAXBException {
        String filePath = "C:\\Users\\MEV\\Desktop\\";
        String file = "4.27 济宁市空气质量日报 - 模板 (2).docx";

        Docx4j docx4j = new Docx4j();
        WordprocessingMLPackage template = docx4j.getTemplate(filePath+file);

//      MainDocumentPart documentPart = template.getMainDocumentPart();

        List<Object> texts = getAllElementFromObject(
                template.getMainDocumentPart(), Text.class);
        searchAndReplace(texts, new HashMap<String, String>(){
            {
                this.put("${curr_date_ymd}", "2014-02-28");
                this.put("${city_names}", "济宁市");
                this.put("${pre_date_md}", "4月27日");
                this.put("${pm25_d}", "33");
                this.put("${pm10_d}", "86");
                this.put("${o3_d}", "77");
                this.put("${no2_d}", "36");
                this.put("${so2_d}", "24");
                this.put("${co_d}", "0.8");
                this.put("${aqi_d}", "68");
                this.put("${lev_d}","良");
                this.put("${pri_emision}","so2");
//        月-表格
                this.put("${pm25_m}", "33");
                this.put("${pm10_m}", "86");
                this.put("${o3_m}", "77");
                this.put("${no2_m}", "36");
                this.put("${so2_m}", "24");
                this.put("${co_m}", "0.8");
                this.put("${pm25_m_per}", "34");
                this.put("${pm10_m_per}", "86");
                this.put("${o3_m_per}", "77");
                this.put("${no2_m_per}", "36");
                this.put("${so2_m_per}", "24");
                this.put("${co_m_per}", "0.8");
                this.put("${pm25_m_rank}", "33");
                this.put("${pm10_m_rank}", "86");
                this.put("${o3_m_rank}", "77");
                this.put("${no2_m_rank}", "36");
                this.put("${so2_m_rank}", "24");
                this.put("${co_m_rank}", "0.8");
                this.put("${pm25_m_rank_dym}", "33");
                this.put("${pm10_m_rank_dym}", "86");
                this.put("${o3_m_rank_dym}", "77");
                this.put("${no2_m_rank_dym}", "36");
                this.put("${so2_m_rank_dym}", "24");
                this.put("${co_m_rank_dym}", "0.8");
//        月-段落
                this.put("${month}","4月");
                this.put("${aqi_m}", "11");
                this.put("${aqi_m_rank}", "2");
                this.put("${aqi_m_per_dyms}","增长了10%");
                this.put("${aqi_m_rank_dyms}","前进了5名");
                this.put("${total_m}","44");
                this.put("${total_m_per_dym}","同比增长了11%");
                this.put("${city_type}","2+26");
                this.put("${total_m_rank}","3");
//        年-表格
                this.put("${pm25_y}", "33");
                this.put("${pm10_y}", "86");
                this.put("${o3_y}", "77");
                this.put("${no2_y}", "36");
                this.put("${so2_y}", "24");
                this.put("${co_y}", "0.8");
                this.put("${pm25_y_per}", "34");
                this.put("${pm10_y_per}", "84");
                this.put("${o3_y_per}", "74");
                this.put("${no2_y_per}", "34");
                this.put("${so2_y_per}", "24");
                this.put("${co_y_per}", "0.4");
                this.put("${pm25_y_rank}", "35");
                this.put("${pm10_y_rank}", "85");
                this.put("${o3_y_rank}", "75");
                this.put("${no2_y_rank}", "35");
                this.put("${so2_y_rank}", "25");
                this.put("${co_y_rank}", "0.5");
                this.put("${pm25_y_rank_dym}", "1");
                this.put("${pm10_y_rank_dym}", "2");
                this.put("${o3_y_rank_dym}", "3");
                this.put("${no2_y_rank_dym}", "4");
                this.put("${so2_y_rank_dym}", "5");
                this.put("${co_y_rank_dym}", "6");
//        年-段落
                this.put("${years}","2017");
                this.put("${aqi_year}","20");
                this.put("${aqi_y_per_dym}","增长17%");
                this.put("${aqi_y_rank}","2");
                this.put("${aqi_y_rank_dym}","前进了1名");
                this.put("${pm25_predict}","20");

                this.put("${total_y}","11");
                this.put("${total_y_per_dym}","增长20%");
                this.put("${total_year_rank}","1");
            }
            @Override
            public String get(Object key) {
                // TODO Auto-generated method stub
                return super.get(key);
            }
        });

        docx4j.writeDocxToStream(template, filePath+"Hello2.docx");
    }

    public static void searchAndReplace(List<Object> texts, Map<String, String> values){

        // -- scan all expressions
        // Will later contain all the expressions used though not used at the moment
        List<String> els = new ArrayList<String>();

        StringBuilder sb = new StringBuilder();
        int PASS = 0;
        int PREPARE = 1;
        int READ = 2;
        int mode = PASS;

        // to nullify
        List<int[]> toNullify = new ArrayList<int[]>();
        int[] currentNullifyProps = new int[4];

        // Do scan of els and immediately insert value
        for(int i = 0; i<texts.size(); i++){
            Object text = texts.get(i);
            Text textElement = (Text) text;
            String newVal = "";
            String v = textElement.getValue();
//          System.out.println("text: "+v);
            StringBuilder textSofar = new StringBuilder();
            int extra = 0;
            char[] vchars = v.toCharArray();
            for(int col = 0; col<vchars.length; col++){
                char c = vchars[col];
                textSofar.append(c);
                switch(c){
                    case '$': {
                        mode=PREPARE;
                        sb.append(c);
//                  extra = 0;
                    } break;
                    case '{': {
                        if(mode==PREPARE){
                            sb.append(c);
                            mode=READ;
                            currentNullifyProps[0]=i;
                            currentNullifyProps[1]=col+extra-1;
                            System.out.println("extra-- "+extra);
                        } else {
                            if(mode==READ){
                                // consecutive opening curl found. just read it
                                // but supposedly throw error
                                sb = new StringBuilder();
                                mode=PASS;
                            }
                        }
                    } break;
                    case '}': {
                        if(mode==READ){
                            mode=PASS;
                            sb.append(c);
                            els.add(sb.toString());
                            newVal +=textSofar.toString()
                                    +(null==values.get(sb.toString())?sb.toString():values.get(sb.toString()));
                            textSofar = new StringBuilder();
                            currentNullifyProps[2]=i;
                            currentNullifyProps[3]=col+extra;
                            toNullify.add(currentNullifyProps);
                            currentNullifyProps = new int[4];
                            extra += sb.toString().length();
                            sb = new StringBuilder();
                        } else if(mode==PREPARE){
                            mode = PASS;
                            sb = new StringBuilder();
                        }
                    }
                    default: {
                        if(mode==READ) sb.append(c);
                        else if(mode==PREPARE){
                            mode=PASS;
                            sb = new StringBuilder();
                        }
                    }
                }
            }
            newVal +=textSofar.toString();
            textElement.setValue(newVal);
        }

        // remove original expressions
        if(toNullify.size()>0)
            for(int i = 0; i<texts.size(); i++){
                if(toNullify.size()==0) break;
                currentNullifyProps = toNullify.get(0);
                Object text = texts.get(i);
                Text textElement = (Text) text;
                String v = textElement.getValue();
                StringBuilder nvalSB = new StringBuilder();
                char[] textChars = v.toCharArray();
                for(int j = 0; j<textChars.length; j++){
                    char c = textChars[j];
                    if(null==currentNullifyProps) {
                        nvalSB.append(c);
                        continue;
                    }
                    // I know 100000 is too much!!! And so what???
                    int floor = currentNullifyProps[0]*100000+currentNullifyProps[1];
                    int ceil = currentNullifyProps[2]*100000+currentNullifyProps[3];
                    int head = i*100000+j;
                    if(!(head>=floor && head<=ceil)){
                        nvalSB.append(c);
                    }

                    if(j>currentNullifyProps[3] && i>=currentNullifyProps[2]){
                        toNullify.remove(0);
                        if(toNullify.size()==0) {
                            currentNullifyProps = null;
                            continue;
                        }
                        currentNullifyProps = toNullify.get(0);
                    }
                }
                textElement.setValue(nvalSB.toString());
            }
    }

    private WordprocessingMLPackage getTemplate(String name)
            throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = WordprocessingMLPackage
                .load(new FileInputStream(new File(name)));
        return template;
    }

    private static List<Object> getAllElementFromObject(Object obj,
                                                        Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement)
            obj = ((JAXBElement<?>) obj).getValue();

        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }

        }
        return result;
    }

    private void replacePlaceholder(WordprocessingMLPackage template,
                                    String name, String placeholder) {
        List<Object> texts = getAllElementFromObject(
                template.getMainDocumentPart(), Text.class);

        for (Object text : texts) {
            Text textElement = (Text) text;
            if (textElement.getValue().equals(placeholder)) {
                textElement.setValue(name);
            }
        }
    }

    private void writeDocxToStream(WordprocessingMLPackage template,
                                   String target) throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
    }
}
