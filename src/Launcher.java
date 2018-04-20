package WordCount;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @package WordCount
 * @date 2018/4/18
 */
public class Launcher {
    public static void main(String[] args) {
        //args参数 一次只传两个参数 传多个参数报错
        //-c file.txt 统计字符数
        //-w file.txt 统计单词数
        //-s file.txt 统计句子数
        //-d file.txt 统计代码行
        //-k file.txt 统计空行
        //-n file.txt 统计注释行
        Launcher test = new Launcher();
        test.checkValueAndStart(args);
//        File file = new File("test.txt");
//        test.countChars(file);
//        test.countWords(file);
//        test.countSentences(file);
    }

    //检验命令行参数合法性
    public boolean checkValueAndStart(String[] args) {
        if(args.length == 0)    {
            printHelpInfo();
            return false;
        }
        else if(args.length>2)
            System.out.println("输入的参数有误！");
        else {
            File file = new File(args[1]);
            if(!file.exists()) {
                System.out.println("文件不存在！");
                return false;
            }

            if(args[0].matches("-c"))  countChars(file);
            else if(args[0].matches("-w"))  countWords(file);
            else if(args[0].matches("-s"))  countSentences(file);
            else System.out.println("输入的参数有误！");
        }
        return true;
    }

    void countChars(File f) {
        try {
            Scanner sb = new Scanner(f,"GBK");
            int count = 0;

            while(sb.hasNext(Pattern.compile("[a-zA-z]+"))) {
                String s = sb.nextLine();
                if(hasChinese(s))
                    break;

                for(char c:s.toCharArray()) {
                    if(c != ' ') count++;
                }
            }

            sb.close();
            System.out.println("总字符数 = " + count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void countWords(File f) {
        try {
            Scanner sb = new Scanner(f,"GBK");
            int count = 0;

            while(sb.hasNext(Pattern.compile("[a-zA-z]+"))) {
                String s = sb.nextLine();
                if(hasChinese(s))
                    break;

                String[] words = s.split("[.?!\\s]");
                for(String st:words) {
                    if(!st.matches("\\s*")) count += 1;
                }
            }

            sb.close();
            System.out.println("总单词数 = " + count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void countSentences(File f) {
        try {
            Scanner sb = new Scanner(f,"GBK");
            int count = 0;

            while(sb.hasNext(Pattern.compile("[a-zA-z]+"))) {
                String s = sb.nextLine();
                if(hasChinese(s))
                    break;

                String[] sentences = s.split("[.?!]");
                for(String st:sentences) {
                    if(!st.matches("\\s*"))   count += 1;
                }
            }

            sb.close();
            System.out.println("句子数 = " + count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    boolean hasChinese(String s) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");//正则表达式匹配中文
        Matcher m;
        m = p.matcher(s);
        if(m.find()) {
            System.out.println("文件含有中文字符！");
            return true;
        }
        return false;
    }

    void printHelpInfo() {
        System.out.println("参数标准：");
        System.out.println("\t-c file.txt 统计字符数\n" +
                "\t-w file.txt 统计单词数\n" +
                "\t-s file.txt 统计句子数\n" +
                "\t-d file.txt 统计代码行\n" +
                "\t-k file.txt 统计空行\n" +
                "\t-n file.txt 统计注释行");
    }
}
