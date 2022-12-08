package Dictionary;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class DictionaryCalculation {
    private final String ALPHABETREPORT = "report-by-alphabet.txt";
    private final String ALPHABETREVREPORT = "report-by-alphabet-rev.txt";
    private final Map<String, Integer> dictionary = new HashMap<>();


    public Map <String, Integer> read(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String t;
                while ((t = reader.readLine()) != null) {
                    if (t.isEmpty()) continue;
                    StringBuilder builder = new StringBuilder();
                    char[] chars = t.toCharArray();
                    char charOb;
                    for (int i = 0; i < t.length(); i++) {
                        charOb = chars[i];
                        if (Character.UnicodeBlock.of(chars[i]).equals(Character.UnicodeBlock.CYRILLIC) || charOb == ('-')) {
                            builder.append(charOb);
                            if (i != t.length() - 1) continue;
                        }
                    }
                    if (!builder.toString().isEmpty()) {
                        if (dictionary.containsKey(builder.toString().toLowerCase(Locale.ROOT))) {
                            int val = dictionary.get(builder.toString().toLowerCase(Locale.ROOT));
                            dictionary.put(builder.toString().toLowerCase(Locale.ROOT), val + 1);
                            builder.delete(0, builder.length());
                            continue;
                        } else {
                            dictionary.put(builder.toString().toLowerCase(), 1);
                            builder.delete(0, builder.length());
                        }
                    }
                }
            }
        return dictionary;
    }

    public void save() throws IOException{
        try (PrintWriter writer = new PrintWriter(ALPHABETREPORT)){
            TreeMap<String, Integer> write = new TreeMap<>();
            write.putAll(dictionary);
            for (Map.Entry<String, Integer> kv : write.entrySet()) {
                write.forEach((k, v) -> writer.println(k + " относительная частота=" + v + "  абсолютная частота=" + (double) v / write.size()));
            }
        }

        try(PrintWriter writer = new PrintWriter(ALPHABETREVREPORT)) {
            TreeMap<String , Integer> sorted = new TreeMap<>();
            sorted.putAll(dictionary);
            sorted.entrySet().stream().sorted(Map.Entry.<String , Integer>comparingByValue().reversed())
                    .forEach(x -> writer.println(x.getKey() + "  относительная частота=" + x.getValue() + " абсолютная частота="
                            + (double)x.getValue()/dictionary.size()));
        }
    }
}