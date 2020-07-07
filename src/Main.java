import java.security.MessageDigest;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello World!");

        Blockchain blockchain = new Blockchain();

        blockchain.generateBlock(1);
        blockchain.generateBlock(2);
        blockchain.generateBlock(3);
        blockchain.generateBlock(4);
        blockchain.generateBlock(5);

        if (blockchain.validateBlockchain()) {
            blockchain.printBlockchain();
        }
    }
}

class Blockchain{

    private ArrayList<Block> chain = new ArrayList();
    private String hashPrevious;

    public void generateBlock(int Id) {
        if (Id == 1) {
            hashPrevious = "0";
        }
        Block block = new Block();
        block.setTimeStamp();
        block.setHashPrevious(hashPrevious);
        block.setId(Id);
        block.setHash(block.getId(), block.getTimeStamp(), block.getHashPrevious());
        chain.add(block);
        hashPrevious = block.getHash();
    }

    public boolean validateBlockchain() {
        boolean valid = false;
        for (int i = 0; i < chain.size() - 1; i++) {
            if (chain.get(i).getHash() == chain.get(i+1).getHashPrevious()) {
                valid = true;
            }
            else {
                valid = false;
            }
        }
        return valid;
    }

    public void printBlockchain() {
        for (int i = 0; i < chain.size(); i++) {
            System.out.println("Block:");
            System.out.println("Id: " + chain.get(i).getId());
            System.out.println("Timestamp: " + chain.get(i).getTimeStamp());
            System.out.println("Hash of the previous block:");
            System.out.println(chain.get(i).getHashPrevious());
            System.out.println("Hash of the block:");
            System.out.println(chain.get(i).getHash() + "\n");
        }
    }
}

class Block{
    private int Id;
    private long timeStamp;
    private String hashPrevious = new String();
    private String hash = new String();

    public Block() {
        this.Id = Id;
        this.timeStamp = timeStamp;
        this.hashPrevious = hashPrevious;
        this.hash = hash;
    }

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

    public void setHash(int Id, long timeStamp, String hashPrevious) {
        String IdString =
                this.hash = StringUtil.applySha256(Integer.toString(Id) + Long.toString(timeStamp) + hashPrevious);
    }

    public String getHashPrevious() {
        return hashPrevious;
    }

    public void setHashPrevious(String hashPrevious) {
        this.hashPrevious = hashPrevious;
    }
}





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