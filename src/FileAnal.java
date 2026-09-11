import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileAnal {
    public static List<List<Integer>> order = new ArrayList<>();
    public static boolean works=false;
    FileAnal() {
        works=isFileValid();
    }

    public boolean isFileValid() {
        File file = new File("commands");
        if (!file.exists() || !file.isFile()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int cell=0;
            int temp=0;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                line=line.toLowerCase();
                String[] parts = line.split("\\.");
                order.add(new ArrayList<>());
                
                if (parts[0].equals("mouse")){
                    order.get(cell).add(1);
                }else if (parts[0].equals("keyb")){
                    order.get(cell).add(2);
                }else if (parts[0].equals("delay")){
                    order.get(cell).add(3);
                    if (parts[1].equals("big")){
                        order.get(cell).add(-1);
                    }else if ((temp=Integer.parseInt(parts[1]))>0){
                        order.get(cell).add(temp);
                    }else return false;
                    cell++;
                    continue;
                }else return false;

                if (parts[1].equals("hold")){
                    order.get(cell).add(1);
                }else if (parts[1].equals("relese")){
                    order.get(cell).add(2);
                }else if (parts[1].equals("press")){
                    order.get(cell).add(3);
                }else if (parts[1].equals("type")){
                    order.get(cell).add(4);
                    for(int i:typing.translate_list(parts[2])){
                        order.get(cell).add(i);
                    }
                    cell++;
                    continue;
                }else if (parts[1].equals("move")){
                    order.get(cell).add(4);
                    String[] move =parts[2].split("\\,");
                    if (move==null||move.length!=2)return false;
                    order.get(cell).add(Integer.parseInt(move[0]));
                    order.get(cell).add(Integer.parseInt(move[1]));
                    cell++;
                    continue;
                }else return false;

                int isnumber=0;
                if (parts[2].equals("rclick")){
                    order.get(cell).add(4096);
                }else if (parts[2].equals("lclick")){
                    order.get(cell).add(1024);
                }else if (parts[2].equals("mclick")){
                    order.get(cell).add(2048);
                }else if ((isnumber=Integer.parseInt(parts[2]))>0){
                    order.get(cell).add(isnumber);
                }else return false;
                cell++;
            }
            return true;
        } catch (IOException e) {
            return false;
        } catch (NumberFormatException e){
            return false;
        } catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }


    
}