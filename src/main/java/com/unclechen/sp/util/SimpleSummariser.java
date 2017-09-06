package com.unclechen.sp.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;




public class SimpleSummariser{

    /**
     * 
    * @Title: summarise
    * @Description: 文章摘要实现
    * @param @param input
    * @param @param numSentences
    * @param @return    
    * @return String   
    * @throws
     */
    public static String summarise(String title,String input) {
    	input = input.replaceAll("\n","").replaceAll(" ","").replaceAll(" ","").replaceAll("    ","");
        String[] actualSentences = getSentences(input);
        System.out.println("total Sentences:"+actualSentences.length);
        int numSentences=actualSentences.length/10+(actualSentences.length%10>0?1:0);
        if(numSentences<3){
        	numSentences=3;
        }else if(numSentences>10){
        	numSentences=10;
        }
        System.out.println("summarise Sentences:"+numSentences);
        Set<String> mostFrequentWords = ComputeUtil.computeKeyWorkForNews(title, input, numSentences*numSentences);
        
        Set<String> outputSentences = new LinkedHashSet<String>();
        Iterator<String> it = mostFrequentWords.iterator();
        while (it.hasNext()) {
            String word = (String) it.next();
            for (int i = 0; i < actualSentences.length; i++) {
                if (actualSentences[i].indexOf(word) >= 0) {
                    outputSentences.add(actualSentences[i]);
                    break;
                }
                if (outputSentences.size() >= numSentences) {
                    break;
                }
            }
            if (outputSentences.size() >= numSentences) {
                break;
            }

        }

        List<String> reorderedOutputSentences = reorderSentences(outputSentences, input);

        StringBuffer result = new StringBuffer("");
        it = reorderedOutputSentences.iterator();
        while (it.hasNext()) {
            String sentence = (String) it.next();
            result.append(sentence);
            result.append("。"); // This isn't always correct - perhaps it should be whatever symbol the sentence finished with
            if (it.hasNext()) {
                result.append(" ");
            }
        }
        System.out.println("result:"+result);
        return result.toString();
    }
    
    /**
     * 
    * @Title: reorderSentences
    * @Description: 将句子按顺序输出
    * @param @param outputSentences
    * @param @param input
    * @param @return    
    * @return List<String>   
    * @throws
     */
    private static List<String> reorderSentences(Set<String> outputSentences, final String input) {
        // reorder the sentences to the order they were in the 
        // original text
        ArrayList<String> result = new ArrayList<String>(outputSentences);

        Collections.sort(result, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                String sentence1 = (String) arg0;
                String sentence2 = (String) arg1;

                int indexOfSentence1 = input.indexOf(sentence1.trim());
                int indexOfSentence2 = input.indexOf(sentence2.trim());
                int result = indexOfSentence1 - indexOfSentence2;

                return result;
            }

        });
        return result;
    }
    
    
    
    
    
    /**
     * 
    * @Title: getSentences
    * @Description: 把段落按. ! ?分隔成句组
    * @param @param input
    * @param @return    
    * @return String[]   
    * @throws
     */
    public static String[] getSentences(String input) {
        if (input == null) {
            return new String[0];
        } else {
            // split on a ".", a "!", a "?" followed by a space or EOL
        	//"(\\.|!|\\?)+(\\s|\\z)"
            return input.split("(\\.|\\!|\\?|。|？|！)");
        }

    }
    
    public static void main(String[] args){
    	String title="成新战场？ 聊聊备受关注的中级SUV市场";
    	String s="网易汽车7月28日报道  在紧凑级SUV和小型SUV获得市场认可后，中级SUV车型也终于迎来了自己的春天。究其原因主要是因为逐步走低的价格，以及7座车型可选，这都让国人对它的喜爱加倍。成新战场？ 聊聊备受关注的中级SUV市场其实市面上的中级SVU车型数量不少，不仅包括久经沙场的汉兰达和锐界，还有即将国产的本田冠道和大众中级SUV。所以在未来前景光明的情况下，中级SUV市场值得我们去关注一番。▲ 车企纷纷进入中级SUV市场从最早的紧凑SUV井喷，到后来的小型SUV成为“新宠”，以及现阶段的中级SUV爆发，其实看似持续“高温”的SUV市场，也在慢慢发生变化。成新战场？ 聊聊备受关注的中级SUV市场究其原因，主要时因为消费者的需求在慢慢发生改变。比如对7座车型的需求变大，以及对空间的要求更高，这些都导致了大家去考虑中级SUV。而洞察力敏锐的车企们，当然不会放弃这个潜力无限的细分市场，以至于中级SUV车型在这块“土壤”中如雨后春笋般生长。▲ 是什么原因让消费者关注起了中级SVU？① 价格集体跳水 更容易被接受其消费者关注中级SUV，除了用车需求更苛刻以外，价格的集体“跳水”也是促成中级SUV热卖的又一主要原因。成新战场？ 聊聊备受关注的中级SUV市场曾经动辄40万元的汉兰达和锐界，绝不是大多数家庭都能轻易接受的。但随着豪华品牌SUV价格逐渐下探，已经将中级SUV的生存空间越压越小。所以为了保证销量表现，厂家们只能下调价格，中级SUV的入门价格跌至20+万元，基本与紧凑级SUV存在重叠。\n" +
				"② 消费能力变强\n" +
				"成新战场？ 聊聊备受关注的中级SUV市场\n" +
				"中级SUV被接受的另一个原因，就是消费者的接受能力变强，原本在考虑购买紧凑SUV的情况下，一些消费者更愿意“垫脚”去考虑级别更高的中级SUV。\n" +
				"③ 更多车型加入 选择更多\n" +
				"其实中级SUV市场很早就一直存在，只是当时车型选择并不丰富。除了久经沙场的汉兰达外，上代楼兰的表现只能用惨淡来形容。\n" +
				"成新战场？ 聊聊备受关注的中级SUV市场\n" +
				"而如今已经有更多品牌进军这一细分市场，比如别克昂科威、本田冠道、福特锐界，以及重振旗鼓的日产楼兰，还有未来将要推出的大众中级SUV，会让这一市场热闹非凡。\n" +
				"▲ 中级SUV拿什么吸引了消费者？\n" +
				"① 硕大的车身 符合国人“大即为美”的审美\n" +
				"成新战场？ 聊聊备受关注的中级SUV市场\n" +
				"中级SUV之所以受欢迎，主要归结于以下几个原因，首先是硕大的车身尺寸更加符合国人“大即为美”的审美特点。所以对于那些心仪紧凑级SUV，又有充足预算的车友，中级SUV势必会对他们有很大的吸引力。\n" +
				"② 空间宽大舒适\n" +
				"成新战场？ 聊聊备受关注的中级SUV市场\n" +
				"由于中级SUV车身尺寸更大，一般这类车拥有不错的空间表现。尤其是纵向空间，后排同时坐三位乘客不会太过拥挤。同时正是这样的表现，很少有人对中级SUV的空间表现指指点点。\n" +
				"③ 许多车型都有7座版\n" +
				"成新战场？ 聊聊备受关注的中级SUV市场\n" +
				"随着二胎政策放宽，以及MPV车型的实用性被正视，这些都导致了7座车型开始受欢迎。所以车企们基本都会考虑为旗下中级SUV产品推出7座版本，来迎合消费者。\n" +
				"② 稳定的行驶品质\n" +
				"中级SUV宽大的车身会让它在行驶稳定性上表现更好，比如高速驾驶时中级SUV要比紧凑SUV更加轻松惬意，长时间驾驶不会太累。\n" +
				"成新战场？ 聊聊备受关注的中级SUV市场\n" +
				"也正是这个原因，让美国人热衷于驾驶大尺寸SUV，成为高速巡航驾驶的主要原因。比如丰田汉兰达和本田飞行员，在美国市场都是很抢手的巡航驾驶车辆。\n" +
				"总结：随着车友对于用车需求更苛刻，以及应对价格的接受能力更强，未来会更多人选择中级SUV。因为这类车相比紧凑级SUV优势太过明显，它不仅具有超大的车内空间，行驶品质上也要更出色，而且还提供国人喜爱的7座版本。所以在竞争逐步升级的大环境下，产品售价将会进一步走低，产品数量会更加丰富。";
    	System.out.println(summarise(title,s));
    }
}
