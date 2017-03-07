package hello;

/**
 * Created by MEV on 2017/3/7.
 */
public class FeelUser {
    private Long id;
    private String name;
    private String address;
    private String mail;
    public FeelUser(Long id,String name,String address,String mail){
        this.address="haidian";
        this.id=1L;
        this.name="makenv";
        this.mail="aa@qq.com";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
