package dto;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client {

    private String Name;

    private String Phone;
    private Integer NumMember;

    private List<Borrow> borrowing;

    public Client(){}
    public Client(String Name, String Phone ) {
        this.Name = Name;
        this.Phone = Phone;
        this.NumMember = Integer.parseInt(UniqueCodeGenerator(3));
        borrowing = new ArrayList<>();
    }


    //
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
    //
    public Integer getNumMember() {
        return NumMember;
    }

    public void setNumMember(Integer NumMember) {
        this.NumMember = NumMember;
    }


    public static String UniqueCodeGenerator(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }

        return code.toString();
    }

}
