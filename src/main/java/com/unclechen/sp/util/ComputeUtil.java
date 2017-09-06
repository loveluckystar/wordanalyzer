package com.unclechen.sp.util;

import com.alibaba.fastjson.JSONObject;
import com.unclechen.sp.Constant;
import com.unclechen.sp.controller.ComputeController;

import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.ansj.recognition.NatureRecognition;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.util.FilterModifWord;
import org.nlpcn.commons.lang.util.StringUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 计算工具类
public class ComputeUtil {


	public static Map<String,Double> computeTypeForNews(String title,String content){
//
//		return pwdstr!=null&&username!=null?MD5(username+"spadmin4ever"+pwdstr):null;
        FilterModifWord.insertStopNatures("r") ;
        FilterModifWord.insertStopNatures("v") ;
        FilterModifWord.insertStopNatures("c") ;
        FilterModifWord.insertStopNatures("u") ;
        FilterModifWord.insertStopNatures("d") ;
        content = content.replaceAll("\n","").replaceAll(" ","").replaceAll(" ","").replaceAll("    ","");
        List<Term> parse = NlpAnalysis.parse(content) ;
        System.out.println(parse);
        new NatureRecognition(parse).recognition() ;
        KeyWordComputer kwc = new KeyWordComputer(10);
        List<Keyword> result = kwc.computeAsrticleTfidf(FilterModifWord.modifResult(parse), content.length(), title.length());
        double[] counterArray = new double[ComputeController.typeArray.length];
        Map<String,Double> resultMap = new HashMap<String,Double>();
        for(Keyword keyword:result){
            for(int i=0;i<ComputeController.typeArray.length;i++){
                String type = ComputeController.typeArray[i];
                String probablility = RedisManager.hgetString(Constant.PROBABLILY_KEYWORD_PRE + ":" + type, keyword.getName());
                if(StringUtil.isBlank(probablility)){
                    probablility = "0";
                }
                counterArray[i] = counterArray[i] + Double.parseDouble(probablility)*keyword.getScore();
            }
        }
        for(int k=0;k<counterArray.length;k++){
        	resultMap.put(ComputeController.typeArray[k], counterArray[k]);
        }
        return resultMap;
	}
	
	public static Set<String> computeKeyWorkForNews(String title,String content,int num){
		        FilterModifWord.insertStopNatures("r") ;
		        FilterModifWord.insertStopNatures("v") ;
		        FilterModifWord.insertStopNatures("c") ;
		        FilterModifWord.insertStopNatures("u") ;
		        FilterModifWord.insertStopNatures("d") ;
		        content = content.replaceAll("\n","").replaceAll(" ","").replaceAll(" ","").replaceAll("    ","");
		        List<Term> parse = NlpAnalysis.parse(content) ;
		        new NatureRecognition(parse).recognition() ;
		        KeyWordComputer kwc = new KeyWordComputer(num);
		        List<Keyword> result = kwc.computeAsrticleTfidf(FilterModifWord.modifResult(parse), content.length(), title.length());
		        Set<String> resultSet=new HashSet<String>();
		        for(Keyword k:result){
		        	resultSet.add(k.getName());
		        }
		        return resultSet;
			}
}
