
//KIVANÇ ADIGÜZEL 191180003 FILE ORGANIZATION ASSIGNMENT1

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CENG307 {

    public static void main(String[] args) {
        BST1 bst = new BST1();//binary search tree
        REISCH1 reisch = new REISCH1();//REISCH
        long startTime;//process time hesaplama
        ArrayList<Integer> sourceArray = new ArrayList<>();//random olarak secilmis sayilar kumesi

        int MAXINDEX = reisch.getMAXINDEX();
        int i = 0;
        int count = 0;
        while (true) {
            i = reisch.getRandomNumber();
            if (!sourceArray.contains(Integer.valueOf(i))) {
                sourceArray.add(Integer.valueOf(i));
                if (++count >= MAXINDEX - MAXINDEX * 0.1)
                    //PACKING FACTOR 90 VERILMIS O YUZDEN 0.1 KESTIM
                    // 100 -(100*0.1)= 90---->PACKING FACTOR
                    break;
            }
        }

        for (i = 0; i < sourceArray.size(); i++) {
            bst.insert(sourceArray.get(i).intValue());
            reisch.insertRec(sourceArray.get(i).intValue());
        }

        sourceArray.sort(null);
        for (i = 0; i < sourceArray.size(); i++) {
            //if (sourceArray.get(i) != null)
              //  System.out.print(sourceArray.get(i).intValue() + " ");
        }

        //SCREEN
        do {
            System.out.println();
            Scanner in = new Scanner(System.in);
            System.out.print("Enter number to search, or -1 to exit :");
            int a = in.nextInt();
            if (a == -1)
                break;

            System.out.println();
            startTime = System.nanoTime();
            System.out.println("BST ["+ a + "]" + ": " + (bst.search(a) >= 0 ? "BULUNDU---> " : "BULUNAMADI ")
                    + (System.nanoTime() - startTime) + " millisecond" + ", counter(SAYAC) =" + bst.getCount());
            startTime = System.nanoTime();
            System.out.println("REISCH ["+ a + "]" + ": " + (reisch.search(a) >= 0 ? "BULUNDU---> " : "BULUNAMADI ")
                    + (System.nanoTime() - startTime) + " millisecond" + ", counter(SAYAC) =" + reisch.getCount());
           //BULUNAMADIĞI SENARYODA DA SAYACI GÖSTERMEYİ VE NE KADAR SÜREDE BULUNAMADIĞINI GÖSTERMEYİ HEDEFLEDİM
            //DOLAYISIYLA BİLİNÇLİ BİR ŞEKİLDE KALDIRMADIM
            if((bst.getCount()> reisch.getCount())&&(bst.search(a)==1 || reisch.search(a)==1))
                System.out.println("REISCH DAHA AZ HAMLEYLE BULDU ARADAKI FARK "+(bst.getCount() - reisch.getCount()));
            else if ((bst.getCount()<reisch.getCount()&&(bst.search(a)==1 || reisch.search(a)==1)))
                //System.out.println("REISCH ILE BST AYNI SAYIDA HAMLEYLE BULDU");
                System.out.println("BST DAHA AZ HAMLEYLE BULDU ARADAKI FARK "+(reisch.getCount() - bst.getCount()));
            else if((bst.getCount()==reisch.getCount()&&(bst.search(a)==1 || reisch.search(a)==1)))
                System.out.println("REISCH ILE BST AYNI SAYIDA HAMLEYLE BULDU");
            else
                System.out.println("BULUNAMADIII");

        } while (true);
    }

}

class BST1 {
    private int count = 0;

    //RC VE LC VE MEVCUDU ICERMELI
    private class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    // ROOT
    private Node root;

    // CONST
    BST1() {
        root = null;
    }

    BST1(int value) {
        root = new Node(value);
    }

    public int getCount() {
        return count;
    }

    // insertRec()
    public void insert(int key) {
        root = insertRec(root, key);
    }

    //RECURSIVE FONKS AGACA EKLEME YAPACAK
    private Node insertRec(Node root, int key) {


        //  AGAC BOSSA YENI NODA DONECEK

        if (root == null) {
            root = new Node(key);
            return root;
        }

        /* Otherwise, recur down the tree */
        else if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);

        // DEGISMEYEN NODE POINTERI
        return root;
    }

    // InorderRec() CAGRILACAK
    public void inorder() {
        inorderRec(root);
    }


    // BST INORDER TRAVERSAL
    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            // System.out.println(root.key);
            inorderRec(root.right);
        }
    }

    //
    public int search(int key) {
        int result = 0;
        count = 0;
        if (root != null) {
            if (root.key == key) {
                // System.out.println("found");
                return 1;
            } else {
                result = search(key, root);
                return result;
            }
        }
        return result;
    }

    private int search(int key, Node root) {

        count++;
        if (root != null) {
            if (root.key == key) {
                // System.out.println("found");
                return 1;
            }
            if (key < root.key)
                if (search(key, root.left) == 1)
                    return 1;
            if (key > root.key)
                if (search(key, root.right) == 1)
                    return 1;
        }
        return -1;
    }
}

//REISCH
class REISCH1 {
    private int count = 0;
    private final int MAXINDEX = 1000;
    //900 RANDOM KEY OLACAĞI ICIN MAXINDEXI 1000 VERDIM
    // KUCUK DEGERLER UZERINDE DAHA RAHAT GOZLEMLEYEBILMEK AMACLI
    //TERCIHE GORE MAXINDEX GUNCELLENEBILIR
    private final int MOD = 11;

    private Node source[] = new Node[MAXINDEX];
    private Random rand = new Random();

    private class Node {
        int key;
        int link;

        public Node(int item) {
            key = item;
            link = -1;
        }
    }

    public int getRandomNumber() {
        return rand.nextInt(MAXINDEX);
    }

    public int getMAXINDEX() {
        return MAXINDEX;
    }
    public int getCount() {
        return count;
    }
    public void insertRec(int key) {
        int modX = key % MOD;

        if (source[modX] == null)
            source[modX] = new Node(key);
        else if (source[modX].link == -1)
            source[modX].link = insertEmpty(key);
        else
            insertRec(key, source[modX].link);
    }

    private void insertRec(int key, int index) {
        int i = 0;
        if (source[index].link == -1) {
            i = insertEmpty(key);
            source[index].link = i;
        } else {
            insertRec(key, source[index].link);
        }
    }

    private int insertEmpty(int key) {

        int i = 0;
        while (true) {
            i = getRandomNumber();
            if (i >= MOD && source[i] == null) {
                source[i] = new Node(key);
                break;
            }
        }

        return i;
    }

    //
    public int search(int key) {
        count = 0;
        return search(key, -1);
    }

    //indeX ve index bilinçli kullandım
    private int search(int key, int index) {
        int indeX = index;
        int result = -2;

        if (indeX == -1)
            indeX = key % MOD;
        else if (indeX == -2)
            return -2;

        count++;
        if (source[indeX] != null) {
            if (source[indeX].key == key)
                return indeX;
            else if (source[indeX].link == -1)
                return -2;
            else {
                result = search(key, source[indeX].link);
                if (result >= 0)
                    return result;
                else
                    return -2;
            }
        } else
            return -1;
    }
}
