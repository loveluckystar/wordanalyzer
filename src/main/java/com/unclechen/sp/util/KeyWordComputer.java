package com.unclechen.sp.util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.util.FilterModifWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.util.StringUtil;

import java.util.*;

public class KeyWordComputer {
    private static final Map<String, Double> POS_SCORE = new HashMap();
    private int nKeyword = 5;

    public KeyWordComputer() {
    }

    public KeyWordComputer(int nKeyword) {
        this.nKeyword = nKeyword;
    }

    public List<Keyword> computeAsrticleTfidf(List<Term> parse,int contentLength,int titleLength){
        HashMap tm = new HashMap();
        parse = FilterModifWord.updateNature(parse, new Forest[0]);
        Iterator treeSet = parse.iterator();

        while(treeSet.hasNext()) {
            Term arrayList = (Term)treeSet.next();
            double weight = this.getWeight(arrayList, contentLength, titleLength);
            if(weight != 0.0D) {
                Keyword keyword = (Keyword)tm.get(arrayList.getName());
                if(keyword == null) {
                    keyword = new Keyword(arrayList.getName(), arrayList.natrue().allFrequency, weight);
                    tm.put(arrayList.getName(), keyword);
                } else {
                    keyword.updateWeight(1);
                }
            }
        }

        TreeSet treeSet1 = new TreeSet(tm.values());
        ArrayList arrayList1 = new ArrayList(treeSet1);
        if(treeSet1.size() <= this.nKeyword) {
            return arrayList1;
        } else {
            return arrayList1.subList(0, this.nKeyword);
        }

    }

    private List<Keyword> computeArticleTfidf(String content, int titleLength) {
        HashMap tm = new HashMap();
        List parse = NlpAnalysis.parse(content);
        parse = FilterModifWord.updateNature(parse, new Forest[0]);
        Iterator treeSet = parse.iterator();

        while(treeSet.hasNext()) {
            Term arrayList = (Term)treeSet.next();
            double weight = this.getWeight(arrayList, content.length(), titleLength);
            if(weight != 0.0D) {
                Keyword keyword = (Keyword)tm.get(arrayList.getName());
                if(keyword == null) {
                    keyword = new Keyword(arrayList.getName(), arrayList.natrue().allFrequency, weight);
                    tm.put(arrayList.getName(), keyword);
                } else {
                    keyword.updateWeight(1);
                }
            }
        }

        TreeSet treeSet1 = new TreeSet(tm.values());
        ArrayList arrayList1 = new ArrayList(treeSet1);
        if(treeSet1.size() <= this.nKeyword) {
            return arrayList1;
        } else {
            return arrayList1.subList(0, this.nKeyword);
        }
    }

    public List<Keyword> computeArticleTfidf(String title, String content) {
        if(StringUtil.isBlank(title)) {
            title = "";
        }

        if(StringUtil.isBlank(content)) {
            content = "";
        }

        return this.computeArticleTfidf(title + "\t" + content, title.length());
    }

    public List<Keyword> computeArticleTfidf(String content) {
        return this.computeArticleTfidf(content, 0);
    }

    private double getWeight(Term term, int length, int titleLength) {
        if(term.getName().trim().length() < 2) {
            return 0.0D;
        } else {
            String pos = term.natrue().natureStr;
            Double posScore = (Double)POS_SCORE.get(pos);
            if(posScore == null) {
                posScore = Double.valueOf(1.0D);
            } else if(posScore.doubleValue() == 0.0D) {
                return 0.0D;
            }

            return titleLength > term.getOffe()?5.0D * posScore.doubleValue():(double)(length - term.getOffe()) * posScore.doubleValue() / (double)length;
        }
    }

    static {
        POS_SCORE.put("null", Double.valueOf(0.0D));
        POS_SCORE.put("w", Double.valueOf(0.0D));
        POS_SCORE.put("en", Double.valueOf(0.0D));
        POS_SCORE.put("m", Double.valueOf(0.0D));
        POS_SCORE.put("num", Double.valueOf(0.0D));
        POS_SCORE.put("nr", Double.valueOf(3.0D));
        POS_SCORE.put("nrf", Double.valueOf(3.0D));
        POS_SCORE.put("nw", Double.valueOf(3.0D));
        POS_SCORE.put("nt", Double.valueOf(3.0D));
        POS_SCORE.put("l", Double.valueOf(0.2D));
        POS_SCORE.put("a", Double.valueOf(0.2D));
        POS_SCORE.put("nz", Double.valueOf(3.0D));
        POS_SCORE.put("v", Double.valueOf(0.2D));
        POS_SCORE.put("kw", Double.valueOf(6.0D));
    }
}
