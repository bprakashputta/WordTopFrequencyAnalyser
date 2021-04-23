package word.topfrequencyanalyser;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReaderRunnable implements Runnable {

    private final String threadName;
    private String filePath; 
    HashMap<String, Integer> frequencyOfWords = new HashMap<String, Integer>();
    public FileReaderRunnable(String threadName, String filePath) {
        this.threadName = threadName;
        this.filePath = filePath;
    }

    public String getFilePath(){
        return this.filePath;
    }

    public void run() {

        File file = new File(filePath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNextLine())
        {
            String str = sc.next();
            String word = str.toLowerCase();
            if(frequencyOfWords.containsKey(word))
            {
                frequencyOfWords.put(word,frequencyOfWords.get(word)+1);
            }
            else
            {
                frequencyOfWords.put(word,1);
            }
        }

        frequencyOfWords = sortByValueJava8Stream(frequencyOfWords);

//        for(Map.Entry entry : frequencyOfWords.entrySet())
//        {
//            System.out.println(threadName+" : "+entry.getKey()+" : "+entry.getValue());
//        }
        String json = new Gson().toJson(frequencyOfWords);
        System.out.println(json);
        sc.close();
    }

    private static HashMap<String, Integer> sortByValueJava8Stream(HashMap<String, Integer> unSortedMap)
    {
//        Map<String, Integer> unSortedMap = getUnSortedMap();

//        System.out.println("Unsorted Map : " + unSortedMap);
//
//        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
//        unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
//                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

//        System.out.println("Sorted Map   : " + sortedMap);

        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

//        System.out.println("Reverse Sorted Map   : " + reverseSortedMap);
        return reverseSortedMap;
    }

//    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
//    {
//        // Create a list from elements of HashMap
//        List<Map.Entry<String, Integer> > list =
//                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
//
//        // Sort the list
//        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
//            public int compare(Map.Entry<String, Integer> o1,
//                               Map.Entry<String, Integer> o2)
//            {
//                return (o1.getValue()).compareTo(o2.getValue());
//            }
//        });
//
//        // put data from sorted list to hashmap
//        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
//        for (Map.Entry<String, Integer> aa : list) {
//            temp.put(aa.getKey(), aa.getValue());
//        }
//        return temp;
//    }

    public static void main(String[] args) {

        System.out.println("This is Bhanu Prakash, Running FIleUtility at "+ new Date().toString());
        String filePath = "/home/theharrypotter1999/IdeaProjects/JavaProjects/WordTopFrequencyAnalyser/data/practice/file/IndianNationalAnthem";
        FileReaderRunnable runnable1 = new FileReaderRunnable("Thread_A",filePath);

        Thread thread1 = new Thread(runnable1);
        thread1.start();
    }
}
