import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Blockchain blockchain = new Blockchain();
        System.out.print("Enter how many zeros the hash must start with: ");
        int zeros = scanner.nextInt();
        int count = 0;
        while (count < 5) {
            count++;
            blockchain.generateBlock(count, zeros);
        }
    }
}

//Factory for generating blocks
class Blockchain{
//private fields
    private String hashPrevious;

//public methods
//method to generate a block and print its attributes.
    public void generateBlock(int Id, int zeros) {
        if (Id == 1) {
            hashPrevious = "0";
        }
        Block block = new Block();
        block.setTimeStamp();
        block.setHashPrevious(hashPrevious);
        block.setId(Id);

        char[] zerosArray = new char[zeros];
        for (int i = 0; i < zeros; i++) {
            zerosArray[i] = '0';
        }
        String zerosString = new String(zerosArray);

        boolean status = false;

        long startTime = System.nanoTime();

        int magicNumber = 0;

        while (!status) {
            magicNumber = Math.abs(ThreadLocalRandom.current().nextInt());
            block.setHash(block.getId(), block.getTimeStamp(), block.getHashPrevious(), magicNumber);
            status = block.getHash().substring(0, zeros).equals(zerosString) ? true : false;
        }
        long endTime = System.nanoTime();
        block.setTimeToGenerate((endTime - startTime) / 1000000000);
        block.setMagicNumber(magicNumber);
        hashPrevious = block.getHash();
        printThisBlock(block);
    }

//method to print the attributes of a block
    public void printThisBlock(Block block) {
        System.out.println("\nBlock:");
        System.out.println("Id: " + block.getId());
        System.out.println("Timestamp: " + block.getTimeStamp());
        System.out.println("Magic number: " + block.getMagicNumber());
        System.out.println("Hash of the previous block:");
        System.out.println(block.getHashPrevious());
        System.out.println("Hash of the block:");
        System.out.println(block.getHash());
        System.out.println("Block was generating for " + block.getTimeToGenerate() + " seconds");
    }
}

//Block element class
class Block implements Serializable {
//private fields
    private int Id;
    private long timeStamp;
    private String hashPrevious = new String();
    private String hash = new String();
    private int magicNumber;
    private long timeToGenerate;

//Constructor
    public Block() {
        this.Id = Id;
        this.timeStamp = timeStamp;
        this.hashPrevious = hashPrevious;
        this.hash = hash;
    }

//Getters and Setters
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp() {
        this.timeStamp = new Date().getTime();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(int Id, long timeStamp, String hashPrevious, int magicNumber) {
        String IdString =
                this.hash = StringUtil.applySha256(Integer.toString(Id) + Long.toString(timeStamp) + hashPrevious + magicNumber);
    }

    public String getHashPrevious() {
        return hashPrevious;
    }

    public void setHashPrevious(String hashPrevious) {
        this.hashPrevious = hashPrevious;
    }

    public int getMagicNumber() {
        return this.magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public long getTimeToGenerate() {
        return this.timeToGenerate;
    }

    public void setTimeToGenerate(long timeToGenerate) {
        this.timeToGenerate = timeToGenerate;
    }
}

//////////SHA256 Hashing//////////
class StringUtil {
    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}